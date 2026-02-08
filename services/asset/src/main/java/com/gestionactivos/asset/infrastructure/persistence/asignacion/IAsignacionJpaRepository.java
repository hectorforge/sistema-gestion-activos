package com.gestionactivos.asset.infrastructure.persistence.asignacion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAsignacionJpaRepository extends JpaRepository<AsignacionEntity, UUID> {
}
