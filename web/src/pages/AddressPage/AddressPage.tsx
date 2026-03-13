import React, { useContext, useEffect, useState } from 'react';
import {
  IonItem,
  IonInput,
  IonSelect,
  IonSelectOption,
  IonHeader,
  IonButtons,
  IonBackButton,
  IonToolbar,
  IonTitle,
  IonButton,
  IonContent,
  IonPage,
  IonCheckbox,
  IonText,
  useIonRouter,
} from '@ionic/react';
import { DataContext } from '../../DataProvider';
import { stateCodes } from '../../util/states';
import './AddressPage.css';
import { RouteComponentProps } from 'react-router';
import { Address, User } from '../../ShopAPIPlugin';

type AddressPageProps = {
  id: string;
};

const AddressPage: React.FC<RouteComponentProps<AddressPageProps>> = (
  props
) => {
  const { id } = props.match.params;
  const { user, setUser } = useContext(DataContext);
  const [address, setAddress] = useState<Address>();
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
          <IonInput
            label="Full Name"
            labelPlacement="fixed"
            placeholder=""
            disabled
            value={`${user?.firstName} ${user?.lastName}`.trim()}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonInput
            label="Address"
            labelPlacement="fixed"
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              setAddress({ ...address, street: event.detail.value! });
            }}
            value={address.street}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonInput
            label="Zip Code"
            labelPlacement="fixed"
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
          <IonInput
            label="City"
            labelPlacement="fixed"
            placeholder=""
            debounce={500}
            onIonChange={(event) => {
              setAddress({ ...address, city: event.detail.value! });
            }}
            value={address.city}
          ></IonInput>
        </IonItem>
        <IonItem lines="full">
          <IonSelect
            label="State"
            labelPlacement="fixed"
            interface="popover"
            value={address.state}
            onIonChange={(event) => {
              setAddress({ ...address, state: event.detail.value });
            }}
          >
            {stateCodes.map((code) => (
              <IonSelectOption key={code} value={code}>
                {code}
              </IonSelectOption>
            ))}
          </IonSelect>
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
