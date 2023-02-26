package it.epicode.be.forestrycorps.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Location {

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
	@JsonIgnore
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private List<Emergency> emergencies;
	
	public String toString() {
		return getName() + " - Coordinate: " + getLatitude() + " " + getLongitude();
	}
}
