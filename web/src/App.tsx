import React from 'react';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Route } from 'react-router';
import { AddressPage } from './pages/AddressPage';
import { CheckoutPage } from './pages/CheckoutPage';
import { HelpPage } from './pages/HelpPage';
import { PaymentPage } from './pages/PaymentPage';
import { UserDetailPage } from './pages/UserDetailPage';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';

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
