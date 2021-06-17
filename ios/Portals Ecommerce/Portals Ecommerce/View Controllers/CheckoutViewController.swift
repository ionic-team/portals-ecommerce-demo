import UIKit
import Capacitor

class CheckoutViewController: HostedContentViewController, ShopAPIActionDelegateProtocol {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        apiPlugin?.actionDelegate = self
    }
    
    override func webViewCompletedInitialLoad() {
        super.webViewCompletedInitialLoad()
        webView?.evaluateJavaScript("window.location.href = \"/checkout\"", completionHandler: nil)
    }
    
    // MARK: - ShopAPIActionDelegateProtocol
    
    func completeCheckout(with status: ShopAPICheckoutStatus) {
        if status == .completed {
            coordinator?.dataStore.cart.clear()
        }
        DispatchQueue.main.async {
            self.dismiss(animated: true, completion: nil)
        }
    }
    // MARK: - CAPBridgeViewController
    
    override func instanceDescriptor() -> InstanceDescriptor {
        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
        return descriptor
    }
}
