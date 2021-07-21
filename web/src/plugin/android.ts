import type { PortalsPlugin } from './definitions';
import { getInitialContext } from './shared';

export class PortalsAndroid implements PortalsPlugin {
  async echo(options: { value: string; }): Promise<{ value: string; }> {
    console.log('ECHO', options);
    return options;
  }

  async getInitialContext<T>() {
    return getInitialContext<T>();
  }
}
