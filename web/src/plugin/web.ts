import { WebPlugin } from '@capacitor/core';

import type { PortalsPlugin } from './definitions';
import { getInitialContext } from './shared';

export class PortalsWeb extends WebPlugin implements PortalsPlugin {
  async echo(options: { value: string; }): Promise<{ value: string; }> {
    console.log('ECHO', options);
    return options;
  }

  async getInitialContext<T>() {
    return getInitialContext<T>();
  }

}
