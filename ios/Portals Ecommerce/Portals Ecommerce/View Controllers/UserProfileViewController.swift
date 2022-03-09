import UIKit
import IonicPortals

class UserProfileViewController: AppParticipantViewController {
    
    override var requiresPreloading: Bool { return true }
    
    override func loadView() {
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("user")
        
        var initialContext: [String: String] = [:]
        
        initialContext["startingRoute"] = "/user"
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
    }
}
