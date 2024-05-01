package tyt.record.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * BaseEntity is a MappedSuperclass that other entities can extend to inherit common properties.
 * This class implements Serializable, which means it can be converted into a byte stream and
 * restored from it later.
 *
 * @author baranyalcinn
 * @version 1.0
 * @since 2024.1
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * The unique identifier for each entity that extends BaseEntity.
     * It is automatically generated.
     */
    @Id
    @GeneratedValue
    protected Long id;
}