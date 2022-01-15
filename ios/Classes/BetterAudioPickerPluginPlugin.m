//
//  BetterAudioPickerPluginPlugin.m
//  Pods
//
//  Created by 汪洋 on 2022/1/15.
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

- (void)pickAudioWithInstanceId:(NSInteger)instanceId uri:(NSString *)uri path:(NSString *)path {
    // TODO
    
}

@end
