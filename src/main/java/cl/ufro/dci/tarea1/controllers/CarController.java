package cl.ufro.dci.tarea1.controllers;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/car")
public class CarController {
    private final CarService carService;

    @GetMapping("create/{quantity}")
    public List<Car> generateCars(@PathVariable int quantity){
        return carService.generateCars(quantity);
    }

    @GetMapping("")
    public List<Car> getCars(){
        return carService.getCars();
    }

    @GetMapping("agent/filter")
    public List<Car> getCarsWithFilter(@RequestParam Integer maxPrice, @RequestParam String type, @RequestParam String color){
        return carService.filter(maxPrice, type,color);}


    @GetMapping("contact/{carId}")
    public Car contactAgency(@PathVariable int carId) {
        return carService.contactAgency(carId);
    }
}
