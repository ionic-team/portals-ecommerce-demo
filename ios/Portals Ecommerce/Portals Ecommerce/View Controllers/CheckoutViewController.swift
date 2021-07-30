import UIKit
import Capacitor

class CheckoutViewController: PortalViewController {
    
//    init(_ name: String) {
//        super.init(nibName: nil, bundle: nil)
//        self.name = name
//    }
//
//    required init?(coder: NSCoder) {
//        super.init(coder: coder)
//    }
    //    private(set) var apiPlugin: ShopAPIPlugin?
    
//    public override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
//        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
//    }
//
//    public required init?(coder: NSCoder) {
//        super.init(coder: coder)
//    }

        
//    required init?(coder: NSCoder) {
//        fatalError("init(coder:) has not been implemented")
//    }
//
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        apiPlugin?.actionDelegate = self
    }
    
//    override func webViewCompletedInitialLoad() {
//        super.webViewCompletedInitialLoad()
//        webView?.evaluateJavaScript("window.location.href = \"/checkout\"", completionHandler: nil)
//    }
    
    // MARK: - ShopAPIActionDelegateProtocol
    
//    func completeCheckout(with status: ShopAPICheckoutStatus) {
//        if status == .completed {
//            coordinator?.dataStore.cart.clear()
//        }
//        DispatchQueue.main.async {
//            self.dismiss(animated: true, completion: nil)
//        }
//    }
    // MARK: - CAPBridgeViewController
    
//    override func instanceDescriptor() -> InstanceDescriptor {
//        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
//        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
//        return descriptor
//    }
}
