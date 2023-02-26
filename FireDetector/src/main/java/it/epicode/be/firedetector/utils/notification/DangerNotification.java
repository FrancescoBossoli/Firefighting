package it.epicode.be.firedetector.utils.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DangerNotification implements Notification {

	public static final String red(String message) {
		return danger + message + reset;
	}

	public static void write(String message) {
		log.info(red(line));
		log.info(red("DANGER - " + message));
		log.info(red(line));
	}
}
