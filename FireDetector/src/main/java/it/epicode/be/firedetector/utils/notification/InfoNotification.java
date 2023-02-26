package it.epicode.be.firedetector.utils.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InfoNotification implements Notification {

	public static final String green(String message) {
		return info + message + reset;
	}
	
	public static void write(String message) {
		log.info(green(line));
		log.info(green("INFO - " + message));
		log.info(green(line));
	}

}
