package com.example.revenueshare.ctgy.base.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.base.domain.Cmpny;
import com.example.revenueshare.ctgy.base.domain.repository.CmpnyRepository;
import com.example.revenueshare.ctgy.base.model.CmpnyDTO;
import com.example.revenueshare.ctgy.base.model.CmpnySearchDTO;
import com.example.revenueshare.ctgy.base.model.mapstruct.CmpnyMapper;
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
public class CmpnyMngService extends CrudServiceTmplate<ResponseVO, CmpnySearchDTO, CmpnyDTO, Long> {

    private final CmpnyRepository cmpnyRepository;

    private CmpnyMapper mapper = Mappers.getMapper(CmpnyMapper.class);

    @Override
    public ResponseVO<List<CmpnyDTO>> findAllBy(CmpnySearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<Cmpny> cmpnys = cmpnyRepository.findAllByDto(searchDTO);

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
    public void add(CmpnyDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        cmpnyRepository.findByCmpnyNm(dto.getCmpnyNm())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, "동일한 회사명("+data.getCmpnyNm()+")이 등록되어 있습니다.");
                });

        /* ======================================================
         * find data
         ====================================================== */
        Cmpny cmpny = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        cmpnyRepository.save(cmpny);
    }

    @Override
    public void modify(CmpnyDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

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
