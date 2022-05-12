package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.model.CreatorSearchDTO;

import java.util.List;

public interface CreatorRepositoryCustom {

    List<Creator> findAllByDto(CreatorSearchDTO searchDTO);
}
