# Architecture

This demo is structured around native host apps consuming Portal web bundles.

## Native Hosts

- `apps/ios` contains the native iOS app.
- `apps/android` contains the native Android app.
- Native dependencies are managed by each platform: Swift Package Manager for iOS and Gradle for Android.

## Portal Bundles

- `portals/shop` builds the main ecommerce web bundle.
- `portals/featured` builds the featured products web bundle.
- The native apps copy the built bundles into app assets during native builds.
- Android currently embeds the shop bundle. iOS embeds both the shop and featured bundles.
- The Android Updates tab uses the sibling `live-update-provider-mock` repo to sync the Profile Portal through the live update provider flow.
- Android Gradle includes the mock provider `:core` module from source (as `:live-update-provider-mock-core`) and links it directly, since Portals apps consume the provider core without the Capacitor plugin adapter. The mock ships no Maven artifact.
- The provider is configured with `bundleType` `PORTAL` (`LiveUpdateProviderMockConfig`), selecting the mock's `portal` asset bundle.
- The mock core depends on `io.ionic:liveupdateprovider`, supplied from the sibling `live-update-provider-sdk` repo via an `includeBuild` dependency substitution.
- iOS wires the same sibling `live-update-provider-mock` repo into the Featured Portal via a local Swift Package reference declared in the Xcode project (`Portal.liveUpdateSource(.provider(manager:))` in `AppDelegate.swift`). Both platforms require `live-update-provider-mock` to be checked out alongside this repo.

The Portal web apps may use Capacitor and Portals JavaScript APIs, but their generated standalone native shells are not committed. The native host apps in `apps/` are the source of truth for the demo.

## Shared Demo Data

- `shared/data.json` is the canonical product data source.
- `shared/assets/images` contains shared product images.

Platform-specific copies may exist where native resource systems require them.
