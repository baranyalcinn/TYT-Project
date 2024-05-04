package tyt.record.service;

import tyt.record.controller.response.RecordResponse;

import java.io.IOException;

/**
 * RecordService is an interface that defines the contract for services related to records.
 * It provides methods to create a record for an order, get an order by its ID, and get a product by its ID.
 */
public interface RecordService {

    /**
     * Creates a record for an order with the given ID.
     *
     * @param id The ID of the order for which a record is to be created.
     * @return A string indicating the status of the record creation.
     */
    RecordResponse createRecordForOrder(Long id) throws IOException;


}