package cl.ufro.dci.tarea1.repositories;

import cl.ufro.dci.tarea1.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
