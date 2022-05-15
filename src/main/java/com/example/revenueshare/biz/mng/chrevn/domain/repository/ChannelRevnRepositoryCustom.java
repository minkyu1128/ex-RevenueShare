package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnSearchDTO;

import java.util.List;

public interface ChannelRevnRepositoryCustom {

    List<ChannelRevn> findFetchAllByDto(ChannelRevnSearchDTO searchDTO);


    List<ChannelRevn> findAllByChannelIdAndRevnDeLikeAndDelYn(Long channelId, String revnDe, String delYn);
}
