package cl.ufro.dci.tarea1.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This class represents a Car.
 */
@Data
@Entity
public class Car {
    /**
     * Car identifier
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Car brand
     */
    private String brand;

    /**
     * Car production year
     */
    private int productionYear;

    /**
     * Car color
     */
    private String color;

    /**
     * Car price
     */
    private int price;

    /**
     * Car turbo
     */
    private boolean turbo;

    /**
     * Car type
     */
    private String type;

    /**
     * Car motor
     */
    private String motor;

    /**
     * Car amount of cabins
     */
    private int cabin;

    /**
     * Car sunroof
     */
    private boolean sunroof;

    /**
     * Car popularity
     */
    private int popularity;

    public Car(String brand, int productionYear, String color, int price, boolean turbo, String type, String motor, int cabin, boolean sunroof, int popularity) {
        this.brand = brand;
        this.productionYear = productionYear;
        this.color = color;
        this.price = price;
        this.turbo = turbo;
        this.type = type;
        this.motor = motor;
        this.cabin = cabin;
        this.sunroof = sunroof;
        this.popularity = popularity;
    }

    public Car() {

    }
}
