package com.gzr7702.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookAdapter extends ArrayAdapter<Book> {

    private final Context context;
    private final ArrayList<Book> mBookArrayList;

    public BookAdapter(Context context, ArrayList<Book> bookArrayList) {

        super(context, R.layout.bookview_item, bookArrayList);

        this.context = context;
        this.mBookArrayList = bookArrayList;
    }

    // Class to use View Holder pattern
    static class ViewHolder {
        @BindView(R.id.title) TextView titleView;
        @BindView(R.id.author) TextView authorView;
        @BindView(R.id.publish_date) TextView publishDateview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = null;
        ViewHolder holder;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.bookview_item, parent, false);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);

            holder.titleView.setText(mBookArrayList.get(position).getTitle());
            holder.authorView.setText(mBookArrayList.get(position).getAuthors());
            holder.publishDateview.setText(mBookArrayList.get(position).getPublishDate());

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        return rowView;
    }
}

