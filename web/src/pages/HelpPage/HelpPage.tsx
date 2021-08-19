import React, { useState } from 'react';
import {
  IonButton,
  IonContent,
  IonIcon,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonPage,
} from '@ionic/react';
import { callOutline, mailOutline } from 'ionicons/icons';
import Portals, { PortalSubscription } from '@native-portal/portals';

import './HelpPage.scss';

const HelpPage = () => {
  const [topic, setTopic] = useState('sayHi');
  const [portalsSubscription, setPortalsSubscription] =
    useState<PortalSubscription>();
  const [message, setMessage] = useState('');
  const [messageFromApp, setMessageFromApp] = useState<any>();
  const [secondTopic, setSecondTopic] = useState('');

  const subscribe = async () => {
    const portalSubscription = await Portals.subscribe(
      {
        topic,
      },
      (result) => {
        setMessageFromApp(result);
      }
    );
    setPortalsSubscription(portalSubscription);
  };

  const unsubscribe = async () => {
    Portals.unsubscribe(portalsSubscription!);
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
        <IonButton expand="block" onClick={subscribe}>
          Subscribe
        </IonButton>
        <IonButton
          expand="block"
          disabled={!portalsSubscription}
          onClick={unsubscribe}
        >
          Unsubscribe
        </IonButton>
        <IonButton expand="block" onClick={publish}>
          Publish
        </IonButton>
        portalSubscription: <br />
        {JSON.stringify(portalsSubscription)}
        <br/><br/>
        messageFromApp: <br />
        {JSON.stringify(messageFromApp)}
        {/* <IonItem lines="none">
          <h1>Get Assistance</h1>
        </IonItem>
        <IonItem lines="none">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
            ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
            aliquip ex ea commodo consequat.
          </p>
        </IonItem>
        <IonItem lines="none">
          <a className="help-link" href="mailto:help@portals.ionic.io">
            <IonIcon slot="icon-only" icon={mailOutline} />
            help@portals.ionic.io
          </a>
        </IonItem>
        <IonItem lines="none">
          <a className="help-link" href="tel:1-800-767-8257">
            <IonIcon slot="icon-only" icon={callOutline} />
            1-800-PORTALS
          </a>
        </IonItem> */}
      </IonContent>
    </IonPage>
  );
};

export default HelpPage;
