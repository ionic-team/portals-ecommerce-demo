import Combine
import Capacitor
import IonicPortals

class CartViewModel: ObservableObject {
    @Published var shouldDisplayCheckout = false
    
    private var subRef: Int?
    private var coordinator: ApplicationCoordinator
    private var apiPlugin: ShopAPIPlugin?
    private var bridge: CAPBridgeProtocol?
    private var cartSubscription: AnyCancellable?
   
    var contents: [Cart.Item] {
        coordinator.dataStore.cart.contents
    }
    
    var cartTotal: String {
        coordinator.dataStore.cart.formattedSubtotal ?? ""
    }
    
    private var cart: Cart { coordinator.dataStore.cart }
    
    init(coordinator: ApplicationCoordinator) {
        self.coordinator = coordinator
        // This is required so that updates to the cart will trigger view updates.
        // Making an @Published property doesn't do anything for an ObervableObject.
        cartSubscription = coordinator.dataStore.cart.objectWillChange
            .receive(on: DispatchQueue.main)
            .sink { [weak self] in
                self?.objectWillChange.send()
            }
        
    }
    
    func handle(bridge: CAPBridgeProtocol?) {
        subRef = PortalsPlugin.subscribe("dismiss") { [weak self] result in
            if result.data as! String == "cancel" || result.data as! String == "success" {
                DispatchQueue.main.async {
                    
                    self?.shouldDisplayCheckout = false
                }
            }
        }
        
        apiPlugin = bridge?.plugin(withName: "ShopAPI") as? ShopAPIPlugin
        apiPlugin?.dataProvider = coordinator.dataStore
        apiPlugin?.actionDelegate = self
    }
    
    func unsubscribleFromPortal() {
        if let subRef = subRef {
            PortalsPlugin.unsubscribe("dismiss", subRef)
            self.subRef = nil
        }
        
        apiPlugin = nil
    }
    
    func deleteProducts(for indexSet: IndexSet) {
        indexSet.compactMap { cart.contents[safe: $0]?.product }
            .forEach { product in cart.update(product: product, quantity: 0) }
    }
    
    func update(product: Product, with quantity: UInt) {
        cart.update(product: product, quantity: quantity)
    }
    
    func image(named name: String) -> UIImage {
        coordinator.dataStore.imageLoader.imageFor(name)
    }
}

extension CartViewModel: ShopAPIActionDelegateProtocol {
    func completeCheckout(with status: ShopAPICheckoutStatus) {
        if status == .completed {
            cart.clear()
        }
    }
}

#if DEBUG
extension CartViewModel {
    static let debug: CartViewModel = {
        let mockData = try! Data(contentsOf: Bundle.main.url(forResource: "data", withExtension: ".json")!)
        
        let decoder = JSONDecoder()
        struct ProductRespone: Codable {
            var products: [Product]
        }
        
        let productResponse = try! decoder.decode(ProductRespone.self, from: mockData)
        
        let products = productResponse.products.map {
            Cart.Item(product: $0, quantity: 1)
        }
        
        let coordinator = ApplicationCoordinator()
        
        products.forEach { coordinator.dataStore.cart.update(product: $0.product, quantity: $0.quantity) }
        
        return CartViewModel(coordinator: coordinator)
    }()
}
#endif
