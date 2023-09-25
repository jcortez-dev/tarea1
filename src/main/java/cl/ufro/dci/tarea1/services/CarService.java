package cl.ufro.dci.tarea1.services;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
     * It fulfills the function of creating cars
     * @param carQuantity Number of cars to be created
     * @return List of cars created
     */
    public List<Car> generateCars(int carQuantity){
        List<Car> generatedCars = new ArrayList<>();

        for (int i = 0; i < carQuantity; i++) {
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
     * Method whose function is to obtain the complete list of cars
     * @return Complete list of cars
     */
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    /**
     * Determine whether or not the SUV type car has a sunroof
     * @param carType Type of car
     * @return A boolean value that determines whether the car has a sunroof
     */
    private boolean generateRandomSunroof(String carType) {
        if(carType.equalsIgnoreCase("suv")){
            return random.nextBoolean();
        }else{
            return false;
        }
    }

    /**
     * determines if the TRUCK type car has 1 or 2 cabins
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
     * Validates the car type and assigns a random value from a defined list
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
     * Randomly determines the value to return from a list
     * @param specifications List of data of type String
     * @return a random value
     */
    private String generateCarSpecification(String [] specifications) {
        int randomIndex = random.nextInt(specifications.length);

        return specifications[randomIndex];
    }

    /**
     * Randomly determines the boolean value to return
     * @return A boolean value that determines whether the car has a turbo or not
     */
    private boolean generateRandomTurbo() {
        return random.nextBoolean();
    }

    /**
     * Method that randomly determines the value of a car
     * @param minimumValue Minimum price range to assign to cars
     * @param maximumValue Maximum price range to assign to cars
     * @return A numerical value that determines the price of a car
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
     * Filter a list of cars
     * @param maxPrice Maximum price to consult
     * @param type Type of car
     * @param color Car color
     * @return A list of filtered cars without the popularity attribute of each car
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
     * Filter a list of cars
     * @param maxPrice Maximum price to consult
     * @param type Type of car
     * @param color Car color
     * @return A list of leaked cars
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
