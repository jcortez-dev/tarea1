package cl.ufro.dci.tarea1.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Random;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue
    private Integer id;
    private String brand;
    private int productionYear;
    private String color;
    private double price;
    private boolean turbo;
    private String type;
    private double motor;
    private int cabin;
    private boolean sunroof;
    private int popularity;

    public Car(){
        this.brand = generateRandomBrand();
        this.productionYear = generateRandomYear();
        this.color = generateRandomColor();
        this.price = generateRandomPrice();
        this.turbo = generateRandomTurbo();
        this.type = generateRandomCarType();
        this.motor = generateRandomMotorType(this.type);
        this.cabin = generateRandomCabins(this.type);
        this.sunroof = generateRandomSunroof(this.type);
        this.popularity = 0;
    }

    private boolean generateRandomSunroof(String type) {
        return false;
    }

    private int generateRandomCabins(String type) {
        return 0;
    }

    private double generateRandomMotorType(String carType) {
        return 0;
    }

    private String generateRandomCarType() {
        String[] carTypes = {"SUV", "Truck", "Sedan"};

        Random random = new Random();
        int randomIndex = random.nextInt(carTypes.length);

        return carTypes[randomIndex];
    }

    private boolean generateRandomTurbo() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public double generateRandomPrice() {
        int minimumPrice = 8000000;
        int maximumPrice = 30000000;

        Random random = new Random();
        return random.nextInt(maximumPrice - minimumPrice + 1) + minimumPrice;
    }

    public String generateRandomBrand(){
        String[] carBrands = {"Toyota", "Ford", "Honda", "Chevrolet", "BMW"};

        Random random = new Random();
        int randomIndex = random.nextInt(carBrands.length);

        return carBrands[randomIndex];
    }

    public int generateRandomYear(){
        int minimumYear = 2015;
        int maximumYear = 2023;

        Random random = new Random();
        return random.nextInt(maximumYear - minimumYear + 1) + minimumYear;
    }

    public String generateRandomColor(){
        String[] carColors = {"Red", "Grey", "Blue", "Yellow", "Orange"};

        Random random = new Random();
        int randomIndex = random.nextInt(carColors.length);

        return carColors[randomIndex];
    }
}
