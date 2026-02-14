package com.gestionactivos.asset.infrastructure.persistence.activo;

import com.gestionactivos.asset.domain.activo.Activo;
import com.gestionactivos.asset.domain.activo.ports.out.IActivoRepositoryOutPort;
import com.gestionactivos.asset.domain.activo.utils.ActivoFiltro;
import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.persistence.mappers.ActivoDomainEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ActivoRepositoryOutAdapter implements IActivoRepositoryOutPort {

    private final IActivoJpaRepository activoRepository;
    private final ActivoDomainEntityMapper mapper;

    @Override
    @Transactional
    public Activo guardar(Activo activo) {
        ActivoEntity entity = mapper.toEntity(activo);
        if (entity.getIdActivo() == null) {
            entity.setFechaCreacion(LocalDateTime.now());
        }
        entity.setFechaActualizacion(LocalDateTime.now());
        entity.setEsActivo(true);
        entity.setEstaEliminado(false);
        ActivoEntity savedEntity = activoRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Activo> obtenerPorId(UUID idActivo) {
        return activoRepository.findById(idActivo)
                .filter(activo -> !activo.getEstaEliminado())
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(UUID idActivo) {
        return activoRepository.existsById(idActivo);
    }

    @Override
    @Transactional
    public boolean cambiarEstadoNegocio(UUID idActivo, EstadoActivo nuevoEstado) {
        return activoRepository.findById(idActivo)
                .map(entity -> {
                    entity.setEstadoActual(nuevoEstado);
                    entity.setFechaActualizacion(LocalDateTime.now());
                    activoRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean activarDesactivar(UUID idActivo, Boolean esActivo) {
        return activoRepository.findById(idActivo)
                .map(entity -> {
                    entity.setEsActivo(esActivo);
                    entity.setFechaActualizacion(LocalDateTime.now());
                    activoRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean eliminarPorId(UUID idActivo) {
        return activoRepository.findById(idActivo)
                .map(entity -> {
                    entity.setEstaEliminado(true);
                    entity.setFechaActualizacion(LocalDateTime.now());
                    activoRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Activo> findAll(ActivoFiltro filtros, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivoEntity> pageResult = activoRepository.buscarConFiltros(
                filtros.nombre(),
                filtros.codigoInventario(),
                filtros.categoriaId(),
                filtros.esActivo(),
                pageable
        );

        // A dominio es mejor
        List<Activo> activos = pageResult.getContent().stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                activos,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isFirst(),
                pageResult.isLast(),
                pageResult.hasNext(),
                pageResult.hasPrevious(),
                pageResult.isEmpty()
        );
    }

    @Override
    public String obtenerUltimoCodigoDelDia(String abreviatura) {
        return activoRepository.obtenerUltimoCodigoDelDia(abreviatura);
    }

    @Override
    public List<Activo> findAllSinPaginacion(ActivoFiltro filtros) {

        List<ActivoEntity> entities =
                activoRepository.buscarConFiltrosSinPaginacion(
                        filtros.nombre(),
                        filtros.codigoInventario(),
                        filtros.categoriaId(),
                        filtros.esActivo()
                );

        return entities.stream()
                .map(mapper::toDomain)
                .toList();
    }

}