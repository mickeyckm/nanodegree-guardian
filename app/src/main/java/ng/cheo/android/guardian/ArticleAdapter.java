package ng.cheo.android.guardian;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mickey on 6/11/16.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    static class ViewHolder {
        TextView nameTextView;
        TextView sectionTextView;
        TextView pubDateTextView;
    }

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name);
            viewHolder.sectionTextView = (TextView) convertView.findViewById(R.id.section);
            viewHolder.pubDateTextView = (TextView) convertView.findViewById((R.id.date)) ;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article article = getItem(position);

        viewHolder.nameTextView.setText(article.getName());
        viewHolder.sectionTextView.setText(article.getSection());

        return convertView;
    }
}
