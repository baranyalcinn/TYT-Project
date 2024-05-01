package tyt.record.model;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.dto.OrderProductDTO;
import tyt.record.model.dto.ProductDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * A utility class that generates a PDF document for an OrderEntity object.
 */
@Log4j2
@Component
public class PdfGenerator {

    /**
     * Current date and time.
     */
    public final LocalDateTime now = LocalDateTime.now();
    final Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());



    /**
     * Generates a PDF document for the given OrderEntity and saves it to the specified file path.
     *
     * @param filePath The path where the generated PDF will be saved.
     * @param order The OrderEntity object to be converted to a PDF document.
     * @throws IOException If an error occurs during the generation of the PDF.
     */
    public void generatePdf(String filePath, OrderDTO order) throws IOException {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));

            String htmlContent = generateHtmlContent(order);
            HtmlConverter.convertToPdf(htmlContent, writer);

            writer.close();
        } catch (FileNotFoundException e) {
            log.error("File not found", e);
        }
    }

    /**
     * Generates the HTML content for the given OrderEntity.
     *
     * @param order The OrderEntity object to be converted to HTML.
     * @return The generated HTML content.
     */
    private String generateHtmlContent(OrderDTO order) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"tr\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<title>Order Slip</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append(generateCssContent());
        htmlBuilder.append("</style>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");


        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat nowFormatter = new SimpleDateFormat("HH.mm");
        SimpleDateFormat nowDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(order.getOrderDate());
        String now = nowFormatter.format(date);

        htmlBuilder.append("<div style=\"display: flex; justify-content: space-between;\">");
        htmlBuilder.append("<div style=\"width: 50%;\">");
        htmlBuilder.append("<h2>Order Slip</h2>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("<div style=\"width: 50%; text-align: right;\">");
        htmlBuilder.append("<p style=\"font-size: 15px; margin: 0;\">").append(now).append("</p>");
        htmlBuilder.append("<p style=\"font-size: 15px; margin: 0;\">").append(nowDateFormatter.format(date)).append("</p>");
        htmlBuilder.append("</div>");

        htmlBuilder.append("</div>");

        htmlBuilder.append("<br>");

        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Date: ").append(formattedDate).append("</p>");
        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Number: ").append(order.getOrderNumber()).append("</p>");
        htmlBuilder.append("<table>");
        htmlBuilder.append("<tr>");
        htmlBuilder.append("<th>Product</th>");
        htmlBuilder.append("<th>Quantity</th>");
        htmlBuilder.append("<th>Total</th>");
        htmlBuilder.append("</tr>");


        for (OrderProductDTO orderProduct : order.getOrderProducts()) {
            ProductDTO product = orderProduct.getProduct();
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<td>").append(product.getName()).append("</td>");
            htmlBuilder.append("<td>").append(orderProduct.getQuantity()).append("</td>");
            htmlBuilder.append("<td>").append(product.getPrice() * orderProduct.getQuantity()).append("</td>");
            htmlBuilder.append("</tr>");
        }

        htmlBuilder.append("</table>");
        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Total: ").append(order.getTotal()).append("</p>");

        htmlBuilder.append("</div>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }


    /**
     * Generates the CSS content for the HTML document.
     *
     * @return The generated CSS content.
     */
    private String generateCssContent() {
        return "body, p {" +
                "    font-family: Arial, sans-serif;" +
                "    font-size: 13px;" +
                "}" +
                "div {" +
                "    overflow: hidden;" +
                "}" +
                "table {" +
                "    width: 100%;" +
                "    border-collapse: collapse;" +
                "    border: 1px solid #ddd;" +
                "}" +
                "th, td {" +
                "    padding: 10px;" +
                "    text-align: left;" +
                "    font-size: inherit;" +
                "    border-bottom: 1px solid #ddd;" +
                "}" +
                "th {" +
                "    background-color: #f2f2f2;" +
                "    font-weight: bold;" + "}";

    }


}