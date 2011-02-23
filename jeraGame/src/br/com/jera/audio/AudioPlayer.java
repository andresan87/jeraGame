package br.com.jera.audio;

public interface AudioPlayer {

	public void load(int id);
	public void play(int id);
	public void setGlobalVolume(float volume);
}
