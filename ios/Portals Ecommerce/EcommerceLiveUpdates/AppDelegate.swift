//
//  AppDelegate.swift
//  EcommerceLiveUpdates
//
//  Created by Steven Sherry on 7/5/22.
//

import UIKit
import IonicPortals
import IonicLiveUpdates
import CapacitorCamera

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
//         PortalsRegistrationManager.shared.register(key: "YOUR_KEY_HERE")
        try? LiveUpdateManager.shared.add(.help)
        try? LiveUpdateManager.shared.add(.webapp)
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
}

extension Portal {
    private static let commonPlugins: [Plugin] = [
        .type(ShopAPIPlugin.self),
        .instance(
            WebVitalsPlugin { portalName, duration in
                print("Portal \(portalName) - First Contentful Paint: \(duration)ms")
            }
        )
    ]

    private static func shopPortal(named name: String, liveUpdateConfig: LiveUpdate) -> Portal {
        .init(
            name: name,
            startDir: "shopwebapp",
            initialContext: ["startingRoute": "/checkout"],
            plugins: [
                .type(ShopAPIPlugin.self),
                .instance(
                    WebVitalsPlugin { portalName, duration in
                        print("Portal \(portalName) - First Contentful Paint: \(duration)ms")
                    }
                )
            ],
            liveUpdateConfig: liveUpdateConfig
        )
    }

    static let checkout = shopPortal(named: "checkout", liveUpdateConfig: .webapp)
    static let user = shopPortal(named: "user", liveUpdateConfig: .webapp)
        .adding(CameraPlugin.self)

    static let help = shopPortal(named: "help", liveUpdateConfig: .help)
    static let featured = Portal(name: "featured")
}

extension LiveUpdate {
    private static let activeChannel = UserDefaults.standard.string(forKey: "active_channel") ?? "production"
    
    static let webapp = Self(
        appId: "186b544f",
        channel: activeChannel,
        syncOnAdd: false
    )
    
    static let help = Self(
        appId: "a81b2440",
        channel: activeChannel,
        syncOnAdd: false
    )
}
