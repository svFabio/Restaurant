package com.restaurante.pos.service;

import com.restaurante.pos.dto.SalesReportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExcelExportService {

    public byte[] createSalesReportExcel(SalesReportDTO report) throws IOException {
        // Creamos un nuevo libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();
        // Creamos una nueva hoja llamada "Reporte de Ventas"
        Sheet sheet = workbook.createSheet("Reporte de Ventas");

        // Creamos un estilo para las celdas de cabecera (negrita)
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        int rowNum = 0;

        // --- SECCIÓN DE RESUMEN GENERAL ---
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("Resumen General");
        titleRow.getCell(0).setCellStyle(headerStyle);

        Row revenueRow = sheet.createRow(rowNum++);
        revenueRow.createCell(0).setCellValue("Ingresos Totales (Bs.)");
        revenueRow.createCell(1).setCellValue(report.getTotalRevenue().doubleValue());

        Row ordersRow = sheet.createRow(rowNum++);
        ordersRow.createCell(0).setCellValue("Pedidos Totales");
        ordersRow.createCell(1).setCellValue(report.getTotalOrders());

        rowNum++; // Dejamos una fila en blanco

        // --- SECCIÓN DE PLATOS MÁS VENDIDOS ---
        Row dishTitleRow = sheet.createRow(rowNum++);
        dishTitleRow.createCell(0).setCellValue("Platos Vendidos");
        dishTitleRow.getCell(0).setCellStyle(headerStyle);

        Row dishHeaderRow = sheet.createRow(rowNum++);
        dishHeaderRow.createCell(0).setCellValue("Plato");
        dishHeaderRow.getCell(0).setCellStyle(headerStyle);
        dishHeaderRow.createCell(1).setCellValue("Cantidad Vendida");
        dishHeaderRow.getCell(1).setCellStyle(headerStyle);

        // Llenamos la tabla con los datos de los platos
        report.getDishSales().forEach(dishSale -> {
            Row currentRow = sheet.createRow(sheet.getLastRowNum() + 1);
            currentRow.createCell(0).setCellValue(dishSale.getDishName());
            currentRow.createCell(1).setCellValue(dishSale.getQuantitySold());
        });

        // Ajustamos el tamaño de las columnas para que se lea bien
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // Convertimos el libro de trabajo a un array de bytes para poder enviarlo
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}