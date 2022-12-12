import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { getInitialContext } from '@ionic/portals';
import { registerOnLoad } from './PortalLoadedPlugin';

let initialContextValue = getInitialContext<{ startingRoute: string }>()
  ?.value ?? { startingRoute: '/' };
registerOnLoad();

ReactDOM.render(
  <React.StrictMode>
    <App context={initialContextValue} />
  </React.StrictMode>,
  document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
