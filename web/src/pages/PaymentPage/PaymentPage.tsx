import React, { useContext, useEffect, useState } from 'react';
import { IonBackButton, IonButton, IonButtons, IonCheckbox, IonContent, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonPage, IonText, IonTitle, IonToolbar, useIonPicker, } from '@ionic/react';
import { caretDownOutline } from 'ionicons/icons';
import { RouteComponentProps } from 'react-router';
import { DataContext } from '../../DataProvider';
import './PaymentPage.css';
import { CreditCard } from '../../models';

type PaymentPageMatch = {
  id: string;
}

const maskCreditCardNumber = (value: string) => {
  // Only change number if value is exactly 16 chars
  if (value.length >= 16) {
    return '**** **** **** ' + value.slice(-4);
  }

  return value;
}

const safeGetYear = (value: string | undefined) => {
  const arr = (value || '').split('/');
  if (arr.length > 1) return arr[1];
  return '';
}

const PaymentPage = (props: RouteComponentProps<PaymentPageMatch>) => {
  const { id } = props.match.params;
  const { user, setUser } = useContext(DataContext);
  const [isChecked, setIsChecked] = useState(false);
  const [present] = useIonPicker();

  // TODO: use variable to determine if you should prefill data or not
  const isNewPaymentMethod = id === undefined;
  const paymentMethodIndex = isNewPaymentMethod ? (user?.creditCards.length || 1) : +id;
  let initialCreditCardValue: CreditCard;

  if (isNewPaymentMethod) {
    initialCreditCardValue = {
      id: paymentMethodIndex + 1,
      company: 'Visa',
      expirationDate: '6/2021',
      number: '',
      cvv: 0,
      zip: 0,
      preferred: false,
    }
  } else {
    initialCreditCardValue = user?.creditCards.find(value => {
      return value.id === +id
    }) as CreditCard;
  }

  const [creditCard, setCreditCard] = useState<CreditCard>(initialCreditCardValue as CreditCard);

  useEffect(() => {
    setCreditCard(initialCreditCardValue);
  }, [user])

  const pickMonth = () => {
    present({
      buttons: [
        {
          text: 'Confirm',
          handler: (selected) => {
            if (user) {
              const year = safeGetYear(creditCard.expirationDate);
              setCreditCard({ ...creditCard, expirationDate: `${selected.Month.value}/${year}` })
            }
          },
        },
      ],
      columns: [
        {
          name: 'Month',
          options: ['1','2','3','4','5','6','7','8','9','10','11','12'].map(code => { 
            return { text: code, value: code }; 
          })
        },
      ],
    })
  }

  const pickYear = () => {
    present({
      buttons: [
        {
          text: 'Confirm',
          handler: (selected) => {
            if (user) {
              const month = creditCard.expirationDate[0].split('/')[0]
              setCreditCard({ ...creditCard, expirationDate: `${month}/${selected.Year.value}` })
            }
          },
        },
      ],
      columns: [
        {
          name: 'Year',
          options: ['2021', '2022', '2023', '2024', '2025', '2026', '2027', '2028'].map(code => { 
            return { text: code, value: code }; 
          })
        },
      ],
    })
  }

  return <IonPage>
    {
      user && creditCard &&
      <React.Fragment>
        <IonHeader>
          <IonToolbar>
            <IonTitle>{isNewPaymentMethod ? 'Add' : 'Edit'} Payment Method</IonTitle>
            <IonButtons slot="start">
              <IonBackButton text="Cancel" />
            </IonButtons>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <IonItem lines="full">
            <IonLabel position="fixed">Card Number</IonLabel>
            <IonInput
              maxlength={16}
              placeholder=""
              onIonFocus={(event) => {
                if (user) {
                  setCreditCard({ ...creditCard, number: ''})
                }
              }}
              onIonBlur={(event) => {
                if (user) {
                  const number = (event.target as any).value;
                  setCreditCard({ ...creditCard, number })
                }
              }}
              value={maskCreditCardNumber(creditCard.number)}
            ></IonInput>
          </IonItem>
          <IonItem lines="full">
            <IonLabel position="fixed">Exp. Date</IonLabel>
            <IonInput 
              placeholder=""
              onClick={pickMonth}
              value={creditCard.expirationDate.split('/')[0]}
            ></IonInput>
            {/* TODO: Style this to fit design better */}
            <IonButton
              color="light"
              fill="clear"
              expand="block"
              onClick={pickMonth}
            >
              <IonIcon slot="icon-only" icon={caretDownOutline} />
            </IonButton>
            <IonInput 
              placeholder=""
              onClick={pickMonth}
              value={safeGetYear(creditCard.expirationDate)}
            ></IonInput>
            <IonButton
              color="light"
              fill="clear"
              expand="block"
              onClick={pickYear}
            >
              <IonIcon slot="icon-only" icon={caretDownOutline} />
            </IonButton>
          </IonItem>
          <IonItem lines="full">
            <IonLabel position="fixed">CVV</IonLabel>
            <IonInput 
              placeholder=""
              maxlength={4}
              debounce={500}
              onIonChange={(event) => {
                if (user) {
                  const cvv = (event.target as any).value;
                  setCreditCard({ ...creditCard, cvv })
                }
              }}
              value={creditCard.cvv}
            ></IonInput>
          </IonItem>
          <IonItem lines="full">
            <IonLabel position="fixed">Zip Code</IonLabel>
            <IonInput 
              placeholder=""
              maxlength={5}
              debounce={500}
              onIonChange={(event) => {
                if (user) {
                  const zip = (event.target as any).value;
                  setCreditCard({ ...creditCard, zip })
                }
              }}
              value={creditCard.zip}
            ></IonInput>
          </IonItem>
          <IonItem lines="none">
            <IonCheckbox checked={isChecked} onIonChange={e => setIsChecked(e.detail.checked)} />
            <IonText>Set as default payment method</IonText>
          </IonItem>
          <IonButton 
            expand="block"
            onClick={() => {
              if (user) {
                const newUser = user;
                newUser.creditCards[paymentMethodIndex] = creditCard;
                setUser(newUser);
                window.location.href = `/payment/${creditCard.id}`
              }
            }}
          >
            Save
          </IonButton>
        </IonContent>
      </React.Fragment>
    }
    </IonPage>;
};

export default PaymentPage;
