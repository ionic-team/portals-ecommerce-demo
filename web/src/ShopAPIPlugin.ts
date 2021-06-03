import { registerPlugin } from "@capacitor/core";
import { Cart, User } from "./models";

export interface ShopAPIPlugin {
  getCart(): Promise<Cart>;
  getUserDetails(): Promise<User>;
  updateUserDetails(user: User): Promise<void>;
  checkoutResult(status: "success" | "cancel" | "failure"): Promise<void>;
  getUserPicture(): Promise<string>;
  setUserPicture(picture: string): Promise<void>;
}

const ShopAPI = registerPlugin<ShopAPIPlugin>("ShopAPI");
export default ShopAPI;
