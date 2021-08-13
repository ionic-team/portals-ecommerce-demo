import UIKit
import IonicPortals

class CheckoutViewController: AppParticipantViewController, ShopAPIActionDelegateProtocol {
    override func viewDidLoad() {
        
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("checkout")
        var initialContext: [String: String] = [:]
        initialContext["startingRoute"] = "/checkout"
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(frame: view.frame, portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
        
        super.viewDidLoad()
    }

   func completeCheckout(with status: ShopAPICheckoutStatus) {
        if status == .completed {
            coordinator?.dataStore.cart.clear()
        }
        DispatchQueue.main.async {
            self.dismiss(animated: true, completion: nil)
        }
   }
    
}
