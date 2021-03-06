package robot;

public class SXTimer {
	private long oldTime;
	private long updateInterval;
	private boolean eqreset;
	private long pauseVal;
	
	public SXTimer (long ui) {
		eqreset = true;
		updateInterval = ui;
		reset();
	}
	
	public SXTimer () {
		this(0);
		pauseVal = 0;
	}
	
	public void reset() {
		oldTime = System.currentTimeMillis();
		pauseVal = 0;
	}
	
	/**
	 * reset and set update interval
	 * @param ui
	 */
	public void reset(int ui) {
		updateInterval = ui;
		oldTime = System.currentTimeMillis();
		pauseVal = 0;
	}
	
	/**
	 * Use to check if enough time has gone by for update. Also resets if trigger. 
	 * Default:	reset on return >= 0. Can be changed by resetOnEqual(boolean) 
	 * @return 1 if enough time has gone by. 0 if time is exactly equal. -1 if not enough time has gone by
	 */
	public int isTriggered() {
		long thisTime = System.currentTimeMillis();
		long comp = thisTime - oldTime;
		if (comp > updateInterval) {
			reset();
			return 1;
		}
		else if (comp == updateInterval) {
			if(eqreset) reset();
			return 0;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Use to check if enough time has gone by only. No reset.
	 * @return
	 */
	public boolean check() {
		long thisTime = System.currentTimeMillis();
		long comp = thisTime - oldTime;
		if (comp > updateInterval) {
			pauseVal = 0;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void resetOnEqual(boolean b) {
		eqreset = b;
	}
	
	public int percentage() {
		long thisTime = System.currentTimeMillis();
		long comp = thisTime - oldTime;
		if(comp >= updateInterval) 
			return 100;
		else {
			return Math.round(((float)comp/(float)updateInterval)*100f);
		}
	}
	
	public void pause() {
		if(!check())
			pauseVal = System.currentTimeMillis() - oldTime;
	}
	
	public void unpause() {
		if(pauseVal == 0)
			return;
		
		oldTime = System.currentTimeMillis();
		updateInterval = pauseVal;
		pauseVal = 0;
	}
}
