package com.example.revenueshare.ctgy.revn.domain.ids;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelRevenueMastCrtIds implements Serializable {

    private String contractCreator;

    private String calYm;
}
