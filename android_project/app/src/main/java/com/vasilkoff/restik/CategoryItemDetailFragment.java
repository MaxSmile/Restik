package com.vasilkoff.restik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vasilkoff.restik.data.DishContent;
import com.vasilkoff.restik.front.AsymmetricListActivity;

import java.util.HashMap;

/**
 * A fragment representing a single CategoryItem detail screen.
 * This fragment is either contained in a {@link CategoryItemListActivity}
 * in two-pane mode (on tablets) or a {@link CategoryItemDetailActivity}
 * on handsets.
 */
public class CategoryItemDetailFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DishContent.DishItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this.getActivity();
        if (getArguments().containsKey(ARG_ITEM_ID)) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getArguments().getString(ARG_ITEM_ID));

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() instanceof HashMap) {
                        HashMap<String,String>itemData = (HashMap<String, String>) dataSnapshot.getValue();
                        mItem = new DishContent.DishItem(itemData.get("name"),
                                itemData.get("image"),itemData.get("description"));
                        setupWithData();
                    }
                    String value = dataSnapshot.getValue().toString();
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());

                }
            });
        }
    }

    public void setupWithData () {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.content);
        }
        if (mItem != null && rootView != null) {
            ((TextView) rootView.findViewById(R.id.categoryitem_detail)).setText(mItem.details);
        }
    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.categoryitem_detail, container, false);

        return rootView;
    }
}
