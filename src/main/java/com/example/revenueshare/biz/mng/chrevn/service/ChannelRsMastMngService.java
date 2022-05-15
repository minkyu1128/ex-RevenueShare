package com.example.revenueshare.biz.mng.chrevn.service;

import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRsMastIds;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRsMastRepository;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastSearchDTO;
import com.example.revenueshare.biz.mng.chrevn.model.mapstruct.ChannelRsMastMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelRsMastMngService extends CrudValidServiceTmplate<ResponseVO, ChannelRsMastSearchDTO, ChannelRsMastDTO, ChannelRsMastIds> {

    private final ChannelRsMastRepository channelRsMastRepository;
    private final ChannelRepository channelRepository;

    private ChannelRsMastMapper mapper = Mappers.getMapper(ChannelRsMastMapper.class);

    @Override
    public ResponseVO<List<ChannelRsMastDTO>> findAllBy(ChannelRsMastSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRsMast> channelRsMasts = channelRsMastRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRsMastDTO> resultInfo = channelRsMasts.size() == 0 ?
                new ArrayList<ChannelRsMastDTO>() :
                channelRsMasts.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRsMastDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRsMastDTO> findById(ChannelRsMastIds id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRsMast channelRsMast = channelRsMastRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRsMastDTO resultInfo = mapper.toDto(channelRsMast);

        return ResponseVO.<ChannelRsMastDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ChannelRsMastDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("등록되지 않은 채널(%d) 입니다.", dto.getChannelId())));
        if(Integer.parseInt(dto.getCalYm()) > Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"))))
            throw new RsException(ErrCd.ERR401, String.format("\"%s년%s월\" 채널 수익대장을 미리 등록 할 수 없습니다.", dto.getCalYm().substring(0, 4), dto.getCalYm().substring(4)));
        if (type.equals(ValidateType.C))
            channelRsMastRepository.findById(ChannelRsMastIds.builder()
                            .channel(dto.getChannelId())
                            .calYm(dto.getCalYm())
                            .build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s 채널의 %s 수익대장이 등록되어 있습니다.", data.getChannel().getChannelNm(), data.getCalYm()));
                    });

    }

    @Override
    protected void addProc(ChannelRsMastDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        ChannelRsMast channelRsMast = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRsMast.setChannelAmt(0L);
        channelRsMast.setBalanceAmt(0L);
        channelRsMastRepository.save(channelRsMast);
    }

    @Override
    protected void modifyProc(ChannelRsMastDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRsMast channelRsMast = channelRsMastRepository.findById(ChannelRsMastIds.builder()
                        .channel(dto.getChannelId())
                        .calYm(dto.getCalYm())
                        .build())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRsMast);
    }
}
