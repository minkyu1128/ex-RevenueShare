package com.example.revenueshare.ctgy.cntrt.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.domain.Creator;
import com.example.revenueshare.ctgy.cntrt.domain.ContractCreator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCreatorRepository extends JpaRepository<ContractCreator, Long>, ContractCreatorRepositoryCustom {

    Optional<ContractCreator> findByCreatorAndChannel(Creator creator, Channel channel);
}
