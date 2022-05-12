package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Cmpny;
import com.example.revenueshare.ctgy.rs.model.CmpnySearchDTO;

import java.util.List;

public interface CmpnyRepositoryCustom {

    List<Cmpny> findAllByDto(CmpnySearchDTO searchDTO);
}
