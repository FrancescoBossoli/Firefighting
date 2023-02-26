package it.epicode.be.forestrycorps.controllers;

import java.time.LocalDate;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.forestrycorps.models.Coordinate;
import it.epicode.be.forestrycorps.models.Direction;
import it.epicode.be.forestrycorps.models.Emergency;
import it.epicode.be.forestrycorps.models.Latitude;
import it.epicode.be.forestrycorps.models.Location;
import it.epicode.be.forestrycorps.models.Longitude;
import it.epicode.be.forestrycorps.services.EmergencyService;
import it.epicode.be.forestrycorps.services.LocationService;
import it.epicode.be.forestrycorps.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EmergencyController {
	
	@Autowired
	AuthenticationManager aM;	
	@Autowired
	PasswordEncoder pE;
	@Autowired
	JwtUtils jU;
	@Autowired
	LocationService lS;
	@Autowired
	EmergencyService eS;

	@PostMapping("/alarm")
	public String emergencyCall(@RequestParam int idsonda, @RequestParam String lat, @RequestParam String lon, @RequestParam int smokelevel) {
		try {
			Latitude x = (Latitude)decode(lat);
			Longitude y = (Longitude)decode(lon);
			Location l = lS.getLocationByCoordinates(x, y).get();
			log.info("Pericolo d'incendio rilevato: " + l.toString());
			log.info("Quantità di fumo rilevata: livello " + smokelevel);
			log.info("Un log della richiesta di soccorso è stato inserito nel database. \n");
			eS.save(Emergency.builder().date(LocalDate.now()).time(Calendar.getInstance()).location(l).emergencyLevel(smokelevel).build());
			return "Segnalazione ricevuta, i Soccorsi sono in mobilitazione verso " + l.getName();
		} catch (IllegalArgumentException e) {
			log.info("Segnalazione ricevuta, ma i dati trasmessi non sono leggibili. \nSembrano non essere conformi allo standard utilizzato.\n");
		} catch (NullPointerException e) {
			log.info("Segnalazione ricevuta, ma le coordinate inserite non corrispondono ad un'Area monitorata.\n");
		}
		return "Non è stato possibile ricevere la vostra segnalazione. Se i dati inseriti sono corretti contattateci al 1515";
	}
	
	public Coordinate decode(String x) {
		Coordinate c;
		Direction d = Direction.valueOf(x.substring(0, 1));		
		int g = Integer.parseInt(x.substring(1, x.indexOf("g")));
		int m = Integer.parseInt(x.substring(x.indexOf("g") + 1, x.indexOf("m")));
		int s = Integer.parseInt(x.substring(x.indexOf("m") + 1, x.indexOf("s")));
		if (d.toString() == "N" || d.toString() == "S") c = Latitude.builder().direction(d).grades(g).minutes(m).seconds(s).build();
		else c = Longitude.builder().direction(d).grades(g).minutes(m).seconds(s).build();
		return c;		
	}
}
