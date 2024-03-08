import UIKit
import IonicPortals
import CapacitorCamera

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.

        // Register Portals
        // PortalsRegistrationManager.shared.register(key: "")
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
    static let featured = Self(name: "featured")

    private static func shopPortal(named name: String) -> Portal {
        .init(
            name: name,
            startDir: "shopwebapp",
            initialContext: ["startingRoute": "/\(name)"],
            plugins: [
                .type(ShopAPIPlugin.self),
                .instance(
                    WebVitalsPlugin { portalName, duration in
                        print("Portal \(portalName) - First Contentful Paint: \(duration)ms")
                    }
                )
            ]
        )
    }

    static let checkout = shopPortal(named: "checkout")
    static let help = shopPortal(named: "help")
    static let user = shopPortal(named: "user")
        .adding(CameraPlugin.self)
}

