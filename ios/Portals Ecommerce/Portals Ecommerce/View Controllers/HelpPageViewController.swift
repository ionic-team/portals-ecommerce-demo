import UIKit
import IonicPortals

class HelpPageViewController: UIViewController {
    override func loadView() {
        self.view = PortalUIView(portal: .help)
    }
}
