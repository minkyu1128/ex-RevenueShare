package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCreatorRepository extends JpaRepository<ContractCreator, Long>, ContractCreatorRepositoryCustom {

    Optional<ContractCreator> findByCreatorAndChannel(Creator creator, Channel channel);
}
