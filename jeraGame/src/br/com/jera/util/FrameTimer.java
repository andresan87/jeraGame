package br.com.jera.util;


public class FrameTimer {
	
	public FrameTimer()
	{
		currentFrame = currentFirst = currentLast = 0;
		lastTime = 0;
	}

	public int getFrame()
	{
		return currentFrame;
	}

	public int setFrame(int first, int last, long stride)
	{
		if (first != currentFirst || last != currentLast)
		{
			currentFrame = first;
			currentFirst = first;
			currentLast  = last;
			lastTime = System.currentTimeMillis();
			return currentFrame;
		}

		if (System.currentTimeMillis()-lastTime > stride)
		{
			currentFrame++;
			if (currentFrame > last)
				currentFrame = first;
			lastTime = System.currentTimeMillis();
		}

		return currentFrame;
	}

	private long lastTime;
	private int currentFirst;
	private int currentLast;
	private int currentFrame;
}
