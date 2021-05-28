import { Capacitor } from '@capacitor/core';
import React, { useCallback, useEffect, useState } from 'react';
import { Cart, Data, Product, User } from './models';

export interface DataState {
  loading: boolean;
  user?: User;
  setUser: (user: User) => void;
  products?: Product[];
  cart?: Cart;
}

export const DataContext = React.createContext<DataState>({} as any);

export const DataProvider: React.FC = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<User>();
  const [products, setProducts] = useState<Product[]>();
  const [cart] = useState<Cart>({
    id: 1,
    basket: []
  })

  useEffect(() => {
    async function init() {
      setIsLoading(true);
      const [user, products] = await Promise.all([getUserDetails(), getProducts()]);
      setUser(user);
      setProducts(products);
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
        cart
      }}
    >
      {children}
    </DataContext.Provider>
  );
};

async function getUserDetails(): Promise<User> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
    return {} as any;
  } else {
    // mock data for use in dev
    const response = await fetch('/data.json');
    const data = await response.json() as Data;
    return data.user;
  }
}

async function getProducts(): Promise<Product[]> {
  if (Capacitor.isNativePlatform()) {
    // todo: make calls into native
    return {} as any;
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


