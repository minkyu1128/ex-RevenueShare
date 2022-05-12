package com.example.revenueshare.biz.mng.revn.domain.ids;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelRsMastIds implements Serializable {

    private Long channel;

    private String calYm;
}
