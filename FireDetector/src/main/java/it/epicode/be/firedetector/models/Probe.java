package it.epicode.be.firedetector.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import it.epicode.be.firedetector.observe.Subject;
import it.epicode.be.firedetector.utils.NotificationManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "probes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Probe extends Subject{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank
	private String name;
	@NonNull
	@OneToOne
	private Latitude latitude;
	@NonNull
	@OneToOne
	private Longitude longitude;
	private int danger;
	
	public void scan() {
		int x = (int)(Math.random() * 101);	
		if (x > 98) danger = 10;
		else if (x > 97) danger = 9;
		else if (x > 96) danger = 8;
		else if (x > 94) danger = 7;
		else if (x > 92) danger = 6;
		else if (x > 90) danger = 5;
		else if (x > 70) danger = 4;
		else if (x > 50) danger = 3;
		else if (x > 30) danger = 2;
		else if (x > 10) danger = 1;
		else danger = 0;
		NotificationManager.redact(this);
		if (danger > 5) notifyObserver();
	}
	
	public String toString() {
		return "Sonda: " + getName() + " - Coordinate: " + getLatitude()
						 + " " + getLongitude();
	}
	
	@Override
	public void notifyObserver() {
		observer.update(this);
	}
		
	
}
