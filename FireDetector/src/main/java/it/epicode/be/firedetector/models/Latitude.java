package it.epicode.be.firedetector.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "latitudes")
@DiscriminatorValue("latitude")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Latitude extends Coordinate {

	
}
