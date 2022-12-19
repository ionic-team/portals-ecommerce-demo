# portals-ecommerce-demo
E-commerce Demo App using Ionic Portals

This is an example application built in iOS and Android. Both are using the same web resources for their Portals.

## Portals Registration Key

To try our Ionic Portals demo, both iOS and Android applications require you to input a Portals registration key. You may get a key by going to [ionic.io/register-portals](https://ionic.io/register-portals). Follow the instructions below to add your key to each platform demo application.

## iOS

### Install the dependencies and open the project

```bash
cd ./ios/Portals\ Ecommerce/
pod install
xed Portals\ Ecommerce.xcworkspace
```

It is **important** that you open the `xcworkspace` and _not_ the `xcodeproj` file.

### Portals registration key

Get you registration key from [ionic.io/register-portals](https://ionic.io/register-portals), then uncomment the following line in `AppDelegate.swift` and replace `YOUR_KEY_HERE` with your key.

```Swift
// Register Portals
PortalManager.register("YOUR_KEY_HERE");
```

## Android
### Portals registration key

Get you registration key from [ionic.io/register-portals](https://ionic.io/register-portals), then uncomment the following line in `EcommerceApp.java` and replace `YOUR_KEY_HERE` with your key.

```Java
// Register Portals
PortalManager.register("YOUR_KEY_HERE");
```

Build and run the Android app

## Web
### Building

Before you build the iOS or Android source you will need to build the web resources in the `web` and `featured-component` directories:
```bash
cd ./web
npm install
npm run build

cd ../featured-component
npm install
npm run build
```
