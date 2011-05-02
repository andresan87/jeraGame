package br.com.jera.platform.android;

import android.app.Activity;
import br.com.jera.audio.AudioPlayer;

public interface JGRunnable {
	public void run(final String status, Activity activity, final AudioPlayer audioPlayer);
}
