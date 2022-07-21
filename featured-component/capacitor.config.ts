import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'io.ionic.portals.ecommercewebapp',
  appName: 'Portals Web App',
  webDir: 'build',
  bundledWebRuntime: false,
  ios: {
    scrollEnabled: false,
    contentInset: 'never',
  },
};

export default config;
