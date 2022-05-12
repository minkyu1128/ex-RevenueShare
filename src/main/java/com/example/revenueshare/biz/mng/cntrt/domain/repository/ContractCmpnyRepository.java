package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCmpnyRepository extends JpaRepository<ContractCmpny, Long>, ContractCmpnyRepositoryCustom {

    Optional<ContractCmpny> findByCmpnyAndChannel(Cmpny cmpny, Channel channel);
}
