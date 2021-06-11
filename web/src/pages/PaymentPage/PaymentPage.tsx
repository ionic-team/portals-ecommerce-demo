import React from 'react';
import {
  IonContent,
  IonPage,
} from '@ionic/react';
import { useParams } from 'react-router';

interface PaymentProps {}

const Payment: React.FC<PaymentProps> = () => {
  const params = useParams<{ id: string }>();
  return (
    <IonPage>
      <IonContent>
        Payment
        <br />
        {params.id}
      </IonContent>
    </IonPage>
  );
};

export default Payment;
