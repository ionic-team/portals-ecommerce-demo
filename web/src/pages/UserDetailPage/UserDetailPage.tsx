import {
  IonButton,
  IonChip,
  IonContent,
  IonIcon,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonListHeader,
  IonPage,
  useIonRouter,
} from '@ionic/react';
import React, { useCallback, useContext, useEffect, useState } from 'react';
import { DataContext } from '../../DataProvider';
import { add } from 'ionicons/icons';
import { Camera, CameraDirection, CameraResultType } from '@capacitor/camera';
import './UserDetailPage.scss';

interface FormData {
  firstName: string;
  lastName: string;
  email: string;
}

const UserDetailPage = () => {
  const { user, setUser } = useContext(DataContext);
  const [imageUrl, setImageUrl] = useState<string>();
  const [formData, setFormData] = useState<FormData>();
  const router = useIonRouter();

  useEffect(() => {
    if (user && !formData) {
      setFormData({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
      });
      setImageUrl(`images/${user.image}`);
    }
  }, [formData, user]);

  const updateUser = useCallback(() => {
    if (user && formData) {
      setUser({ ...user, ...formData });
    }
  }, [user, formData, setUser]);

  const handlePictureClick = async () => {
    const image = await takePicture();
    setImageUrl(image);
  };

  return (
    <IonPage id="user-detail-page">
      <IonContent>
        {user && formData && (
          <>
            <div className="user-image" onClick={handlePictureClick}>
              <img src={imageUrl} alt={`${user.firstName} ${user.lastName}`} />
              <IonIcon icon={add} />
            </div>
            <div className="user-info">
              <IonList lines="full">
                <IonItem>
                  <IonLabel>First name</IonLabel>
                  <IonInput
                    value={formData.firstName}
                    onIonChange={(e) => {
                      const value = e.detail.value!;
                      if (value) {
                        setFormData({ ...formData, firstName: value });
                      }
                    }}
                    onBlur={updateUser}
                  ></IonInput>
                </IonItem>
                <IonItem>
                  <IonLabel>Last name</IonLabel>
                  <IonInput
                    value={formData.lastName}
                    onIonChange={(e) => {
                      const value = e.detail.value!;
                      if (value) {
                        setFormData({ ...formData, lastName: value });
                      }
                    }}
                    onBlur={updateUser}
                  ></IonInput>
                </IonItem>
                <IonItem>
                  <IonLabel>Email Address</IonLabel>
                  <IonInput
                    value={formData.email}
                    onIonChange={(e) => {
                      setFormData({ ...formData, email: e.detail.value! });
                    }}
                    onBlur={updateUser}
                  ></IonInput>
                </IonItem>
              </IonList>

              <div className="list-section">
                <IonList lines="none">
                  <IonListHeader>Addresses</IonListHeader>
                  {user.addresses.map((address) => (
                    <IonItem className="ion-no-padding">
                      <IonLabel>
                        {user.firstName} {user.lastName}
                        <br />
                        {address.street}
                        <br />
                        {address.city}, {address.state} {address.postal}
                      </IonLabel>
                      {address.preferred && (
                        <IonChip color="success">Default</IonChip>
                      )}
                      <IonButton fill="clear" slot="end">
                        Edit
                      </IonButton>
                    </IonItem>
                  ))}
                </IonList>
                <IonButton expand="block" color="secondary">
                  New Address
                </IonButton>
              </div>
            </div>

            <div className="list-section">
              <h4>Payment Methods</h4>
              <IonList lines="none">
                {user.creditCards.map((creditCard) => (
                  <IonItem className="ion-no-padding">
                    <IonLabel>
                      {creditCard.company} ending in{' '}
                      {creditCard.number.substring(
                        creditCard.number.length - 4
                      )}
                      <br />
                    </IonLabel>
                    {creditCard.preferred && (
                      <IonChip color="success">Default</IonChip>
                    )}
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
                ))}
              </IonList>
              <IonButton expand="block" color="secondary">
                New Payment Method
              </IonButton>
            </div>
          </>
        )}
      </IonContent>
    </IonPage>
  );
};

async function takePicture() {
  const image = await Camera.getPhoto({
    quality: 100,
    allowEditing: false,
    direction: CameraDirection.Front,
    resultType: CameraResultType.Uri,
  });
  return image.webPath;
}

export default UserDetailPage;
