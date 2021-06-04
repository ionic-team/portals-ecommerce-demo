import UIKit

class ProductDetailViewController: UIViewController, ApplicationCoordinationParticipant {
    var product: Product?
    weak var coordinator: ApplicationCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        print("showing \(String(describing: product?.title))")
    }
}
