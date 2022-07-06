import UIKit
import IonicLiveUpdates

class SyncPageViewController: UIViewController {
    @IBOutlet weak var profileAppUILabel: UILabel!
    @IBOutlet weak var helpAppUILabel: UILabel!
    @IBOutlet weak var lastCheckProfileAppUILabel: UILabel!
    @IBOutlet weak var lastCheckHelpAppUILabel: UILabel!
    @IBOutlet weak var activeChannelUILabel: UILabel!
    
    private var activeChannel: String = UserDefaults.standard.string(forKey: "active_channel") ?? "production"
    private var appIds: [String] = [profileAppId, helpAppId]
    private static let profileAppId: String = "186b544f"
    private static let helpAppId: String = "a81b2440"
    
    private lazy var lum = LiveUpdateManager.shared
    
    override func viewDidLoad() {
        activeChannelUILabel.text = activeChannel.capitalized
        UserDefaults.standard.set("HEYYOOO", forKey: "MyAppKey")
    }
    
    @IBAction func deleteLiveUpdates(_ sender: Any) {
        try? lum.reset()
        NotificationCenter.default.post(name: .init(rawValue: "reload_profile"), object: nil)
        self.profileAppUILabel.text = "NOT UPDATED"
        self.lastCheckProfileAppUILabel.text = "N/A"
        self.helpAppUILabel.text = "NOT UPDATED"
        self.lastCheckHelpAppUILabel.text = "N/A"
    }
    
    @IBAction func syncLiveUpdates(_ sender: Any) {
        lum.sync(appIds: appIds) { //, channel: activeChannel) {
            print("Sync complete")
        } appComplete: { result in
            switch result {
            case .success(let liveUpdate):
                if liveUpdate.appId == Self.profileAppId {
                    NotificationCenter.default.post(name: .init(rawValue: "reload_profile"), object: nil)
                    DispatchQueue.main.async {
                        self.profileAppUILabel.text = "UPDATED"
                        self.lastCheckProfileAppUILabel.text = Date().description
                    }
                }
                
                if liveUpdate.appId == Self.helpAppId {
                    DispatchQueue.main.async {
                        self.helpAppUILabel.text = "UPDATED"
                        self.lastCheckHelpAppUILabel.text = Date().description
                    }
                }
            case .failure(let failureStep):
                print("App Sync failed at \(failureStep) step")
            }
        }
    }
    
    @IBAction func setProductionAsActiveChannel(_ sender: Any) {
        activeChannel = "production"
        UserDefaults.standard.set(activeChannel, forKey: "active_channel")
        activeChannelUILabel.text = activeChannel.capitalized
    }
    
    @IBAction func setDevelopmentAsActiveChannel(_ sender: Any) {
        activeChannel = "development"
        UserDefaults.standard.set(activeChannel, forKey: "active_channel")
        activeChannelUILabel.text = activeChannel.capitalized
    }
}
