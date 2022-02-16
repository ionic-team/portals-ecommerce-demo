//
//  CartListView.swift
//  Ecommerce
//
//  Created by Steven Sherry on 2/15/22.
//

import SwiftUI
import IonicPortals

struct CartListView: View {
    @ObservedObject private var viewModel: CartViewModel
    
    init(viewModel: CartViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        if viewModel.contents.isEmpty {
           Text("Your cart is empty")
                .font(.title)
        } else {
            List {
                ForEach(viewModel.contents) { item in
                    CartItemView(
                        item: item,
                        image: Image(uiImage: viewModel.image(named: item.product.imageName)),
                        onSelectQuantity: { quantity in
                            viewModel.update(product: item.product, with: quantity)
                        }
                    )
                    .listRowSeparator(.hidden)
                }
                .onDelete(perform: viewModel.deleteProducts)
                
                CartFooterView(total: viewModel.cartTotal) {
                    viewModel.shouldDisplayCheckout = true
                }
                .buttonStyle(.plain)
                .padding(.top, 16)
            }
            .listStyle(.plain)
            .sheet(
                isPresented: $viewModel.shouldDisplayCheckout,
                onDismiss: { viewModel.unsubscribleFromPortal() }
            ) {
                makeCheckoutPortal()
            }
        }
    }
    
    func makeCheckoutPortal() -> some View {
        let portal = try! PortalManager.getPortal("checkout")
        portal.initialContext = ["startingRoute": "/checkout"]
        
        let portalWebView = PortalUIWebView(portal, onBridgeAvailable: viewModel.handle(bridge:))
        
        return portalWebView
    }
}

extension Cart.Item: Identifiable {
    var id: Int { product.id }
}

struct CartListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            CartListView(viewModel: .debug)
                .navigationTitle("Cart")
                .navigationBarTitleDisplayMode(.large)
        }
        .onAppear {
            // Register Portals
            PortalManager.register("YOUR KEY HERE");
            
            // Setup Ionic Portals
            let checkoutPortal = Portal("checkout", "portals/shopwebapp")
            PortalManager.addPortal(checkoutPortal)
        }
    }
}

extension Array {
    subscript(safe index: Index) -> Element? {
        guard index >= startIndex, index < endIndex else { return nil }
        return self[index]
    }
}
