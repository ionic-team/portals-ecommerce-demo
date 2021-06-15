import XCTest
@testable import Capacitor
@testable import Ecommerce

class ShopAPIPluginTests: XCTestCase {
    var plugin: ShopAPIPlugin?
    var coordinator: ApplicationCoordinator?
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        if let bridgeType = ShopAPIPlugin.self as? CAPBridgedPlugin.Type {
            plugin = ShopAPIPlugin(bridge: MockBridge(), pluginId: bridgeType.pluginId(), pluginName: "ShopAPI")
            coordinator = ApplicationCoordinator()
            plugin?.dataProvider = coordinator?.dataStore
        }
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        plugin = nil
        coordinator = nil
    }
    
    func testDemoData() throws {
        XCTAssertNotNil(plugin)
        XCTAssertNotNil(plugin?.dataProvider)
        XCTAssertEqual(coordinator?.dataStore.products.count, 15)
    }
    
    func testGetUser() throws {
        let call = CAPPluginCall(callbackId: "", options: [:]) { result, call in
            print(result ?? "")
        } error: { error in
            print(error ?? "")
            XCTAssert(false, "Call errored!")
        }
        plugin?.getUserDetails(call!)
    }
    
    func testSetUserFailure() throws {
        let call = CAPPluginCall(callbackId: "", options: [:]) { result, call in
            XCTAssert(false, "Call succeeded with bad data!")
        } error: { error in
            XCTAssertNotNil(error)
        }
        plugin?.updateUserDetails(call!)
    }
    
    func testGetCart() throws {
        let call = CAPPluginCall(callbackId: "", options: [:]) { result, call in
            print(result ?? "")
        } error: { error in
            print(error ?? "")
            XCTAssert(false, "Call errored!")
        }
        plugin?.getCart(call!)
    }
    
    func testGetPicture() throws {
        let call = CAPPluginCall(callbackId: "", options: [:]) { result, call in
            print(result ?? "")
        } error: { error in
            print(error ?? "")
            XCTAssert(false, "Call errored!")
        }
        plugin?.getUserPicture(call!)
    }
    
    func testSetPicture() throws {
        let setCall = CAPPluginCall(callbackId: "", options: ["picture":"testString"]) { result, call in
            print(result ?? "")
        } error: { error in
            print(error ?? "")
            XCTAssert(false, "Call errored!")
        }
        plugin?.setUserPicture(setCall!)
        
        let getCall = CAPPluginCall(callbackId: "", options: [:]) { result, call in
            guard case let .dictionary(data) = result?.resultData else {
                XCTAssert(false, "No result!")
                return
            }
            XCTAssertEqual(data["picture"] as? String, "testString")
        } error: { error in
            print(error ?? "")
            XCTAssert(false, "Call errored!")
        }
        plugin?.getUserPicture(getCall!)
    }
}
