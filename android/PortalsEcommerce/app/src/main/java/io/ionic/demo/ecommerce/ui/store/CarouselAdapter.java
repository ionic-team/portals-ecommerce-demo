package io.ionic.demo.ecommerce.ui.store;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    Context context;
    ArrayList<Product> productList;

    NumberFormat format;

    public CarouselAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;

        format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.card_product,parent,false);
        return new CarouselViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Product product = productList.get(position);
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(product.image.substring(0, product.image.lastIndexOf(".")), "drawable", context.getPackageName());
        Picasso.get().load(resourceId).into(holder.productImageView);
        holder.productTitle.setText(product.title);
        holder.productPrice.setText(format.format(product.price));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView productImageView;
        TextView productTitle;
        TextView productPrice;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
