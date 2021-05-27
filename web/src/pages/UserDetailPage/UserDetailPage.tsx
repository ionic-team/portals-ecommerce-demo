import { IonButton, IonContent, IonPage } from '@ionic/react';
import React, { useContext } from 'react';
import { DataContext } from '../../DataProvider';
import './UserDetailPage.css';

const UserDetailPage = () => {
  const {user, setUser} = useContext(DataContext);
  return (
    <IonPage>
      <IonContent>
        <h1>UserDetail Page</h1>
        <p>{JSON.stringify(user)}</p>
        <div>
          <IonButton onClick={() => {
            if (user) {
              setUser({ ...user, firstName: 'Josh UPDATED' });
            }
          }}>Update User</IonButton>
        </div>
      </IonContent>
    </IonPage>
  );
};

export default UserDetailPage;
