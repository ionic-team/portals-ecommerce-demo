import Capacitor

enum ShopAPICheckoutStatus: String {
    case completed = "success"
    case canceled = "cancel"
    case failed = "failure"
}

protocol ShopAPIDataProviderProtocol: AnyObject {
    var cart: Cart { get }
    var user: User { get set }
    var userImage: String { get set }
}

protocol ShopAPIActionDelegateProtocol: AnyObject {
    func completeCheckout(with status: ShopAPICheckoutStatus)
}

@objc (IONShopAPIPlugin)
class ShopAPIPlugin: CAPPlugin {
    weak var dataProvider: ShopAPIDataProviderProtocol?
    weak var actionDelegate: ShopAPIActionDelegateProtocol?
    
    @objc func getCart(_ call: CAPPluginCall) {
        guard let cart = dataProvider?.cart, let object = encode(cart) else {
            call.reject("Cart unavailable!")
            return
        }
        call.resolve(object)
    }
    
    @objc func getUserDetails(_ call: CAPPluginCall) {
        guard let user = dataProvider?.user, let object = encode(user) else {
            call.reject("User unavailable!")
            return
        }
        call.resolve(object)
    }
    
    @objc func updateUserDetails(_ call: CAPPluginCall) {
        guard let user: User = decode(object: call.jsObjectRepresentation) else {
            call.reject("Invalid user details!")
            return
        }
        call.resolve()
        dataProvider?.user = user
    }
    
    @objc func checkoutResult(_ call: CAPPluginCall) {
        guard let status = ShopAPICheckoutStatus(rawValue: call.getString("result", "")) else {
            call.reject("Missing result!")
            return
        }
        call.resolve()
        actionDelegate?.completeCheckout(with: status)
    }
    
    @objc func getUserPicture(_ call: CAPPluginCall) {
        guard let picture = dataProvider?.userImage else {
            call.reject("No picture available")
            return
        }
        call.resolve(["picture": picture])
    }
    
    @objc func setUserPicture(_ call: CAPPluginCall) {
        if let picture = call.getString("picture") {
            dataProvider?.userImage = picture
        }
        call.resolve()
    }
}

private func encode<T: Encodable>(_ object: T) -> JSObject? {
    do {
        let data = try JSONEncoder().encode(object)
        let dictionary = try JSONSerialization.jsonObject(with: data, options: []) as? NSDictionary
        if let object = JSTypes.coerceDictionaryToJSObject(dictionary) {
            return object
        }
    }
    catch {
        assertionFailure("Failed to encode object to JSON")
    }
    return nil
}

private func decode<T: Decodable>(object: JSObject) -> T? {
    do {
        let data = try JSONSerialization.data(withJSONObject: object, options: [])
        let result = try JSONDecoder().decode(T.self, from: data)
        return result
    }
    catch {
        print("failed to decode object from JSON:\(error)")
    }
    return nil
}
