package com.angryzpua.testtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CatListAdapter extends ArrayAdapter<CatListItem> {
    public CatListAdapter(@NonNull Context context, @NonNull List<CatListItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(convertView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }
        final CatListItem catListItem = super.getItem(position);
        TextView catBreed = listItemView.findViewById(R.id.catBreed);
        catBreed.setText(catListItem.getBREED());
        return listItemView;
    }
}
