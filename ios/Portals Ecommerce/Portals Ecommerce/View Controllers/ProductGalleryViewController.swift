import UIKit
import IonicPortals

class ProductGalleryViewController: UIViewController, UICollectionViewDelegate {
    @IBOutlet private weak var collectionView: UICollectionView!
    @IBOutlet weak var portalContainer: UIView!
    
    private var viewModel: GalleryViewModel = GalleryViewModel()
    private let detailSegueIdentifier = "ShowDetailSegue"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let portal = PortalUIView(portal: .featured)
        portal.translatesAutoresizingMaskIntoConstraints = false
        portalContainer.addSubview(portal)
        NSLayoutConstraint.activate([
            portal.topAnchor.constraint(equalTo: portalContainer.topAnchor),
            portal.trailingAnchor.constraint(equalTo: portalContainer.trailingAnchor),
            portal.bottomAnchor.constraint(equalTo: portalContainer.bottomAnchor),
            portal.leadingAnchor.constraint(equalTo: portalContainer.leadingAnchor)
        ])
        
        viewModel.carouselProducts = ShopAPI.dataStore
            .products
            .filter { $0.category == .mustHaves }
        
        viewModel.listProducts = ShopAPI.dataStore.products.shuffled()
        viewModel.imageLoader = ShopAPI.dataStore.imageLoader
        viewModel.configure(with: collectionView)
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
