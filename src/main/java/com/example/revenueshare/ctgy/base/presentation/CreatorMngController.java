package com.example.revenueshare.ctgy.base.presentation;


import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.ctgy.base.model.CreatorDTO;
import com.example.revenueshare.ctgy.base.model.CreatorSearchDTO;
import com.example.revenueshare.ctgy.base.service.CreatorMngService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CreatorMngController", description = "크리에이터 관리")
@RestController
@RequiredArgsConstructor
public class CreatorMngController {

    private final CreatorMngService creatorMngService;



    @Operation(summary = "크리에이터 조회(다건)", description = "검색조건에 따른 크리에이터 목록을 조회 한다.")
    @GetMapping(value = "/base/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(CreatorSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = creatorMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 조회(단건)", description = "크리에이터 정보를 조회 한다.")
    @GetMapping(value = "/base/creator/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = creatorMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 등록", description = "크리에이터 정보를 신규 등록 한다.")
    @PostMapping(value = "/base/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody CreatorDTO dto) {
        try {
            creatorMngService.add(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 수정", description = "크리에이터 정보를 수정 한다.")
    @PutMapping(value = "/base/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody CreatorDTO dto) {
        try {
            creatorMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
