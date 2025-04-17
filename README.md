Домашнее задание 8 (Spring Boot + Hibernate)

Описание проекта Этот проект создан на Maven с использованием Spring Boot. В нем реализовано хранилище данных с поддержкой CRUD-операций с использованием Hibernate, встроенной базы данных H2, а также инструментов для unit и интеграционного тестирования — JUnit 5 и Mockito.

Требования Java 17 или новее, !!! не страше Java 21, так как Mockito еще не поддерживает Java 24. Maven 3.8.1 или новее

Проект использует in-memory базу данных H2 со следующими параметрами: URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1 Пользователь: sa Пароль:

Использование в своем проекте:
1.Клонировать репозиторий
2.Перейти в директорию проекта
3.Собрать проект: mvn clean install
4.Проверить правильность дериктории расположения hibernate.cfg.xml

Зависимости
Основные зависимости проекта:
Hibernate Core 5.6+
JUnit 5 для тестирования
Mockito для мокирования
H2 Database для интеграционных тестов
Полный список зависимостей можно посмотреть в файле pom.xml

Тестирование Проект содержит два типа тестов:
Unit тесты (CarServiceTest): C использованием Mockito Так же использованы аннотации @Mock и @InjectMocks, конструкции when(), thenReturn(), verify() необходимые для проверки корректности поведения.
Интеграционные тесты (CarRepositoryIT): Тестируют взаимодействие с реальной базой данных Используют in-memory H2 Перед каждым тестом создается чистая база данных

Чтобы использовать PostgreSQL вместо H2:
1.Добавить зависимость в pom.xml:
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.3.6</version>
</dependency>
2.Изменить настройки в hibernate.cfg.xml:
<property name="connection.driver_class">org.postgresql.Driver</property>
<property name="connection.url"></property>
<property name="connection.username"></property>
<property name="connection.password"></property>
<property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
