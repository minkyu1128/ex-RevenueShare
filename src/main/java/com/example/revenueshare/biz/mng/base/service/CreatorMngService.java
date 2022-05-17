package com.example.revenueshare.biz.mng.base.service;

import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.domain.repository.CreatorRepository;
import com.example.revenueshare.biz.mng.base.model.CreatorDTO;
import com.example.revenueshare.biz.mng.base.model.CreatorSearchDTO;
import com.example.revenueshare.biz.mng.base.model.mapstruct.CreatorMapper;
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
public class CreatorMngService extends CrudValidServiceTmplate<ResponseVO, CreatorSearchDTO, CreatorDTO, Long> {

    private final CreatorRepository creatorRepository;

    private CreatorMapper mapper = Mappers.getMapper(CreatorMapper.class);

    @Override
    public ResponseVO<List<CreatorDTO>> findAllBy(CreatorSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<Creator> creators = creatorRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<CreatorDTO> resultInfo = creators.size() == 0 ?
                new ArrayList<CreatorDTO>() :
                creators.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<CreatorDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<CreatorDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        CreatorDTO resultInfo = mapper.toDto(creator);

        return ResponseVO.<CreatorDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(CreatorDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
//        if (type.equals(ValidateType.C))
        creatorRepository.findByCreatorNm(dto.getCreatorNm())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, "동일한 크리에이터(" + data.getCreatorNm() + ")이 등록되어 있습니다.");
                });

    }

    @Override
    protected ResponseVO<Long> addProc(CreatorDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        Creator creator = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        creatorRepository.save(creator);


        return ResponseVO.<Long>okBuilder().resultInfo(creator.getCreatorId()).build();
    }

    @Override
    protected void modifyProc(CreatorDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        Creator creator = creatorRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, creator);
    }

}
