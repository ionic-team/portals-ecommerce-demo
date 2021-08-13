import Foundation
import UIKit
import Capacitor

class AppParticipantViewController: UIViewController, ApplicationCoordinationParticipant {
    
    var apiPlugin: ShopAPIPlugin?
    var bridge: CAPBridgeProtocol?
    
    // MARK: - ApplicationCoordinationParticipant
    weak var coordinator: ApplicationCoordinator? {
        didSet {
            apiPlugin?.dataProvider = coordinator?.dataStore
        }
    }
    
    override func viewDidLoad() {
        if(self.bridge == nil) {
            assertionFailure("Capacitor Bridge (self.bridge) must be set in child view controller viewDidLoad method")
        }
        apiPlugin = bridge?.plugin(withName: "ShopAPI") as? ShopAPIPlugin
        apiPlugin?.dataProvider = coordinator?.dataStore
        
        // now call super which will start the initial load
        super.viewDidLoad()
       
    }
    
}
