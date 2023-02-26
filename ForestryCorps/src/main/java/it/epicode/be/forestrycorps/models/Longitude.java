package it.epicode.be.forestrycorps.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "longitudes")
@DiscriminatorValue("longitude")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Longitude extends Coordinate {

	
}
