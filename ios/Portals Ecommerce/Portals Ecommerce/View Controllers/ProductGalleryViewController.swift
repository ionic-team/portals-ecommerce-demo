import UIKit
import Combine
import IonicPortals

class ProductGalleryViewController: UIViewController, UICollectionViewDelegate {
    @IBOutlet private weak var collectionView: UICollectionView!
    private var viewModel: GalleryViewModel = GalleryViewModel()
    private let detailSegueIdentifier = "ShowDetailSegue"
    private var featuredItemSelectedCancellable: AnyCancellable?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel.carouselProducts = ShopAPI.dataStore
            .products
            .filter { $0.category == .mustHaves }
        
        viewModel.listProducts = ShopAPI.dataStore.products.shuffled()
        viewModel.imageLoader = ShopAPI.dataStore.imageLoader
        viewModel.configure(with: collectionView)
        
        featuredItemSelectedCancellable = PortalsPubSub.publisher(for: "featured:select-item")
            .data(as: Int.self)
            .compactMap { $0 }
            .compactMap(ShopAPI.dataStore.product)
            .receive(on: DispatchQueue.main)
            .sink { [weak self] product in
                guard let self = self else { return }
                self.performSegue(withIdentifier: self.detailSegueIdentifier, sender: product)
            }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == detailSegueIdentifier, let product = sender as? Product, let viewController = segue.destination as? ProductDetailViewController {
            viewController.product = product
        }
    }
    
    // MARK: - UICollectionViewDelegate
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let product = viewModel.product(at: indexPath) {
            performSegue(withIdentifier: detailSegueIdentifier, sender: product)
        }
    }
}
