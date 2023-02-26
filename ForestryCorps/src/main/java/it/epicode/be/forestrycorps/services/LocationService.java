package it.epicode.be.forestrycorps.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.forestrycorps.models.Coordinate;
import it.epicode.be.forestrycorps.models.Latitude;
import it.epicode.be.forestrycorps.models.Location;
import it.epicode.be.forestrycorps.models.Longitude;
import it.epicode.be.forestrycorps.repositories.LocationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocationService {

	@Autowired
	private LocationRepository pR;
	
	@Autowired
	private CoordinateService cS;
	
	public void save(Location p) {
		Optional<Location> o = getLocationByCoordinates(p.getLatitude(), p.getLongitude());
		if (o.isEmpty()) {
			pR.save(p);
			log.info("The Location has been saved in the Database.");			
		}
		else log.info("This Location is already present in the Database.");
	}
	
	public Optional<Location> getLocationById(int id) {
		return pR.findById(id);
	}
		
	public List<Location> getAllLocations(){
		return pR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Location> getAllLocations(Pageable p) {
		return pR.findAll(p);
	}
	
	public Optional<Location> getLocationByCoordinates(Latitude a, Longitude b) {
		int x, y;
		Optional<Coordinate> xC = cS.getSpecificCoordinate(a.getDirection(), a.getGrades(), a.getMinutes(), a.getSeconds());
		if (xC.isPresent()) x = xC.get().getId();
		else return null;
		Optional<Coordinate> yC = cS.getSpecificCoordinate(b.getDirection(), b.getGrades(), b.getMinutes(), b.getSeconds());
		if (yC.isPresent()) y = yC.get().getId();
		else return null;
		return pR.findLocationByCoordinates(x, y);
	}
	
	public void deleteLocationById(int id) {
		pR.deleteById(id);
	}
	
	public void printList(List<Location> list) {
		for (Location l : list)
			log.info(l.toString());
	}
}
