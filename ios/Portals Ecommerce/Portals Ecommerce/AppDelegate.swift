import UIKit
import IonicPortals

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.

        // Register Portals
        PortalsRegistrationManager.shared.register(key: "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMDU2ZjBlNC1kYTFkLTQ1YWMtYWJjZi1hNDg3MzMyZTQwNGYifQ.0H_gnwXCL1Z-GtFCwQ3J9YrybMxQO56CYo3PFGzoueB56DMvKT4jiQhLzhDYKEE5GwlqX-r0H_qklYKg_jtMyK9QZ_-kTNWi6LyjrJTcgFVwxjz27PZaqZPoKWyJLotSIbBhN8BF5flunCGW8kWL4_nY6FUmswatPDgcvyPmOydr9InbEHHDUVvi9mGwy_G78BjDrl9bThezpGRseBTOI7KH5FUdXwH9DCZJ2RC4_ukTNKMqaKFh-OcD8KDBUIdSP8GE0quO7zL4qSINvxMMzpupTdQKf3Td5B1mvLlrS4kF_8VPoQtvB8JqMrmH2fa8f31fCiz1EV4Wkngb_5yC7w")
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
        initialContext: ["startingRoute": "/checkout"]
    )
    
    static let help = Self(
        name: "help",
        startDir: "portals/shopwebapp",
        initialContext: ["startingRoute": "/help"]
    )
    
    static let user = Self(
        name: "user",
        startDir: "portals/shopwebapp",
        initialContext: ["startingRoute": "/user"]
    )
}

