package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by vaibhav on 03-11-2017.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int mcolourId;
    private int mmediaplayer;
    public WordAdapter(Activity context, ArrayList<Word> words,int colour)
    {
        super(context,0,words);
        mcolourId=colour;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView= convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final Word currentWord=getItem(position);
        TextView miwokTextView= (TextView)listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());
        TextView defaultTextView= (TextView)listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());
        ImageView img=(ImageView)listItemView.findViewById(R.id.image_view);
        if(currentWord.isImage()) {
            img.setImageResource(currentWord.getImageResourceId());
        }
        else
        {
            img.setVisibility(View.GONE);
        }

        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mcolourId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
