package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Cmpny;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CmpnyRepository extends JpaRepository<Cmpny, Long>, CmpnyRepositoryCustom {


    Optional<Cmpny> findByCmpnyNm(String cmpnyNm);
}
