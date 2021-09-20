import React from "react";
import { IonButton, IonContent, IonPage } from "@ionic/react";

const DevPage: React.FC = () => (
  <IonPage>
    <IonContent>
      <IonButton expand="block" href="/help">
        Help Portal
      </IonButton>
      <IonButton expand="block" href="/user">
        Profile Portal
      </IonButton>
      <IonButton expand="block" href="/checkout">
        Checkout Modal Portal
      </IonButton>
    </IonContent>
  </IonPage>
);

export default DevPage;
