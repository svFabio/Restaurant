package com.restaurante.pos.controller;

import com.restaurante.pos.dto.ReportRequestDTO;
import com.restaurante.pos.dto.SalesReportDTO;
import com.restaurante.pos.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.pos.service.ExcelExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExcelExportService excelExportService; // Inyectamos el nuevo servicio

    @PostMapping("/sales")
    public ResponseEntity<SalesReportDTO> getSalesReport(@RequestBody ReportRequestDTO reportRequest) {
        SalesReportDTO report = reportService.generateSalesReport(
                reportRequest.getStartDate(),
                reportRequest.getEndDate()
        );
        return ResponseEntity.ok(report);
    }


    @PostMapping("/sales/export")
    public ResponseEntity<byte[]> exportSalesReport(@RequestBody ReportRequestDTO reportRequest) throws IOException {
        // 1. Generamos los datos del reporte como antes
        SalesReportDTO report = reportService.generateSalesReport(
                reportRequest.getStartDate(),
                reportRequest.getEndDate()
        );

        // 2. Usamos el nuevo servicio para crear el archivo Excel en memoria
        byte[] excelFile = excelExportService.createSalesReportExcel(report);

        // 3. Preparamos la respuesta para que el navegador/app sepa que es un archivo para descargar
        String filename = "Reporte_Ventas_" + reportRequest.getStartDate() + "_a_" + reportRequest.getEndDate() + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFile);
    }
}