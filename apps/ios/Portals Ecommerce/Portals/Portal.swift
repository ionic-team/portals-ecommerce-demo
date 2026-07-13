//
//  Portal.swift
//  Ecommerce
//
//  Created by Ely Lucas on 7/22/21.
//

import Foundation


class Portal {
    var name: String
    var initialContext: Any?
    var startDir: String
    
    init(_ name: String, _ startDir: String?) {
        self.name = name
        self.startDir = startDir ?? name
    }
    

}
