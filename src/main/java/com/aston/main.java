package com.aston;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class main {
    public static void main(String[] args) {
        // Создание SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Car.class)
                .buildSessionFactory();

        // Создание репозитория и сервиса
        CarRepository carRepository = new CarRepository(sessionFactory);
        CarService carService = new CarService(carRepository);

        // Пример использования
        Car car1 = new Car("Toyota", "Camry", 4);
        Car car2 = new Car("Hyundai", "Sonata", 4);

        // Сохранение авто
        Car savedCar1 = carService.save(car1);
        Car savedCar2 = carService.save(car2);
        System.out.println("Автомобиль сохранен: " + savedCar1);
        System.out.println("Автомобиль сохранен: " + savedCar2);

        // Поиск по ID
        Optional<Car> foundCar = carService.findById(savedCar1.getId());
        foundCar.ifPresent(car -> System.out.println("Найден автомобиль: " + car));

        // Поиск всех авто
        List<Car> allCars = carService.findAll();
        System.out.println("Список авто: " + allCars);

        //Обновление авто
        savedCar1.setDoorCount(5);
        Car updatedCar = carService.update(savedCar1);
        System.out.println("Обновленный автомобиль: " + updatedCar);

        // Удаление авто
        carService.delete(updatedCar.getId());

        // Закрытие sessionFactory
        sessionFactory.close();

        //Финальный метод поле завершения
        System.out.println("DONE");
    }
}
