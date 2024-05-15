package tyt.record.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tyt.record.model.dto.OrderDTO;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class PdfGeneratorTest {

    @Test
    public void generatePdf_InvalidFilePath_ThrowsException() {
        PdfGenerator pdfGenerator = new PdfGenerator();
        OrderDTO order = new OrderDTO();

        String invalidFilePath = "/path/does/not/exist.pdf";

        assertThrows(IOException.class, () -> pdfGenerator.generatePdf(invalidFilePath, order));
    }

    @Test
    public void generatePdf_NullOrder_ThrowsException() {
        PdfGenerator pdfGenerator = new PdfGenerator();
        assertThrows(NullPointerException.class, () -> pdfGenerator.generatePdf("validFilePath", null));
    }

    @Mock
    private PdfGenerator pdfGenerator;


    @Test
    public void generatePdf_IOExceptionThrown_LogsError() throws IOException {
        OrderDTO order = new OrderDTO();
        order.setOrderDate(new Date());

        String filePath = "test.pdf";

        Mockito.doThrow(IOException.class).when(pdfGenerator).generatePdf(anyString(), any(OrderDTO.class));

        assertThrows(IOException.class, () -> pdfGenerator.generatePdf(filePath, order));

        Mockito.verify(pdfGenerator).generatePdf(anyString(), any(OrderDTO.class));
    }
}