package com.sindu.mybuckeylistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sindu.mybuckeylistapp.R;
import com.sindu.mybuckeylistapp.model.BucketList;

import java.util.ArrayList;
import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.MyBucketViewHolder> implements Filterable {

    Context context;
    List<BucketList> bucketLists;
    private List<BucketList> bucketListFull;

    public BucketListAdapter(Context context, List<BucketList> bucketLists) {
        this.context = context;
        this.bucketLists = bucketLists;
        this.bucketLists = new ArrayList<>(bucketLists);
    }

    @NonNull
    @Override
    public BucketListAdapter.MyBucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.bucket_list_item,parent,false);
        return new MyBucketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.MyBucketViewHolder holder, int position) {
        holder.bucket_item_id.setText("Item id: "+String.valueOf(bucketLists.get(position).getId()));
        holder.bucket_item_listid.setText("Cart Number: "+String.valueOf(bucketLists.get(position).getListId()));
        holder.bucket_item_name.setText("Name: "+bucketLists.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return bucketLists.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyBucketViewHolder extends RecyclerView.ViewHolder {
        TextView bucket_item_id,bucket_item_listid, bucket_item_name;

        public MyBucketViewHolder(@NonNull View itemView) {
            super(itemView);
            bucket_item_id = itemView.findViewById(R.id.bucket_item_id);
            bucket_item_listid = itemView.findViewById(R.id.bucket_item_listid);
            bucket_item_name = itemView.findViewById(R.id.bucket_item_name);
        }
    }

  /*

    private Filter bucketListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BucketList> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(bucketListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BucketList item : bucketListFull) {
                    if (String.valueOf(item.getId()).contains(filterPattern) ||
                            String.valueOf(item.getListId()).contains(filterPattern) ||
                            (item.getName() != null && item.getName().toLowerCase().contains(filterPattern))) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bucketLists.clear();
            bucketLists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

*/

}
