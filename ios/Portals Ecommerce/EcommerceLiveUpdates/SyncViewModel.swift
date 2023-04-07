//
//  SyncViewModel.swift
//  EcommerceLiveUpdates
//
//  Created by Steven Sherry on 3/17/23.
//

import IonicLiveUpdates
import SwiftUI

struct SyncResultCache {
    var buildId: String?
    var channel: String
    var snapshotId: String?
    var lastUpdated: Date
}

extension SyncResultCache {
    init(syncResult: LiveUpdateManager.SyncResult, date: Date) {
        self.buildId = syncResult.buildId
        self.channel = syncResult.channel.capitalized
        self.snapshotId = syncResult.id
        self.lastUpdated = date
    }
}

extension SyncResultCache: Codable {}

class SyncViewModel: ObservableObject {
    private lazy var lum = LiveUpdateManager.shared
    private lazy var encoder = JSONEncoder()
    private lazy var decoder = JSONDecoder()
    
    public var profileUpdateCache: SyncResultCache? {
        guard let data = _profileUpdateCache else { return nil }
        return try? decoder.decode(SyncResultCache.self, from: data)
    }
    
    public var helpUpdateCache: SyncResultCache? {
        guard let data = _helpUpdateCache else { return nil }
        return try? decoder.decode(SyncResultCache.self, from: data)
    }
    
    @AppStorage("profile_update_cache")
    private var _profileUpdateCache: Data?
    
    @AppStorage("help_update_cache")
    private var _helpUpdateCache: Data?
    
    @AppStorage("active_channel")
    var activeChannel: String = "production"
    
    func sync() {
        lum.sync(liveUpdates: [.webapp, .help]) {
            print("Sync Complete")
        } appComplete: { result in
            switch result {
            case .success(let syncResult):
                let cacheValue = SyncResultCache(
                    syncResult: syncResult,
                    date: Date()
                )
                
                let cacheData = try? self.encoder.encode(cacheValue)

                if syncResult.liveUpdate == .webapp {
                    NotificationCenter.default.post(name: .init("reload_profile"), object: nil)
                    DispatchQueue.main.async {
                        self._profileUpdateCache = cacheData
                    }
                }
                
                if syncResult.liveUpdate == .help {
                    DispatchQueue.main.async {
                        self._helpUpdateCache = cacheData
                    }
                }
            case .failure(let error):
                print("App sync failed with \(error.localizedDescription)")
            }
        }
    }
    
    func delete() {
        try? lum.reset()
        NotificationCenter.default.post(name: .init("reload_profile"), object: nil)
        _profileUpdateCache = nil
        _helpUpdateCache = nil
    }
}
