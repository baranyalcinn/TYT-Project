package tyt.record.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
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

    private static final String TEMPLATE_PREFIX = "templates/";
    private static final String TEMPLATE_SUFFIX = ".html";
    private static final String TEMPLATE_NAME = "order";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat NOW_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH.mm");

    private final TemplateEngine templateEngine;
    private final Code128Writer barcodeWriter;

    public PdfGenerator() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(TEMPLATE_PREFIX);
        templateResolver.setSuffix(TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode("HTML");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        barcodeWriter = new Code128Writer();

    }

    /**
     * Generates a PDF document for the given OrderEntity and saves it to the specified file path.
     *
     * @param filePath The path where the generated PDF will be saved.
     * @param order   The OrderEntity object to be converted to a PDF document.
     * @throws IOException If an error occurs during the generation of the PDF.
     */
    public void generatePdf(String filePath, OrderDTO order) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath);
             PdfWriter writer = new PdfWriter(outputStream)) {

            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

            String htmlContent = generateHtmlContent(order, now, date);
            HtmlConverter.convertToPdf(htmlContent, writer);
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
        String formattedDate = DATE_FORMATTER.format(order.getOrderDate());
        String formattedNow = now.format(TIME_FORMATTER);
        String nowDate = NOW_DATE_FORMATTER.format(date);
        String barcodeImageUrl = generateBarcodeImage(order.getOrderNumber());

        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("formattedNow", formattedNow);
        context.setVariable("nowDate", nowDate);
        context.setVariable("barcodeImageUrl", barcodeImageUrl);
        context.setVariable("qrCodeImageUrl", generateQrCodeImage(order.getOrderNumber()));

        return templateEngine.process(TEMPLATE_NAME, context);
    }

    /**
     * Generates a CODE_128 barcode image for the given text and saves it as a temporary file.
     *
     * @param text The text to be encoded in the barcode.
     * @return The URL of the generated barcode image.
     * @throws IOException If an error occurs while generating or saving the barcode image.
     */
    private String generateBarcodeImage(String text) throws IOException {
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, 100, 32);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        File tempFile = File.createTempFile("barcode-", ".png");
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            ImageIO.write(barcodeImage, "png", outputStream);
        }
        return tempFile.toURI().toURL().toString();
    }

    private String generateQrCodeImage(String text) throws IOException {
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        File tempFile = File.createTempFile("qrcode-", ".png");
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            ImageIO.write(barcodeImage, "png", outputStream);
        }
        return tempFile.toURI().toURL().toString();
    }
}