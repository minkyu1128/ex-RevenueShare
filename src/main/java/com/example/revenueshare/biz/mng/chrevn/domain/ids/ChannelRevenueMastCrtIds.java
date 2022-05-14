package com.example.revenueshare.biz.mng.chrevn.domain.ids;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelRevenueMastCrtIds implements Serializable {

    private Long contractCreator;

    private String calYm;
}
