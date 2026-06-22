package io.ionic.demo.ecommerce.ui.cart;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    /**
     * The context of the adapter.
     */
    private final Activity activity;

    /**
     * A formatter for currency.
     */
    private final NumberFormat format;

    /**
     * Update listener for navbar badge
     */
    private View.OnClickListener updateListener;

    /**
     * Constructs a ProductAdapter with context, a list of products to display, and whether
     * the adapter will be used to display a grid or not.
     *
     * @param activity The Activity containing the adapter.
     */
    public CartAdapter(Activity activity, View.OnClickListener updateListener) {
        this.activity = activity;
        this.updateListener = updateListener;

        format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
    }

    /**
     * Inflates a product view to be used to display products in the adapter.
     *
     * @param parent The parent view group.
     * @param viewType The view type.
     * @return A view to display in the adapter.
     */
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.card_cart_product, parent,false);
        return new CartAdapter.CartViewHolder(rootView);
    }

    /**
     * Binds the product view in the adapter to the product data to display.
     *
     * @param holder The view to use to display the product information.
     * @param position The id of the product to display within the view.
     */
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Map<Product, Integer> cart = EcommerceApp.getInstance().getShoppingCart().getProductsInCart();
        Product[] products = EcommerceApp.getInstance().getShoppingCart().getProductsInCartAsArray();

        Product product = products[position];
        int quantity = cart.get(product);

        Resources resources = activity.getResources();

        // Get image asset for the product and load into product card image view
        String imageResourceName = product.image.substring(0, product.image.lastIndexOf(".")).replaceAll("-", "_");
        final int resourceId = resources.getIdentifier(imageResourceName, "drawable", activity.getPackageName());
        holder.productImageView.setImageResource(resourceId);

        CartAdapter self = this;

        holder.productTitle.setText(product.title);
        holder.productQty.setText(String.format(Locale.US,"Qty: %d", quantity));
        holder.productPrice.setText(format.format(product.price * quantity));

        holder.productQtySection.setOnClickListener(v -> {
            CartQuantityDialog.Builder builder = new CartQuantityDialog.Builder(activity);
            builder.setTitle(R.string.quantity);
            builder.createView((ViewGroup) v.getRootView(), product, quantity);
            builder.setupListeners();
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Decrement badge number on the bottom nav
//                BottomNavigationView navView = activity.findViewById(R.id.nav_view);
//                BadgeDrawable badge = navView.getOrCreateBadge(R.id.navigation_cart);
//                badge.setNumber(EcommerceApp.getInstance().getShoppingCart().getTotalItemCount());
//                if (EcommerceApp.getInstance().getShoppingCart().getTotalItemCount() == 0) {
//                    badge.setVisible(false);
//                }

                self.notifyDataSetChanged();
                updateListener.onClick(v);
            });
            builder.show();
        });
    }

    /**
     * Get a total count of the products in the list to display.
     * @return The count of unique products.
     */
    @Override
    public int getItemCount() {
        return EcommerceApp.getInstance().getShoppingCart().getUniqueItemCount();
    }

    /**
     * A view holder used to display product information in the adapter.
     */
    static class CartViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout productCard;
        ConstraintLayout productQtySection;
        ShapeableImageView productImageView;
        TextView productTitle;
        TextView productQty;
        TextView productPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id.product_card);
            productImageView = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productQty = itemView.findViewById(R.id.product_cart_qty);
            productQtySection = itemView.findViewById(R.id.quantity_section);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
