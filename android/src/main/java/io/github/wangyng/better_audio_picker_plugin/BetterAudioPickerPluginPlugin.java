package io.github.wangyng.better_audio_picker_plugin;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;

public class BetterAudioPickerPluginPlugin implements FlutterPlugin, BetterAudioPickerPluginApi {

    BetterAudioPickerPluginEventSink scanResultStream;

    BetterAudioPickerPluginEventSink pickResultStream;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        BetterAudioPickerPluginApi.setup(binding, this, binding.getApplicationContext());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        BetterAudioPickerPluginApi.setup(binding, null, null);
    }

    @Override
    public void setScanResultStream(Context context, BetterAudioPickerPluginEventSink scanResultStream) {
        this.scanResultStream = scanResultStream;
    }

    @Override
    public void setPickResultStream(Context context, BetterAudioPickerPluginEventSink pickResultStream) {
        this.pickResultStream = pickResultStream;
    }

    @Override
    public void scanAudio(Context context, int instanceId) {
        new Thread(() -> {
            context.getContentResolver().notifyChange(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null);
            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
            List<AudioModel> audioList = new ArrayList<>();

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME));
                    Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_MODIFIED));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE));

                    AudioModel audioModel = new AudioModel();
                    audioModel.name = name;
                    audioModel.uri = uri.toString();
                    audioModel.date = date;
                    audioModel.duration = duration;
                    audioModel.size = size;

                    audioList.add(audioModel);
                }
                cursor.close();
            }

            // ????????????????????????????????????
            List<String> pathList = new ArrayList<>();
            for (int i = audioList.size() - 1; i >= 0; i--) {
                AudioModel audio = audioList.get(i);
                if (audio.size < 1024) { // ????????????1k?????????
                    audioList.remove(audio);
                } else if (audio.duration < 100) { // ????????????0.1????????????
                    audioList.remove(audio);
                } else if (pathList.contains(audio.uri)) {
                    audioList.remove(audio);
                } else {
                    pathList.add(audio.uri);
                }
            }

            // ???????????????
            Collections.sort(audioList, (o1, o2) -> Long.compare(o2.date, o1.date));

            // ?????????Flutter
            new Handler(Looper.getMainLooper()).post(() -> {
                JSONArray audioListJson = new JSONArray();
                for (AudioModel audioModel : audioList) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", audioModel.name);
                        jsonObject.put("uri", audioModel.uri);
                        jsonObject.put("date", audioModel.date);
                        jsonObject.put("duration", audioModel.duration);
                        jsonObject.put("size", audioModel.size);
                        audioListJson.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Map<String, Object> data = new HashMap<>();
                data.put("instanceId", instanceId);
                data.put("data", audioListJson.toString());
                scanResultStream.event.success(data);
            });
        }).start();
    }

    @Override
    public void pickAudio(Context context, int instanceId, String uri, String path) {

        // ?????? uri ??? path
        new Thread(() -> {
            ContentResolver contentResolver = context.getContentResolver();

            // ????????????
            File tempFile = new File(path);
            if (tempFile.exists()) {
                tempFile.deleteOnExit();
            }

            FileOutputStream fileOutputStream = null;
            FileInputStream reader = null;

            try {
                fileOutputStream = new FileOutputStream(tempFile);

                ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(Uri.parse(uri), "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                // ???????????????????????????
                byte[] chunk = new byte[4096];
                int bytesRead;
                reader = new FileInputStream(fileDescriptor);

                while ((bytesRead = reader.read(chunk)) > 0) {
                    fileOutputStream.write(chunk, 0, bytesRead);
                }

                // ?????????Flutter
                new Handler(Looper.getMainLooper()).post(() -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("instanceId", instanceId);
                    data.put("data", tempFile.getAbsolutePath());
                    pickResultStream.event.success(data);
                });

            } catch (IOException e) {
                // ?????????Flutter
                new Handler(Looper.getMainLooper()).post(() -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("instanceId", instanceId);
                    data.put("error", e.getLocalizedMessage());
                    pickResultStream.event.success(data);
                });
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class AudioModel {
        public String name;
        public String uri;
        public long date;
        public long duration;
        public long size;
    }

}
