import React, { useContext } from 'react';
import { IonCard, IonCardTitle, IonCardSubtitle } from '@ionic/react';
import { DataContext } from '../DataProvider';

import './NewFeatured.scss';

const NewFeatured = () => {
  const { productList } = useContext(DataContext);
  const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    maximumSignificantDigits: 2,
  });

  return (
    <div className="ion-padding">
      <h2>New &amp; Featured</h2>
      <div className="product-list">
        {productList.map((product) => (
          <IonCard routerLink={`/shop/${product.id}`} routerDirection="forward">
            <img
              alt={product.title}
              decoding="async"
              src={`/images/${product.image}`}
            />
            <IonCardTitle>{product.title}</IonCardTitle>
            <IonCardSubtitle>{formatter.format(product.price)}</IonCardSubtitle>
          </IonCard>
        ))}
      </div>
    </div>
  );
};

export default NewFeatured;
