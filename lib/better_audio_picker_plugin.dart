import 'dart:convert';

import 'package:better_audio_picker_plugin/better_audio_picker_plugin_api.dart';

class BetterAudioPickerPlugin {
  static int _firstInstanceId = 1;

  final int instanceId = _firstInstanceId++;

  late Stream<List<BetterAudioPickerPluginAudioModel>> scanResultStream;

  late Stream<String?> pickResultStream;

  BetterAudioPickerPlugin() {
    scanResultStream = BetterAudioPickerPluginApi.scanResultStream.where((event) {
      if (event is Map) {
        final instanceId = int.tryParse(event["instanceId"]?.toString() ?? "") ?? -1;
        return instanceId == this.instanceId;
      }
      return false;
    }).map<List<BetterAudioPickerPluginAudioModel>>((event) {
      return List<Map>.from(jsonDecode(event["data"].toString())).map<BetterAudioPickerPluginAudioModel>((e) {
        return BetterAudioPickerPluginAudioModel(e['name'], e['uri'], e['date'], e['duration'], e['size']);
      }).toList();
    });
    pickResultStream = BetterAudioPickerPluginApi.pickResultStream.where((event) {
      if (event is Map) {
        final instanceId = int.tryParse(event["instanceId"]?.toString() ?? "") ?? -1;
        return instanceId == this.instanceId;
      }
      return false;
    }).map<String?>((event) {
      if (event["error"] != null) {
        print("${event["error"]}");
      }
      return event["data"]?.toString();
    });
  }

  /// 搜索音频文件
  Future<void> scanAudio() async {
    return BetterAudioPickerPluginApi.scanAudio(instanceId: instanceId);
  }

  /// 选择音频
  Future<void> pickAudio({required String uri, required String path}) async {
    return BetterAudioPickerPluginApi.pickAudio(instanceId: instanceId, uri: uri, path: path);
  }
}

class BetterAudioPickerPluginAudioModel {
  late String name;
  late String uri;
  late int date;
  late int duration;
  late int size;

  BetterAudioPickerPluginAudioModel(this.name, this.uri, this.date, this.duration, this.size);

  @override
  String toString() {
    return 'BetterAudioPickerPluginAudioModel{name: $name, uri: $uri, date: $date, duration: $duration, size: $size}';
  }
}
