//
//  SyncView.swift
//  EcommerceLiveUpdates
//
//  Created by Steven Sherry on 3/17/23.
//

import SwiftUI

struct SyncView: View {
    @ObservedObject var viewModel: SyncViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 30) {
            WebAppStatusView(
                profileState: viewModel.profileUpdateCache,
                helpState: viewModel.helpUpdateCache
            )

            LiveUpdatesManagementView {
                viewModel.delete()
            } syncTapped: {
                viewModel.sync()
            }

            ChannelManagementView(activeChannel: viewModel.activeChannel) {
                viewModel.activeChannel = "production"
            } developmentTapped: {
                viewModel.activeChannel = "Development"
            }

            Spacer()
        }
        .padding()
    }
}

struct WebAppStatusView: View {
    let profileState: SyncResultCache?
    let helpState: SyncResultCache?

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Web App Status")
                .font(.system(size: 20, weight: .semibold))
            LiveUpdateView(
                label: "Profile and Checkout Apps",
                state: profileState
            )
            LiveUpdateView(
                label: "Help Web App",
                state: helpState
            )
        }
        
    }
}

struct LiveUpdateView: View {
    private let label: String
    private let state: SyncResultCache?
    
    init(label: String, state: SyncResultCache?) {
        self.label = label
        self.state = state
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(label)
                .font(.system(size: 16))
            VStack(alignment: .leading, spacing: 4) {
                switch state {
                case .none:
                    Text("NOT UPDATED")
                case let .some(result):
                    HStack {
                        Text("Channel")
                        Spacer()
                        Text(result.channel)
                    }
                    HStack {
                        Text("Snapshot Id")
                        Spacer()
                        Text(result.snapshotId ?? "No snapshot available")
                    }
                    HStack {
                        Text("Build Id")
                        Spacer()
                        Text(result.buildId ?? "No build id available")
                    }
                }
                
                HStack {
                    Text("Last Checked")
                    Spacer()
                    if case let .some(cache) = state {
                        Text(cache.lastUpdated.description)
                    } else {
                        Text("N/A")
                    }
                }
            }
            .font(.system(size: 12, weight: .thin))
        }
    }
}

struct LiveUpdatesManagementView: View {
    let deleteTapped: () -> Void
    let syncTapped: () -> Void
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Live Updates Management")
                .font(.system(size: 20, weight: .semibold))
            HStack {
                Text("Delete Downloaded Updates")
                    .font(.system(size: 16))
                Spacer()
                Button("Delete", action: deleteTapped)
                    .liveUpdateManagementButton()
                    .tint(.blue)
            }
            HStack {
                Text("Sync Live Updates")
                    .font(.system(size: 16))
                Spacer()
                Button("Sync", action: syncTapped)
                    .liveUpdateManagementButton()
                    .tint(.blue)
            }
        }
    }
}

struct ChannelManagementView: View {
    let activeChannel: String?
    let productionTapped: () -> Void
    let developmentTapped: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Channel Management")
                .font(.system(size: 20, weight: .semibold))
            HStack {
                Text("Active Channel")
                Spacer()
                Text(activeChannel?.capitalized ?? "---")
                    .font(.system(size: 12, weight: .thin))
            }
            .font(.system(size: 16))
            
            Button("Production", action: productionTapped)
                .liveUpdateManagementButton()
                .tint(.red)

            Button("Development", action: developmentTapped)
                .liveUpdateManagementButton()
                .tint(.green)
        }
    }
}

extension Button {
    func liveUpdateManagementButton() -> some View {
        modifier(ChannelButtonModifier())
    }
}

struct ChannelButtonModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .frame(height: 28)
            .buttonStyle(.borderedProminent)
            .cornerRadius(16)
            .font(.system(size: 15))
    }
}

struct SyncView_Previews: PreviewProvider {
    static var previews: some View {
        SyncView(viewModel: SyncViewModel())
    }
}
