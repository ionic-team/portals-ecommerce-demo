import { Capacitor } from '@capacitor/core';
import React, { useCallback, useEffect, useState } from 'react';
import { Cart, Data, User } from './models';
import ShopAPI, { CheckoutResult } from './ShopAPIPlugin';

export interface DataState {
  loading: boolean;
  user?: User;
  setUser: (user: User) => void;
  cart?: Cart;
  checkout: (result: CheckoutResult) => void;
  getUserPicture: () => Promise<string>;
}

export const DataContext = React.createContext<DataState>({} as any);

export const DataProvider: React.FC = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<User>();
  const [cart, setCart] = useState<Cart>();

  useEffect(() => {
    async function init() {
      setIsLoading(true);
      const [user, cart] = await Promise.all([
        getUserDetails(),
        getCart(),
      ]);
      setUser(user);
      setCart(cart);
      setIsLoading(false);
    }
    init();
  }, []);

  const setUserData = useCallback(async (user: User) => {
    setIsLoading(true);
    await updateUserDetails(user);
    setUser(user);
    setIsLoading(false);
  }, []);

  return (
    <DataContext.Provider
      value={{
        loading: isLoading,
        user,
        setUser: setUserData,
        cart,
        checkout: checkout,
        getUserPicture: getUserPicture
      }}
    >
      {children}
    </DataContext.Provider>
  );
};

async function getUserDetails(): Promise<User> {
  if (Capacitor.isNativePlatform()) {
    return ShopAPI.getUserDetails();
  } else {
    // mock data for use in dev
    const response = await fetch('/data.json');
    const data = (await response.json()) as Data;
    return data.user;
  }
}

async function updateUserDetails(user: User): Promise<void> {
  if (Capacitor.isNativePlatform()) {
    return ShopAPI.updateUserDetails(user);
  } else {
    // noop for use in dev
  }
}

async function getCart(): Promise<Cart> {
  if (Capacitor.isNativePlatform()) {
    return ShopAPI.getCart();
  } else {
    //test data for dev
    return {
      id: 1,
      subTotal: 32.33,
      basket: [{ productId: 1, quantity: 1 }],
    };
  }
}

async function checkout(result: CheckoutResult) {
  if (Capacitor.isNativePlatform()) {
    ShopAPI.checkoutResult(result);
  } else {
    console.log('checkout: ', { result });
  }
}

async function getUserPicture() {
  const userPicture = await ShopAPI.getUserPicture();
  return userPicture.picture;
}
