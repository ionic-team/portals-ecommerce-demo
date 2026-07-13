import React, { useCallback, useEffect, useState } from 'react';
import {
  ShopAPI,
  CheckoutResult,
  User,
  Cart,
  Data,
  Product,
} from './ShopAPIPlugin';

export interface DataState {
  loading: boolean;
  user?: User;
  setUser: (user: User) => void;
  cart?: Cart;
  checkout: (result: CheckoutResult) => void;
  userPhoto?: string;
  setUserPhoto: (photo: string) => void;
  productList: Product[];
}

export const DataContext = React.createContext<DataState>({} as any);

export const DataProvider: React.FC = ({ children }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<User>();
  const [cart, setCart] = useState<Cart>();
  const [userPhoto, setUserPhoto] = useState<string>();
  const [productList, setProductList] = useState<Product[]>([]);

  useEffect(() => {
    async function init() {
      setIsLoading(true);
      const [user, cart, photo, products] = await Promise.all([
        getUserDetails(),
        getCart(),
        getUserPicture(),
        getProductList(),
      ]);
      setUser(user);
      setCart(cart);
      setProductList(products);
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
        productList,
      }}
    >
      {children}
    </DataContext.Provider>
  );
};

async function getProductList(): Promise<Product[]> {
  const response = await fetch('/data.json');
  const data = (await response.json()) as Data;
  return data.products;
}

async function getUserDetails(): Promise<User> {
  return ShopAPI.getUserDetails();
}

async function updateUserDetails(user: User): Promise<void> {
  return ShopAPI.updateUserDetails(user);
}

async function getCart(): Promise<Cart> {
  return ShopAPI.getCart();
}

async function checkout(result: CheckoutResult) {
  ShopAPI.checkoutResult(result);
}

async function getUserPicture() {
  const userPicture = await ShopAPI.getUserPicture();
  return userPicture.picture;
}

async function setUserPicture(picture: string) {
  return await ShopAPI.setUserPicture({ picture });
}
