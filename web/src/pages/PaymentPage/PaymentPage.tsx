import { IonBackButton, IonButton, IonButtons, IonCheckbox, IonContent, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonPage, IonText, IonTitle, IonToolbar, useIonPicker, } from '@ionic/react';
import { caretDownOutline } from 'ionicons/icons';
import React, { useContext, useState } from 'react';
import { DataContext } from '../../DataProvider';
import './PaymentPage.css';

const setCcNumber = (value: string) => {
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

const PaymentPage = () => {
  const { user, setUser } = useContext(DataContext);
  const [isChecked, setIsChecked] = useState(false);
  const [present] = useIonPicker();

  // TODO: use variable to determine if you should prefill data or not
  const isNewPaymentMethod = false;
  const paymentMethodIndex = 0;
  const paymentMethod = user?.creditCards[paymentMethodIndex];

  if (isNewPaymentMethod && user) {
    user.creditCards[paymentMethodIndex].company = 'Visa'
    user.creditCards[paymentMethodIndex].expirationDate = '6/2021';
    user.creditCards[paymentMethodIndex].id = 0;
    user.creditCards[paymentMethodIndex].number = '';
    user.creditCards[paymentMethodIndex].preferred = false;
  }

  const [ccNum, setCcNum] = useState(user?.creditCards[paymentMethodIndex].number || '');

  const pickMonth = () => {
    present({
      buttons: [
        {
          text: 'Confirm',
          handler: (selected) => {
            if (user) {
              const year = safeGetYear(user.creditCards[paymentMethodIndex].expirationDate);
              user.creditCards[paymentMethodIndex].expirationDate = `${selected.Month.value}/${year}`;
              setUser(user);
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
              const month = user.creditCards[paymentMethodIndex].expirationDate[0].split('/')[0]
              user.creditCards[paymentMethodIndex].expirationDate = `${month}/${selected.Year.value}`;
              setUser(user);
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

  return (
    <IonPage>
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
                setCcNum('')
              }
            }}
            onIonBlur={(event) => {
              if (user) {
                user.creditCards[paymentMethodIndex].number = (event.target as any).value;
                setCcNum(user.creditCards[paymentMethodIndex].number)
                setUser(user);
              }
            }}
            value={setCcNumber(ccNum)}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">Exp. Date</IonLabel>
          <IonInput 
            placeholder=""
            onClick={pickMonth}
            value={user?.creditCards[paymentMethodIndex].expirationDate.split('/')[0]}
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
            value={safeGetYear(user?.creditCards[paymentMethodIndex].expirationDate)}
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
                user.creditCards[paymentMethodIndex].cvv = (event.target as any).value;
                setUser(user);
              }
            }}
            value={user?.creditCards[paymentMethodIndex].cvv}
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
                user.creditCards[paymentMethodIndex].zip = (event.target as any).value;
                setUser(user);
              }
            }}
            value={user?.creditCards[paymentMethodIndex].zip}
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
              setUser(user);
            }
          }}
        >
          Save
        </IonButton>
      </IonContent>
    </IonPage>
  );
};

export default PaymentPage;
