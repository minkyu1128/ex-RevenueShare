package com.example.revenueshare.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

//    @CreationTimestamp
    private String registDt;

//    @UpdateTimestamp
    private String lastUpdtDt;
}
