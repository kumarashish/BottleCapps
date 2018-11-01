package adapters;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bottle_caps_adminapp.R;

import java.util.ArrayList;

import interfaces.ItemClickCallBack;
import model.OrderModel;
import utils.Util;

/**
 * Created by Ashish.Kumar on 18-05-2018.
 */

public class OrderAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Activity act;
    ArrayList<OrderModel> orderList;
    ItemClickCallBack callBack;
    public OrderAdapter(Activity act,ArrayList<OrderModel> orderList)
    {
        this.orderList=orderList;
        this.act=act;
        inflater=act.getLayoutInflater();
        callBack=(ItemClickCallBack)act;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        final OrderModel model=orderList.get(i);
        if(view==null)
        {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.order_row, null, true);
            holder.view1=(View)view.findViewById(R.id.view1);
            holder.view2=(View)view.findViewById(R.id.view2);

            holder.order_number = (common.Bold_TextView) view.findViewById(R.id.order_number);
            holder.amount = (common.Bold_TextView) view.findViewById(R.id.amount);
            holder.date = (common.Bold_TextView) view.findViewById(R.id.date);
            holder.time = (common.Bold_TextView) view.findViewById(R.id.time);
            holder.status = (common.DetailsCustomTextView) view.findViewById(R.id.status);
            holder.deliveryTypeIcon = (ImageView) view.findViewById(R.id.deliveryTypeIcon);
            holder.type_title= (common.Bold_TextView) view.findViewById(R.id.type_title);
            holder.status_icon=(ImageView)view.findViewById(R.id.status_icon);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnItemClick(model,i);
            }
        });
        holder.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnStatusClicked(model,i);
            }
        });
        Util.setStatusIcon(holder.status_icon,model.getOrderStatusId());
        holder.order_number.setText(model.getOrderNo());
        holder.amount.setText(model.getTotalAmountString());
        holder.date.setText(model.getOrderDate());
        holder.time.setText(model.getOrderTime());
        holder.status.setText(model.getOrderStatus());
      if(model.getOrderType().equalsIgnoreCase("Delivery"))
      {
          holder.type_title.setText("Delivery");
          holder.deliveryTypeIcon.setImageResource(R.drawable.type_delivery_icon);
      }else {
          holder.type_title.setText("Pickup");
        holder.deliveryTypeIcon.setImageResource(R.drawable.type_pickup_icon);
      }
        view.setTag(holder);
        return view;
    }
    public class ViewHolder{
        View view1,view2;
        common.Bold_TextView order_number,type_title;
        common.Bold_TextView amount;
        common.Bold_TextView date;
        common.Bold_TextView time;
        common.DetailsCustomTextView status;
        ImageView deliveryTypeIcon,status_icon;
    }

    }

