package com.example.revenueshare.ctgy.cntrt.domain.repository;

import com.example.revenueshare.ctgy.cntrt.domain.ContractCreator;
import com.example.revenueshare.ctgy.cntrt.model.ContractCreatorSearchDTO;

import java.util.List;

public interface ContractCreatorRepositoryCustom {

    List<ContractCreator> findAllByDto(ContractCreatorSearchDTO searchDTO);
}
