//
//  PortalManager.swift
//  Ecommerce
//
//  Created by Ely Lucas on 7/22/21.
//

import Foundation

class PortalManager {
    static var portals: [String:Portal] = [:]
    
    static func addPortal(_ portal: Portal) {
        portals[portal.name] = portal
    }
    
    static func getPortal(_ name: String) -> Portal? {
        return portals[name]
    }
}
