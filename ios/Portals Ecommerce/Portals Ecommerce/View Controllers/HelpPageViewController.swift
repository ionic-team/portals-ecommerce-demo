import UIKit
import IonicPortals
import Combine
import Capacitor


class HelpPageViewController: UIViewController {
    lazy var initialView: PortalUIView = {
        let view = PortalUIView(portal: .help.adding(PortalLoadedPlugin{[weak self] in
            DispatchQueue.main.async {
                UIView.animate(withDuration: 0.5) {
                  self?.initialView.alpha = 1.0
              }
            }
        }).adding(ShopAPIPlugin.self))
        view.alpha = 0
        return view
    }()
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        initialView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(initialView)
        NSLayoutConstraint.activate([
            initialView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            initialView.topAnchor.constraint(equalTo: view.topAnchor),
            initialView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            initialView.trailingAnchor.constraint(equalTo: view.trailingAnchor)
        ])
    }
}

class PortalLoadedPlugin: CAPInstancePlugin, CAPBridgedPlugin {
    static func pluginId() -> String! {
        "PortalLoadedPlugin"
    }
    
    static func jsName() -> String! {
        "PortalLoaded"
    }
    static let methods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "portalLoaded", returnType: CAPPluginReturnPromise)
    ]
    static func pluginMethods() -> [Any]! {
        methods
    }
    
    static func getMethod(_ methodName: String!) -> CAPPluginMethod? {
        methods.first {$0.name == methodName}
    }
    
    let onPortalLoaded: () -> Void
    init(onPortalLoaded: @escaping () -> Void) {
        self.onPortalLoaded = onPortalLoaded
        super.init()
    }
    @objc
    func portalLoaded(_ call: CAPPluginCall) {
        onPortalLoaded()
        call.resolve()
    }
}
