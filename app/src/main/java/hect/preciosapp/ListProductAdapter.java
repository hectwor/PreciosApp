package hect.preciosapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import hect.preciosapp.Models.Product;

/**
 * Created by hect on 17/02/18.
 */

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    private ArrayList<Product> product;
    private Context context;
    public ListProductAdapter(ArrayList<Product> product, Context context) {
        this.context = context;
        this.product = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product pr = product.get(position);
        holder.nameTextView.setText(pr.getName());
        holder.priceTextView.setText(pr.getPrice());
//        holder.photoImageView.setImageURI(Uri.parse(pr.getPhotourl()));
        Glide.with(context)
                .load(pr.getPhotourl())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);;
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoImageView;
        private TextView nameTextView;
        private TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.photoProductView);
            nameTextView = itemView.findViewById(R.id.nameProductView);
            priceTextView = itemView.findViewById(R.id.priceProductView);
        }
    }
}
