//
//  RNCarrierInfo.m
//  RNCarrierInfo
//
//  Created by Matthew Knight on 09/05/2015.
//  Copyright (c) 2015 Anarchic Knight. All rights reserved.
//

#import "RNCarrierInfo.h"
#import <CoreTelephony/CTTelephonyNetworkInfo.h>
#import <CoreTelephony/CTCarrier.h>

@implementation RNCarrierInfo

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(allowsVOIP:(RCTPromiseResolveBlock)resolve
                    rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    BOOL allowsVoip = [[nInfo subscriberCellularProvider] allowsVOIP];
    resolve([NSNumber numberWithBool:allowsVoip]);
}

RCT_EXPORT_METHOD(carrierName:(RCTPromiseResolveBlock)resolve
                     rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    NSString *carrierName = [[nInfo subscriberCellularProvider] carrierName];
    if(carrierName)
    {
        resolve(carrierName);
    }
    else
    {
        reject(@"no_carrier_name", @"Carrier Name cannot be resolved", nil);
    }
}

RCT_EXPORT_METHOD(isoCountryCode:(RCTPromiseResolveBlock)resolve
                        rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    NSString *iso = [[nInfo subscriberCellularProvider] isoCountryCode];
    if(iso)
    {
        resolve(iso);
    }
    else
    {
        reject(@"no_iso_country_code", @"ISO country code cannot be resolved", nil);
    }
}

RCT_EXPORT_METHOD(mobileCountryCode:(RCTPromiseResolveBlock)resolve
                           rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    NSString *mcc = [[nInfo subscriberCellularProvider] mobileCountryCode];
    if(mcc)
    {
        resolve(mcc);
    }
    else
    {
        reject(@"no_mobile_country_code", @"Mobile country code cannot be resolved", nil);
    }
}

RCT_EXPORT_METHOD(mobileNetworkCode:(RCTPromiseResolveBlock)resolve
                           rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    NSString *mnc = [[nInfo subscriberCellularProvider] mobileNetworkCode];
    if(mnc)
    {
        resolve(mnc);
    }
    else
    {
        reject(@"no_mobile_network", @"Mobile network code cannot be resolved", nil);
    }
}

// return MCC + MNC, e.g. 46697
RCT_EXPORT_METHOD(mobileNetworkOperator:(RCTPromiseResolveBlock)resolve
                               rejecter:(RCTPromiseRejectBlock)reject)
{
    CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
    NSString *mcc = [[nInfo subscriberCellularProvider] mobileCountryCode];
    NSString *mnc = [[nInfo subscriberCellularProvider] mobileNetworkCode];
    NSString *operator = [NSString stringWithFormat: @"%@%@", mcc, mnc];
    if (operator) {
        resolve(operator);
    }
    else {
        reject(@"no_network_operator", @"Mobile network operator code cannot be resolved", nil);
    }
}

RCT_EXPORT_METHOD(addCarrierChangeListener:(RCTResponseSenderBlock)successCallback)
{
//  CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];
  CTTelephonyNetworkInfo *nInfo = [[CTTelephonyNetworkInfo alloc] init];

  if (@available(iOS 12.0, *)) {
    nInfo.serviceSubscriberCellularProvidersDidUpdateNotifier =  ^(NSString* carrier) { //TODO: add checks for nosim, Esim and cdma
      RCTResponseSenderBlock callback = successCallback;
       NSLog(@"--------------CHANGED----------------");
      NSArray *events = [NSArray arrayWithObjects:carrier,nil];
      callback(@[[NSNull null], events]);
    };
    NSArray *events = [NSArray arrayWithObjects:@"SUBSCRIBED",nil];
    successCallback(@[[NSNull null], events]);
  } else {
    NSLog(@"--------------NOT_AVAILABLE_ON_YOUR_VESION----------------");
    NSArray *events = [NSArray arrayWithObjects:@"NOT_AVAILABLE_ON_YOUR_VESION",nil];
    successCallback(@[[NSNull null], events]);
  }
}

@end

