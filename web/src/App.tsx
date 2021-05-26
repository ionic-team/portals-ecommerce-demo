import React from 'react';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Route } from 'react-router';
import { AddressPage } from './pages/AddressPage';
import { CheckoutPage } from './pages/CheckoutPage';
import { HelpPage } from './pages/HelpPage';
import { PaymentPage } from './pages/PaymentPage';
import { UserDetailPage } from './pages/UserDetailPage';
import './App.css';

// Temp component for testing
const Home = () => {
  return <div>
    <a href="/address">Address</a>&nbsp;
    <a href="/checkout">Checkout</a>&nbsp;
    <a href="/help">Help</a>&nbsp;
    <a href="/payment">Payment</a>&nbsp;
    <a href="/user">User</a>&nbsp;
  </div>
}

function App() {
  return (
    <IonApp>
      <IonReactRouter>
        <IonRouterOutlet>
          <Route path="/" exact component={Home} />
          <Route path="/address" exact component={AddressPage} />
          <Route path="/checkout" exact component={CheckoutPage} />
          <Route path="/help" exact component={HelpPage} />
          <Route path="/payment" exact component={PaymentPage} />
          <Route path="/user" exact component={UserDetailPage} />
        </IonRouterOutlet>
      </IonReactRouter>
    </IonApp>
  );
}

export default App;
