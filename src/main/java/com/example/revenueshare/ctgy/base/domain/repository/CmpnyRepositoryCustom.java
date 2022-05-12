package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Cmpny;
import com.example.revenueshare.ctgy.base.model.CmpnySearchDTO;

import java.util.List;

public interface CmpnyRepositoryCustom {

    List<Cmpny> findAllByDto(CmpnySearchDTO searchDTO);
}
