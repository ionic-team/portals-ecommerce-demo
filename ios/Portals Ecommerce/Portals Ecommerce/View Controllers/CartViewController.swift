import UIKit

class CartViewController: UIViewController, ApplicationCoordinationParticipant {
    weak var coordinator: ApplicationCoordinator?
    
    @IBOutlet private weak var textView: UITextView!

    override func viewDidLoad() {
        super.viewDidLoad()

        textView.text = ""
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if let cart = coordinator?.dataStore.cart {
            if let data = try? JSONEncoder().encode(cart), let cartText = String(data: data, encoding: .utf8) {
                textView.text = cartText
            }
        }
    }
}
