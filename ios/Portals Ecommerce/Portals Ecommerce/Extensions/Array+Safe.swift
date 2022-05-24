//
//  Array+Safe.swift
//  Ecommerce
//
//  Created by Steven Sherry on 5/13/22.
//

extension Array {
    subscript(safe index: Index) -> Element? {
        guard index >= startIndex, index < endIndex else { return nil }
        return self[index]
    }
}
