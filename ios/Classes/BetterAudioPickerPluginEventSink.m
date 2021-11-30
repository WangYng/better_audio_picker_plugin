//
//  BetterAudioPickerPluginEventSink.m
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import "BetterAudioPickerPluginEventSink.h"

@implementation BetterAudioPickerPluginEventSink

- (FlutterError * _Nullable)onCancelWithArguments:(id _Nullable)arguments {
    self.event = NULL;
    return nil;
}

- (FlutterError * _Nullable)onListenWithArguments:(id _Nullable)arguments eventSink:(nonnull FlutterEventSink)events {
    self.event = events;
    return nil;
}

@end
