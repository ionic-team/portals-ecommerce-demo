import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
// import { getInitialContext } from './util/getInitialContext';
// import reportWebVitals from './reportWebVitals';
import { Portals } from 'ionic-portals';


// const context = Portals.getInitialContext();

//  const contextFromIndex = (window as any).portalInitialContext;

// console.log('stuff: context in index: ' + JSON.stringify(context))
// console.log(
//   'stuff: contextFromIndex in index: ' + JSON.stringify(contextFromIndex)
// );


Portals.getInitialContext().then((context) => {
  ReactDOM.render(
    <React.StrictMode>
      <App context={context.value as any} />
    </React.StrictMode>,
    document.getElementById('root')
  );
});

// checkForContext();

// function checkForContext() {
//   if ((window as any).portalInitialContext) {
//     render();
//   } else {
//     console.log('stuff: not ready yet');
//     setTimeout(checkForContext, 100);
//   }
// }

// function render() {

// }

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
