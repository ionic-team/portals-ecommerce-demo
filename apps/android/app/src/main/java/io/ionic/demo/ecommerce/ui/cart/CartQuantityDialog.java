package io.ionic.demo.ecommerce.ui.cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

public class CartQuantityDialog extends AlertDialog {

    public static class Builder extends AlertDialog.Builder {
        /**
         * The dialog root view
         */
        private View root;

        /**
         * The product associated with this dialog
         */
        private Product product;

        public Builder(Activity activity) {
            super(activity);
        }

        public void createView(ViewGroup viewGroup, Product product, int qty) {
            root = LayoutInflater.from(super.getContext()).inflate(R.layout.dialog_cart_quantity, viewGroup, false);
            TextView quantityText = root.findViewById(R.id.text_quantity);
            quantityText.setText(String.valueOf(qty));
            this.product = product;
            super.setView(root);
        }

        public void setupListeners() {
            Button increment = root.findViewById(R.id.increment_button);
            Button decrement = root.findViewById(R.id.decrement_button);
            TextView quantityText = root.findViewById(R.id.text_quantity);

            increment.setOnClickListener(v -> {
                int qty = Integer.parseInt(quantityText.getText().toString());
                qty += 1;
                quantityText.setText(String.valueOf(qty));
                EcommerceApp.getInstance().getShoppingCart().addItem(product);
            });

            decrement.setOnClickListener(v -> {
                int qty = Integer.parseInt(quantityText.getText().toString());
                qty -= 1;
                if (qty >= 0) {
                    EcommerceApp.getInstance().getShoppingCart().removeItem(product);
                } else {
                    qty = 0;
                }
                quantityText.setText(String.valueOf(qty));
            });
        }
    }

    public CartQuantityDialog(@NonNull Context context) {
        super(context);
    }
}
