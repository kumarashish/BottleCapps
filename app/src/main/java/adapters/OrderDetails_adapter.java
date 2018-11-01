package adapters;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bottle_caps_adminapp.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import model.OrderModel;
import model.ProductDetails;

/**
 * Created by ashish.kumar on 18-06-2018.
 */

public class OrderDetails_adapter  extends BaseAdapter{
    Activity activity;
    ArrayList<ProductDetails> list;
    LayoutInflater inflater;
    public OrderDetails_adapter(Activity activity, ArrayList<ProductDetails> list){
        this.list=list;
        this.activity=activity;
        inflater=activity.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final ProductDetails orderDetailsModel = list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.order_details_row, null, true);
            holder.productName=(common.DetailsCustomTextView)view.findViewById(R.id.productName);
            holder.quantity=(common.DetailsCustomTextView)view.findViewById(R.id.quantity);
            holder.discountPrice=(common.Bold_TextView)view.findViewById(R.id.discountPrice);
            holder.realPrice=(common.Bold_TextView)view.findViewById(R.id.realPrice);
            holder.sku=(common.DetailsCustomTextView)view.findViewById(R.id.sku);
            holder.productImage=(ImageView)view.findViewById(R.id.productImage);
            holder.c_icon=(ImageView)view.findViewById(R.id.c_icon);
        }
            else{
                holder=(ViewHolder)view.getTag();
            }
            if(!orderDetailsModel.getStrikedPrice().equalsIgnoreCase(orderDetailsModel.getPrice())) {

                holder.realPrice.setText(orderDetailsModel.getStrikedPrice());
                SpannableString spannable = new SpannableString(holder.realPrice.getText().toString());
                spannable.setSpan(new StrikethroughSpan(), 0, orderDetailsModel.getStrikedPrice().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.realPrice.setText(spannable);
            }
        Picasso.with(activity).load(orderDetailsModel.getProductImage()).resize(80,80).placeholder(R.drawable.loading).into(holder.productImage);
        holder.productName.setText(orderDetailsModel.getProductName());
        holder.discountPrice.setText(orderDetailsModel.getPrice());
        holder.quantity.setText("Quantity : " + orderDetailsModel.getQuantity());
        holder.sku.setText("SKU : " + orderDetailsModel.getSKU() + " | UPC : " + orderDetailsModel.getUPC());
        if(orderDetailsModel.isClubPriceFlag())
        {
            holder.c_icon.setVisibility(View.VISIBLE);
        }else{
            holder.c_icon.setVisibility(View.INVISIBLE);
        }
            view.setTag(holder);
            return view;
        }

    public class ViewHolder{
        ImageView c_icon;
        common.DetailsCustomTextView productName;
        common.DetailsCustomTextView quantity;
        common.Bold_TextView  discountPrice;
        common.Bold_TextView  realPrice;
        common.DetailsCustomTextView       sku;
        ImageView productImage;
    }
}
