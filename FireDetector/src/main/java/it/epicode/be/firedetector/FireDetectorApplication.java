package it.epicode.be.firedetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import it.epicode.be.firedetector.config.BeanConfig;
import it.epicode.be.firedetector.models.Coordinate;
import it.epicode.be.firedetector.models.Direction;
import it.epicode.be.firedetector.models.FireDetector;
import it.epicode.be.firedetector.models.Latitude;
import it.epicode.be.firedetector.models.Longitude;
import it.epicode.be.firedetector.models.Probe;
import it.epicode.be.firedetector.observe.Check;
import it.epicode.be.firedetector.services.CoordinateService;
import it.epicode.be.firedetector.services.FireDetectorService;
import it.epicode.be.firedetector.services.ProbeService;

@SpringBootApplication
public class FireDetectorApplication implements CommandLineRunner {

	private ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);

	@Autowired
	CoordinateService cS;

	@Autowired
	ProbeService pS;

	@Autowired
	FireDetectorService fS;	

	public static void main(String[] args) {
		SpringApplication.run(FireDetectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		populateDbAndRun();			
	}

	public void populateDbAndRun() {
		List<Probe> list = new ArrayList<Probe>();
		FireDetector f = save("Sistema Rilevamento Incendi");
		Coordinate a1 = save(Direction.N, 41, 22, 11);
		Coordinate a2 = save(Direction.E, 13, 1, 53);
		Probe a = save("Riserva Naturale del Circeo - Area Nord", a1, a2);
		list.add(a);
		Coordinate b1 = save(Direction.N, 41, 20, 55);
		Coordinate b2 = save(Direction.E, 13, 0, 31);
		Probe b = save("Riserva Naturale del Circeo - Area Ovest", b1, b2);
		list.add(b);
		Coordinate c1 = save(Direction.N, 41, 20, 27);
		Coordinate c2 = save(Direction.E, 13, 2, 57);
		Probe c = save("Riserva Naturale del Circeo - Area Centro", c1, c2);
		list.add(c);
		Coordinate d1 = save(Direction.N, 41, 19, 42);
		Coordinate d2 = save(Direction.E, 13, 4, 51);
		Probe d = save("Riserva Naturale del Circeo - Area Est", d1, d2);
		list.add(d);
		Coordinate e1 = save(Direction.N, 41, 18, 23);
		Coordinate e2 = save(Direction.E, 13, 3, 10);
		Probe e = save("Riserva Naturale del Circeo - Area Sud", e1, e2);
		list.add(e);
		for (Probe probe : list) probe.register(f);
		Timer timer = new Timer();
		for (Probe probe : list) {
			try {
				Thread.sleep(3000);
				timer.schedule(new Check(probe, timer), 0, 15000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public Coordinate save(Direction d, int g, int m, int s) {
		Coordinate x = (Coordinate) ctx.getBean("newCoordinate", d, g, m, s);
		cS.save(x);
		x = cS.getSpecificCoordinate(d, g, m, s).get();
		return x;
	}

	public Probe save(String s, Coordinate x, Coordinate y) {
		Probe p = null;
		if ((x.getDirection() == Direction.N || x.getDirection() == Direction.S)
				&& (y.getDirection() == Direction.E || y.getDirection() == Direction.W))
			p = (Probe) ctx.getBean("newProbe", s, x, y);
		else if ((x.getDirection() == Direction.E || x.getDirection() == Direction.W)
				&& (y.getDirection() == Direction.N || y.getDirection() == Direction.S))
			p = (Probe) ctx.getBean("newProbe", s, y, x);
		if (p != null)
			pS.save(p);
		p = pS.getProbeByCoordinates((Latitude)x,(Longitude)y).get();
		return p;
	}

	public FireDetector save(String s) {
		FireDetector f = (FireDetector) ctx.getBean("newFireDetector", s);
		fS.save(f);
		return f;
	}
}
