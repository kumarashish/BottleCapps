package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bottle_caps_adminapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import common.AppController;
import interfaces.OnStoreSelect;
import model.StoreModel;

/**
 * Created by Ashish.Kumar on 18-05-2018.
 */

public class StoreAdapter  extends BaseAdapter {
    LayoutInflater inflater;
    Activity act;
    ArrayList<StoreModel> storeList;
    OnStoreSelect callback;
    AppController controller;
    public StoreAdapter(Activity act, ArrayList<StoreModel> storeList)
    {
        this.storeList=storeList;
        this.act=act;
        inflater=act.getLayoutInflater();
        callback=(OnStoreSelect)act;
        controller=(AppController)act.getApplicationContext();
    }
    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder=null;
        final StoreModel model=storeList.get(i);
        StoreModel selectedStore=controller.getSelectedStore();
        if(view==null)
        {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.select_store_row, null, true);
            holder.storeAddress=( common.DetailsCustomTextView)view.findViewById(R.id.storeAddress);
            holder.storeName=(common.Bold_TextView)view.findViewById(R.id.storeName);
            holder.storeImage=(ImageView)view.findViewById(R.id.storeImage) ;
            holder.selectedStore=(ImageView) view.findViewById(R.id.selectedStore) ;
           holder.view=(View)view.findViewById(R.id.view);

        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.storeName.setText(model.getStoreName());
        holder.storeAddress.setText(model.getAddress());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onStoreSelect( model);
            }
        });
        Picasso.with(act).load(model.getStoreImageUrl()).resize(80,80).placeholder(R.drawable.img).into(holder.storeImage);
        view.setTag(holder);
        if(selectedStore!=null)
        {
          if( model.getStoreId().equalsIgnoreCase(selectedStore.getStoreId()))
          {
              holder.selectedStore.setVisibility(View.VISIBLE);
          }else{
              holder.selectedStore.setVisibility(View.GONE);
          }
        }
        return view;
    }
    public class ViewHolder {
        View view;
        common.DetailsCustomTextView   storeAddress;
        common.Bold_TextView storeName;
        ImageView storeImage,selectedStore;
    }
    }