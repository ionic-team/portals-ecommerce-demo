import React, { useContext } from 'react';
import {
  IonContent,
  IonList,
  IonButton,
  IonItem,
  IonLabel,
  IonNote,
  IonPage,
} from '@ionic/react';
import Portals from '@ionic/portals';
import { DataContext } from '../../DataProvider';
import FadeIn from '../../components/FadeIn';

import './CartTotalPage.scss';

const CartTotalPage: React.FC = () => {
  const { cart, user } = useContext(DataContext);

  return (
    <IonPage id="cart-total-page">
      <FadeIn isLoaded={user != null && cart != null}>
        <IonContent>
          {cart && user && (
            <>
              <IonList lines="none">
                <IonItem>
                  <IonLabel>Subtotal</IonLabel>
                  <IonNote slot="end">${cart.subTotal}</IonNote>
                </IonItem>
                <IonItem>
                  <IonLabel>Shipping</IonLabel>
                  <IonNote slot="end">Standard - Free</IonNote>
                </IonItem>
                <IonItem>
                  <IonLabel>Estimated total</IonLabel>
                  <IonNote slot="end">${cart.subTotal}</IonNote>
                </IonItem>
              </IonList>
              <IonButton
                className="checkout-button"
                expand="block"
                onClick={() => {
                  Portals.publish({ topic: 'showCheckout' });
                }}
              >
                Checkout
              </IonButton>
            </>
          )}
        </IonContent>
      </FadeIn>
    </IonPage>
  );
};

export default CartTotalPage;
