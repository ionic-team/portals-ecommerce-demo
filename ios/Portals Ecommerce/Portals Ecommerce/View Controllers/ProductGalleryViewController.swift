import UIKit

class ProductGalleryViewController: UIViewController, ApplicationCoordinationParticipant, UICollectionViewDelegate {
    weak var coordinator: ApplicationCoordinator?
    
    @IBOutlet private weak var collectionView: UICollectionView!
    var viewModel: GalleryViewModel = GalleryViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
                
        viewModel.products = coordinator?.products ?? []
        viewModel.imageLoader = coordinator?.imageLoader
        viewModel.configure(with: collectionView)
    }
}
