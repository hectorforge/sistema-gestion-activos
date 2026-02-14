package com.gestionactivos.reports.infrastructure.rest;

import com.gestionactivos.reports.application.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/activos")
    public ResponseEntity<byte[]> generarReporte(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) Boolean esActivo,
            @RequestHeader("Authorization") String token
    ) {

        byte[] excelBytes = reportService.generarReporteActivos(nombre, codigo, categoriaId, esActivo, token);

        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "reporte_activos_" + timestamp + ".xlsx";

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelBytes);
    }
}
