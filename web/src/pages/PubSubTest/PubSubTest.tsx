import React, { useState } from 'react';
import {
  IonButton,
  IonContent,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonPage,
} from '@ionic/react';
import { subscribe, publish, getInitialContext } from '@ionic/portals';
import type { PluginListenerHandle } from '@capacitor/core';


const PubSubTest = () => {
  const [topic, setTopic] = useState('sayHi');
  const receiveTopic = getInitialContext()?.name ?? "not_in_portal"
  const [portalsSubscription, setPortalsSubscription] =
    useState<PluginListenerHandle>();
  const [message, setMessage] = useState('');
  const [messageFromApp, setMessageFromApp] = useState<any>();

  const mySubscribe = async () => {
    const handle = await subscribe(receiveTopic, (result) => setMessageFromApp(result));
    setPortalsSubscription(handle);
  };

  const unsubscribe = async () => {
    await portalsSubscription?.remove()
  };

  const myPublish = async () => {
    setMessageFromApp('');
    publish({ topic, data: { message } });
  };

  return (
    <IonPage id="help-page">
      <IonContent>
        <IonList>
          <IonItem>
            <IonLabel>Topic</IonLabel>
            <IonInput
              value={topic}
              onIonChange={(e) => setTopic(e.detail.value!)}
            ></IonInput>
          </IonItem>
          <IonItem>
            <IonLabel>Message</IonLabel>
            <IonInput
              value={message}
              onIonChange={(e) => setMessage(e.detail.value!)}
            ></IonInput>
          </IonItem>
        </IonList>
        <IonButton expand="block" onClick={mySubscribe}>
          Subscribe
        </IonButton>
        <IonButton
          expand="block"
          disabled={!portalsSubscription}
          onClick={unsubscribe}
        >
          Unsubscribe
        </IonButton>
        <IonButton expand="block" onClick={myPublish}>
          Publish
        </IonButton>
        portalSubscription: <br />
        {JSON.stringify(portalsSubscription)}
        <br /><br />
        messageFromApp: <br />
        {JSON.stringify(messageFromApp)}
      </IonContent>
    </IonPage>
  );
};

export default PubSubTest;
