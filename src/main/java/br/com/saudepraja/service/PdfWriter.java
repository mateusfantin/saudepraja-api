package br.com.saudepraja.service;

import br.com.saudepraja.domain.model.util.Template;
import br.com.saudepraja.domain.repository.util.TemplateRepository;
import br.com.saudepraja.exception.SaudePrajaNotFoundException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfWriter {

    private TemplateRepository templateRepository;

    public byte[] generatePdfFromHtml(String templateId, Map<String, String> mapInfo) {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new SaudePrajaNotFoundException("Template não encontrado"));
        String html = replaceInfo(template.getTxtTemplate(), mapInfo);
        return buildPdf(html);
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
