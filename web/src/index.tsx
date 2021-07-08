import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
// import reportWebVitals from './reportWebVitals';

checkForContext();

function checkForContext() {
  if ((window as any).portalInitialContext) {
    render();
  } else {
    setTimeout(checkForContext, 100);
  }
}

function render() {
  const context = (window as any).portalInitialContext;
  ReactDOM.render(
    <React.StrictMode>
      <App context={context} />
    </React.StrictMode>,
    document.getElementById('root')
  );
}



// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
