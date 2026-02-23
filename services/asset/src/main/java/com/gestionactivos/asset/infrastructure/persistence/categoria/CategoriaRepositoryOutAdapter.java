package com.gestionactivos.asset.infrastructure.persistence.categoria;

import com.gestionactivos.asset.domain.categoria.Categoria;
import com.gestionactivos.asset.domain.categoria.ports.out.ICategoriaRepositoryOutPort;
import com.gestionactivos.asset.domain.categoria.utils.CategoriaFiltro;
import com.gestionactivos.asset.domain.common.PagedResult;
import com.gestionactivos.asset.infrastructure.persistence.mappers.CategoriaDomainEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoriaRepositoryOutAdapter implements ICategoriaRepositoryOutPort {

    private final ICategoriaJpaRepository categoriaJpaRepository;
    private final CategoriaDomainEntityMapper mapper;

    @Override
    @Transactional
    public Categoria guardar(Categoria categoria) {
        CategoriaEntity entity = mapper.toEntity(categoria);

        if (entity.getIdCategory() == null) {
            entity.setEsActivo(true);
            entity.setEstaEliminado(false);
        }

        CategoriaEntity saved = categoriaJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public boolean eliminarPorId(UUID idCategoria) {
        return categoriaJpaRepository.findById(idCategoria)
                .map(entity -> {
                    entity.setEstaEliminado(true);
                    categoriaJpaRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> obtenerPorId(UUID idCategoria) {
        return Optional.ofNullable(categoriaJpaRepository.findById(idCategoria)
                .filter(cat -> !cat.getEstaEliminado())
                .map(mapper::toDomain)
                .orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Categoria> listar(CategoriaFiltro filtros, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoriaEntity> pageResult = categoriaJpaRepository.buscarConFiltros(
                filtros.nombre(),
                filtros.esActivo(),
                pageable
        );

        List<Categoria> categorias = pageResult.getContent().stream()
                .map(mapper::toDomain).toList();

        return new PagedResult<Categoria>(
                categorias,
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
    public List<Categoria> findAllSinPaginacion(CategoriaFiltro filtros) {
        return categoriaJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}