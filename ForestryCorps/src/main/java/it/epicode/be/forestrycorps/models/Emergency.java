package it.epicode.be.forestrycorps.models;

import java.time.LocalDate;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "emergencies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Emergency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NonNull
	private LocalDate date;
	private Calendar time;
	@Column(name = "emergency_level")
	private int emergencyLevel;
	@ManyToOne
	@NonNull
	private Location location;
	
}
