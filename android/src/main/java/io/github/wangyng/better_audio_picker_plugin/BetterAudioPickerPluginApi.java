package io.github.wangyng.better_audio_picker_plugin;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.StandardMessageCodec;

public interface BetterAudioPickerPluginApi {

    void setScanResultStream(Context context, BetterAudioPickerPluginEventSink scanResultStream);

    void setPickResultStream(Context context, BetterAudioPickerPluginEventSink pickResultStream);

    void scanAudio(Context context, int instanceId);

    void pickAudio(Context context, int instanceId, String uri);

    static void setup(FlutterPlugin.FlutterPluginBinding binding, BetterAudioPickerPluginApi api, Context context) {
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();

        {
            EventChannel eventChannel = new EventChannel(binaryMessenger, "io.github.wangyng.better_audio_picker_plugin/scanResultStream");
            BetterAudioPickerPluginEventSink eventSink = new BetterAudioPickerPluginEventSink();
            if (api != null) {
                eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        eventSink.event = events;
                    }

                    @Override
                    public void onCancel(Object arguments) {
                        eventSink.event = null;
                    }
                });
                api.setScanResultStream(context, eventSink);
            } else {
                eventChannel.setStreamHandler(null);
            }
        }

        {
            EventChannel eventChannel = new EventChannel(binaryMessenger, "io.github.wangyng.better_audio_picker_plugin/pickResultStream");
            BetterAudioPickerPluginEventSink eventSink = new BetterAudioPickerPluginEventSink();
            if (api != null) {
                eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        eventSink.event = events;
                    }

                    @Override
                    public void onCancel(Object arguments) {
                        eventSink.event = null;
                    }
                });
                api.setPickResultStream(context, eventSink);
            } else {
                eventChannel.setStreamHandler(null);
            }
        }

        {
            BasicMessageChannel<Object> channel = new BasicMessageChannel<>(binaryMessenger, "io.github.wangyng.better_audio_picker_plugin.scanAudio", new StandardMessageCodec());
            if (api != null) {
                channel.setMessageHandler((message, reply) -> {
                    Map<String, Object> wrapped = new HashMap<>();
                    try {
                        HashMap<String, Object> params = (HashMap<String, Object>) message;
                        int instanceId = (int)params.get("instanceId");
                        api.scanAudio(context, instanceId);
                        wrapped.put("result", null);
                    } catch (Exception exception) {
                        wrapped.put("error", wrapError(exception));
                    }
                    reply.reply(wrapped);
                });
            } else {
                channel.setMessageHandler(null);
            }
        }

        {
            BasicMessageChannel<Object> channel = new BasicMessageChannel<>(binaryMessenger, "io.github.wangyng.better_audio_picker_plugin.pickAudio", new StandardMessageCodec());
            if (api != null) {
                channel.setMessageHandler((message, reply) -> {
                    Map<String, Object> wrapped = new HashMap<>();
                    try {
                        HashMap<String, Object> params = (HashMap<String, Object>) message;
                        int instanceId = (int)params.get("instanceId");
                        String uri = (String)params.get("uri");
                        api.pickAudio(context, instanceId, uri);
                        wrapped.put("result", null);
                    } catch (Exception exception) {
                        wrapped.put("error", wrapError(exception));
                    }
                    reply.reply(wrapped);
                });
            } else {
                channel.setMessageHandler(null);
            }
        }

   }

    static HashMap<String, Object> wrapError(Exception exception) {
        HashMap<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", exception.toString());
        errorMap.put("code", null);
        errorMap.put("details", null);
        return errorMap;
    }
}
