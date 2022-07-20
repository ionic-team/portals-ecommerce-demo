import React from 'react';
import { IonContent, IonPage } from '@ionic/react';
import NewFeatured from '../../components/NewFeatured';

const DevPage: React.FC = () => (
  <IonPage>
    <IonContent>
      <NewFeatured />
    </IonContent>
  </IonPage>
);

export default DevPage;
