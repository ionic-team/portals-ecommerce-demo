import React, { useContext, useEffect, useMemo, useState } from 'react';
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

  const dateString = useMemo(() => {
    if (creditCard) {
      const date = new Date(creditCard.expirationDate);
      return `${date.getMonth()}/${date.getFullYear()}`;
    }
  }, [creditCard]);

  if (!user || !creditCard) {
    return null;
  }

  return (
    <IonPage>
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
          {creditCard.id > 0 ? (
            <>
              <IonItem lines="none">
                <IonText slot="start">{creditCard.company}</IonText>
              </IonItem>
              <IonItem lines="none">
                <IonText slot="start">Card last 4 digits</IonText>
                <IonText slot="end">{creditCard.number.slice(-4)}</IonText>
              </IonItem>
              <IonItem lines="none">
                <IonLabel slot="start">Card exp date</IonLabel>
                <IonText slot="end">{dateString}</IonText>
              </IonItem>
            </>
          ) : (
            <>
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
                  value={creditCard.number}
                ></IonInput>
              </IonItem>
              <IonItem lines="none">
                <IonGrid>
                  <IonRow>
                    <IonCol>
                      <IonLabel position="stacked">Exp Date</IonLabel>
                      <IonDatetime
                        displayFormat="MM/YYYY"
                        min={new Date().toISOString()}
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
            </>
          )}
          <IonItem lines="none">
            <IonLabel position="stacked">Zip Code</IonLabel>
            <IonInput
              placeholder="Zip Code"
              type="number"
              pattern="[0-9]*"
              maxlength={5}
              debounce={500}
              onIonChange={(event) => {
                setCreditCard({
                  ...creditCard,
                  zip: event.detail.value!,
                });
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
    </IonPage>
  );
};

export default PaymentPage;
