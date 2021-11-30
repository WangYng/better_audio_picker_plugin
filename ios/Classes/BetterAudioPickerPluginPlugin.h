//
//  BetterAudioPickerPluginPlugin.h
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import <Flutter/Flutter.h>
#import "BetterAudioPickerPluginApi.h"

@interface BetterAudioPickerPluginPlugin : NSObject<BetterAudioPickerPluginApiDelegate>

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar;

@end
