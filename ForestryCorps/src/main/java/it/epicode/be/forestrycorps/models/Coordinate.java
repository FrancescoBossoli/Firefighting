package it.epicode.be.forestrycorps.models;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "coordinates")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Coordinate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Enumerated(EnumType.STRING)
	protected Direction direction;
	protected int grades;
	protected int minutes;
	protected int seconds;
	
	@Override
	public String toString() {
		return this.getDirection().toString() + this.getGrades() + "°" + this.getMinutes() + "'" + this.getSeconds() + "\"";
	}
}
