package tyt.sales.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tyt.sales.model.OrderEntity;

/**
 * This is a Spring Data JPA repository for OrderEntity objects.
 * It provides methods to perform CRUD operations on the 'orders' table in the database.
 *
 * Spring Data JPA repositories are interfaces with methods supporting creating, reading, updating, and deleting records in a table.
 *
 * @Repository makes this interface a Spring Bean, meaning it can be auto-detected and auto-configured by Spring Boot.
 * It also indicates that this interface is a Data Access Object (DAO).
 *
 * JpaRepository is a JPA specific extension of Repository. It has methods like save(), findOne(), findAll(), count(), delete() etc.
 * We are extending JpaRepository using OrderEntity and Long, where OrderEntity is the entity class and Long is the type of the primary key of 'orders'.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}