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
  userPhoto?: string;
  setUserPhoto: (photo: string) => void;
}

export const DataContext = React.createContext<DataState>({} as any);

export const DataProvider: React.FC = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<User>();
  const [cart, setCart] = useState<Cart>();
  const [userPhoto, setUserPhoto] = useState<string>();

  useEffect(() => {
    async function init() {
      setIsLoading(true);
      const [user, cart, photo] = await Promise.all([
        getUserDetails(),
        getCart(),
        getUserPicture(),
      ]);
      setUser(user);
      setCart(cart);
      setUserPhoto(photo);
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

  const setPhotoData = useCallback(async (photo: string) => {
    await setUserPicture(photo);
    setUserPhoto(photo);
  }, []);

  return (
    <DataContext.Provider
      value={{
        loading: isLoading,
        user,
        setUser: setUserData,
        cart,
        checkout: checkout,
        userPhoto,
        setUserPhoto: setPhotoData,
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
    await sleep(1000);
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
    await sleep(1000);
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
  if (Capacitor.isNativePlatform()) {
    const userPicture = await ShopAPI.getUserPicture();
    return userPicture.picture;
  } else {
    await sleep(1000);
    return 'images/jt-avatar.png';
  }
}

async function setUserPicture(picture: string) {
  if (Capacitor.isNativePlatform()) {
    return await ShopAPI.setUserPicture({ picture });
  }
}

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
