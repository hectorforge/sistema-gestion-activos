package com.gestionactivos.reports.application;

import com.gestionactivos.reports.domain.ActivoReporteResponse;
import com.gestionactivos.reports.domain.common.OperationResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "activoClient", url = "http://localhost:8050")
public interface ActivoClientFeign {

    @GetMapping("/assets/report")
    OperationResult<List<ActivoReporteResponse>> obtenerActivos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) Boolean esActivo,
            @RequestHeader("Authorization") String bearerToken
    );
}
