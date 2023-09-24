package cl.ufro.dci.tarea1.services;

import cl.ufro.dci.tarea1.models.Car;
import cl.ufro.dci.tarea1.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    private boolean generateRandomSunroof(String carType) {
        if(carType.equalsIgnoreCase("suv")){
            return random.nextBoolean();
        }else{
            return false;
        }
    }

    private int generateRandomCabins(String carType) {
        if(carType.equalsIgnoreCase("truck")){
            return random.nextInt(2)+1;
        }else{
            return 2;
        }
    }

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


    private String generateCarSpecification(String [] specifications) {
        int randomIndex = random.nextInt(specifications.length);

        return specifications[randomIndex];
    }

    private boolean generateRandomTurbo() {
        return random.nextBoolean();
    }

    public int generateNumericValue(int minimumValue, int maximumValue) {
        return random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
    }

    public Car contactAgency(Integer carId){
        Car contactedCar = carRepository.findById(carId).get();
        int carPopularity = contactedCar.getPopularity();
        contactedCar.setPopularity(carPopularity+1);
        carRepository.save(contactedCar);
        return contactedCar;
    }

    public List<Car> filter(Integer maxPrice, String type, String color) {

        List<Car>cars = carRepository.findAll();

        return cars.stream()
                .filter(car -> (color == null || color.equalsIgnoreCase(car.getColor()))
                        && (maxPrice == null || maxPrice >= car.getPrice())
                        && (type == null || type.equalsIgnoreCase(car.getType())))
                .collect(Collectors.toList());
    }
}
