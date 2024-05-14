package tyt.record.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.dto.OrderProductDTO;
import tyt.record.model.dto.ProductDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * A utility class that generates a PDF document for an OrderEntity object.
 */
@Log4j2
@Component
public class PdfGenerator {

    /**
     * Generates a PDF document for the given OrderEntity and saves it to the specified file path.
     *
     * @param filePath The path where the generated PDF will be saved.
     * @param order   The OrderEntity object to be converted to a PDF document.
     * @throws IOException If an error occurs during the generation of the PDF.
     */
    public void generatePdf(String filePath, OrderDTO order) throws IOException {
        try {
            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            String htmlContent = generateHtmlContent(order, now, date);
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
     * @throws IOException If an error occurs while generating the QR code image.
     */
    private String generateHtmlContent(OrderDTO order, LocalDateTime now, Date date) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>").append("<html lang=\"tr\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<title></title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append(generateCssContent());
        htmlBuilder.append("</style>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat nowDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(order.getOrderDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");
        String formattedNow = now.format(timeFormatter);
        htmlBuilder.append("<div style=\"display: flex; justify-content: space-between;\">");
        htmlBuilder.append("<div style=\"width: 50%;\">");
        String barcodeImageUrl = generateBarcodeImage(order.getOrderNumber());
        htmlBuilder.append("<img src=\"").append(barcodeImageUrl).append("\" alt=\"Order Number Barcode\" width=\"200\" height=\"50\">");
        htmlBuilder.append("</div>");
        htmlBuilder.append("<div style=\"width: 50%; text-align: right;\">");
        htmlBuilder.append("<p style=\"font-size: 15px; margin: 0;\">").append(formattedNow).append("</p>");
        htmlBuilder.append("<p style=\"font-size: 15px; margin: 0;\">").append(nowDateFormatter.format(date)).append("</p>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("<br>");
        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Date: ").append(formattedDate).append("</p>");
        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Number: ").append(order.getOrderNumber()).append("</p>");
        // Generate and embed the barcode image for order number
        htmlBuilder.append("<table>");
        htmlBuilder.append("<tr>");
        htmlBuilder.append("<th>Product</th>");
        htmlBuilder.append("<th>Quantity</th>");
        htmlBuilder.append("<th>Total</th>");
        htmlBuilder.append("</tr>");
        List<OrderProductDTO> orderProducts = order.getOrderProducts();
        if (orderProducts != null) {
            for (OrderProductDTO orderProduct : orderProducts) {
                ProductDTO product = orderProduct.getProduct();
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td>").append(product.getName()).append("</td>");
                htmlBuilder.append("<td>").append(orderProduct.getQuantity()).append("</td>");
                htmlBuilder.append("<td>").append(product.getPrice() * orderProduct.getQuantity()).append("</td>");
                htmlBuilder.append("</tr>");
            }
        }
        htmlBuilder.append("</table>");
        htmlBuilder.append("<p style=\"font-size: 15px;\">Order Total: ").append(order.getTotal()).append("</p>");
        if (order.getOffer() != null)
            htmlBuilder.append("<p style=\"font-size: 15px;\">Applied Offer: ").append(order.getOffer()).append("</p>");
        htmlBuilder.append("</div>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");
        return htmlBuilder.toString();
    }

    /**
     * Generates a CODE_128 barcode image for the given text and saves it as a temporary file.
     *
     * @param text The text to be encoded in the barcode.
     * @return The URL of the generated barcode image.
     * @throws IOException If an error occurs while generating or saving the barcode image.
     */
    private String generateBarcodeImage(String text) throws IOException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, 160, 35);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File tempFile = File.createTempFile("barcode-", ".png");
        ImageIO.write(barcodeImage, "png", tempFile);
        return tempFile.toURI().toURL().toString();
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
                "    font-weight: bold;" +
                "}";
    }
}