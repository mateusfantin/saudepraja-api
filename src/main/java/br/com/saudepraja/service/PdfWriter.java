package br.com.saudepraja.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PdfWriter {

    public byte[] generatePdf(String html, Map<String, String> mapInfo) {
        String htmlReplace = replaceInfo(html, mapInfo);
        return buildPdf(htmlReplace);
    }

    private String replaceInfo(String html, Map<String, String> info) {

        for(Map.Entry<String, String> item : info.entrySet()) {
            html = html.replace("${" + item.getKey() + "}", item.getValue());
        }

        return html;
    }
    private byte[] buildPdf(String html) {

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(output);
            builder.run();

            return output.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

}
