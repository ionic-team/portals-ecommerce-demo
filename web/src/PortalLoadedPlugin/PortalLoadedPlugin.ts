import { registerPlugin } from '@capacitor/core';
import { PortalLoadedPlugin } from './definitions';

const PortalLoaded = registerPlugin<PortalLoadedPlugin>('PortalLoaded', {
  web: { portalLoaded: () => {} },
});

export const registerOnLoad = () => {
  const onPageLoad = async () => {
    PortalLoaded.portalLoaded();
  };

  // Check if the page has already loaded
  if (document.readyState === 'complete') {
    onPageLoad();
  } else {
    window.addEventListener('load', onPageLoad);
    return () => window.removeEventListener('load', onPageLoad);
  }
};
