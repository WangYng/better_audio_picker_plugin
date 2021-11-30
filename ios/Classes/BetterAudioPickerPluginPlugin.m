//
//  BetterAudioPickerPluginPlugin.m
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import "BetterAudioPickerPluginPlugin.h"
#import "BetterAudioPickerPluginEventSink.h"

@implementation BetterAudioPickerPluginPlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    BetterAudioPickerPluginPlugin* instance = [[BetterAudioPickerPluginPlugin alloc] init];
    [BetterAudioPickerPluginApi setup:registrar api:instance];
}

- (void)setScanResultStream:(BetterAudioPickerPluginEventSink *)scanResultStream {
    // TODO
}

- (void)setPickResultStream:(BetterAudioPickerPluginEventSink *)pickResultStream {
    // TODO
}

- (void)scanAudioWithInstanceId:(NSInteger)instanceId {
    // TODO
    
}

- (void)pickAudioWithInstanceId:(NSInteger)instanceId uri:(NSString *)uri {
    // TODO
    
}

@end
