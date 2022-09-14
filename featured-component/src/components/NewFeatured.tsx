import React, { useEffect, useState } from 'react';
import { IonCard, IonCardTitle, IonCardSubtitle } from '@ionic/react';
import Portals from '@ionic/portals';

import './NewFeatured.css';
import { Product } from '../models';

const CATEGORY_NAME = 'MustHaves';

const NewFeatured = () => {
  const [productList, setProductList] = useState<Product[]>([]);

  useEffect(() => {
    console.log('what effect')
    fetch('/data.json')
      .then((res) => res.json())
      .then((products: Product[]) =>
        setProductList(
          products.filter((product) => {
            return product.category === CATEGORY_NAME;
          }),
        ),
      );
  }, []);

  const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    maximumSignificantDigits: 2,
  });

  return (
    <div className="featured-products">
      <h2>Must Haves, Bestsellers &amp; More</h2>
      <div className="product-list">
        <div className="product-list-inner">
          {productList.map((product) => (
            <IonCard
              key={product.id}
              onClick={() => {
                Portals.publish({ topic: 'featured:select-item', data: product.id });
              }}
            >
              <img
                alt={product.title}
                src={`/images/${product.image}`}
              />
              <IonCardTitle>{product.title}</IonCardTitle>
              <IonCardSubtitle>
                {formatter.format(product.price)}
              </IonCardSubtitle>
            </IonCard>
          ))}
        </div>
      </div>
    </div>
  );
};

export default NewFeatured;
