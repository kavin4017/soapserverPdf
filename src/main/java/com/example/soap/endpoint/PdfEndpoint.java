package com.example.soap.endpoint;

import com.example.soap.model.PdfToTextRequest;
import com.example.soap.model.PdfToTextResponse;
import com.example.soap.model.TextToPdfRequest;
import com.example.soap.model.TextToPdfResponse;
import com.example.soap.service.PdfService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PdfEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/soap";
    private final PdfService pdfService;

    public PdfEndpoint(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TextToPdfRequest")
    @ResponsePayload
    public TextToPdfResponse generatePdf(@RequestPayload TextToPdfRequest request) throws Exception {
        TextToPdfResponse response = new TextToPdfResponse();
        response.setPdfContent(pdfService.generatePdf(request.getText()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PdfToTextRequest")
    @ResponsePayload
    public PdfToTextResponse processPdf(@RequestPayload PdfToTextRequest request) throws Exception {
        PdfToTextResponse response = new PdfToTextResponse();
        response.setText(pdfService.processPdf(request.getPdfContent()));
        return response;
    }
}
