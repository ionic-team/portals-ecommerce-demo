import React, { useRef, useState } from 'react';
import {
  IonButton,
  IonContent,
  IonInput,
  IonItem,
  IonList,
  IonPage,
} from '@ionic/react';
import * as Portals from '@ionic/portals';


const PubSubTest = () => {
  const [topic, setTopic] = useState('sayHi');
  const portalsSubscription = useRef<any | undefined>(undefined);
  const [message, setMessage] = useState('');
  const [messageFromApp, setMessageFromApp] = useState<any>();

  const subscribe = async () => {
    const portalSubscription = await Portals.subscribe(topic,
      (result) => {
        setMessageFromApp(result);
      }
    );
    portalsSubscription.current = portalSubscription;
  };

  const publish = async () => {
    setMessageFromApp('');
    Portals.publish({
      topic,
      data: { message },
    });
  };

  return (
    <IonPage id="help-page">
      <IonContent>
        <IonList>
          <IonItem>
            <IonInput
              label="Topic"
              labelPlacement="start"
              value={topic}
              onIonChange={(e) => setTopic(e.detail.value!)}
            ></IonInput>
          </IonItem>
          <IonItem>
            <IonInput
              label="Message"
              labelPlacement="start"
              value={message}
              onIonChange={(e) => setMessage(e.detail.value!)}
            ></IonInput>
          </IonItem>
        </IonList>
        <IonButton expand="block" onClick={subscribe}>
          Subscribe
        </IonButton>
        <IonButton expand="block" onClick={publish}>
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
