package it.epicode.be.forestrycorps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.be.forestrycorps.models.Role;
import it.epicode.be.forestrycorps.models.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleType r);
}