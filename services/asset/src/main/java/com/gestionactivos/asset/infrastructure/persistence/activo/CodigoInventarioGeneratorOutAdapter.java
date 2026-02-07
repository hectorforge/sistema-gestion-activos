package com.gestionactivos.asset.infrastructure.persistence.activo;

import com.gestionactivos.asset.domain.activo.ports.out.ICodigoInventarioGeneratorOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
@RequiredArgsConstructor
public class CodigoInventarioGeneratorOutAdapter implements ICodigoInventarioGeneratorOutPort {

    private final IActivoJpaRepository activoRepository;

    @Override
    public String generarCodigo(String abreviaturaCategoria) {

        if (abreviaturaCategoria == null || abreviaturaCategoria.length() != 3) {
            throw new IllegalArgumentException("La abreviatura debe tener 3 caracteres");
        }

        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        String prefijo = abreviaturaCategoria + "-" + fecha + "-";
        String ultimoCodigo = activoRepository.obtenerUltimoCodigoDelDia(prefijo);
        int siguienteNumero = 1;
        if (ultimoCodigo != null) {
            String numeroStr = ultimoCodigo.substring(ultimoCodigo.lastIndexOf("-") + 1);
            siguienteNumero = Integer.parseInt(numeroStr) + 1;
        }
        return prefijo + String.format("%05d", siguienteNumero);
    }
}