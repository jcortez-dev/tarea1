package cl.ufro.dci.tarea1.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Car {
    @Id
    @GeneratedValue
    private Integer id;
    private String brand;
    private int productionYear;
    private String color;
    private int price;
    private boolean turbo;
    private String type;
    private String motor;
    private int cabin;
    private boolean sunroof;
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
