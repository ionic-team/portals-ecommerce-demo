package io.ionic.demo.ecommerce.ui.store;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;
import io.ionic.demo.ecommerce.ui.product.ProductFragment;
import io.ionic.demo.ecommerce.ui.product.ProductFragmentDirections;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> productList;

    NumberFormat format;
    boolean isGrid;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;

        format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
    }

    public ProductAdapter(Context context, ArrayList<Product> productList, boolean isGrid) {
        this(context, productList);
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_product,parent,false);
        if (isGrid) {
            rootView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return new ProductViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(product.image.substring(0, product.image.lastIndexOf(".")), "drawable", context.getPackageName());
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics());
        Picasso.get().load(resourceId).transform(new RoundedCornersTransformation(px,0)).into(holder.productImageView);
        holder.productTitle.setText(product.title);
        holder.productPrice.setText(format.format(product.price));
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreFragmentDirections.StoreToProduct action = StoreFragmentDirections.storeToProduct();
                action.setProductId(product.id);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout productCard;
        ImageView productImageView;
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
