package tyt.record.model;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfGenerator {

    private static final Logger logger = LogManager.getLogger(PdfGenerator.class);
    private static final String FONT_PATH = "fonts/arial.ttf";

    public void generatePdf(String filePath) throws IOException {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));

            String htmlContent = generateHtmlContent();
            HtmlConverter.convertToPdf(htmlContent, writer);

            writer.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
    }


    private String generateHtmlContent() {
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
        htmlBuilder.append("<th>Başlık 1</th>");
        htmlBuilder.append("<th>Başlık 2</th>");
        htmlBuilder.append("<th>Başlık 3</th>");
        htmlBuilder.append("</tr>");

        for (int i = 0; i < 10; i++) {
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<td>Veri ").append(i + 1).append("</td>");
            htmlBuilder.append("<td>Veri ").append(i + 1).append("</td>");
            htmlBuilder.append("<td>Veri ").append(i + 1).append("</td>");
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