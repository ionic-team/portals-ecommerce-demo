import UIKit
import IonicPortals

class UserProfileViewController: UIViewController {
    override func loadView() {
        self.view = PortalUIView(portal: .user)
    }
}
