package com.example.revenueshare.ctgy.cntrt.domain.repository;

import com.example.revenueshare.ctgy.cntrt.domain.ContractCmpny;
import com.example.revenueshare.ctgy.cntrt.model.ContractCmpnySearchDTO;

import java.util.List;

public interface ContractCmpnyRepositoryCustom {

    List<ContractCmpny> findAllByDto(ContractCmpnySearchDTO searchDTO);
}
