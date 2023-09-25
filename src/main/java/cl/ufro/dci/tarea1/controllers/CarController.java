package cl.ufro.dci.tarea1.controllers;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This controller handles operations related to Car class
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/car")
public class CarController {
    private final CarService carService;

    /**
     * Generates a list of cars based on a number entered by the user
     *
     * @param carsQuantity The amount of cars to generate
     * @return The list of cars generated
     */
    @GetMapping("create/{carsQuantity}")
    public List<Car> generateCars(@PathVariable int carsQuantity){
        return carService.generateCars(carsQuantity);
    }

    /**
     * Filters the cars based on the filters entered by an agent
     *
     * @param maxPrice The maximum price of cars
     * @param type The type of car
     * @param color The color of cars
     * @return The list of cars filtered
     */
    @GetMapping("agent/filter")
    public List<Car> filterCarsForAgent(@RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String type, @RequestParam(required = false) String color){
        return carService.agentFilter(maxPrice, type, color);}

    /**
     * Filters the cars based on the filters entered by a user
     *
     * @param maxPrice The maximum price of cars
     * @param type The type of car
     * @param color The color of cars
     * @return The list of cars filtered without their popularity
     */
    @GetMapping("filter")
    public List<Map<String, Object>> filterCars(@RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String type, @RequestParam(required = false) String color){
        return carService.filter(maxPrice, type, color);}


    /**
     * Allows a user to contact the agency for a specific car
     *
     * @param carId The id of the car
     * @return The car that is being consulted
     */
    @GetMapping("contact/{carId}")
    public Car contactAgency(@PathVariable int carId) {
        return carService.contactAgency(carId);
    }
}
