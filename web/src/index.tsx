import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import { getInitialContext } from '@ionic/portals';

const initialContext = getInitialContext<{ startingRoute: string }>()
  ?.value ?? { startingRoute: '/' };
const rootElement = document.getElementById('root');

if (!rootElement) {
  throw new Error('Root element not found');
}

createRoot(rootElement).render(
  <React.StrictMode>
    <App context={initialContext} />
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
