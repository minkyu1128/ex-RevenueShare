package com.example.revenueshare.biz.mng.cntrt.service;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.base.domain.repository.CmpnyRepository;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCmpnyRepository;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;
import com.example.revenueshare.biz.mng.cntrt.model.mapstruct.ContractCmpnyMapper;
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
public class ContractCmpnyMngService extends CrudValidServiceTmplate<ResponseVO, ContractCmpnySearchDTO, ContractCmpnyDTO, Long> {

    private final ContractCmpnyRepository contractCmpnyRepository;
    private final CmpnyRepository cmpnyRepository;
    private final ChannelRepository channelRepository;

    private ContractCmpnyMapper mapper = Mappers.getMapper(ContractCmpnyMapper.class);

    @Override
    public ResponseVO<List<ContractCmpnyDTO>> findAllBy(ContractCmpnySearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ContractCmpny> contractCmpnys = contractCmpnyRepository.findFetchAllByDto(searchDTO);

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
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * mapping
         ====================================================== */
        ContractCmpnyDTO resultInfo = mapper.toDto(contractCmpny);

        return ResponseVO.<ContractCmpnyDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ContractCmpnyDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        cmpnyRepository.findById(dto.getCmpnyId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("???????????? ?????? ??????(%d) ?????????.", dto.getCmpnyId())));
        channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("???????????? ?????? ??????(%d) ?????????.", dto.getChannelId())));
        if (type.equals(ValidateType.C))
            contractCmpnyRepository.findByCmpnyAndChannel(Cmpny.builder().cmpnyId(dto.getCmpnyId()).build()
                            , Channel.builder().channelId(dto.getChannelId()).build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s??? ??????(%s) ??????????????? ???????????? ????????????.", data.getCmpny().getCmpnyNm(), data.getChannel().getChannelNm()));
                    });
        Optional<Integer> sumRsRate = contractCmpnyRepository.findAllByChannel(Channel.builder().channelId(dto.getChannelId()).build()).stream()
                .filter(row -> !row.getCmpny().getCmpnyId().equals(dto.getCmpnyId()))
                .map(row -> row.getRsRate())
                .reduce(Integer::sum);
        if ((sumRsRate.orElse(0) + dto.getRsRate()) > 100)
            throw new RsException(ErrCd.ERR401, String.format("????????? ?????? ?????? RS ????????? ?????? 100%%??? ?????? ??? ??? ????????????. [ ???????????? %d%% ]", sumRsRate.orElse(0)));
    }

    @Override
    protected ResponseVO<Long> addProc(ContractCmpnyDTO dto) {
        /* ======================================================
         * conversion
         ====================================================== */
        ContractCmpny contractCmpny = mapper.toEntity(dto);
        contractCmpny.setCntrCmpId(null);

        /* ======================================================
         * save
         ====================================================== */
        contractCmpnyRepository.save(contractCmpny);


        return ResponseVO.<Long>okBuilder().resultInfo(contractCmpny.getCntrCmpId()).build();
    }

    @Override
    protected void modifyProc(ContractCmpnyDTO dto) {
        /* ======================================================
         * find data
         ====================================================== */
        ContractCmpny contractCmpny = contractCmpnyRepository.findById(dto.getCntrCmpId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, contractCmpny);
    }
}
