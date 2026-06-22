#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

/*
 Mock bridge object for testing plugins. This is implemented in Obj-C because we can silence
 warnings about the lack of protocol conformance without actually having to implement it.
 However, calling any of the missing methods or properties can cause crashes. If the plugin
 needs any of the bridge functionality than this should implement the minimum necessary to
 satisfy that.
 */

@interface MockBridge : NSObject<CAPBridgeProtocol>
@end
