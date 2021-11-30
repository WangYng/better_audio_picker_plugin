# better_audio_picker_plugin

A new flutter plugin project.

## Install Started

1. Add this to your **pubspec.yaml** file:

```yaml
dependencies:
  better_audio_picker_plugin: ^0.0.1
```

2. Install it

```bash
$ flutter packages get
```

## Normal usage

```dart
scanResultStreamSubscription = audioPickerPlugin.scanResultStream.listen((event) {
  print("音频搜索结果：$event");
  setState(() {
    audioList = event;
  });
});
audioPickerPlugin.scanAudio()
```

```dart
pickResultStreamSubscription = audioPickerPlugin.pickResultStream.listen((event) {
  print("音频保存路径：$event");
});
audioPickerPlugin.pickAudio(uri: audio.uri);
```

## Feature
- [x] pick audio (only Android)
