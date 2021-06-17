import { Capacitor } from '@capacitor/core';
import React, { useCallback, useEffect, useState } from 'react';
import { Cart, Data, Product, User } from './models';
import ShopAPI, { CheckoutResult } from './ShopAPIPlugin';

export interface DataState {
  loading: boolean;
  user?: User;
  setUser: (user: User) => void;
  products?: Product[];
  cart?: Cart;
  checkout: (result: CheckoutResult) => void;
}

export const DataContext = React.createContext<DataState>({} as any);

export const DataProvider: React.FC = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<User>();
  const [products, setProducts] = useState<Product[]>();
  const [cart, setCart] = useState<Cart>();

  useEffect(() => {
    async function init() {
      setIsLoading(true);
      const [user, products, cart] = await Promise.all([
        getUserDetails(),
        getProducts(),
        getCart()
      ]);
      setUser(user);
      setProducts(products);
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
        products,
        setUser: setUserData,
        cart,
        checkout: checkout
      }}
    >
      {children}
    </DataContext.Provider>
  );
};

async function getUserDetails(): Promise<User> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
    const response = await fetch('/data.json');
    const data = (await response.json()) as Data;
    return data.user;
  } else {
    // mock data for use in dev
    const response = await fetch('/data.json');
    const data = (await response.json()) as Data;
    return data.user;
  }
}

async function getProducts(): Promise<Product[]> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
    const response = await fetch('/data.json');
    const data = (await response.json()) as Data;
    return data.products;
  } else {
    // mock data for use in dev
    const response = await fetch('/data.json');
    const data = (await response.json()) as Data;
    return data.products;
  }
}

async function updateUserDetails(user: User): Promise<void> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
  } else {
    // noop for use in dev
  }
}

async function getCart(): Promise<Cart> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
    return ShopAPI.getCart()
  } else {
    // noop for use in dev
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
