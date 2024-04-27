package tyt.record.model;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tyt.record.database.OrderRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Log4j2
@Component
public class PdfGenerator {

    private final OrderRepository orderRepository;
    private static final String FONT_PATH = "fonts/arial.ttf";

    public void generatePdf(String filePath, OrderEntity order) throws IOException {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));

            String htmlContent = generateHtmlContent(order);
            HtmlConverter.convertToPdf(htmlContent, writer);

            writer.close();
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
        }
    }

    @Autowired
    public PdfGenerator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    private String generateHtmlContent(OrderEntity order) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"tr\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<title>Tablo PDF'i</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append(generateCssContent());
        htmlBuilder.append("</style>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<table>");
        htmlBuilder.append("<tr>");
        htmlBuilder.append("<th>ProductName</th>");
        htmlBuilder.append("<th>Quantity</th>");
        htmlBuilder.append("<th>Total</th>");
        htmlBuilder.append("</tr>");

        OrderEntity orderEntity = orderRepository.findById(order.getId()).orElse(null);

        for (OrderProductEntity orderProduct : order.getOrderProducts()) {
            ProductEntity product = orderProduct.getProduct();
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<td>").append(product.getName()).append("</td>");
            htmlBuilder.append("<td>").append(orderProduct.getQuantity()).append("</td>");
            htmlBuilder.append("<td>").append(product.getPrice() * orderProduct.getQuantity()).append("</td>");
            htmlBuilder.append("</tr>");
        }

        htmlBuilder.append("</table>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    private String generateCssContent() {

        return "body {" +
                "font-family: Arial, sans-serif;" +
                "}" +
                "table {" +
                "width: 100%;" +
                "border-collapse: collapse;" +
                "}" +
                "th, td {" +
                "border: 1px solid #ccc;" +
                "padding: 5px;" +
                "text-align: center;" +
                "}";
    }
}