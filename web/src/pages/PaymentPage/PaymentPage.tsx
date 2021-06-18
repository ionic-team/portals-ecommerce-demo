import React, { useContext, useEffect, useState } from 'react';
import {
  IonBackButton,
  IonButton,
  IonButtons,
  IonCheckbox,
  IonCol,
  IonContent,
  IonDatetime,
  IonGrid,
  IonHeader,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonPage,
  IonRow,
  IonText,
  IonTitle,
  IonToolbar,
  useIonRouter,
} from '@ionic/react';
import { RouteComponentProps } from 'react-router';
import { DataContext } from '../../DataProvider';
import './PaymentPage.css';
import { CreditCard, User } from '../../models';

type PaymentPageMatch = {
  id: string;
};

const maskCreditCardNumber = (value: string) => {
  // Only change number if value is exactly 16 chars
  if (value.length >= 16) {
    return '**** **** **** ' + value.slice(-4);
  }

  return value;
};

const PaymentPage = (props: RouteComponentProps<PaymentPageMatch>) => {
  const { id } = props.match.params;
  const { user, setUser } = useContext(DataContext);
  const [creditCard, setCreditCard] = useState<CreditCard>();
  const router = useIonRouter();

  useEffect(() => {
    if (user) {
      if (id) {
        const cc = user.creditCards.find((x) => x.id === Number(id));
        setCreditCard(cc);
      } else {
        setCreditCard({
          id: 0,
          company: 'Visa',
          cvv: '',
          expirationDate: '',
          number: '',
          zip: '',
          preferred: false,
        });
      }
    }
  }, [id, user]);

  const handleSave = () => {
    if (user && creditCard) {
      let newUser: User;
      if (creditCard.id === 0) {
        creditCard.id =
          (user.creditCards.length > 0
            ? Math.max(...user.creditCards.map((x) => x.id))
            : 0) + 1;
        newUser = {
          ...user,
          creditCards: [...user.creditCards, creditCard],
        };
        setUser(newUser);
      } else {
        newUser = {
          ...user,
          creditCards: [
            ...user.creditCards.map((x) =>
              x.id === creditCard.id ? creditCard : x
            ),
          ],
        };
        setUser(newUser);
      }
      if (creditCard.preferred) {
        newUser.creditCards.forEach((x) => {
          if (x.id !== creditCard.id) {
            x.preferred = false;
          }
        });
      }
      if (router.canGoBack()) {
        router.goBack();
      }
    }
  };

  return (
    <IonPage>
      {user && creditCard && (
        <>
          <IonHeader>
            <IonToolbar>
              <IonTitle>
                {creditCard.id === 0 ? 'Add' : 'Edit'} Payment Method
              </IonTitle>
              <IonButtons slot="start">
                <IonBackButton text="Cancel" />
              </IonButtons>
            </IonToolbar>
          </IonHeader>
          <IonContent className="ion-padding">
            <IonList>
              <IonItem lines="none">
                <IonLabel position="stacked">Card Number</IonLabel>
                <IonInput
                  type="number"
                  pattern="[0-9]*"
                  maxlength={16}
                  placeholder="Card Number"
                  onIonFocus={() => {
                    if (user) {
                      setCreditCard({ ...creditCard, number: '' });
                    }
                  }}
                  onIonBlur={(event) => {
                    const number = (event.target as any).value as string;
                    if (!number.includes('*')) {
                      setCreditCard({ ...creditCard, number });
                    }
                  }}
                  value={maskCreditCardNumber(creditCard.number)}
                ></IonInput>
              </IonItem>
              <IonItem lines="none">
                <IonGrid>
                  <IonRow>
                    <IonCol>
                      <IonLabel position="stacked">Exp Date</IonLabel>
                      <IonDatetime
                        displayFormat="MM/YYYY"
                        min="2021"
                        max="2031"
                        placeholder="Exp Date"
                        value={creditCard.expirationDate}
                        onIonChange={(event) =>
                          setCreditCard({
                            ...creditCard,
                            expirationDate: event.detail.value!,
                          })
                        }
                      ></IonDatetime>
                    </IonCol>
                    <IonCol>
                      <IonLabel position="stacked">CVV</IonLabel>
                      <IonInput
                        placeholder="CVV"
                        type="number"
                        pattern="[0-9]*"
                        maxlength={4}
                        debounce={500}
                        onIonChange={(event) => {
                          setCreditCard({
                            ...creditCard,
                            cvv: event.detail.value!,
                          });
                        }}
                        value={creditCard.cvv}
                      ></IonInput>
                    </IonCol>
                  </IonRow>
                </IonGrid>
              </IonItem>
              <IonItem lines="none">
                <IonLabel position="stacked">Zip Code</IonLabel>
                <IonInput
                  placeholder="Zip Code"
                  type="number"
                  pattern="[0-9]*"
                  maxlength={5}
                  debounce={500}
                  onIonChange={(event) => {
                    setCreditCard({ ...creditCard, zip: event.detail.value! });
                  }}
                  value={creditCard.zip}
                ></IonInput>
              </IonItem>
              <IonItem lines="none">
                <IonCheckbox
                  checked={creditCard.preferred}
                  onIonChange={(e) => {
                    setCreditCard({
                      ...creditCard,
                      preferred: !creditCard.preferred,
                    });
                  }}
                />
                <IonText>Set as default payment method</IonText>
              </IonItem>
            </IonList>
            <IonButton expand="block" onClick={handleSave}>
              Save
            </IonButton>
          </IonContent>
        </>
      )}
    </IonPage>
  );
};

export default PaymentPage;
