import UIKit
import IonicPortals

class CheckoutViewController: AppParticipantViewController, ShopAPIActionDelegateProtocol {
    var subscriptionRef: Int?;
    
    override func viewDidLoad() {
        
        // Do any additional setup after loading the view.
        let portal = try! PortalManager.getPortal("checkout")
        var initialContext: [String: String] = [:]
        initialContext["startingRoute"] = "/checkout"
        portal.initialContext = initialContext
        
        let portalWebView = PortalWebView(frame: view.frame, portal: portal)
          
        self.view = portalWebView
        self.bridge = portalWebView.bridge
        
        self.subscriptionRef = PortalsPlugin.subscribe("dismiss", {result in
            if(result.data as! String == "cancel" || result.data as! String == "success") {
                DispatchQueue.main.async {
                    self.dismiss(animated: true, completion: nil)
                }
            }
        })
        
        super.viewDidLoad()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        PortalsPlugin.unsubscribe("dismiss", self.subscriptionRef!)
    }

   func completeCheckout(with status: ShopAPICheckoutStatus) {
        if status == .completed {
            coordinator?.dataStore.cart.clear()
        }
   }
    
}
