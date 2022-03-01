import { WebPlugin } from '@capacitor/core';
import {
  User,
  CheckoutResult,
  UserPicture,
  ShopAPIPlugin,
  Data,
} from './definitions';

// This is an implementation that allows for usage when Capacitor
// recognizes it is being ran on the web platform.

// The following implementations in this app are just mocks so that
// Web developers can write their code outside of the native application
export class ShopAPIPluginWeb extends WebPlugin implements ShopAPIPlugin {
  async getCart() {
    await sleep(1000);
    return {
      id: 1,
      subTotal: 32.33,
      basket: [{ productId: 1, quantity: 1 }],
    };
  }

  async getUserDetails() {
    const response = await fetch('/data.json');
    await sleep(1000);
    const data = (await response.json()) as Data;
    return data.user;
  }

  async updateUserDetails(user: User) {}

  async checkout(result: CheckoutResult) {
    await sleep(1000);
    console.log('checkout: ', { result });
  }

  async checkoutResult(result: CheckoutResult) {}

  async getUserPicture() {
    await sleep(1000);
    return {
      picture: 'images/jt-avatar.png',
    };
  }

  async setUserPicture(picture: UserPicture) {}
}

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
