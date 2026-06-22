import Combine
import Capacitor
import IonicPortals

class CartViewModel: ObservableObject {
    @Published var shouldDisplayCheckout = false
    
    private var dataStore: DataStoreViewModel
    private var cancellables: Set<AnyCancellable> = []
   
    var contents: [Cart.Item] {
        dataStore.cart.contents
    }
    
    var cartTotal: String {
        dataStore.cart.formattedSubtotal ?? ""
    }
    
    private var cart: Cart { dataStore.cart }
    
    init(dataStore: DataStoreViewModel) {
        self.dataStore = dataStore
        // This is required so that updates to the cart will trigger view updates.
        // Making an @Published property doesn't do anything for an ObervableObject.
        dataStore.cart.objectWillChange
            .receive(on: DispatchQueue.main)
            .sink { [weak self] in
                self?.objectWillChange.send()
            }
            .store(in: &cancellables)
        
        PortalsPubSub.publisher(for: "dismiss")
            .data(as: String.self)
            .compactMap { $0 }
            .filter { $0 != "cancel" || $0 != "success" }
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in
                self?.shouldDisplayCheckout = false
            }
            .store(in: &cancellables)
       
        ShopAPI.checkoutStatusPublisher
            .filter { $0 == .completed }
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in
                self?.cart.clear()
            }
            .store(in: &cancellables)
    }
    
    func deleteProducts(for indexSet: IndexSet) {
        indexSet.compactMap { cart.contents[safe: $0]?.product }
            .forEach { product in cart.update(product: product, quantity: 0) }
    }
    
    func update(product: Product, with quantity: UInt) {
        cart.update(product: product, quantity: quantity)
    }
    
    func image(named name: String) -> UIImage {
        dataStore.imageLoader.imageFor(name)
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
        
        products.forEach { ShopAPI.dataStore.cart.update(product: $0.product, quantity: $0.quantity) }
        
        return CartViewModel(dataStore: ShopAPI.dataStore)
    }()
}
#endif
