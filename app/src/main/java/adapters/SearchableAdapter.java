package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bottle_caps_adminapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import interfaces.OnStoreSelect;
import model.StoreModel;
import utils.Util;

/**
 * Created by ashish.kumar on 18-06-2018.
 */

public class SearchableAdapter extends BaseAdapter implements Filterable {

    private List<StoreModel> originalData = null;
    private List<StoreModel>filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    OnStoreSelect callback;
    Activity act;
    public SearchableAdapter(Activity context, List<StoreModel> data) {
        this.filteredData = data ;
        this.originalData = data ;
        mInflater = LayoutInflater.from(context);
        callback=(OnStoreSelect)context;
        act=context;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        final StoreModel model=filteredData.get(position);
        if ( view== null) {
            holder=new ViewHolder();
            view=    mInflater.inflate(R.layout.select_store_row, null, true);
            holder.storeAddress=( common.DetailsCustomTextView) view.findViewById(R.id.storeAddress);
            holder.storeName=(common.Bold_TextView) view.findViewById(R.id.storeName);
            holder.view=(View) view.findViewById(R.id.view);
            holder.storeImage=(ImageView)view.findViewById(R.id.storeImage);
            // Bind the data efficiently with the holder.


        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder)  view.getTag();
        }
        view.setTag(holder);
        // If weren't re-ordering this you could rely on what you set last time
        if(Util.isTablet(act))
        {
            Picasso.with(act).load(model.getStoreImageUrl()).resize(80, 80).placeholder(R.drawable.img).into(holder.storeImage);
        }else {
            Picasso.with(act).load(model.getStoreImageUrl()).resize(120, 120).placeholder(R.drawable.img).into(holder.storeImage);
        }
        holder.storeName.setText(model.getStoreName());
        holder.storeAddress.setText(model.getAddress());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onStoreSelect( model);
            }
        });

        return  view;
    }



    static class ViewHolder {
        View view;
        common.DetailsCustomTextView   storeAddress;
        common.Bold_TextView storeName;
        ImageView storeImage;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<StoreModel> list = originalData;

            int count = list.size();
            final ArrayList<StoreModel> nlist = new ArrayList<StoreModel>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getStoreName();
                if (filterableString.toLowerCase().contains(filterString.toLowerCase())) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<StoreModel>) results.values;
            notifyDataSetChanged();
        }

    }
}

