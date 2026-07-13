#import "MockBridge.h"

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wprotocol"
@implementation MockBridge
@synthesize config;
@synthesize isDevEnvironment;
@synthesize isSimEnvironment;
@synthesize notificationRouter;
@synthesize statusBarAnimation;
@synthesize statusBarStyle;
@synthesize statusBarVisible;
@synthesize userInterfaceStyle;
@synthesize viewController;
@synthesize webView;

@end
#pragma clang diagnostic pop
