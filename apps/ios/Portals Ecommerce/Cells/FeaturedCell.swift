//
//  FeaturedCell.swift
//  Portals Ecommerce
//
//  Created by Steven Sherry on 7/22/22.
//

import UIKit
import Combine
import IonicPortals

class FeaturedCell: UICollectionViewCell {
    static let cellIdentifier = "FeaturedCell"

    private let portalView = PortalUIView(portal: .featured)
    private var reloadCancellable: AnyCancellable?

    override var reuseIdentifier: String? { Self.cellIdentifier }

    override init(frame: CGRect) {
        super.init(frame: frame)

        portalView.translatesAutoresizingMaskIntoConstraints = false

        contentView.addSubview(portalView)

        NSLayoutConstraint.activate([
            portalView.topAnchor.constraint(equalTo: contentView.topAnchor),
            portalView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            portalView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor),
            portalView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor)
        ])

        reloadCancellable = NotificationCenter.default.publisher(for: .init("reload_featured"))
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in
                self?.portalView.reload()
            }
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
