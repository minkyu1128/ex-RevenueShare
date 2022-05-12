package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRsMastRepository extends JpaRepository<Creator, Long>, ChannelRsMastRepositoryCustom {
}
