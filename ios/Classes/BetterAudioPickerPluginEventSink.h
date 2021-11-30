//
//  BetterAudioPickerPluginEventSink.h
//  Pods
//
//  Created by 汪洋 on 2021/11/29.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>

@interface BetterAudioPickerPluginEventSink : NSObject <FlutterStreamHandler>

@property (nonatomic, copy) FlutterEventSink event;

@end
