import UIKit
import SwiftUI
import IonicLiveUpdates

class SyncPageViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        let syncVc = UIHostingController(rootView: SyncView(viewModel: SyncViewModel()))
        syncVc.view.frame = view.frame
        view.addSubview(syncVc.view)
        addChild(syncVc)
        syncVc.didMove(toParent: self)
    }
}
