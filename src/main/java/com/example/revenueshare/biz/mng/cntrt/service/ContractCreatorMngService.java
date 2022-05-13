package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.base.domain.repository.CreatorRepository;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCreatorRepository;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;
import com.example.revenueshare.biz.mng.cntrt.model.mapstruct.ContractCreatorMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudValidServiceTmplate;
import com.example.revenueshare.core.service.ValidateType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractCreatorMngService extends CrudValidServiceTmplate<ResponseVO, ContractCreatorSearchDTO, ContractCreatorDTO, Long> {

    private final ContractCreatorRepository contractCreatorRepository;
    private final CreatorRepository creatorRepository;
    private final ChannelRepository channelRepository;

    private ContractCreatorMapper mapper = Mappers.getMapper(ContractCreatorMapper.class);

    @Override
    public ResponseVO<List<ContractCreatorDTO>> findAllBy(ContractCreatorSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ContractCreator> contractCreators = contractCreatorRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ContractCreatorDTO> resultInfo = contractCreators.size() == 0 ?
                new ArrayList<ContractCreatorDTO>() :
                contractCreators.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ContractCreatorDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ContractCreatorDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ContractCreator contractCreator = contractCreatorRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ContractCreatorDTO resultInfo = mapper.toDto(contractCreator);

        return ResponseVO.<ContractCreatorDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ContractCreatorDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        creatorRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("등록되지 않은 크리에이터(%d) 입니다.", dto.getCreatorId())));
        channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("등록되지 않은 채널(%d) 입니다.", dto.getChannelId())));
        if (type.equals(ValidateType.C))
            contractCreatorRepository.findByCreatorAndChannel(Creator.builder().creatorId(dto.getCreatorId()).build()
                            , Channel.builder().channelId(dto.getChannelId()).build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s의 채널(%s) 계약정보가 등록되어 있습니다.", data.getCreator().getCreatorNm(), data.getChannel().getChannelNm()));
                    });
        Optional<Integer> sumRsRate = contractCreatorRepository.findAllByChannel(Channel.builder().channelId(dto.getChannelId()).build()).stream()
                .filter(row -> !row.getCreator().getCreatorId().equals(dto.getCreatorId()))
                .map(row -> row.getRsRate())
                .reduce(Integer::sum);
        if ((sumRsRate.orElse(0) + dto.getRsRate()) > 100)
            throw new RsException(ErrCd.ERR401, String.format("채널에 대한 크리에이터 RS 요율의 합은 100%%를 초과 할 수 없습니다. [ 요율합계 %d%% ]", sumRsRate.orElse(0)));
    }

    @Override
    protected void addProc(ContractCreatorDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        ContractCreator contractCreator = mapper.toEntity(dto);
        contractCreator.setCntrCrtId(null);

        /* ======================================================
         * save
         ====================================================== */
        contractCreatorRepository.save(contractCreator);
    }

    @Override
    protected void modifyProc(ContractCreatorDTO dto) {
        /* ======================================================
         * find data
         ====================================================== */
        ContractCreator contractCreator = contractCreatorRepository.findById(dto.getCntrCrtId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, contractCreator);
    }
}
