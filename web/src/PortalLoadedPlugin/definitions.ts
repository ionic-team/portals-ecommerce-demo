export interface PortalLoadedPlugin {
  portalLoaded(): Promise<void>;
}
