//
//  PubSubTest.swift
//  Portals Ecommerce
//
//  Created by Steven Sherry on 4/6/23.
//

import SwiftUI
import IonicPortals
import Capacitor
import Combine

struct PubSubTest: View {
    @State var messageText: String = "Hello world"
    @State var messageFromPortal: String = ""
    var body: some View {
        VStack {
            HStack {
                TextField("Text to Publish", text: $messageText)
                Button("Publish") {
                    PortalsPubSub.publish(messageText, to: Portal.pubsub.name)
                }
            }
            .padding()
            
            HStack {
                Text("Message from Portal")
                Text(messageFromPortal)
            }
            .padding()
            
            PortalView(portal: .pubsub)
        }
        .onReceive(PortalsPubSub.publisher(for: "sayHi").data(as: [String: JSValue].self).receive(on: DispatchQueue.main)) { received in
            messageFromPortal = received?["message"] as? String ?? ""
        }
    }
}

struct PubSubTest_Previews: PreviewProvider {
    static var previews: some View {
        PubSubTest()
    }
}
