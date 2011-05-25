package br.com.jera.platform.android;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import br.com.jera.audio.AudioPlayer;

public class AndroidAudioPlayer implements AudioPlayer {

	private static final int MAXIMUM_SIMULTANEOUS_SFX = 8;

	public AndroidAudioPlayer(Activity activity) {
		this.context = activity;
		this.pool = new SoundPool(MAXIMUM_SIMULTANEOUS_SFX, AudioManager.STREAM_MUSIC, 0);
		this.manager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
	}

	public void load(int id) {
		if (samples.get(new Integer(id)) == null) {
			samples.put(id, pool.load(context, id, 1));
		}
	}

	public int play(int id) {
		float streamVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume /= manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		streamVolume *= globalVolume;
		return pool.play(samples.get(id), streamVolume, streamVolume, 1, 0, 1.0f);
	}
	
	public void stop(int streamId) {
		pool.pause(streamId);
	}

	public void setGlobalVolume(float volume) {
		globalVolume = volume;
	}

	public float getGlobalVolume() {
		return globalVolume;
	}

	private static float globalVolume = 1.0f;
	private Context context;
	private SoundPool pool;
	private AudioManager manager;
	private HashMap<Integer, Integer> samples = new HashMap<Integer, Integer>();
}
