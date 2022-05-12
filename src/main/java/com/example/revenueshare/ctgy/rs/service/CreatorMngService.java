package com.example.revenueshare.ctgy.rs.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.rs.domain.Creator;
import com.example.revenueshare.ctgy.rs.domain.repository.CreatorRepository;
import com.example.revenueshare.ctgy.rs.model.CreatorDTO;
import com.example.revenueshare.ctgy.rs.model.CreatorSearchDTO;
import com.example.revenueshare.ctgy.rs.model.mapstruct.CreatorMapper;
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
public class CreatorMngService extends CrudServiceTmplate<ResponseVO, CreatorSearchDTO, CreatorDTO, Long> {

    private final CreatorRepository creatorRepository;

    private CreatorMapper mapper = Mappers.getMapper(CreatorMapper.class);

    @Override
    public ResponseVO<List<CreatorDTO>> findAllBy(CreatorSearchDTO searchDTO) {

        List<Creator> creators = creatorRepository.findAllByDto(searchDTO);

        List<CreatorDTO> resultInfo = creators.size() == 0 ?
                new ArrayList<CreatorDTO>() :
                creators.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<CreatorDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<CreatorDTO> findById(Long id) {

        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        CreatorDTO resultInfo = mapper.toDto(creator);

        return ResponseVO.<CreatorDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(CreatorDTO creatorDTO) {
        ResponseVO validate = validate(creatorDTO);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg());

        Creator creator = mapper.toEntity(creatorDTO);

        creatorRepository.save(creator);

    }

    @Override
    public void modify(CreatorDTO creatorDTO) {

        Creator creator = creatorRepository.findById(creatorDTO.getCreatorId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        mapper.updateFromDto(creatorDTO, creator);

    }

    @Override
    public void removeById(Long id) {

        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

    }
}
