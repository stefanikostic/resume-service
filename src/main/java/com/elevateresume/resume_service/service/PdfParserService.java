package com.elevateresume.resume_service.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PdfParserService {

    public String fetchPdfContent(MultipartFile file) throws IOException {

        // Save the uploaded file locally
        File savedFile = saveFileLocally(file);

        // Extract text from the PDF

        // Process text using AI (call AI service)
        return extractTextFromPdf(savedFile);
    }

    private File saveFileLocally(MultipartFile file) throws IOException {
        File localFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
        file.transferTo(localFile);
        return localFile;
    }

    private String extractTextFromPdf(File pdfFile) throws IOException {
        StringBuilder fullText = new StringBuilder();
        try (PDDocument document = PDDocument.load(pdfFile)) {

            // Extract text and links from each page
            for (PDPage page : document.getPages()) {
                // Extract text using PDFTextStripper
                PDFTextStripper pdfStripper = new PDFTextStripper();
                fullText.append(pdfStripper.getText(document));

                // Extract links (annotations)
                for (PDAnnotation link : page.getAnnotations()) {
                    if (((PDAnnotationLink) link).getAction() instanceof PDActionURI) {
                        PDActionURI action = (PDActionURI) ((PDAnnotationLink) link).getAction();
                        fullText.append(action.getURI());
                    }
                }
            }

            // Close the document
            document.close();
        }
        return fullText.toString();

    }
}

