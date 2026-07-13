# portals-ecommerce-demo

An Ionic Portals ecommerce demo with native iOS and Android host apps.

This repo contains native host apps and Portal web bundles:

```text
apps/
  ios/        Native iOS app using Ionic Portals
  android/    Native Android app using Ionic Portals
portals/
  shop/       Shop, checkout, help, and profile Portal bundle
  featured/   Featured products Portal bundle
shared/
  assets/     Shared product images
  data.json   Shared product data
```

The committed native apps are the Portals host apps. The Portal web bundles are built as static assets and embedded by the native apps at build time.

## Install

Install JavaScript dependencies from the repo root:

```bash
npm install
```

## Build Portal Bundles

Build both Portal bundles before building either native app:

```bash
npm run build
```

You can also build each Portal separately:

```bash
npm run build:shop
npm run build:featured
```

## iOS

Open the native iOS project:

```bash
xed apps/ios/Portals\ Ecommerce.xcodeproj
```

The iOS app uses Swift Package Manager for native dependencies. Most packages are remote and Xcode resolves them automatically when the project opens, but `ionic-portals-ios` and `live-update-provider-mock` are referenced as local Swift packages and must exist as sibling checkouts next to this repo in the local workspace (the same requirement Android has, below). The bundled Capacitor Camera package also resolves from `portals/shop/node_modules`, so run `npm install` in `portals/shop` first.

## Android

Open `apps/android` in Android Studio or build from the command line:

```bash
cd apps/android
./gradlew assembleDebug
```

The Android app targets Capacitor 8 and uses Java 21 for native builds.

The Android app includes an Updates tab that syncs the Profile Portal through
the sibling `live-update-provider-mock` repo. Tap Sync Provider App to install
the mock provider bundle, then the Profile Portal reloads from the synced
directory when active.

Android builds expect `live-update-provider-mock` to exist next to this repo in
the local workspace.

## Portal Web Apps

The Portal web apps remain Capacitor-aware so they can use Capacitor and Portals JavaScript APIs inside the native host apps. This repo does not commit generated standalone Capacitor native shells for those web apps.

See [docs/architecture.md](docs/architecture.md) for the repo architecture.
