package cl.ufro.dci.tarea1.controllers;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/car")
public class CarController {
    private final CarService carService;

    @GetMapping("create/{carsQuantity}")
    public List<Car> generateCars(@PathVariable int carsQuantity){
        return carService.generateCars(carsQuantity);
    }

    @GetMapping("")
    public List<Car> getCars(){
        return carService.getCars();
    }

    @GetMapping("agent/filter")
    public List<Car> filterCarsForAgent(@RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String type, @RequestParam(required = false) String color){
        return carService.agentFilter(maxPrice, type, color);}

    @GetMapping("filter")
    public List<Map<String, Object>> filterCars(@RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String type, @RequestParam(required = false) String color){
        return carService.filter(maxPrice, type, color);}


    @GetMapping("contact/{carId}")
    public Car contactAgency(@PathVariable int carId) {
        return carService.contactAgency(carId);
    }
}
