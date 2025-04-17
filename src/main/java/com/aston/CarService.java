package com.aston;

import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        // Проверка количества дверей
        if (car.getDoorCount() <= 0) {
            throw new IllegalArgumentException("Количество дверей должно быть положительным");
        }
        return carRepository.save(car);
    }

    public Optional<Car> findById(Long id) {
        // Логирование попытки поиска автомобиля
        System.out.println("Поиск автомобиля с ID: " + id);
        return carRepository.findById(id);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car update(Car car) {
        // Проверка существования автомобиля перед обновлением
        if (car.getId() == null) {
            throw new IllegalArgumentException("Автомобиль с указанным ID не существует");
        }
        return carRepository.update(car);
    }

    public void delete(Long id) {
        carRepository.delete(id);
        System.out.println("Автомобиль с ID " + id + " успешно удален");
    }
}
