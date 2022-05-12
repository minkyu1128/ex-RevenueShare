package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreatorRepository extends JpaRepository<Creator, Long>, CreatorRepositoryCustom {


    Optional<Creator> findByCreatorNm(String creatorNm);
}
