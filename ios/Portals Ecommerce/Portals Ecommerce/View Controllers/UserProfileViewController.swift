import UIKit
import IonicPortals

class UserProfileViewController: AppParticipantViewController {
    
    override var requiresPreloading: Bool { return true }
    
    override func viewDidLoad() {
        
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("user")
        var initialContext: [String: String] = [:]
        initialContext["startingRoute"] = "/user"
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(frame: view.frame, portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
        
        super.viewDidLoad()
    }
}
