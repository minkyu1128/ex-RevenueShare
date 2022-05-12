package com.example.revenueshare.ctgy.cntrt.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.domain.Cmpny;
import com.example.revenueshare.ctgy.cntrt.domain.ContractCmpny;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCmpnyRepository extends JpaRepository<ContractCmpny, Long>, ContractCmpnyRepositoryCustom {

    Optional<ContractCmpny> findByCmpnyAndChannel(Cmpny cmpny, Channel channel);
}
