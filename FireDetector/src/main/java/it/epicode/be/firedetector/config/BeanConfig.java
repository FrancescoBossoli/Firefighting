package it.epicode.be.firedetector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import it.epicode.be.firedetector.models.Coordinate;
import it.epicode.be.firedetector.models.Direction;
import it.epicode.be.firedetector.models.FireDetector;
import it.epicode.be.firedetector.models.Latitude;
import it.epicode.be.firedetector.models.Longitude;
import it.epicode.be.firedetector.models.Probe;

@Configuration
public class BeanConfig {
	
	@Bean
	@Scope("prototype")
	public Coordinate newCoordinate(Direction d, int g, int m, int s) {
		if (d == Direction.N || d == Direction.S) return Latitude.builder().direction(d).grades(g).minutes(m).seconds(s).build();
		else return Longitude.builder().direction(d).grades(g).minutes(m).seconds(s).build();
	}
	
	@Bean
	@Scope("prototype")
	public Probe newProbe(String s, Latitude x, Longitude y) {
		return Probe.builder().name(s).latitude(x).longitude(y).build();
	}
	
	@Bean
	@Scope("prototype")
	public FireDetector newFireDetector(String s) {
		return FireDetector.builder().name(s).build();
	}


}
