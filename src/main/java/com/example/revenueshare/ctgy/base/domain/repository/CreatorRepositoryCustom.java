package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Creator;
import com.example.revenueshare.ctgy.base.model.CreatorSearchDTO;

import java.util.List;

public interface CreatorRepositoryCustom {

    List<Creator> findAllByDto(CreatorSearchDTO searchDTO);
}
