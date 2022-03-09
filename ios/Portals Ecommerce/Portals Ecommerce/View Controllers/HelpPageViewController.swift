import UIKit
import IonicPortals

class HelpPageViewController: AppParticipantViewController {
    
    private var subscriptionRef = 0
    
    override var requiresPreloading: Bool { return true }

    override func loadView() {
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("help")
        
        var initialContext: [String: String] = [:]
        initialContext["startingRoute"] = "/help"
        
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
    }
}
