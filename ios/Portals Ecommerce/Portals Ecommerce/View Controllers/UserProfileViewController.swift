import UIKit
import Capacitor

class UserProfileViewController: HostedContentViewController {
    override var requiresPreloading: Bool { return true }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func webViewCompletedInitialLoad() {
        super.webViewCompletedInitialLoad()
        webView?.evaluateJavaScript("window.location.href = \"/user\"", completionHandler: nil)
    }
    
    override func instanceDescriptor() -> InstanceDescriptor {
        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
        return descriptor
    }
}
