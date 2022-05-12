package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;

import java.util.List;

public interface ContractCmpnyRepositoryCustom {

    List<ContractCmpny> findAllByDto(ContractCmpnySearchDTO searchDTO);
}
