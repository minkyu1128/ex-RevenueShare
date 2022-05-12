package com.example.revenueshare.ctgy.revn.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.domain.Creator;
import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.revn.domain.ids.ChannelRevenueMastCrtIds;
import com.example.revenueshare.ctgy.revn.domain.repository.ChannelRevnMastCrtRepository;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnMastCrtSearchDTO;
import com.example.revenueshare.ctgy.revn.model.mapstruct.ChannelRevnMastCrtMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelRevnMastCrtMngService extends CrudServiceTmplate<ResponseVO, ChannelRevnMastCrtSearchDTO, ChannelRevnMastCrtDTO, ChannelRevenueMastCrtIds> {

    private final ChannelRevnMastCrtRepository contractCreatorRepository;

    private ChannelRevnMastCrtMapper mapper = Mappers.getMapper(ChannelRevnMastCrtMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnMastCrtDTO>> findAllBy(ChannelRevnMastCrtSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevnMastCrt> contractCreators = contractCreatorRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRevnMastCrtDTO> resultInfo = contractCreators.size() == 0 ?
                new ArrayList<ChannelRevnMastCrtDTO>() :
                contractCreators.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRevnMastCrtDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRevnMastCrtDTO> findById(ChannelRevenueMastCrtIds id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt contractCreator = contractCreatorRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnMastCrtDTO resultInfo = mapper.toDto(contractCreator);

        return ResponseVO.<ChannelRevnMastCrtDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelRevnMastCrtDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        contractCreatorRepository.findByCreatorAndChannel(Creator.builder().creatorId(dto.getCreatorId()).build()
                        , Channel.builder().channelId(dto.getChannelId()).build())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, data.getCreator().getCreatorNm() + "의 채널(" + data.getChannel().getChannelNm() + ") 계약정보가 등록되어 있습니다.");
                });

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt contractCreator = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        contractCreatorRepository.save(contractCreator);
    }

    @Override
    public void modify(ChannelRevnMastCrtDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt contractCreator = contractCreatorRepository.findById(dto.getCntrCrtId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, contractCreator);
    }
}
