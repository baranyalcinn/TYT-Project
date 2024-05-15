package tyt.record.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import tyt.record.model.dto.OrderDTO;

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

/**
 * A utility class that generates a PDF document for an OrderEntity object using Thymeleaf.
 */
@Log4j2
@Component
public class PdfGenerator {

    private final TemplateEngine templateEngine;

    public PdfGenerator() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

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
     * Generates the HTML content for the given OrderEntity using Thymeleaf.
     *
     * @param order The OrderEntity object to be converted to HTML.
     * @return The generated HTML content.
     * @throws IOException If an error occurs while generating the QR code image.
     */
    private String generateHtmlContent(OrderDTO order, LocalDateTime now, Date date) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat nowDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(order.getOrderDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");
        String formattedNow = now.format(timeFormatter);
        String barcodeImageUrl = generateBarcodeImage(order.getOrderNumber());

        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("formattedNow", formattedNow);
        context.setVariable("nowDate", nowDateFormatter.format(date));
        context.setVariable("barcodeImageUrl", barcodeImageUrl);
        return templateEngine.process("order", context);
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
            BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, 100, 32);
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            File tempFile = File.createTempFile("barcode-", ".png");
            ImageIO.write(barcodeImage, "png", tempFile);
            return tempFile.toURI().toURL().toString();
        }

    private String generateQRImage(String text) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File tempFile = File.createTempFile("qr-", ".png");
        ImageIO.write(qrImage, "png", tempFile);
        return tempFile.toURI().toURL().toString();
    }
}