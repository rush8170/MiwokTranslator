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
public class NumbersFragment extends Fragment {


    public NumbersFragment() {
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

    private MediaPlayer.OnCompletionListener onCompleteList=new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer media)
        {
            releaseMediaPlayer();
        }
    };
    private AudioManager maudioManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_words,container,false);
        maudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words= new ArrayList<Word>();
        words.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter itemsAdapter= new WordAdapter(getActivity(),words,R.color.category_numbers);
        ListView listView= (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                releaseMediaPlayer();
                Word word= words.get(position);
                int result=maudioManager.requestAudioFocus(mAudioChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaplayer = MediaPlayer.create(getActivity(), word.getsoundId());
                    mediaplayer.start();
                    mediaplayer.setOnCompletionListener(onCompleteList);
                }
            }
        });
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
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
