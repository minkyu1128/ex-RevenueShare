package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRsMastRepository extends JpaRepository<Creator, Long>, ChannelRsMastRepositoryCustom {
}
