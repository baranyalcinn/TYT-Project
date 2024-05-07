package tyt.record.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tyt.record.controller.response.RecordResponse;
import tyt.record.service.RecordService;

import java.io.IOException;

/**
 * This class is a controller for handling requests related to records.
 * It uses the RecordService to perform operations related to records.
 * It is annotated with @RestController, meaning it's a special @Controller that always returns @ResponseBody.
 * It is also annotated with @Log4j2 for logging purposes.
 */
@Log4j2
@RestController
@RequestMapping("/record")
@AllArgsConstructor
public class RecordController {

    // The RecordService instance used to perform operations related to records.
    private final RecordService recordService;

    /**
     * This method is a POST endpoint for creating a record for a specific order.
     * It is mapped to "/create/{id}", where {id} is the ID of the order.
     * @param id The ID of the order for which a record is to be created.
     * @return A string message indicating the result of the operation.
     */
  @PostMapping("/create/{id}")
public RecordResponse createRecordForOrder(@Valid @PathVariable Long id) throws IOException {
    log.info("Creating record for order with id: {}", id);
    return recordService.createRecordForOrder(id);
}


}