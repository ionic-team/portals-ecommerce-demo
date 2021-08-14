import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { Portals } from '@native-portal/portals';
// import reportWebVitals from './reportWebVitals';
import { Portals } from '@ionic/portals';

Portals.getInitialContext<{ startingRoute: string; }>().then((context) => {
  ReactDOM.render(
    <React.StrictMode>
      <App context={context.value}/>
    </React.StrictMode>,
    document.getElementById('root')
  );
})


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
