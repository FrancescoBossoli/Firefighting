package it.epicode.be.firedetector.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import it.epicode.be.firedetector.observe.Observer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fire_detectors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FireDetector implements Observer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@Override
	public void update(Probe p) {			
		raS.emergencyAlert(p);
	}
	
	
}
