import UIKit
import Capacitor

class HelpPageViewController: HostedContentViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    override func instanceDescriptor() -> InstanceDescriptor {
        let path = Bundle.main.url(forResource: "portals/shopwebapp", withExtension: nil)!
        let descriptor = InstanceDescriptor(at: path, configuration: nil, cordovaConfiguration: nil)
        return descriptor
    }
}
