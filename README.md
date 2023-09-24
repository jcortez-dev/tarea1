# tarea 1: Cars generator.

## Team members
- Rodrigo Brevis <r.brevis01@ufromail.cl> <br>
- Jorge Cortez <j.cortez01@ufromail.cl>

## Description
API Rest developed with Java 17 and Spring Boot. The program allows the generation of cars with random specifications, specifically: color, car type, brand, motor, turbo and sunroof.

## How to use
1. First, you need to create the cars, by indicating on the request the amount of cars you want to create:

```
GET localhost:8080/api/car/create/20
```
2. To consult a car, you need to indicate the car id on the request. By doing this, the car popularity will increase by +1:
```
GET localhost:8080/api/car/1
```
3. To filter cars, you can indicate it on the request. You can filter cars by: color, maximumprice and car type. You can use any amount of filters you want:

```
GET localhost:8080/api/car/filter?maxprice=20000000&color=orange&type=suv
```

4. To filter cars, and also view their popularity, you need to access as an agent. This is similar to the previous filter, but the route changes:

```
GET localhost:8080/api/car/agent/filter?maxprice=20000000&color=orange&type=suv
```