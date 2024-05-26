package tyt.record.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import tyt.record.model.dto.OrderDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * A utility class that generates a PDF document for an OrderEntity object using Thymeleaf.
 */
@Log4j2
@Component
@Async
public class PdfGenerator {

    private static final String TEMPLATE_PREFIX = "templates/";
    private static final String TEMPLATE_SUFFIX = ".html";
    private static final String TEMPLATE_NAME = "order";


    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH.mm");

    private final TemplateEngine templateEngine;
    private final Code128Writer barcodeWriter;
    private final QRCodeWriter qrCodeWriter;

    public PdfGenerator() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(TEMPLATE_PREFIX);
        templateResolver.setSuffix(TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode("HTML");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        barcodeWriter = new Code128Writer();
        qrCodeWriter = new QRCodeWriter();

    }

    /**
     * Generates a PDF document for the given OrderEntity and saves it to the specified file path.
     *
     * @param filePath The path where the generated PDF will be saved.
     * @param order   The OrderEntity object to be converted to a PDF document.
     * @throws IOException If an error occurs during the generation of the PDF.
     */
    public void generatePdf(String filePath, OrderDTO order) throws IOException {

        if (order == null) {
            throw new NullPointerException("Order cannot be null");
        }

        Path pdfFilePath = Path.of(filePath);

        try (OutputStream outputStream = Files.newOutputStream(pdfFilePath);
             PdfWriter writer = new PdfWriter(outputStream)) {

            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

            String htmlContent = generateHtmlContent(order, now, date);
            HtmlConverter.convertToPdf(htmlContent, writer);
        } catch (WriterException e) {
            log.error("Error while generating PDF", e);
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Generates the HTML content for the given OrderEntity using Thymeleaf.
     *
     * @param order The OrderEntity object to be converted to HTML.
     * @return The generated HTML content.
     * @throws IOException If an error occurs while generating the QR code image.
     */
    private String generateHtmlContent(OrderDTO order, LocalDateTime now, Date date) throws IOException, WriterException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
         SimpleDateFormat nowDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        String formattedDate = dateFormatter.format(order.getOrderDate());
        String formattedNow = now.format(TIME_FORMATTER);
        String nowDate = nowDateFormatter.format(date);
        String barcodeImageUrl = generateBarcodeImage(order.getOrderNumber());


        String qrCodeContent = "https://www.linkedin.com/in/baran-yalçın-521691242/";
        String qrCodeImageUrl = generateQrCodeImage(qrCodeContent);

        Context context = new Context();
        context.setVariable(TEMPLATE_NAME, order);
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("formattedNow", formattedNow);
        context.setVariable("nowDate", nowDate);
        context.setVariable("barcodeImageUrl", barcodeImageUrl);
        context.setVariable("qrCodeImageUrl", qrCodeImageUrl);


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

        return generateDataUrl(barcodeImage);
    }

    private String generateQrCodeImage(String text) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 2);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200, hints);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        return generateDataUrl(qrCodeImage);
    }

    private String generateDataUrl(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return "data:image/" + "png" + ";base64," + java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }

}