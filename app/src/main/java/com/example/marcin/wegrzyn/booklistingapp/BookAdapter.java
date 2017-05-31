package com.example.marcin.wegrzyn.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marcin on 30.05.2017 :)
 */

class BookAdapter extends ArrayAdapter<Book> {
    BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;

        if (listView == null) {

            listView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }

        Book book = getItem(position);

        TextView title = (TextView) listView.findViewById(R.id.titleTv);
        TextView subtitle = (TextView) listView.findViewById(R.id.subtitleTv);
        TextView author = (TextView) listView.findViewById(R.id.authorTv);

        title.setText(book.getTitle());

        if (book.hasSubtitle()) {
            subtitle.setText(book.getSubtitle());
            subtitle.setVisibility(View.VISIBLE);
        } else {
            subtitle.setVisibility(View.GONE);
        }
        author.setText(book.getAuthors());

        return listView;
    }

}
