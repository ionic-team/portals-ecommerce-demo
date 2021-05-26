import UIKit

protocol ApplicationCoordinationParticipant: AnyObject {
    var coordinator: ApplicationCoordinator? { get set }
}

@objc class ApplicationCoordinator: NSObject, UINavigationControllerDelegate {
    let imageLoader = ImageLoader()
    private(set) var products: [Product]
    
    private static func generatedProducts() -> [Product] {
        var list: [Product] = []
        for i in 1...20 {
            list.append(Product(id: UUID.init().uuidString, name: "Product \(i)", price: Int.random(in: 199...3499), descriptions: "Some awesome details."))
        }
        return list
    }
    
    override init() {
        products = ApplicationCoordinator.generatedProducts()
    }
    
    // MARK: - UINavigationControllerDelegate
    
    func navigationController(_ navigationController: UINavigationController, willShow viewController: UIViewController, animated: Bool) {
        if let participant = viewController as? ApplicationCoordinationParticipant {
            participant.coordinator = self
        }
    }
}
