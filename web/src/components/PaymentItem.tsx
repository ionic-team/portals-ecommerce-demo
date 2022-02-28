import {
  IonItem,
  IonCheckbox,
  IonLabel,
  IonButton,
  useIonRouter,
} from '@ionic/react';
import React from 'react';
import { CreditCard } from '../ShopAPIPlugin';

interface PaymentItemProps {
  creditCard: CreditCard;
  onPaymentSelected?: (payment: CreditCard) => void;
  selectable?: boolean;
  selectedId?: number;
}

const PaymentItem: React.FC<PaymentItemProps> = ({
  creditCard,
  onPaymentSelected = () => {},
  selectable = true,
  selectedId,
}) => {
  const router = useIonRouter();
  return (
    <IonItem
      button
      detail={false}
      key={creditCard.id}
      onClick={() => {
        if (creditCard.id !== selectedId) {
          onPaymentSelected(creditCard);
        }
      }}
    >
      {selectable && (
        <IonCheckbox
          slot="start"
          checked={creditCard.id === selectedId}
        ></IonCheckbox>
      )}
      <IonLabel className="ion-text-wrap">
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
  );
};

export default PaymentItem;
