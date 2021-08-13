import UIKit
import IonicPortals

class HelpPageViewController: AppParticipantViewController {

    override func viewDidLoad() {
        
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("help")
        var initialContext: [String: String] = [:]
        initialContext["startingRoute"] = "/help"
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(frame: view.frame, portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
        super.viewDidLoad()
    }
}
