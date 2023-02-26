package it.epicode.be.forestrycorps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import it.epicode.be.forestrycorps.models.Coordinate;
import it.epicode.be.forestrycorps.models.Direction;
import it.epicode.be.forestrycorps.models.Latitude;
import it.epicode.be.forestrycorps.models.Location;
import it.epicode.be.forestrycorps.models.Longitude;
import it.epicode.be.forestrycorps.models.Role;
import it.epicode.be.forestrycorps.models.RoleType;
import it.epicode.be.forestrycorps.models.User;


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
	public Location newLocation(String s, Latitude x, Longitude y) {
		return Location.builder().name(s).latitude(x).longitude(y).build();
	}
	
	@Bean
	@Scope("prototype")
	public User newUser(String u, String e, String p, String fn) {
		return User.builder().username(u).email(e).password(p).name(fn).build();
	}
	
	@Bean
	@Scope("prototype")
	public Role newRole(RoleType r) {
		return Role.builder().name(r).build();
	}



}
