package com.aston.aston;

import com.aston.Car;
import com.aston.CarRepository;
import com.aston.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//включаем поддержку Mockito в JUnit 5
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    //создаем mock-объект CarRepository
    @Mock
    private CarRepository carRepository;

    //создаем экземпляр CarService и внедряет в него мокированные зависимости
    @InjectMocks
    private CarService carService;

    private Car testCar;

    //создаем авто перед каждым методом
    @BeforeEach
    void setUp() {
        testCar = new Car("Hyundai", "Sonata", 4);
        testCar.setId(1L);
    }

    // тестовые методы
    @Test
    void save_ShouldReturnSavedCar() {
        // Arrange
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        // Act
        Car result = carService.save(testCar);

        // Assert
        assertNotNull(result);
        assertEquals(testCar.getId(), result.getId());
        verify(carRepository, times(1)).save(testCar);
    }

    @Test
    void save_InvalidDoorCount_ShouldThrowException() {
        // Arrange
        Car invalidCar = new Car("Hyundai", "Sonata", 0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> carService.save(invalidCar));
        verify(carRepository, never()).save(any());
    }

    @Test
    void findById_ExistingId_ShouldReturnCar() {
        // Arrange
        when(carRepository.findById(1L)).thenReturn(Optional.of(testCar));

        // Act
        Optional<Car> result = carService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCar.getName(), result.get().getName());
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void findById_NonExistingId_ShouldReturnEmpty() {
        // Arrange
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Car> result = carService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(carRepository, times(1)).findById(99L);
    }

    @Test
    void findAll_ShouldReturnAllCars() {
        // Arrange
        List<Car> cars = Arrays.asList(
                new Car("Toyota", "Camry", 4),
                new Car("Honda", "Accord", 4)
        );
        when(carRepository.findAll()).thenReturn(cars);

        // Act
        List<Car> result = carService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void update_ValidCar_ShouldReturnUpdatedCar() {
        // Arrange
        when(carRepository.update(testCar)).thenReturn(testCar);

        // Act
        Car result = carService.update(testCar);

        // Assert
        assertNotNull(result);
        verify(carRepository, times(1)).update(testCar);
    }

    @Test
    void update_CarWithoutId_ShouldThrowException() {
        // Arrange
        Car carWithoutId = new Car("Toyota", "Camry", 4);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> carService.update(carWithoutId));
        verify(carRepository, never()).update(any());
    }

    @Test
    void delete_ExistingId_ShouldCallRepository() {
        // Arrange
        doNothing().when(carRepository).delete(1L);

        // Act
        carService.delete(1L);

        // Assert
        verify(carRepository, times(1)).delete(1L);
    }
}
