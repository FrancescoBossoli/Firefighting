package it.epicode.be.firedetector.utils.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WarningNotification implements Notification {

	public static final String yellow(String message) {
		return warning + message + reset;
	}

	public static void write(String message) {
		log.info(yellow(line));
		log.info(yellow("WARNING - " + message));
		log.info(yellow(line));
	}

}
