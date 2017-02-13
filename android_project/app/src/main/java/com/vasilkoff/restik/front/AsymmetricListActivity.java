package com.vasilkoff.restik.front;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vasilkoff.restik.CategoryItemDetailActivity;
import com.vasilkoff.restik.CategoryItemDetailFragment;
import com.vasilkoff.restik.CategoryItemListActivity;
import com.vasilkoff.restik.R;
import com.vasilkoff.restik.front.model.TileItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AsymmetricListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final String TAG = this.getClass().getSimpleName();
    protected AsymmetricGridView listView;
    protected AsymmetricListAdapter adapter;
    private final Context context = this;
    private List<TileItem> tilesList = new ArrayList<>(20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asymmetric_list);
        listView = (AsymmetricGridView) findViewById(R.id.listView);


       loadData();

        listView.setRequestedColumnCount(3);
        listView.setRequestedHorizontalSpacing(Utils.dpToPx(this, 2));
        listView.setOnItemClickListener(this);

    }

    private void setAdapter() {
        // initialize your items array
        adapter = new AsymmetricListAdapter(this,tilesList);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<>(this, listView, adapter);
        listView.setAdapter(asymmetricAdapter);
        listView.setAllowReordering(true);
        listView.determineColumns();
    }

    private void addItems(ArrayList<HashMap> items) {

        for (HashMap<String,String> val : items) {
            String name = val.get("name");
            String image = val.get("image");
            if (name != null && image != null) {
                int colSpan = Math.random() < 0.2f ? 2 : 1;
                int rowSpan = Math.random() < 0.2f ? 2 : 1;
                TileItem tile = new TileItem(colSpan, rowSpan,  name, image);
                tilesList.add(tile);

            }
        }

        setAdapter();
    }

    private void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("menu");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value.toString());

                if (value instanceof ArrayList) {
                    tilesList.clear();
                    addItems((ArrayList)value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, CategoryItemListActivity.class);
        intent.putExtra(CategoryItemDetailFragment.ARG_ITEM_ID, tilesList.get(position));
        context.startActivity(intent);
    }
}
