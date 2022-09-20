import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { getInitialContext } from '@ionic/portals';
import { Capacitor } from '@capacitor/core';
// import reportWebVitals from './reportWebVitals';

if (!Capacitor.isNativePlatform()) {
  // do something
  (window as any).portalInitialContext = {
    value: { startingRoute: '/' },
  };
}

const initialContext = getInitialContext<{ startingRoute: string }>();

ReactDOM.render(
  <React.StrictMode>
    <App context={initialContext?.value} />
  </React.StrictMode>,
  document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
