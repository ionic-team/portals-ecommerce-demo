import UIKit

class GalleryViewModel: NSObject, UICollectionViewDataSource {
    var carouselProducts: [Product] = []
    var listProducts: [Product] = []
    var imageLoader: ImageLoaderProtocol?
    
    private enum Sections: Int, CaseIterable {
        case carousel, list
    }
    
    // MARK: - Public
    
    func configure(with collectionView: UICollectionView) {
        collectionView.register(UINib(nibName: "ProductCarouselCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: ProductCarouselCollectionViewCell.cellIdentifier)
        collectionView.register(UINib(nibName: "ProductCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: ProductCollectionViewCell.cellIdentifier)
        collectionView.register(UINib(nibName: "GallerySectionHeaderView", bundle: nil), forSupplementaryViewOfKind: "header", withReuseIdentifier: GallerySectionHeaderView.viewIdentifier)
        collectionView.setCollectionViewLayout(collectionLayout(), animated: false)
        collectionView.dataSource = self
    }
    
    func product(at indexPath: IndexPath) -> Product? {
        if let section = Sections(rawValue: indexPath.section) {
            switch section {
            case .carousel:
                if indexPath.item < carouselProducts.count {
                    return carouselProducts[indexPath.item]
                }
            case .list:
                if indexPath.item < listProducts.count {
                    return listProducts[indexPath.item]
                }
            }
        }
        return nil
    }
    
    // MARK: - UICollectionViewDataSource
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return Sections.allCases.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        switch Sections(rawValue: section)! {
        case .carousel:
            return carouselProducts.count
        case .list:
            return listProducts.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        switch Sections(rawValue: indexPath.section)! {
        case .carousel:
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ProductCarouselCollectionViewCell.cellIdentifier, for: indexPath) as? ProductCarouselCollectionViewCell {
                cell.configure(with: carouselProducts[indexPath.item], loader: imageLoader)
                return cell
            }
        case .list:
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ProductCollectionViewCell.cellIdentifier, for: indexPath) as? ProductCollectionViewCell {
                cell.configure(with: listProducts[indexPath.item], loader: imageLoader)
                return cell
            }
        }
        
        assertionFailure("invalid cell request")
        return UICollectionViewCell()
    }
    
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        
        guard let headerView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: GallerySectionHeaderView.viewIdentifier, for: indexPath) as? GallerySectionHeaderView else {
            return GallerySectionHeaderView()
        }
        
        switch Sections(rawValue: indexPath.section)! {
        case .carousel:
            headerView.title = ProductCategory.mustHaves.title
        case .list:
            headerView.title = ProductCategory.featured.title
        }
        return headerView
    }
    
    // MARK: - Internal
    
    private func collectionLayout() -> UICollectionViewLayout {
        let compositionalLayout = UICollectionViewCompositionalLayout {
            (sectionIndex: Int, layoutEnvironment: NSCollectionLayoutEnvironment) -> NSCollectionLayoutSection? in
            
            switch Sections(rawValue: sectionIndex)! {
            case .carousel:
                return self.carouselSection()
            case .list:
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
        
        // header
        let headerItemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .estimated(44))
        let headerItem = NSCollectionLayoutBoundarySupplementaryItem(layoutSize: headerItemSize, elementKind: "header", alignment: .top)
        
        // section
        let section = NSCollectionLayoutSection(group: group)
        section.boundarySupplementaryItems = [headerItem]
        section.orthogonalScrollingBehavior = .continuous
        section.contentInsets = NSDirectionalEdgeInsets(top: 10, leading: 16, bottom: 0, trailing: 6)
        return section
    }
    
    private func listSection() -> NSCollectionLayoutSection {
        // item
        let itemSize = NSCollectionLayoutSize(widthDimension: .absolute(ProductCollectionViewCell.fixedWidth), heightDimension: .estimated(ProductCollectionViewCell.estimatedHeight))
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        // group
        let groupSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .estimated(ProductCollectionViewCell.estimatedHeight))
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, subitems: [item])
        group.interItemSpacing = .flexible(8)
        
        // header
        let headerItemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .estimated(44))
        let headerItem = NSCollectionLayoutBoundarySupplementaryItem(layoutSize: headerItemSize, elementKind: "header", alignment: .top)
        
        // section
        let section = NSCollectionLayoutSection(group: group)
        section.boundarySupplementaryItems = [headerItem]
        section.contentInsets = NSDirectionalEdgeInsets(top: 16, leading: 16, bottom: 0, trailing: 16)
        
        return section
    }
}
