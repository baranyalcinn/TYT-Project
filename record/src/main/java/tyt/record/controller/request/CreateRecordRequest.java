package tyt.record.controller.request;

import lombok.Value;

/**
 * This class represents a request to create a record.
 * It is annotated with @Value from the Lombok library, which means it is an immutable class.
 * Lombok will automatically generate a constructor, getters, equals, hashCode and toString methods.
 * <p>
 * The class has one field, orderId, which is of type Long.
 */
@Value
public class CreateRecordRequest {

    /**
     * The ID of the order for which a record is to be created.
     */
    Long orderId;
}