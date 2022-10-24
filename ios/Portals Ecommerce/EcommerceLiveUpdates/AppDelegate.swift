//
//  AppDelegate.swift
//  EcommerceLiveUpdates
//
//  Created by Steven Sherry on 7/5/22.
//

import UIKit
import IonicPortals
import IonicLiveUpdates

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
    static let checkout = Self(
        name: "checkout",
        startDir: "portals/shopwebapp",
        initialContext: ["startingRoute": "/checkout"],
        liveUpdateConfig: .webapp
    )
    
    static let help = Self(
        name: "help",
        startDir: "portals/shopwebapp",
        initialContext: ["startingRoute": "/help"],
        liveUpdateConfig: .help
    )
    
    static let user = Self(
        name: "user",
        startDir: "portals/shopwebapp",
        initialContext: ["startingRoute": "/user"],
        liveUpdateConfig: .webapp
    )

    static let featured = Self(
        name: "featured",
        startDir: "portals/featured"
    )
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
