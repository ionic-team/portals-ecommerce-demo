import React, { useContext, useState } from 'react';
import { caretDownOutline } from 'ionicons/icons';
import { 
  IonItem, 
  IonLabel, 
  IonInput, 
  IonHeader, 
  IonButtons, 
  IonBackButton, 
  IonToolbar, 
  IonTitle, 
  IonButton, 
  IonContent, 
  IonPage, 
  IonIcon,
  IonCheckbox,
  IonText,
  useIonPicker,
} from '@ionic/react';
import { DataContext } from '../../DataProvider';
import { stateCodes } from '../../util/states';
import './AddressPage.css';

const AddressPage = () => {
  const { user, setUser } = useContext(DataContext);
  const [isChecked, setIsChecked] = useState(false);
  const [present] = useIonPicker();

  // TODO: use variable to determine if you should prefill data or not
  const isNewAddress = false;
  const addressIndex = 0;
  const address = user?.addresses[addressIndex]

  if (isNewAddress && user) {
    user.addresses[addressIndex].street = '';
    user.addresses[addressIndex].postal = '';
    user.addresses[addressIndex].city = '';
    user.addresses[addressIndex].state = '';
    user.addresses[addressIndex].preferred = false;
  }

  const pickStateCode = () => {
    present({
      buttons: [
        {
          text: 'Confirm',
          handler: (selected) => {
            if (user) {
              user.addresses[addressIndex].state = selected.StateCode.value;
              setUser(user);
            }
          },
        },
      ],
      columns: [
        {
          name: 'StateCode',
          options: stateCodes.map(code => { 
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
          <IonTitle>{isNewAddress ? 'Add' : 'Edit'} Address</IonTitle>
          <IonButtons slot="start">
            <IonBackButton text="Cancel" />
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonItem lines="full">
          <IonLabel position="fixed">Full Name</IonLabel>
          <IonInput
            placeholder=""
            disabled
            value={`${user?.firstName} ${user?.lastName}`.trim()}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">Address</IonLabel>
          <IonInput 
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              if (user) {
                user.addresses[addressIndex].street = (event.target as any).value
                setUser(user);
              }
            }}
            value={user?.addresses[addressIndex].street}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">Zip Code</IonLabel>
          <IonInput 
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              if (user) {
                user.addresses[addressIndex].postal = (event.target as any).value
                setUser(user);
              }
            }}
            value={user?.addresses[addressIndex].postal}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">City</IonLabel>
          <IonInput 
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              if (user) {
                user.addresses[addressIndex].city = (event.target as any).value
                setUser(user);
              }
            }}
            value={address?.city}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">State</IonLabel>
          <IonInput 
            placeholder=""
            onClick={pickStateCode}
            value={user?.addresses[addressIndex].state}
          ></IonInput>
          {/* TODO: Style this to fit design better */}
          <IonButton
            color="light"
            fill="clear"
            expand="block"
            onClick={pickStateCode}
          >
            <IonIcon slot="icon-only" icon={caretDownOutline} />
          </IonButton>
        </IonItem>
        <IonItem lines="none">
          <IonCheckbox checked={isChecked} onIonChange={e => setIsChecked(e.detail.checked)} />
          <IonText>Set as default address</IonText>
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

export default AddressPage;
