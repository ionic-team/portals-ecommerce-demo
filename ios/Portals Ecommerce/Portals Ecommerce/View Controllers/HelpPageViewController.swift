import UIKit
import Capacitor

class HelpPageViewController: HostedContentViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        navigationItem.title = NSLocalizedString("Help", comment: "Help page title")
    }
    
    override func webViewCompletedInitialLoad() {
        super.webViewCompletedInitialLoad()
//       webView?.evaluateJavaScript("window.location.href = \"/help\"", completionHandler: nil)
    }
    
    override func instanceDescriptor() -> InstanceDescriptor {
        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
        return descriptor
    }
}
