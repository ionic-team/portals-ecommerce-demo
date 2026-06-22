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

The iOS app uses Swift Package Manager for native dependencies. Xcode resolves packages when the project opens.

## Android

Open `apps/android` in Android Studio or build from the command line:

```bash
cd apps/android
./gradlew assembleDebug
```

## Portal Web Apps

The Portal web apps remain Capacitor-aware so they can use Capacitor and Portals JavaScript APIs inside the native host apps. This repo does not commit generated standalone Capacitor native shells for those web apps.

See [docs/architecture.md](docs/architecture.md) for the repo architecture.
