package com.example.revenueshare.ctgy.rs.domain.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ChannelRsMastIds implements Serializable {

    private String channel;

    private String calYm;
}
