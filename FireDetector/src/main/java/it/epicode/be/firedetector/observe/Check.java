package it.epicode.be.firedetector.observe;

import java.util.Timer;
import java.util.TimerTask;

import it.epicode.be.firedetector.models.Probe;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Check extends TimerTask {
	
	private static int counter = 0;
	
	private Probe p;
	private Timer t;

	public void run() {
		
		if (counter++ > 100) {
			t.cancel();
	        t.purge();
		}
		
		p.scan();			
	}
}
