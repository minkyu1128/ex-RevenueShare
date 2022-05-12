package com.example.revenueshare.ctgy.revn.domain.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ChannelRevenueMastCmpIds implements Serializable {

    private String contractCmpny;

    private String calYm;
}
