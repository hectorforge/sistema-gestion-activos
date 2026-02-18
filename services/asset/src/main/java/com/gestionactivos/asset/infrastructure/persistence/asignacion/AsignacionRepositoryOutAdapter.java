package com.gestionactivos.asset.infrastructure.persistence.asignacion;

import com.gestionactivos.asset.domain.asignacion.Asignacion;
import com.gestionactivos.asset.domain.asignacion.ports.out.IAsignacionRepositoryOutPort;
import com.gestionactivos.asset.domain.asignacion.utils.AsignacionFiltro;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.persistence.mappers.AsignacionDomainEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AsignacionRepositoryOutAdapter implements IAsignacionRepositoryOutPort {

    private final IAsignacionJpaRepository asignacionRepository;
    private final AsignacionDomainEntityMapper mapper;

    @Override
    @Transactional
    public Asignacion guardar(Asignacion asignacion) {
        AsignacionEntity entity = mapper.toEntity(asignacion);
        AsignacionEntity saved = asignacionRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Asignacion> obtenerPorId(UUID id) {
        return asignacionRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public boolean eliminarPorId(UUID id) {
        return asignacionRepository.findById(id)
                .map(entity -> {
                    asignacionRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Asignacion> listar(AsignacionFiltro filtro, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AsignacionEntity> result = asignacionRepository.buscarConFiltros(
                filtro.idActivo(),
                filtro.idUsuario(),
                filtro.estado() != null ? filtro.estado().toString() : null,
                pageable
        );

        List<Asignacion> asignaciones = result.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                asignaciones,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast(),
                result.hasNext(),
                result.hasPrevious(),
                result.isEmpty()
        );
    }

    @Override
    @Transactional
    public boolean cambiarEstado(UUID id, String estado) {
        return asignacionRepository.findById(id)
                .map(entity -> {
                    entity.setEstadoAsignacion(
                            com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion.valueOf(estado)
                    );
                    asignacionRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }
}
