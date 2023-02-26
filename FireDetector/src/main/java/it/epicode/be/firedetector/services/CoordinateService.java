package it.epicode.be.firedetector.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.firedetector.models.Coordinate;
import it.epicode.be.firedetector.models.Direction;
import it.epicode.be.firedetector.repositories.CoordinateRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoordinateService {

	@Autowired
	private CoordinateRepository cR;
	
	public void save(Coordinate c) {
		Optional<Coordinate> o = getSpecificCoordinate(c.getDirection(), c.getGrades(), c.getMinutes(), c.getSeconds());
		if (o.isEmpty()) {
			cR.save(c);
			log.info("The Coordinate has been saved in the Database.");			
		}
		else log.info("This Coordinate is already present in the Database.");
	}
	
	public Optional<Coordinate> getCoordinateById(int id) {
		return cR.findById(id);
	}
		
	public List<Coordinate> getAllCoordinates(){
		return cR.findAll();
	}
	
	public Optional<Coordinate> getSpecificCoordinate(Direction d, int g, int m, int s) {
		return cR.findSpecificCoordinate(d.toString(), (Integer)g, (Integer)m, (Integer)s);
	}
	
	public void deleteCoordinateById(int id) {
		cR.deleteById(id);
	}
	
	public void printList(List<Coordinate> list) {
		for (Coordinate l : list)
			log.info(l.toString());
	}
}
