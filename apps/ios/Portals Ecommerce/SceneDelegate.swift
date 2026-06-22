import UIKit
import Combine

class SceneDelegate: UIResponder, UIWindowSceneDelegate {
    var window: UIWindow?
    
    private var badgeSubscription: AnyCancellable?
    
    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        guard let _ = (scene as? UIWindowScene) else { return }
        // inject our coordinator
        if let tabController = window?.rootViewController as? UITabBarController {
            
            // Disgusting hack to get the shopping cart badge to display if it hasn't been rendered yet.
            badgeSubscription = ShopAPI.dataStore.cart.$contents
                .receive(on: DispatchQueue.main)
                .sink { [weak tabController] contents in
                    tabController?.tabBar.items?[safe: 1]?.badgeColor = .clear
                    
                    // we don't have an easy way to customize the badge appearance, so instead we will make
                    // the badge clear and customize the appearance of the string that we set in it
                    tabController?.tabBar.items?[safe: 1]?.setBadgeTextAttributes(
                        [
                            .foregroundColor: UIColor(displayP3Red: 255/255.0, green: 184/255.0, blue: 0/255.0, alpha: 1),
                            .font : UIFont.systemFont(ofSize: 8)
                        ],
                        for: .normal
                    )

                    tabController?.tabBar.items?[safe: 1]?.badgeValue = contents.isEmpty ? nil : "●"
                }
        }
    }

    func sceneDidDisconnect(_ scene: UIScene) {
        // Called as the scene is being released by the system.
        // This occurs shortly after the scene enters the background, or when its session is discarded.
        // Release any resources associated with this scene that can be re-created the next time the scene connects.
        // The scene may re-connect later, as its session was not necessarily discarded (see `application:didDiscardSceneSessions` instead).
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
        // Called when the scene has moved from an inactive state to an active state.
        // Use this method to restart any tasks that were paused (or not yet started) when the scene was inactive.
    }

    func sceneWillResignActive(_ scene: UIScene) {
        // Called when the scene will move from an active state to an inactive state.
        // This may occur due to temporary interruptions (ex. an incoming phone call).
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
        // Called as the scene transitions from the background to the foreground.
        // Use this method to undo the changes made on entering the background.
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
        // Called as the scene transitions from the foreground to the background.
        // Use this method to save data, release shared resources, and store enough scene-specific state information
        // to restore the scene back to its current state.
    }
}
