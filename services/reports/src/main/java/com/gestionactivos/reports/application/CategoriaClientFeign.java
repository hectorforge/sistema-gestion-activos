package com.gestionactivos.reports.application;

import com.gestionactivos.reports.domain.CategoriaReporteResponse;
import com.gestionactivos.reports.domain.common.OperationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "categoriaClient", url = "http://localhost:8050")
public interface CategoriaClientFeign {

    @GetMapping("/categories/report")
    OperationResult<List<CategoriaReporteResponse>> listarCategorias(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean esActivo,
            @RequestHeader("Authorization") String bearerToken
    );
}