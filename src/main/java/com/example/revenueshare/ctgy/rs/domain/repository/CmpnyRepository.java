package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Cmpny;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmpnyRepository extends JpaRepository<Cmpny, Long>, CmpnyRepositoryCustom {
}
