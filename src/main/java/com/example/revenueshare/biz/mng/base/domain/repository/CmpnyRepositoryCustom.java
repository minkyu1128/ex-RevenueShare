package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.base.model.CmpnySearchDTO;

import java.util.List;

public interface CmpnyRepositoryCustom {

    List<Cmpny> findAllByDto(CmpnySearchDTO searchDTO);
}
