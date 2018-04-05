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
public class FamilyFragment extends Fragment {


    public FamilyFragment() {
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
        View rootView=inflater.inflate(R.layout.activity_words,container,false);
        maudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words= new ArrayList<Word>();
        words.add(new Word("father","epe",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","eta",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliyi",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter itemsAdapter= new WordAdapter(getActivity(),words,R.color.category_family);
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
