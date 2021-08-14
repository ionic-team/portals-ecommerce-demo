import React from 'react';
import { IonApp, IonContent, IonHeader, IonPage, IonRouterOutlet, IonTitle, IonToolbar } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Redirect, Route } from 'react-router';
import { Link } from 'react-router-dom'
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
import { DataProvider } from './DataProvider';

interface HomeProps { }



const Home: React.FC<HomeProps> = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Home</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <div>
          <Link to="/address">Address</Link>&nbsp;
          <Link to="/checkout">Checkout</Link>&nbsp;
          <Link to="/help">Help</Link>&nbsp;
          <Link to="/payment">Payment</Link>&nbsp;
          <Link to="/user">User</Link>&nbsp;
        </div>
      </IonContent>
    </IonPage>
  );
};

interface AppProps {
  context: {
    startingRoute: string;
  };
}

const App: React.FC<AppProps> = ({ context }) => {
  return (
    <DataProvider>
      <IonApp>
        <IonReactRouter>
          <IonRouterOutlet>
            <Redirect path="/" exact to={context.startingRoute} />
            <Route path="/address" exact component={AddressPage} />
            <Route path="/address/:id" exact component={AddressPage} />
            <Route path="/checkout" exact component={CheckoutPage} />
            <Route path="/help" exact component={HelpPage} />
            <Route path="/payment" exact component={PaymentPage} />
            <Route path="/payment/:id" component={PaymentPage} />
            <Route path="/user" exact component={UserDetailPage} />
          </IonRouterOutlet>
        </IonReactRouter>
      </IonApp>
    </DataProvider>
  );
};

export default App;
