package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;
import com.example.revenueshare.biz.mng.cntrt.model.mapstruct.ContractCreatorMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCreatorRepository;
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
public class ContractCreatorMngService extends CrudServiceTmplate<ResponseVO, ContractCreatorSearchDTO, ContractCreatorDTO, Long> {

    private final ContractCreatorRepository contractCreatorRepository;

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
    public void add(ContractCreatorDTO dto) {
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
        ContractCreator contractCreator = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        contractCreatorRepository.save(contractCreator);
    }

    @Override
    public void modify(ContractCreatorDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

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
