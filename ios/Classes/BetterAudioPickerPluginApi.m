//
//  BetterAudioPickerPluginApi.m
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import "BetterAudioPickerPluginApi.h"

@implementation BetterAudioPickerPluginApi

+ (void)setup:(NSObject<FlutterPluginRegistrar> *)registrar api:(id<BetterAudioPickerPluginApiDelegate>)api {
    NSObject<FlutterBinaryMessenger> *messenger = [registrar messenger];

    {
        FlutterEventChannel *eventChannel = [FlutterEventChannel eventChannelWithName:@"io.github.wangyng.better_audio_picker_plugin/scanResultStream" binaryMessenger:messenger];
        BetterAudioPickerPluginEventSink *eventSink = [[BetterAudioPickerPluginEventSink alloc] init];
        if (api != nil) {
            [eventChannel setStreamHandler:eventSink];
            [api setScanResultStream:eventSink];
        }
    }

    {
        FlutterEventChannel *eventChannel = [FlutterEventChannel eventChannelWithName:@"io.github.wangyng.better_audio_picker_plugin/pickResultStream" binaryMessenger:messenger];
        BetterAudioPickerPluginEventSink *eventSink = [[BetterAudioPickerPluginEventSink alloc] init];
        if (api != nil) {
            [eventChannel setStreamHandler:eventSink];
            [api setPickResultStream:eventSink];
        }
    }

    {
        FlutterBasicMessageChannel *channel =[FlutterBasicMessageChannel messageChannelWithName:@"io.github.wangyng.better_audio_picker_plugin.scanAudio" binaryMessenger:messenger];
        if (api != nil) {
            [channel setMessageHandler:^(id  message, FlutterReply reply) {
                NSMutableDictionary<NSString *, NSObject *> *wrapped = [NSMutableDictionary new];
                if ([message isKindOfClass:[NSDictionary class]]) {
                    NSDictionary *params = message;
                    NSInteger instanceId = [params[@"instanceId"] integerValue];
                    [api scanAudioWithInstanceId:instanceId];
                    wrapped[@"result"] = nil;
                } else {
                    wrapped[@"error"] = @{@"message": @"parse message error"};
                }
                reply(wrapped);
            }];
        } else {
            [channel setMessageHandler:nil];
        }
    }

    {
        FlutterBasicMessageChannel *channel =[FlutterBasicMessageChannel messageChannelWithName:@"io.github.wangyng.better_audio_picker_plugin.pickAudio" binaryMessenger:messenger];
        if (api != nil) {
            [channel setMessageHandler:^(id  message, FlutterReply reply) {
                NSMutableDictionary<NSString *, NSObject *> *wrapped = [NSMutableDictionary new];
                if ([message isKindOfClass:[NSDictionary class]]) {
                    NSDictionary *params = message;
                    NSInteger instanceId = [params[@"instanceId"] integerValue];
                    NSString *uri = params[@"uri"];
                    [api pickAudioWithInstanceId:instanceId uri:uri];
                    wrapped[@"result"] = nil;
                } else {
                    wrapped[@"error"] = @{@"message": @"parse message error"};
                }
                reply(wrapped);
            }];
        } else {
            [channel setMessageHandler:nil];
        }
    }

}

@end
