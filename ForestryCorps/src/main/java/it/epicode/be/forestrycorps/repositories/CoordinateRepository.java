package it.epicode.be.forestrycorps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.be.forestrycorps.models.Coordinate;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM coordinates WHERE direction = :d AND grades = :g AND minutes = :m AND seconds = :s")
	Optional<Coordinate> findSpecificCoordinate(@Param("d") String d, @Param("g") Integer g, @Param("m") Integer m, @Param("s") Integer s);

}
