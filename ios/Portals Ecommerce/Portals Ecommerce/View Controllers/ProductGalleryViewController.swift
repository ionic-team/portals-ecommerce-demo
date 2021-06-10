import UIKit

class ProductGalleryViewController: UIViewController, ApplicationCoordinationParticipant, UICollectionViewDelegate {
    weak var coordinator: ApplicationCoordinator?
    var requiresPreloading: Bool { return false }
    
    @IBOutlet private weak var collectionView: UICollectionView!
    
    private var viewModel: GalleryViewModel = GalleryViewModel()
    private let detailSegueIdentifier = "ShowDetailSegue"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel.carouselProducts = (coordinator?.dataStore.products ?? [])
            .filter({ $0.category == .mustHaves })
        viewModel.listProducts = (coordinator?.dataStore.products ?? [])
            .shuffled()
        viewModel.imageLoader = coordinator?.dataStore.imageLoader
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
