import Foundation
import UIKit
import Capacitor

class AppParticipantViewController: UIViewController, ApplicationCoordinationParticipant {
    
    var apiPlugin: ShopAPIPlugin?
    var bridge: CAPBridgeProtocol?
    var requiresPreloading: Bool { return false }
    
    // MARK: - ApplicationCoordinationParticipant
    weak var coordinator: ApplicationCoordinator? {
        didSet {
            apiPlugin?.dataProvider = coordinator?.dataStore
        }
    }
    
    override func viewDidLoad() {
        if(self.bridge == nil) {
          print("Capacitor Bridge (self.bridge) must be set in child view controller viewDidLoad method")
            super.viewDidLoad()
            return
        }
        apiPlugin = bridge?.plugin(withName: "ShopAPI") as? ShopAPIPlugin
        apiPlugin?.dataProvider = coordinator?.dataStore
        apiPlugin?.actionDelegate = self as Any as? ShopAPIActionDelegateProtocol;
        
        // now call super which will start the initial load
        super.viewDidLoad()       
    }
    
}
