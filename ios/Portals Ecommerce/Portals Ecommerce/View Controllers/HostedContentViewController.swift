import UIKit
import Capacitor

class HostedContentViewController: CAPBridgeViewController {
    private(set) var isObservingWebLoading: Bool = false

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    func webViewCompletedInitialLoad() {
    }
    
    // MARK: - KVO
    
    override public func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        guard let _ = object as? WKWebView else {
            return
        }
        guard let keyPath = keyPath, keyPath == #keyPath(WKWebView.isLoading) else {
            return
        }
        guard let change = change,
              let newValue = change[NSKeyValueChangeKey.newKey] as? Bool,
              let oldValue = change[NSKeyValueChangeKey.oldKey] as? Bool,
              oldValue && !newValue else {
            return
        }
        // initial load is complete, remove observation
        unobserveWebView()
        webViewCompletedInitialLoad()
    }
    
    // MARK: - Internal
    
    // registers as a KVO observer of the web view's loading state
    private func observeWebView() {
        if let webview = webView, !isObservingWebLoading {
            webview.addObserver(self, forKeyPath: #keyPath(WKWebView.isLoading), options: [.new, .old], context: nil)
            isObservingWebLoading = true
        }
    }
    
    // unregisters from KVO notifications of the webview's loading state
    private func unobserveWebView() {
        if isObservingWebLoading {
            webView?.removeObserver(self, forKeyPath: #keyPath(WKWebView.isLoading))
            isObservingWebLoading = false
        }
    }

}
