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
            list.append(Product(id: UUID.init().uuidString, title: "Product \(i)", description: "Some awesome details.", price: Int.random(in: 199...3499), imageName: "", category: ((i % 2) == 0) ? .featured : .recommended))
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
