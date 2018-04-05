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
public class PhrasesFragment extends Fragment {


    public PhrasesFragment() {
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
        View rootView = inflater.inflate(R.layout.activity_words,container,false);
        maudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("where are you  going?", "minto wuksus?", R.raw.phrase_where_are_you_going));
        words.add(new Word("what is your name?", "tinne oyaase'ne", R.raw.phrase_what_is_your_name));
        words.add(new Word("my  name is", "oyaaset", R.raw.phrase_my_name_is));
        words.add(new Word("how are you  feeling", "michekses", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I am feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "eenes'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes,I am coming", "hee'eenem", R.raw.phrase_yes_im_coming));
        words.add(new Word("i am coming", "eenem", R.raw.phrase_im_coming));
        words.add(new Word("Lets go", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here", "enni'nem", R.raw.phrase_come_here));
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);
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
    public void onStop()
    {
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
