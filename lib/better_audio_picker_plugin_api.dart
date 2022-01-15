import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class BetterAudioPickerPluginApi {

  static Stream scanResultStream = EventChannel("io.github.wangyng.better_audio_picker_plugin/scanResultStream").receiveBroadcastStream();

  static Stream pickResultStream = EventChannel("io.github.wangyng.better_audio_picker_plugin/pickResultStream").receiveBroadcastStream();

  static Future<void> scanAudio({required int instanceId}) async {
    const channel = BasicMessageChannel<dynamic>('io.github.wangyng.better_audio_picker_plugin.scanAudio', StandardMessageCodec());

    final Map<String, dynamic> requestMap = {};
    requestMap["instanceId"] = instanceId;
    final reply = await channel.send(requestMap);

    if (!(reply is Map)) {
      _throwChannelException();
    }

    final replyMap = Map<String, dynamic>.from(reply);
    if (replyMap['error'] != null) {
      final error = Map<String, dynamic>.from(replyMap['error']);
      _throwException(error);
      
    } else {
      // noop
    }
  }

  static Future<void> pickAudio({required int instanceId, required String uri, required String path}) async {
    const channel = BasicMessageChannel<dynamic>('io.github.wangyng.better_audio_picker_plugin.pickAudio', StandardMessageCodec());

    final Map<String, dynamic> requestMap = {};
    requestMap["instanceId"] = instanceId;
    requestMap["uri"] = uri;
    requestMap["path"] = path;
    final reply = await channel.send(requestMap);

    if (!(reply is Map)) {
      _throwChannelException();
    }

    final replyMap = Map<String, dynamic>.from(reply);
    if (replyMap['error'] != null) {
      final error = Map<String, dynamic>.from(replyMap['error']);
      _throwException(error);
      
    } else {
      // noop
    }
  }

}

_throwChannelException() {
  throw PlatformException(code: 'channel-error', message: 'Unable to establish connection on channel.', details: null);
}

_throwException(Map<String, dynamic> error) {
  throw PlatformException(code: "${error['code']}", message: "${error['message']}", details: "${error['details']}");
}
