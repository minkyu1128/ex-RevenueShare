package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCmpnyRepository;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;
import com.example.revenueshare.biz.mng.cntrt.model.mapstruct.ContractCmpnyMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
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
public class ContractCmpnyMngService extends CrudServiceTmplate<ResponseVO, ContractCmpnySearchDTO, ContractCmpnyDTO, Long> {

    private final ContractCmpnyRepository contractCmpnyRepository;

    private ContractCmpnyMapper mapper = Mappers.getMapper(ContractCmpnyMapper.class);

    @Override
    public ResponseVO<List<ContractCmpnyDTO>> findAllBy(ContractCmpnySearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ContractCmpny> contractCmpnys = contractCmpnyRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ContractCmpnyDTO> resultInfo = contractCmpnys.size() == 0 ?
                new ArrayList<ContractCmpnyDTO>() :
                contractCmpnys.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ContractCmpnyDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ContractCmpnyDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ContractCmpny contractCmpny = contractCmpnyRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ContractCmpnyDTO resultInfo = mapper.toDto(contractCmpny);

        return ResponseVO.<ContractCmpnyDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ContractCmpnyDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        contractCmpnyRepository.findByCmpnyAndChannel(Cmpny.builder().cmpnyId(dto.getCmpnyId()).build()
                        , Channel.builder().channelId(dto.getChannelId()).build())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, data.getCmpny().getCmpnyNm() + "의 채널(" + data.getChannel().getChannelNm() + ") 계약정보가 등록되어 있습니다.");
                });

        /* ======================================================
         * find data
         ====================================================== */
        ContractCmpny contractCmpny = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        contractCmpnyRepository.save(contractCmpny);
    }

    @Override
    public void modify(ContractCmpnyDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ContractCmpny contractCmpny = contractCmpnyRepository.findById(dto.getCntrCmpId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, contractCmpny);
    }
}
