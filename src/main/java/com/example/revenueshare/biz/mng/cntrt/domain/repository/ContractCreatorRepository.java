package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractCreatorRepository extends JpaRepository<ContractCreator, Long>, ContractCreatorRepositoryCustom {

    Optional<ContractCreator> findByCreatorAndChannel(Creator creator, Channel channel);

    List<ContractCreator> findAllByChannel(Channel channel);
}
