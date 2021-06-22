import {
  IonItem,
  IonCheckbox,
  IonLabel,
  IonChip,
  IonButton,
  useIonRouter,
} from '@ionic/react';
import React from 'react';
import { Address, User } from '../models';

interface AddressItemProps {
  address: Address;
  onAddressSelected?: (address: Address) => void;
  selectable?: boolean;
  selectedId?: number;
  user: User;
}

const AddressItem: React.FC<AddressItemProps> = ({
  address,
  onAddressSelected = () => {},
  selectable = true,
  selectedId,
  user,
}) => {
  const router = useIonRouter();
  return (
    <IonItem
      button
      detail={false}
      onClick={() => {
        if (address.id !== selectedId) {
          onAddressSelected(address);
        }
      }}
    >
      {selectable && (
        <IonCheckbox
          slot="start"
          checked={address.id === selectedId}
        ></IonCheckbox>
      )}
      <IonLabel className="ion-text-wrap">
        {user.firstName} {user.lastName} <br />
        {address.street} <br />
        {address.city}, {address.state} {address.postal}
      </IonLabel>
      {address.preferred && <IonChip color="success">Default</IonChip>}
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
  );
};

export default AddressItem;
