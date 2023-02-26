package it.epicode.be.firedetector.utils;

import it.epicode.be.firedetector.models.Probe;
import it.epicode.be.firedetector.utils.notification.*;

public class NotificationManager {
	
	public static void redact(Probe p) {
		String smoke = " - Misurazione Rilevata: Livello " + p.getDanger();
		if (p.getDanger() > 7) DangerNotification.write(p.toString() + smoke);
		else if (p.getDanger() > 5) WarningNotification.write(p.toString() + smoke);
		else InfoNotification.write(p.toString() + smoke);
	}
}
