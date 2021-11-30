//
//  BetterAudioPickerPluginApi.h
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import "BetterAudioPickerPluginEventSink.h"

@protocol BetterAudioPickerPluginApiDelegate <NSObject>

- (void)setScanResultStream:(BetterAudioPickerPluginEventSink *)scanResultStream;

- (void)setPickResultStream:(BetterAudioPickerPluginEventSink *)pickResultStream;

- (void)scanAudioWithInstanceId:(NSInteger)instanceId;

- (void)pickAudioWithInstanceId:(NSInteger)instanceId uri:(NSString *)uri;

@end

@interface BetterAudioPickerPluginApi : NSObject

+ (void)setup:(NSObject<FlutterPluginRegistrar> *)registrar api:(id<BetterAudioPickerPluginApiDelegate>)api;

@end

