package com.gzr7702.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;


public class BookAdapter extends ArrayAdapter<Book> {

    private final Context context;
    private final ArrayList<Book> mBookArrayList;

    public BookAdapter(Context context, ArrayList<Book> bookArrayList) {

        super(context, R.layout.bookview_item, bookArrayList);

        this.context = context;
        this.mBookArrayList = bookArrayList;
    }

    // Class to use View Holder pattern
    private class ViewHolder {
        TextView titleView;
        TextView authorView;
        TextView publishDateview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = null;
        ViewHolder holder = new ViewHolder();

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.bookview_item, parent, false);

            holder.titleView = (TextView) rowView.findViewById(R.id.title);
            holder.authorView = (TextView) rowView.findViewById(R.id.author);
            holder.publishDateview = (TextView) rowView.findViewById(R.id.publish_date);

            holder.titleView.setText(mBookArrayList.get(position).getTitle());
            holder.authorView.setText(mBookArrayList.get(position).getAuthors());
            holder.publishDateview.setText(mBookArrayList.get(position).getPublishDate());
            rowView.setTag(holder);

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        return rowView;
    }
}

