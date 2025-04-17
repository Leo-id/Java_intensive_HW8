package com.aston;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CarRepository {
    private final SessionFactory sessionFactory;   //// SessionFactory для работы с БД

    public CarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * @param car
     * @return Car
     * метод отвечает за сохранения объекта в базу данных
     */
    public Car save(Car car) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(car);
                transaction.commit();
                return car;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("save error", e); // выбрасываем ошибку если Car не будет сохранен
            }
        }
    }

    /**
     *
     * @param id - id объекта Car
     * @return возвращает объект по номеру id
     */
    public Optional<Car> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Car car = session.get(Car.class, id);
            return Optional.ofNullable(car);
        } catch (Exception e) {
            throw new RuntimeException("search error", e); // выбрасываем ошибку если Car не будет найден
        }
    }

    /**
     *
     * @return метод должен вернуть все объекты таблицы Cars
     */
    public List<Car> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("FROM Car", Car.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось найти все", e);// выбрасываем ошибку
        }
    }

    /**
     *
     * @param car
     * метод изменяет параметры объекта
     */
    public Car update(Car car) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Car merged = (Car) session.merge(car);
                transaction.commit();
                return merged;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка во время обновления", e); // выбрасываем ошибку в случае если обновление не произошло
            }
        }
    }

    /**
     *
     * @param id - id объекта
     * метод удаляет объект по id
     */
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Car car = session.get(Car.class, id);
                if (car != null) {
                    session.remove(car);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Ошибка удаления по id: " + id, e);// выбрасываем ошибку если не удалось удалить
            }
        }
    }
}