package br.com.jera.audio;

public interface AudioPlayer {

	public void load(int id);
	public int play(int id);
	public void setGlobalVolume(float volume);
	public float getGlobalVolume();
	public void stop(int streamId);
}
