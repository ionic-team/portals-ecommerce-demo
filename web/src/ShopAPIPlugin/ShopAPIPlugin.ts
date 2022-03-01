import { registerPlugin } from '@capacitor/core';
import { ShopAPIPlugin } from './definitions';

const ShopAPI = registerPlugin<ShopAPIPlugin>('ShopAPI', {
  web: () => import('./ShopAPIPluginWeb').then((m) => new m.ShopAPIPluginWeb()),
});

export default ShopAPI;
