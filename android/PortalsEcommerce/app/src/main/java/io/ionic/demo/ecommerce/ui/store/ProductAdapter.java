package io.ionic.demo.ecommerce.ui.store;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

import io.ionic.demo.ecommerce.MainActivity;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;
import io.ionic.demo.ecommerce.ui.product.ProductFragment;

/**
 * An adapter used to create product lists on the Store Fragment.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    /**
     * The context of the adapter.
     */
    private final MainActivity context;

    /**
     * The list of products to use in the adapter.
     */
    private final ArrayList<Product> productList;

    /**
     * A formatter for currency.
     */
    private final NumberFormat format;

    /**
     * Whether the adapter is being used for a list or a grid view.
     */
    boolean isGrid;

    /**
     * Constructs a ProductAdapter with context and a list of products to display.
     *
     * @param context The context containing the adapter.
     * @param productList The list of products to display.
     */
    public ProductAdapter(MainActivity context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;

        format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
    }

    /**
     * Constructs a ProductAdapter with context, a list of products to display, and whether
     * the adapter will be used to display a grid or not.
     *
     * @param context The context containing the adapter.
     * @param productList The list of products to display.
     * @param isGrid True if the adapter will be used for a grid.
     */
    public ProductAdapter(MainActivity context, ArrayList<Product> productList, boolean isGrid) {
        this(context, productList);
        this.isGrid = isGrid;
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_product,parent,false);

        if (isGrid) {
            rootView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        return new ProductViewHolder(rootView);
    }

    /**
     * Binds the product view in the adapter to the product data to display.
     *
     * @param holder The view to use to display the product information.
     * @param position The id of the product to display within the view.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Resources resources = context.getResources();

        // Get image asset for the product and load into product card image view
        String imageResourceName = product.image.substring(0, product.image.lastIndexOf(".")).replaceAll("-", "_");
        final int resourceId = resources.getIdentifier(imageResourceName, "drawable", context.getPackageName());
        holder.productImageView.setImageResource(resourceId);

        holder.productTitle.setText(product.title);
        holder.productPrice.setText(format.format(product.price));
        holder.productCard.setOnClickListener(v -> {
            // Navigate to product page passing the product to be displayed, when tapped
            Fragment productDetailFragment = ProductFragment.newInstance(context, product);
            FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.store_container_layout, productDetailFragment).commit();
            context.showHelpMenu(true);

            context.getSupportActionBar().setHomeButtonEnabled(true);
        });
    }

    /**
     * Get a total count of the products in the list to display.
     *
     * @return The count of products.
     */
    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * A view holder used to display product information in the adapter.
     */
    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout productCard;
        ShapeableImageView productImageView;
        TextView productTitle;
        TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id.product_card);
            productImageView = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
