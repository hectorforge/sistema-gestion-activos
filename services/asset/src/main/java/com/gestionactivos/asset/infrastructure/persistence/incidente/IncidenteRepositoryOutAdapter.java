package com.gestionactivos.asset.infrastructure.persistence.incidente;

import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.domain.incidente.Incidente;
import com.gestionactivos.asset.domain.incidente.ports.out.IIncidenteRepositoryOutPort;
import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.IncidenteFiltro;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;
import com.gestionactivos.asset.infrastructure.persistence.mappers.IncidenteDomainEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class IncidenteRepositoryOutAdapter implements IIncidenteRepositoryOutPort {

    private final IIncidenteJpaRepository incidenteJpaRepository;
    private final IncidenteDomainEntityMapper mapper;

    @Override
    @Transactional
    public Incidente guardar(Incidente incidente) {
        IncidenteEntity entity = mapper.toEntity(incidente);

        if (entity.getIdIncidente() == null) {
            entity.setIdIncidente(UUID.randomUUID());
            entity.setFechaReporte(LocalDateTime.now());
        }

        IncidenteEntity saved = incidenteJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Incidente> obtenerPorId(UUID id) {
        return incidenteJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public boolean eliminarPorId(UUID id) {
        return incidenteJpaRepository.findById(id)
                .map(entity -> {
                    incidenteJpaRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Incidente> listar(IncidenteFiltro filtro, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<IncidenteEntity> pageResult =
                incidenteJpaRepository.buscarConFiltros(
                        filtro.tipo() != null ? filtro.tipo().toString() : null,
                        filtro.estado() != null ? filtro.estado().toString() : null,
                        pageable
                );

        List<Incidente> data = pageResult.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                data,
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
    @Transactional
    public boolean cambiarEstado(UUID id, EstadoIncidente nuevoEstado) {
        return incidenteJpaRepository.findById(id)
                .map(entity -> {
                    entity.setEstadoIncidente(nuevoEstado);
                    incidenteJpaRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean cambiarTipo(UUID id, TipoIncidente nuevoTipo) {
        return incidenteJpaRepository.findById(id)
                .map(entity -> {
                    entity.setTipoIncidente(nuevoTipo);
                    incidenteJpaRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }
}