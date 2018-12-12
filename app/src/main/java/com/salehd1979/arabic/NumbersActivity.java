package com.salehd1979.arabic;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class NumbersActivity extends Activity
{
	/** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange)
		{
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
				focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
			{
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
			else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
			{
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            }
			else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
			{
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer)
		{
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_list);
		
		// Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		

		final ArrayList<Word> words = new ArrayList<Word>();

		words.add(new Word("١", R.raw.n1));
		words.add(new Word("٢", R.raw.n2));
		words.add(new Word("٣", R.raw.n3));
		words.add(new Word("٤", R.raw.n4));
		words.add(new Word("٥", R.raw.n5));
		words.add(new Word("٦", R.raw.n6));
		words.add(new Word("٧", R.raw.n7));
		words.add(new Word("٨", R.raw.n8));
		words.add(new Word("٩", R.raw.n9));
		words.add(new Word("١٠", R.raw.n10));

		WordApater adapter = new WordApater(this, words);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);

		// Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
					// Release the media player if it currently exists because we are about to
					// play a different sound file
					releaseMediaPlayer();

					// Get the {@link Word} object at the given position the user clicked on
					Word word = words.get(position);

					// Request audio focus so in order to play the audio file. The app needs to play a
					// short audio file, so we will request audio focus with a short amount of time
					// with AUDIOFOCUS_GAIN_TRANSIENT.
					int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
																 AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

					if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
						// We have audio focus now.

						// Create and setup the {@link MediaPlayer} for the audio resource associated
						// with the current word
						mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourseId());

						// Start the audio file
						mMediaPlayer.start();

						// Setup a listener on the media player, so that we can stop and release the
						// media player once the sound has finished playing.
						mMediaPlayer.setOnCompletionListener(mCompletionListener);
					}
				}
			});
		


	}
	/**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer()
	{
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null)
		{
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
	
	@Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
	


}
