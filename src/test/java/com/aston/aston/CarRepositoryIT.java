package com.aston.aston;

import com.aston.Car;
import com.aston.CarRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryIT {

    private static SessionFactory sessionFactory;
    private static CarRepository carRepository;

    @BeforeAll
    static void setUp() {
        // Инициализация SessionFactory с H2 in-memory
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Car.class)
                .buildSessionFactory();

        carRepository = new CarRepository(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        // Очистка базы данных после каждого теста
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    void save_ShouldPersistCarWithGeneratedId() {
        // Arrange
        Car car = new Car("Toyota", "Camry", 4);

        // Act
        Car savedCar = carRepository.save(car);

        // Assert
        assertNotNull(savedCar.getId());
        assertEquals("Toyota", savedCar.getName());
    }

    @Test
    void findById_AfterSave_ShouldReturnCar() {
        // Arrange
        Car car = new Car("Honda", "Accord", 4);
        Car savedCar = carRepository.save(car);

        // Act
        Optional<Car> foundCar = carRepository.findById(savedCar.getId());

        // Assert
        assertTrue(foundCar.isPresent());
        assertEquals(savedCar.getId(), foundCar.get().getId());
        assertEquals("Honda", foundCar.get().getName());
    }

    @Test
    void findById_NonExistingId_ShouldReturnEmpty() {
        // Act
        Optional<Car> foundCar = carRepository.findById(999L);

        // Assert
        assertFalse(foundCar.isPresent());
    }

    @Test
    void findAll_AfterSavingMultipleCars_ShouldReturnAll() {
        // Arrange
        carRepository.save(new Car("Toyota", "Camry", 4));
        carRepository.save(new Car("Honda", "Accord", 4));
        carRepository.save(new Car("BMW", "X5", 5));

        // Act
        List<Car> cars = carRepository.findAll();

        // Assert
        assertEquals(3, cars.size());
    }

    @Test
    void update_ShouldChangeCarProperties() {
        // Arrange
        Car car = carRepository.save(new Car("Toyota", "Camry", 4));
        car.setName("Toyota Updated");
        car.setModel("Camry New");
        car.setDoorCount(5);

        // Act
        Car updatedCar = carRepository.update(car);

        // Assert
        Optional<Car> foundCar = carRepository.findById(car.getId());
        assertTrue(foundCar.isPresent());
        assertEquals("Toyota Updated", foundCar.get().getName());
        assertEquals("Camry New", foundCar.get().getModel());
        assertEquals(5, foundCar.get().getDoorCount());
    }

    @Test
    void delete_ShouldRemoveCarFromDatabase() {
        // Arrange
        Car car = carRepository.save(new Car("Toyota", "Camry", 4));

        // Act
        carRepository.delete(car.getId());

        // Assert
        Optional<Car> foundCar = carRepository.findById(car.getId());
        assertFalse(foundCar.isPresent());
    }
}
