package com.example.revenueshare.biz.mng.chrevn.service;

import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnRepository;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnSearchDTO;
import com.example.revenueshare.biz.mng.chrevn.model.mapstruct.ChannelRevnMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudValidServiceTmplate;
import com.example.revenueshare.core.service.ValidateType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelRevnMngService extends CrudValidServiceTmplate<ResponseVO, ChannelRevnSearchDTO, ChannelRevnDTO, Long> {

    private final ChannelRevnRepository channelRevnRepository;
    private final ChannelRepository channelRepository;

    private ChannelRevnMapper mapper = Mappers.getMapper(ChannelRevnMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnDTO>> findAllBy(ChannelRevnSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevn> channelRevns = channelRevnRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRevnDTO> resultInfo = channelRevns.size() == 0 ?
                new ArrayList<ChannelRevnDTO>() :
                channelRevns.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRevnDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRevnDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnDTO resultInfo = mapper.toDto(channelRevn);

        return ResponseVO.<ChannelRevnDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ChannelRevnDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        try {
            RevnSeCd.valueOf(dto.getRevnSeCd());
        } catch (IllegalArgumentException e) {
            throw new RsException(ErrCd.ERR401, String.format("???????????? \"%s\" ?????? ???????????? ????????????. %s", dto.getRevnSeCd(), Arrays.stream(RevnSeCd.values()).map(revnSeCd -> revnSeCd.getCode()).collect(Collectors.toList()).toString()));
        }
        channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("???????????? ?????? ??????(%d) ?????????.", dto.getChannelId())));


    }

    @Override
    protected ResponseVO<Long> addProc(ChannelRevnDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        ChannelRevn channelRevn = mapper.toEntity(dto);
        channelRevn.setRsYn("N");
        channelRevn.setDelYn("N");

        /* ======================================================
         * save
         ====================================================== */
        channelRevnRepository.save(channelRevn);


        return ResponseVO.<Long>okBuilder().resultInfo(channelRevn.getChRevnId()).build();
    }

    @Override
    protected void modifyProc(ChannelRevnDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(dto.getChRevnId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRevn);
    }

    @Override
    public void removeById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));
        if ("Y".equals(channelRevn.getRsYn().toUpperCase(Locale.ROOT)))
            throw new RsException(ErrCd.ERR501, "????????? ???????????? ????????? ?????? ?????????.");

        /* ======================================================
         * save
         ====================================================== */
        channelRevn.setDelYn("Y");
        channelRevn.setDelDt(LocalDateTime.now());
    }
}
