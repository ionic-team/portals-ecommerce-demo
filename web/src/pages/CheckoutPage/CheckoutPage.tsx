import React, { useContext, useEffect, useState } from 'react';
import {
  IonButton,
  IonButtons,
  IonCheckbox,
  IonChip,
  IonContent,
  IonHeader,
  IonItem,
  IonLabel,
  IonList,
  IonListHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  useIonRouter,
} from '@ionic/react';
import { DataContext } from '../../DataProvider';
import './CheckoutPage.scss';
import { Address, CreditCard } from '../../models';

const CheckoutPage: React.FC = () => {
  const { cart, user, checkout } = useContext(DataContext);
  const [selectedAddress, setSelectedAddress] = useState<Address>();
  const [selectedCreditCard, setSelectedCreditCard] = useState<CreditCard>();
  const router = useIonRouter();

  useEffect(() => {
    if (user && !selectedAddress) {
      const address = user.addresses.find((x) => x.preferred);
      if (address) {
        setSelectedAddress(address);
      }
    }
    if (user && !selectedCreditCard) {
      const creditCard = user.creditCards.find((x) => x.preferred);
      if (creditCard) {
        setSelectedCreditCard(creditCard);
      }
    }
  }, [selectedAddress, selectedCreditCard, user]);

  return (
    <IonPage id="checkout-page">
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start">
            <IonButton
              onClick={() => {
                checkout({ result: 'cancel' });
              }}
            >
              Cancel
            </IonButton>
          </IonButtons>
          <IonTitle>Checkout</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        {cart && user && (
          <>
            <IonList lines="none">
              <IonListHeader>Delivery</IonListHeader>
              {user.addresses.map((address) => (
                <IonItem
                  button
                  detail={false}
                  key={address.id}
                  onClick={() => {
                    if (address.id !== selectedAddress?.id) {
                      setSelectedAddress(address);
                    }
                  }}
                >
                  <IonCheckbox
                    slot="start"
                    checked={address.id === selectedAddress?.id}
                  ></IonCheckbox>
                  <IonLabel>
                    {user.firstName} {user.lastName} <br />
                    {address.street} <br />
                    {address.city}, {address.state} {address.postal}
                  </IonLabel>
                  {address.preferred && (
                    <IonChip color="success">Default</IonChip>
                  )}
                  <IonButton
                    fill="clear"
                    slot="end"
                    onClick={(e) => {
                      e.stopPropagation();
                      router.push(`/address/${address.id}`);
                    }}
                  >
                    Edit
                  </IonButton>
                </IonItem>
              ))}
            </IonList>
            <IonButton
              expand="block"
              color="secondary"
              onClick={() => router.push('/address')}
            >
              New Address
            </IonButton>

            <IonList lines="none">
              <IonListHeader>Payment</IonListHeader>
              {user.creditCards.map((creditCard) => (
                <IonItem
                  button
                  detail={false}
                  key={creditCard.id}
                  onClick={() => {
                    if (creditCard.id !== selectedCreditCard?.id) {
                      setSelectedCreditCard(creditCard);
                    }
                  }}
                >
                  <IonCheckbox
                    slot="start"
                    checked={creditCard.id === selectedCreditCard?.id}
                  ></IonCheckbox>
                  <IonLabel>
                    {creditCard.company} ending in {creditCard.number.slice(-4)}
                  </IonLabel>
                  <IonButton
                    fill="clear"
                    slot="end"
                    onClick={(e) => {
                      e.stopPropagation();
                      router.push(`/payment/${creditCard.id}`);
                    }}
                  >
                    Edit
                  </IonButton>
                </IonItem>
              ))}
            </IonList>
            <IonButton
              expand="block"
              color="secondary"
              onClick={() => router.push('/payment')}
            >
              New Payment Method
            </IonButton>

            <IonList lines="none">
              <IonListHeader>Review total</IonListHeader>
              <IonItem>
                <IonLabel>${cart.subTotal} + Tax</IonLabel>
              </IonItem>
            </IonList>
            <IonButton
              className="order-button"
              expand="block"
              onClick={() => {
                checkout({ result: 'success' });
              }}
            >
              Place Your Order
            </IonButton>
          </>
        )}
      </IonContent>
    </IonPage>
  );
};

export default CheckoutPage;
