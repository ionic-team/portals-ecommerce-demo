//
//  CartItemView.swift
//  Ecommerce
//
//  Created by Steven Sherry on 2/15/22.
//

import SwiftUI

struct CartItemView: View {
    private let item: Cart.Item
    private let image: Image
    private let onSelectQuantity: (UInt) -> Void
    
    init(item: Cart.Item, image: Image, onSelectQuantity: @escaping (UInt) -> Void) {
        self.item = item
        self.image = image
        self.onSelectQuantity = onSelectQuantity
    }
    
    var body: some View {
        HStack(spacing: 16) {
            image
                .resizable()
                .frame(width: 108, height: 108, alignment: .center)
                .cornerRadius(12)
            
            VStack(alignment: .leading, spacing: 4) {
                Text(item.product.title).font(.title2)
                
                HStack {
                    Menu {
                        Picker(
                            "Choose a quantity",
                            selection: Binding<UInt>(
                                get: { item.quantity },
                                set: { onSelectQuantity($0) }
                            )
                        ) {
                            ForEach(Range<UInt>(uncheckedBounds: (1, 10)), id: \.self) {
                                Text("\($0)")
                            }
                        }
                    } label: {
                        HStack(spacing: 1) {
                            Text("Qty \(item.quantity)")
                            Image("quantity-dropdown-icon", bundle: .main)
                        }
                        .font(.callout.monospacedDigit())
                        .foregroundColor(.secondary)
                    }
                    
                    Spacer()
                    if let price = item.product.formattedPrice {
                        Text(price)
                    }
                }
                .font(.callout)
                .foregroundColor(.secondary)
                
            }
        }
    }
}

struct CartItem_Previews: PreviewProvider {
    static let item = Cart.Item(
        product: .init(
            id: 1,
            title: "Capacitor Snapback",
            description: "",
            price: 32,
            imageName: "capacitor-hat.png",
            category: .featured
        ),
        quantity: 1
    )
    
    static var previews: some View {
        CartItemView(
            item: item,
            image: Image(uiImage: ImageLoader().imageFor(item.product.imageName))
        ) { _ in }
    }
}
