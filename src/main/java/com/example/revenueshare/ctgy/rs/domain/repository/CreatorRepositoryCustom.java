package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Creator;
import com.example.revenueshare.ctgy.rs.model.CreatorSearchDTO;

import java.util.List;

public interface CreatorRepositoryCustom {

    List<Creator> findAllByDto(CreatorSearchDTO searchDTO);
}
