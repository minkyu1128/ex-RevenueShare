package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ContractCreator;
import com.example.revenueshare.ctgy.rs.model.ContractCreatorSearchDTO;

import java.util.List;

public interface ContractCreatorRepositoryCustom {

    List<ContractCreator> findAllByDto(ContractCreatorSearchDTO searchDTO);
}
