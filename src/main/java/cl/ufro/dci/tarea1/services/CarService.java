package cl.ufro.dci.tarea1.services;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This service has the logic for handling operations related to Car class
 */
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    static final String [] SEDAN_MOTOR_TYPES = {"1.4cc" , "1.6cc", "2.0cc"};
    static final String [] SUV_MOTOR_TYPES = {"1.8cc" , "2.2cc", "2.8cc"};
    static final String [] TRUCK_MOTOR_TYPES = {"2.4cc" , "3.0cc", "4.0cc"};
    static final String[] CAR_TYPES = {"SUV", "Truck", "Sedan"};
    static final String[] CAR_BRANDS = {"Toyota", "Ford", "Honda", "Chevrolet", "BMW"};
    static final String[] CAR_COLORS = {"Red", "Grey", "Blue", "Yellow", "Orange"};
    static final int MINIMUM_PRODUCTION_YEAR = 2015;
    static final int MAXIMUM_PRODUCTION_YEAR = 2023;
    static final int MINIMUM_PRICE = 8000000;
    static final int MAXIMUM_PRICE = 30000000;
    static final Random random = new Random();

    /**
     * Generates a list of cars based on a number entered by the user
     *
     * @param carsQuantity Number of cars to be created
     * @return List of cars created
     */
    public List<Car> generateCars(int carsQuantity){
        List<Car> generatedCars = new ArrayList<>();

        for (int i = 0; i < carsQuantity; i++) {
            String carType = generateCarSpecification(CAR_TYPES);
            generatedCars.add(new Car(
                    generateCarSpecification(CAR_BRANDS),
                    generateNumericValue(MINIMUM_PRODUCTION_YEAR, MAXIMUM_PRODUCTION_YEAR),
                    generateCarSpecification(CAR_COLORS),
                    generateNumericValue(MINIMUM_PRICE, MAXIMUM_PRICE),
                    generateRandomTurbo(),
                    carType,
                    generateRandomMotorType(carType),
                    generateRandomCabins(carType),
                    generateRandomSunroof(carType),
                    0
            ));
        }
        return carRepository.saveAll(generatedCars);
    }

    /**
     * Randomly determines if an SUV car has a sunroof or not
     *
     * @param carType Type of car
     * @return True or false
     */
    private boolean generateRandomSunroof(String carType) {
        if(carType.equalsIgnoreCase("suv")){
            return random.nextBoolean();
        }else{
            return false;
        }
    }

    /**
     * Randomly determines if a TRUCK has 1 or 2 cabins
     *
     * @param carType Type of car
     * @return The number of cabins the car will have
     */
    private int generateRandomCabins(String carType) {
        if(carType.equalsIgnoreCase("truck")){
            return random.nextInt(2)+1;
        }else{
            return 2;
        }
    }

    /**
     * Validates the type of car and assigns a random motor from a defined list
     *
     * @param carType Type of car
     * @return The type of motor the car will have
     */
    private String generateRandomMotorType(String carType) {
        int randomIndex;
        switch (carType.toLowerCase()) {
            case "sedan" -> {
                randomIndex = random.nextInt(SEDAN_MOTOR_TYPES.length);
                return SEDAN_MOTOR_TYPES[randomIndex];
            }
            case "truck" -> {
                randomIndex = random.nextInt(TRUCK_MOTOR_TYPES.length);
                return TRUCK_MOTOR_TYPES[randomIndex];
            }
            default -> {
                randomIndex = random.nextInt(SUV_MOTOR_TYPES.length);
                return SUV_MOTOR_TYPES[randomIndex];
            }
        }
    }

    /**
     * Randomly determines the value to return from a String array
     *
     * @param specifications Array of data of type String
     * @return a random String value
     */
    private String generateCarSpecification(String [] specifications) {
        int randomIndex = random.nextInt(specifications.length);

        return specifications[randomIndex];
    }

    /**
     * Randomly determines the boolean value to return
     *
     * @return A boolean value that determines whether the car has a turbo or not
     */
    private boolean generateRandomTurbo() {
        return random.nextBoolean();
    }

    /**
     * Method that randomly determines a numeric value
     *
     * @param minimumValue Minimum numeric value
     * @param maximumValue Maximum numeric value
     * @return A random numeric value, between the minimum value and maximum value
     */
    public int generateNumericValue(int minimumValue, int maximumValue) {
        return random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
    }

    /**
     * Gets a specific car whose popularity attribute increases by 1 each time the method is called.
     * @param carId Car id value
     * @return A car type object.
     */
    public Car contactAgency(Integer carId){
        Car contactedCar = carRepository.findById(carId).get();
        int carPopularity = contactedCar.getPopularity();
        contactedCar.setPopularity(carPopularity+1);
        carRepository.save(contactedCar);
        return contactedCar;
    }


    /**
     * Filters the cars based on the filters entered by a user
     *
     * @param maxPrice The maximum price of cars
     * @param type The type of car
     * @param color The color of cars
     * @return The list of cars filtered without their popularity
     */
    public List<Map<String, Object>> filter (Integer maxPrice, String type, String color){
        return agentFilter(maxPrice, type, color)
                .stream()
                .map(car -> {
                    Map<String, Object> carInfo = new HashMap<>();
                    carInfo.put("id", car.getId());
                    carInfo.put("brand", car.getBrand());
                    carInfo.put("productionYear", car.getProductionYear());
                    carInfo.put("color", car.getColor());
                    carInfo.put("price", car.getPrice());
                    carInfo.put("turbo", car.isTurbo());
                    carInfo.put("type", car.getType());
                    carInfo.put("motor", car.getMotor());
                    carInfo.put("cabin", car.getCabin());
                    carInfo.put("sunroof", car.isSunroof());
                    return carInfo;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filters the cars based on the filters entered by an agent
     *
     * @param maxPrice The maximum price of cars
     * @param type The type of car
     * @param color The color of cars
     * @return The list of cars filtered
     */
    public List<Car> agentFilter(Integer maxPrice, String type, String color) {

        List<Car>cars = carRepository.findAll();

        return cars.stream()
                .filter(car -> (color == null || color.equalsIgnoreCase(car.getColor()))
                        && (maxPrice == null || maxPrice >= car.getPrice())
                        && (type == null || type.equalsIgnoreCase(car.getType())))
                .collect(Collectors.toList());
    }
}
