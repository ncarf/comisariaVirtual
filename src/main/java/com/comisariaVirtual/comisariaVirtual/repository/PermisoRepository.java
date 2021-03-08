package com.comisariaVirtual.comisariaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.comisariaVirtual.comisariaVirtual.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    List<Permiso> findAll();
}
