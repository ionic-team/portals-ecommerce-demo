#import <Capacitor/Capacitor.h>

CAP_PLUGIN(IONShopAPIPlugin, "ShopAPI",
  CAP_PLUGIN_METHOD(getCart, CAPPluginReturnPromise);
  CAP_PLUGIN_METHOD(getUserDetails, CAPPluginReturnPromise);
  CAP_PLUGIN_METHOD(updateUserDetails, CAPPluginReturnNone);
  CAP_PLUGIN_METHOD(checkoutResult, CAPPluginReturnNone);
  CAP_PLUGIN_METHOD(getUserPicture, CAPPluginReturnPromise);
  CAP_PLUGIN_METHOD(setUserPicture, CAPPluginReturnNone);
)
