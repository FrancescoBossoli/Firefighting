package it.epicode.be.firedetector.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.firedetector.models.Coordinate;
import it.epicode.be.firedetector.models.Latitude;
import it.epicode.be.firedetector.models.Longitude;
import it.epicode.be.firedetector.models.Probe;
import it.epicode.be.firedetector.repositories.CoordinateRepository;
import it.epicode.be.firedetector.repositories.ProbeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProbeService {

	@Autowired
	private ProbeRepository pR;
	
	@Autowired
	private CoordinateRepository cR;
	
	public void save(Probe p) {
		Optional<Probe> o = getProbeByCoordinates(p.getLatitude(), p.getLongitude());
		if (o.isEmpty()) {
			pR.save(p);
			log.info("The Probe has been saved in the Database.");			
		}
		else log.info("This Probe is already present in the Database.");
	}
	
	public Optional<Probe> getProbeById(int id) {
		return pR.findById(id);
	}
		
	public List<Probe> getAllProbes(){
		return pR.findAll(PageRequest.of(0 , 2000)).getContent();
	}
	
	public Page<Probe> getAllProbes(Pageable p) {
		return pR.findAll(p);
	}
	
	public Optional<Probe> getProbeByCoordinates(Latitude a, Longitude b) {
		int x, y;
		Optional<Coordinate> xC = cR.findSpecificCoordinate(a.getDirection().toString(), a.getGrades(), a.getMinutes(), a.getSeconds());
		if (xC.isPresent()) x = xC.get().getId();
		else return null;
		Optional<Coordinate> yC = cR.findSpecificCoordinate(b.getDirection().toString(), b.getGrades(), b.getMinutes(), b.getSeconds());
		if (yC.isPresent()) y = yC.get().getId();
		else return null;
		return pR.findProbeByCoordinates(x, y);
	}
	
	public void deleteProbeById(int id) {
		pR.deleteById(id);
	}
	
	public void printList(List<Probe> list) {
		for (Probe l : list)
			log.info(l.toString());
	}
}
