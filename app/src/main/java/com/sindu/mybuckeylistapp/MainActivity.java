package com.sindu.mybuckeylistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.sindu.mybuckeylistapp.adapters.BucketListAdapter;
import com.sindu.mybuckeylistapp.model.BucketList;
import com.sindu.mybuckeylistapp.retrofit.APIServiceInterface;
import com.sindu.mybuckeylistapp.retrofit.BaseUrl;
import com.sindu.mybuckeylistapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview_list_view;
    private BucketListAdapter bucketListAdapter;

   // private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview_list_view = findViewById(R.id.recyclerview_list_view);
        //searchView = findViewById(R.id.search_view);

        fetchBucketListFromURL();

        // Set up the SearchView to filter data
       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bucketListAdapter.getFilter().filter(newText);
                return false;
            }
        });*/
    }

    private void fetchBucketListFromURL() {
        APIServiceInterface apiServiceInterface = RetrofitClient.getInstance().getApiServiceInterface();

        Call<List<BucketList>> loginResponse = apiServiceInterface.fetchRecords();

        loginResponse.enqueue(new Callback<List<BucketList>>() {
            @Override
            public void onResponse(Call<List<BucketList>> call, Response<List<BucketList>> response) {

                List<BucketList> feedbackList = response.body();

                if (feedbackList != null) {

                    Log.e("fetchBucketListFromURL", new Gson().toJson(feedbackList));

                    // Filter, sort, and group the list
                    List<BucketList> processedList = processList(feedbackList);

                    bucketListAdapter = new BucketListAdapter(getApplicationContext(),processedList);
                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                    recyclerview_list_view.setAdapter(bucketListAdapter);
                    recyclerview_list_view.setLayoutManager(llm);


                }
                else {
                    Log.e("fetchBucketListFromURL", "else: No Data");

                }

            }

            @Override
            public void onFailure(Call<List<BucketList>> call, Throwable t) {
                Log.v("fetchBucketListFromURL", t.getMessage());
            }

        });
    }

    private List<BucketList> processList(List<BucketList> bucketList) {
        // Filter out items where name is blank or null
        List<BucketList> filteredList = new ArrayList<>();
        for (BucketList item : bucketList) {
            if (item.getName() != null && !item.getName().trim().isEmpty()) {
                filteredList.add(item);
            }
        }

        // Sort the list by listId and then by name
        Collections.sort(filteredList, new Comparator<BucketList>() {
            @Override
            public int compare(BucketList o1, BucketList o2) {
                int listIdComparison = Integer.compare(o1.getListId(), o2.getListId());
                if (listIdComparison != 0) {
                    return listIdComparison;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        return filteredList;
    }
}