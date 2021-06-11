import { registerPlugin } from "@capacitor/core";
import { Cart, User } from "./models";

export interface CheckoutResult {
  result: "success" | "cancel" | "failure";
}

export interface UserPicture {
  picture: string;
}

export interface ShopAPIPlugin {
  getCart(): Promise<Cart>;
  getUserDetails(): Promise<User>;
  updateUserDetails(user: User): Promise<void>;
  checkoutResult(result: CheckoutResult): Promise<void>;
  getUserPicture(): Promise<UserPicture>;
  setUserPicture(picture: UserPicture): Promise<void>;
}

const ShopAPI = registerPlugin<ShopAPIPlugin>("ShopAPI");
export default ShopAPI;
