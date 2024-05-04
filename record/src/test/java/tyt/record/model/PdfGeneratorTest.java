package tyt.record.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import tyt.record.model.dto.OrderDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PdfGeneratorTest {

    @InjectMocks
    private PdfGenerator pdfGenerator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generatePdf_ValidOrder_DoesNotThrowException() {
        OrderDTO order = new OrderDTO();

        order.setOrderDate(new Date()); // set the date
        String filePath = "test.pdf";

        assertDoesNotThrow(() -> pdfGenerator.generatePdf(filePath, order));
    }
}