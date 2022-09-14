//
//  FeaturedCell.swift
//  Portals Ecommerce
//
//  Created by Steven Sherry on 7/22/22.
//

import UIKit
import IonicPortals

class FeaturedCell: UICollectionViewCell {
    static let cellIdentifier = "FeaturedCell"

    override var reuseIdentifier: String? { Self.cellIdentifier }

    override init(frame: CGRect) {
        super.init(frame: frame)

        let portalView = PortalUIView(portal: .featured)
        portalView.translatesAutoresizingMaskIntoConstraints = false

        contentView.addSubview(portalView)

        NSLayoutConstraint.activate([
            portalView.topAnchor.constraint(equalTo: contentView.topAnchor),
            portalView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            portalView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor),
            portalView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
