package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColoursFragment extends Fragment {


    public ColoursFragment() {
        // Required empty public constructor
    }

    MediaPlayer mediaplayer;
    private AudioManager.OnAudioFocusChangeListener mAudioChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            }
            else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mediaplayer.start();
            }
            else if(i==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    private AudioManager maudioManager;
    private MediaPlayer.OnCompletionListener onCompleteList=new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer media)
        {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_words,container,false);
        maudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> colors= new ArrayList<Word>();
        colors.add(new Word("red","wetetti",R.drawable.color_red,R.raw.color_red));
        colors.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        colors.add(new Word("brown","takaakki",R.drawable.color_brown,R.raw.color_brown));
        colors.add(new Word("gray","topoppi",R.drawable.color_gray,R.raw.color_gray));
        colors.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        colors.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        colors.add(new Word("dusty yellow","topiise",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow","chiwiite",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        WordAdapter colorsAdapter= new WordAdapter(getActivity(),colors,R.color.category_colors);
        ListView listView=(ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(colorsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                releaseMediaPlayer();
                Word word= colors.get(position);
                int result=maudioManager.requestAudioFocus(mAudioChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaplayer = MediaPlayer.create(getActivity(), word.getsoundId());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(onCompleteList);
                }
            }
        });
        return rootView;
    }
    @Override
    public void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    public void releaseMediaPlayer(){
        if(mediaplayer!=null) {
            mediaplayer.release();
            mediaplayer = null;
            maudioManager.abandonAudioFocus(mAudioChangeListener);
        }
    }
}
