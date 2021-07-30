import UIKit
import Capacitor

class UserProfileViewController: UIViewController, ApplicationCoordinationParticipant {
    var requiresPreloading: Bool = false
    
    private(set) var apiPlugin: ShopAPIPlugin?
    weak var coordinator: ApplicationCoordinator? {
        didSet {
            apiPlugin?.dataProvider = coordinator?.dataStore
        }
    }
       
    
//    @IBOutlet var webView: PortalWebView!
    override func viewDidLoad() {
        super.viewDidLoad()
        let webView = PortalWebView(frame: view.frame, portalName: "user")
        view = webView
        apiPlugin = webView.bridge?.plugin(withName: "ShopAPI") as? ShopAPIPlugin
        apiPlugin?.dataProvider = coordinator?.dataStore
    }
//    override var requiresPreloading: Bool { return true }
//
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        // Do any additional setup after loading the view.
//        navigationItem.title = NSLocalizedString("Help", comment: "Help page title")
//    }
//
//    override func webViewCompletedInitialLoad() {
//        super.webViewCompletedInitialLoad()
//        webView?.evaluateJavaScript("window.location.href = \"/user\"", completionHandler: nil)
//    }
//
//    override func instanceDescriptor() -> InstanceDescriptor {
//        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
//        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
//        return descriptor
//    }
}
