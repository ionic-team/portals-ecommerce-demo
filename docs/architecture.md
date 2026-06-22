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

The Portal web apps may use Capacitor and Portals JavaScript APIs, but their generated standalone native shells are not committed. The native host apps in `apps/` are the source of truth for the demo.

## Shared Demo Data

- `shared/data.json` is the canonical product data source.
- `shared/assets/images` contains shared product images.

Platform-specific copies may exist where native resource systems require them.
