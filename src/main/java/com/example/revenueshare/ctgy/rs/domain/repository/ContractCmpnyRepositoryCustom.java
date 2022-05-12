package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ContractCmpny;
import com.example.revenueshare.ctgy.rs.model.ContractCmpnySearchDTO;

import java.util.List;

public interface ContractCmpnyRepositoryCustom {

    List<ContractCmpny> findAllByDto(ContractCmpnySearchDTO searchDTO);
}
