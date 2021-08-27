# portals-ecommerce-demo
E-commerce Demo App using Ionic Portals

This is an example application built in iOS and Android. Both are using the same web resources for their Portals.

## Github Access

Both platforms require the use of a [Github Personal Access Token](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token). For Cocopods on iOS, the token needs read access for repositories. For Android, the token needs read access for Packages. If building for both platforms, you can generate separate tokens or a shared token with the necessary access.

## iOS
### Add private spec repo

This demo uses a [private spec repo](https://guides.cocoapods.org/making/private-cocoapods.html) to publish the Cocoapods dependencies. You need to add it to your local Cocoapods environment.

```
pod repo add Portals https://github.com/native-portal/podspecs.git
```

(`Portals` is only a suggestion, the local name for the repo can be anything)

Enter your Github account when prompted for a username and enter the token you created when prompted for a password.

### Install pod dependencies
- `cd ./ios/Portals\ Ecommerce`
- `pod install`

Build and run iOS app

## Android
### Add Github credentials to local env

This demo uses Github Packages and requires credentials to an account with access to the Ionic Portals dependencies. Add them to your local Gradle properties file to store them securely outside the project code.

- Add the user name and token to your local gradle.properties file:

`vi ~/.gradle/gradle.properties`

```
GITHUB_USER=USER_NAME_HERE
GITHUB_PERSONAL_ACCESS_TOKEN=XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

## Web
### Building

Before you build the iOS or Android source you will need to build the web resource.
- `cd ./web`
- `npm install`
- `npm run build`
