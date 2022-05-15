package com.example.revenueshare.biz.mng.base.service;

import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.base.domain.repository.CmpnyRepository;
import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import com.example.revenueshare.biz.mng.base.model.CmpnySearchDTO;
import com.example.revenueshare.biz.mng.base.model.mapstruct.CmpnyMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CmpnyMngService extends CrudValidServiceTmplate<ResponseVO, CmpnySearchDTO, CmpnyDTO, Long> {

    private final CmpnyRepository cmpnyRepository;

    private CmpnyMapper mapper = Mappers.getMapper(CmpnyMapper.class);

    @Override
    public ResponseVO<List<CmpnyDTO>> findAllBy(CmpnySearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<Cmpny> cmpnys = cmpnyRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<CmpnyDTO> resultInfo = cmpnys.size() == 0 ?
                new ArrayList<CmpnyDTO>() :
                cmpnys.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<CmpnyDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<CmpnyDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        Cmpny cmpny = cmpnyRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        CmpnyDTO resultInfo = mapper.toDto(cmpny);

        return ResponseVO.<CmpnyDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(CmpnyDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        if (type.equals(ValidateType.C))
            cmpnyRepository.findByCmpnyNm(dto.getCmpnyNm())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, "동일한 회사명(" + data.getCmpnyNm() + ")이 등록되어 있습니다.");
                    });

    }

    @Override
    protected void addProc(CmpnyDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        Cmpny cmpny = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        cmpnyRepository.save(cmpny);
    }

    @Override
    protected void modifyProc(CmpnyDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        Cmpny cmpny = cmpnyRepository.findById(dto.getCmpnyId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, cmpny);
    }

}
