import React, { useContext, useEffect, useState } from 'react';
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
  useIonRouter,
} from '@ionic/react';
import { DataContext } from '../../DataProvider';
import { stateCodes } from '../../util/states';
import './AddressPage.css';
import { RouteComponentProps } from 'react-router';
import { Address, User } from '../../models';

type AddressPageProps = {
  id: string;
};

const AddressPage: React.FC<RouteComponentProps<AddressPageProps>> = (
  props
) => {
  const { id } = props.match.params;
  const { user, setUser } = useContext(DataContext);
  const [address, setAddress] = useState<Address>();
  const [present] = useIonPicker();
  const router = useIonRouter();

  useEffect(() => {
    if (user) {
      if (id) {
        const a = user.addresses.find((x) => x.id === Number(id));
        setAddress(a);
      } else {
        setAddress({
          id: 0,
          city: '',
          postal: '',
          state: '',
          street: '',
          preferred: false,
        });
      }
    }
  }, [id, user]);

  const pickStateCode = () => {
    present({
      buttons: [
        {
          text: 'Confirm',
          handler: (selected) => {
            if (address) {
              setAddress({ ...address, state: selected.StateCode.value });
            }
          },
        },
      ],
      columns: [
        {
          name: 'StateCode',
          options: stateCodes.map((code) => {
            return { text: code, value: code };
          }),
          selectedIndex: stateCodes.findIndex((x) => x === address?.state),
        },
      ],
    });
  };

  const handleSave = () => {
    if (user && address) {
      let newUser: User;
      if (address.id === 0) {
        address.id =
          (user.addresses.length > 0
            ? Math.max(...user.addresses.map((x) => x.id))
            : 0) + 1;
        newUser = {
          ...user,
          addresses: [...user.addresses, address],
        };
      } else {
        newUser = {
          ...user,
          addresses: [
            ...user.addresses.map((x) => (x.id === address.id ? address : x)),
          ],
        };
      }
      if (address.preferred) {
        newUser.addresses.forEach((x) => {
          if (x.id !== address.id) {
            x.preferred = false;
          }
        });
      }
      setUser(newUser);
      if (router.canGoBack()) {
        router.goBack();
      }
    }
  };

  return user && address ? (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>{address.id === 0 ? 'Add' : 'Edit'} Address</IonTitle>
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
              setAddress({ ...address, street: event.detail.value! });
            }}
            value={address.street}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">Zip Code</IonLabel>
          <IonInput
            placeholder=""
            type="number"
            pattern="[0-9]*"
            debounce={500}
            onIonChange={(event) => {
              setAddress({ ...address, postal: event.detail.value! });
            }}
            value={address.postal}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">City</IonLabel>
          <IonInput
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              setAddress({ ...address, city: event.detail.value! });
            }}
            value={address.city}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonLabel position="fixed">State</IonLabel>
          <IonInput
            placeholder=""
            onClick={pickStateCode}
            value={address.state}
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
          <IonCheckbox
            checked={address.preferred}
            onIonChange={(e) => {
              setAddress({ ...address, preferred: !address.preferred });
            }}
          />
          <IonText>Set as default address</IonText>
        </IonItem>
        <IonButton expand="block" onClick={handleSave}>
          Save
        </IonButton>
      </IonContent>
    </IonPage>
  ) : null;
};

export default AddressPage;
