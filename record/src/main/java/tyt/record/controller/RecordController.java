package tyt.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tyt.record.service.RecordService;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/create/{id}")
    public String createRecordForOrder(@PathVariable Long id) {
        return recordService.createRecordForOrder(id);
    }

}