package tyt.record.service;

import tyt.record.controller.response.RecordResponse;

import java.io.IOException;

public interface RecordService {

    RecordResponse createRecordForOrder(Long id) throws IOException;

}