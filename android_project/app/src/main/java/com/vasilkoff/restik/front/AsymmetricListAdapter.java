package com.vasilkoff.restik.front;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vasilkoff.restik.R;
import com.vasilkoff.restik.front.model.TileItem;

import java.util.List;


/**
 * Created by vasilkoff on 13/02/2017.
 */

public class AsymmetricListAdapter extends ArrayAdapter<TileItem> implements ListAdapter {

    private final LayoutInflater layoutInflater;

    public AsymmetricListAdapter(Context context, List<TileItem> items) {
        super(context, 0, items);
        layoutInflater = LayoutInflater.from(context);
    }

    public AsymmetricListAdapter(Context context) {
        super(context, 0);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;

        TileItem item = getItem(position);
        boolean isRegular = getItemViewType(position) == 0;

        if (convertView == null) {
            v = layoutInflater.inflate(
                    isRegular ? R.layout.adapter_item : R.layout.adapter_item_odd, parent, false);
        } else {
            v = convertView;
        }

        TextView textView;
        ImageView imageView;
        if (isRegular) {
            textView = (TextView) v.findViewById(R.id.textview);
            imageView = (ImageView) v.findViewById(R.id.image);
        } else {
            textView = (TextView) v.findViewById(R.id.textview_odd);
            imageView = (ImageView) v.findViewById(R.id.image_odd);
        }
        Picasso.with(getContext()).load(item.getImageUrl()).into(imageView);
        textView.setText(item.getTitle());

        return v;
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }

    public void appendItems(List<TileItem> newItems) {
        addAll(newItems);
        notifyDataSetChanged();
    }

    public void setItems(List<TileItem> moreItems) {
        clear();
        appendItems(moreItems);
    }
}