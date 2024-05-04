package tyt.record.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tyt.record.controller.response.RecordResponse;
import tyt.record.exception.Exceptions;
import tyt.record.service.RecordService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class RecordControllerTest {

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RecordController recordController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recordController).build();
    }

   @Test
@DisplayName("Should create record for valid order id")
void shouldCreateRecordForValidOrderId() throws Exception {
    RecordResponse recordResponse = new RecordResponse("Record created successfully");
    when(recordService.createRecordForOrder(anyLong())).thenReturn(recordResponse);

    mockMvc.perform(post("/record/create/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(new ObjectMapper().writeValueAsString(recordResponse)));
}
@Test
void createRecordForOrder_OrderDoesNotExist_ReturnsErrorMessage() throws IOException {
    Long nonExistentOrderId = 10000L;

    when(recordService.createRecordForOrder(nonExistentOrderId)).thenThrow(new Exceptions.NoSuchOrderException("Order not found with ID: " + nonExistentOrderId));

    Exception exception = assertThrows(Exceptions.NoSuchOrderException.class, () -> recordService.createRecordForOrder(nonExistentOrderId));

    String expectedMessage = "Order not found with ID: " + nonExistentOrderId;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
}
}