package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;

import java.util.List;

public interface ContractCreatorRepositoryCustom {

    List<ContractCreator> findFetchAllByDto(ContractCreatorSearchDTO searchDTO);
}
