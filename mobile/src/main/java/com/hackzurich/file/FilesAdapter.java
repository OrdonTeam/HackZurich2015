package com.hackzurich.file;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.hackzurich.R;
import com.hackzurich.model.DropBoxFiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FilesAdapter extends BaseAdapter {
    private List<String> names = new ArrayList<>();
    private DropBoxFiles files;

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_layout, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.fileName);
        textView.setText(names.get(position));
        view.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files.setSelected(names.get(position), !v.isSelected());
                v.setSelected(!v.isSelected());
            }
        });
        return view;
    }

    public void addAll(DropBoxFiles files) {
        this.files = files;
        this.names = files.getNames();
        notifyDataSetChanged();
    }

    public Collection<DropboxAPI.Entry> getSelected() {
        return files.getSelected();
    }
}
