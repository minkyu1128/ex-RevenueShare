package com.example.revenueshare.ctgy.rs.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.rs.domain.Cmpny;
import com.example.revenueshare.ctgy.rs.domain.repository.CmpnyRepository;
import com.example.revenueshare.ctgy.rs.model.CmpnyDTO;
import com.example.revenueshare.ctgy.rs.model.CmpnySearchDTO;
import com.example.revenueshare.ctgy.rs.model.mapstruct.CmpnyMapper;
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

        List<Cmpny> cmpnys = cmpnyRepository.findAllByDto(searchDTO);

        List<CmpnyDTO> resultInfo = cmpnys.size() == 0 ?
                new ArrayList<CmpnyDTO>() :
                cmpnys.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<CmpnyDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<CmpnyDTO> findById(Long id) {

        Cmpny cmpny = cmpnyRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        CmpnyDTO resultInfo = mapper.toDto(cmpny);

        return ResponseVO.<CmpnyDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(CmpnyDTO cmpnyDTO) {
        ResponseVO validate = validate(cmpnyDTO);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg());

        Cmpny cmpny = mapper.toEntity(cmpnyDTO);

        cmpnyRepository.save(cmpny);

    }

    @Override
    public void modify(CmpnyDTO cmpnyDTO) {

        Cmpny cmpny = cmpnyRepository.findById(cmpnyDTO.getCmpnyId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        mapper.updateFromDto(cmpnyDTO, cmpny);

    }

    @Override
    public void removeById(Long id) {

        Cmpny cmpny = cmpnyRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

    }
}
