import UIKit

class GalleryViewModel: NSObject, UICollectionViewDataSource {
    var products: [Product] = []
    var imageLoader: ProductImageLoaderProtocol?
    
    private enum Sections: Int, CaseIterable {
        case carousel, list
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return Sections.allCases.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        products.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if let section = Sections(rawValue: indexPath.section) {
            switch  section {
            case .carousel:
                if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ProductCarouselCollectionViewCell.cellIdentifier, for: indexPath) as? ProductCarouselCollectionViewCell {
                    cell.configure(with: products[indexPath.item], loader: imageLoader)
                    return cell
                }
            case .list:
                if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ProductCollectionViewCell.cellIdentifier, for: indexPath) as? ProductCollectionViewCell {
                    cell.configure(with: products[indexPath.item], loader: imageLoader)
                    return cell
                }
            }
        }
        
        assertionFailure("invalid cell request")
        return UICollectionViewCell()
    }
    
    func configure(with collectionView: UICollectionView) {
        collectionView.register(UINib(nibName: "ProductCarouselCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: ProductCarouselCollectionViewCell.cellIdentifier)
        collectionView.register(UINib(nibName: "ProductCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: ProductCollectionViewCell.cellIdentifier)
        collectionView.setCollectionViewLayout(collectionLayout(), animated: false)
        collectionView.dataSource = self
    }
    
    func collectionLayout() -> UICollectionViewLayout {
        let compositionalLayout = UICollectionViewCompositionalLayout {
            (sectionIndex: Int, layoutEnvironment: NSCollectionLayoutEnvironment) -> NSCollectionLayoutSection? in
            
            if sectionIndex == 0 {
                return self.carouselSection()
            }
            else {
                return self.listSection()
            }
        }
        return compositionalLayout
    }
    
    private func carouselSection() -> NSCollectionLayoutSection {
        // item
        let itemSize = NSCollectionLayoutSize(
            widthDimension: .absolute(ProductCarouselCollectionViewCell.fixedWidth),
            heightDimension: .estimated(ProductCarouselCollectionViewCell.estimatedHeight))
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        // group
        let groupSize = NSCollectionLayoutSize(
            widthDimension: .absolute(ProductCarouselCollectionViewCell.fixedWidth + 10),
            heightDimension: .estimated(ProductCarouselCollectionViewCell.estimatedHeight))
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, subitems: [item])
        group.interItemSpacing = .fixed(10)

//          let headerSize = NSCollectionLayoutSize(
//            widthDimension: .fractionalWidth(1.0),
//            heightDimension: .estimated(44))
//          let sectionHeader = NSCollectionLayoutBoundarySupplementaryItem(
//            layoutSize: headerSize,
//            elementKind: AlbumsViewController.sectionHeaderElementKind,
//            alignment: .top)
        
        // section
        let section = NSCollectionLayoutSection(group: group)
          //section.boundarySupplementaryItems = [sectionHeader]
        section.orthogonalScrollingBehavior = .continuous
        section.contentInsets = NSDirectionalEdgeInsets(top: 10, leading: 16, bottom: 0, trailing: 6)
        return section
    }
    
    private func listSection() -> NSCollectionLayoutSection {
        let itemSize = NSCollectionLayoutSize(widthDimension: .absolute(ProductCollectionViewCell.fixedWidth), heightDimension: .estimated(ProductCollectionViewCell.estimatedHeight))
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        // group
        let groupSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .estimated(ProductCollectionViewCell.estimatedHeight))
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, subitems: [item])
        group.interItemSpacing = .flexible(8)

        // section
        let section = NSCollectionLayoutSection(group: group)
        section.contentInsets = NSDirectionalEdgeInsets(top: 16, leading: 16, bottom: 0, trailing: 16)
        
        return section
    }
}
