package cl.ufro.dci.tarea1.services;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> generateCars(int carQuantity){
        List<Car> generatedCars = new ArrayList<>();

        for (int i = 0; i < carQuantity; i++) {
            generatedCars.add(new Car());
        }
        return carRepository.saveAll(generatedCars);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }
}
