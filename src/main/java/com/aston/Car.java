package com.aston;

import javax.persistence.*;

/**
 * Создаем класс Car для представления в базе данных
 * который будет содержать такие параметры как название марки
 * модель и количество дверей
 */

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "door_count", nullable = false)
    private Integer doorCount;

    // создаем конструкторы
    public Car() {
    }

    public Car(String name, String model, Integer doorCount) {
        this.name = name;
        this.model = model;
        this.doorCount = doorCount;
    }

    // создаем геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(Integer doorCount) {
        this.doorCount = doorCount;
    }

    // переопределим toString для вывода информации

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", doorCount=" + doorCount +
                '}';
    }
}