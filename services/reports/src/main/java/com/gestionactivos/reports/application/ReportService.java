package com.gestionactivos.reports.application;

import com.gestionactivos.reports.domain.ActivoReporteResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ActivoClientFeign activoClientFeign;
    private final CategoriaClientFeign categoriaClientFeign;

    public byte[] generarReporteActivos(
            String nombre,
            String codigo,
            String categoriaId,
            Boolean esActivo,
            String token
    ) {
        var result = activoClientFeign.obtenerActivos(
                nombre,
                codigo,
                categoriaId,
                esActivo,
                "Bearer " + token
        );
        List<ActivoReporteResponse> activos = result.getData();

        try {
            return generarExcel(activos, token);
        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte Excel", e);
        }
    }

    private byte[] generarExcel(List<ActivoReporteResponse> activos, String token) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Activos");

        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter fechaHoraFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setVerticalAlignment(VerticalAlignment.TOP);
        dataStyle.setWrapText(true);

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Activos");
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleRow.setHeightInPoints(25);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 15));

        Row header = sheet.createRow(1);
        String[] columnas = {
                "ID Activo", "Código Inventario", "Nombre", "Descripción", "URL Imagen",
                "Categoría", "Estado", "Ubicación Física", "Fecha Ingreso", "Valor Referencial",
                "Proveedor", "Vida Útil (Meses)", "Observaciones", "Es Activo",
                "Fecha Creación", "Fecha Actualización"
        };
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        var categoriasResult = categoriaClientFeign.listarCategorias(null, null, "Bearer " + token);
        Map<String, String> categoriaMap = new HashMap<>();
        if (categoriasResult != null && categoriasResult.isSuccess() && categoriasResult.getData() != null) {
            categoriasResult.getData().forEach(c -> {
                categoriaMap.put(c.getIdCategoria(), c.getNombreCategoria());
            });
        }

        int rowNum = 2;
        for (ActivoReporteResponse a : activos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getIdActivo() != null ? a.getIdActivo().toString() : "");
            row.createCell(1).setCellValue(a.getCodigoInventario());
            row.createCell(2).setCellValue(a.getNombreActivo());
            row.createCell(3).setCellValue(a.getDescripcion());
            row.createCell(4).setCellValue(a.getUrlImgActivo());

            String categoriaNombre = "";
            if (a.getCategoriaId() != null) {
                categoriaNombre = categoriaMap.getOrDefault(a.getCategoriaId().toString(), "");
            }
            row.createCell(5).setCellValue(categoriaNombre);

            row.createCell(6).setCellValue(a.getEstadoActual());
            row.createCell(7).setCellValue(a.getUbicacionFisica());
            row.createCell(8).setCellValue(a.getFechaIngreso() != null ? a.getFechaIngreso().format(fechaFormatter) : "");
            row.createCell(9).setCellValue(a.getValorReferencial() != null ? a.getValorReferencial().doubleValue() : 0);
            row.createCell(10).setCellValue(a.getProveedor());
            row.createCell(11).setCellValue(a.getVidaUtilMeses() != null ? a.getVidaUtilMeses() : 0);
            row.createCell(12).setCellValue(a.getObservaciones());
            row.createCell(13).setCellValue(a.getEsActivo() != null ? a.getEsActivo() : false);
            row.createCell(14).setCellValue(a.getFechaCreacion() != null ? a.getFechaCreacion().format(fechaHoraFormatter) : "");
            row.createCell(15).setCellValue(a.getFechaActualizacion() != null ? a.getFechaActualizacion().format(fechaHoraFormatter) : "");

            for (int i = 0; i < columnas.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        sheet.setAutoFilter(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, columnas.length - 1));
        int[] anchos = {15, 20, 25, 40, 50, 25, 15, 25, 15, 18, 25, 15, 40, 15, 20, 20};
        for (int i = 0; i < columnas.length; i++) {
            sheet.setColumnWidth(i, anchos[i] * 256);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}