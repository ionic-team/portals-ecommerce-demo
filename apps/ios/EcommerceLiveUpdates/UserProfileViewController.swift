//
//  UserProfileViewController.swift
//  EcommerceLiveUpdates
//
//  Created by Steven Sherry on 10/20/22.
//

import UIKit
import Combine
import IonicPortals

class UserProfileViewController: UIViewController {
    private var reloadCancellable: AnyCancellable?
    
    override func loadView() {
        self.view = PortalUIView(portal: .user)

        reloadCancellable = NotificationCenter.default.publisher(for: .init("reload_profile"))
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in
                guard let self = self,
                      let portalView = self.view as? PortalUIView,
                      self.tabBarController?.selectedViewController != self // We don't want to jar our users if they are on this screen
                else { return }
                print(portalView.canBecomeFocused)

                portalView.reload()
            }
    }
}
