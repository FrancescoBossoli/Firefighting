package it.epicode.be.forestrycorps;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.epicode.be.forestrycorps.config.BeanConfig;
import it.epicode.be.forestrycorps.models.Coordinate;
import it.epicode.be.forestrycorps.models.Direction;
import it.epicode.be.forestrycorps.models.Location;
import it.epicode.be.forestrycorps.models.Role;
import it.epicode.be.forestrycorps.models.RoleType;
import it.epicode.be.forestrycorps.models.User;
import it.epicode.be.forestrycorps.services.CoordinateService;
import it.epicode.be.forestrycorps.services.LocationService;
import it.epicode.be.forestrycorps.services.RoleService;
import it.epicode.be.forestrycorps.services.UserService;

@SpringBootApplication
public class ForestryCorpsApplication implements CommandLineRunner {
	
	private ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
	
	@Autowired
	UserService uS;
	
	@Autowired
	RoleService rS;
	
	@Autowired
	PasswordEncoder pE;
	
	@Autowired
	CoordinateService cS;
	
	@Autowired
	LocationService lS;


	public static void main(String[] args) {
		SpringApplication.run(ForestryCorpsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if (cS.getAllCoordinates().size() == 0) {
			populateDb();
		}	
		
	}
		
	@SuppressWarnings("unused")
	public void populateDb() {	
		Role r1 = save(RoleType.Administrator);
		Role r2 = save(RoleType.User);		
		User u1 = save("Admin", "admin@gmail.com", "admin123", "Centro Operativo", r1);
		User u2 = save("CirceoAlarm", "circeo@gmail.com", "circeo123", "Sistema di Allarme - Parco Nazionale del Circeo", r2);
		Coordinate a1 = save(Direction.N, 41, 22, 11);
		Coordinate a2 = save(Direction.E, 13, 1, 53);
		Location a = save("Riserva Naturale del Circeo - Area Nord", a1, a2);
		Coordinate b1 = save(Direction.N, 41, 20, 55);
		Coordinate b2 = save(Direction.E, 13, 0, 31);
		Location b = save("Riserva Naturale del Circeo - Area Ovest", b1, b2);
		Coordinate c1 = save(Direction.N, 41, 20, 27);
		Coordinate c2 = save(Direction.E, 13, 2, 57);
		Location c = save("Riserva Naturale del Circeo - Area Centro", c1, c2);
		Coordinate d1 = save(Direction.N, 41, 19, 42);
		Coordinate d2 = save(Direction.E, 13, 4, 51);
		Location d = save("Riserva Naturale del Circeo - Area Est", d1, d2);
		Coordinate e1 = save(Direction.N, 41, 18, 23);
		Coordinate e2 = save(Direction.E, 13, 3, 10);
		Location e = save("Riserva Naturale del Circeo - Area Sud", e1, e2);
		
	}
	
	public Coordinate save(Direction d, int g, int m, int s) {
		Coordinate x = (Coordinate)ctx.getBean("newCoordinate", d, g, m, s);
		cS.save(x);
		return x;
	}
	
	public Location save(String s, Coordinate x, Coordinate y) {
		Location l = null;
		if ((x.getDirection() == Direction.N || x.getDirection() == Direction.S) && (y.getDirection() == Direction.E || y.getDirection() == Direction.W))
			l = (Location)ctx.getBean("newLocation", s, x, y);
		else if ((x.getDirection() == Direction.E || x.getDirection() == Direction.W) && (y.getDirection() == Direction.N || y.getDirection() == Direction.S))
			l = (Location)ctx.getBean("newLocation", s, y, x);		
		if (l != null) lS.save(l);
		return l;
	}
	
	public User save(String u, String e, String p, String fn,  Role r) {
		User x = (User)ctx.getBean("newUser", u, e, pE.encode(p), fn);
		Set<Role> y = new HashSet<Role>();
		y.add(r);
		x.setRoles(y);
		uS.save(x);
		return x;
	}
	
	public Role save(RoleType r) {
		Role x = (Role)ctx.getBean("newRole", r);
		rS.save(x);
		return x;
	}

}
