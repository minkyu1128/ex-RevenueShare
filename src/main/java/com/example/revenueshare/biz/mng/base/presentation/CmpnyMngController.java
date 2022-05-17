package com.example.revenueshare.biz.mng.base.presentation;


import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import com.example.revenueshare.biz.mng.base.model.CmpnySearchDTO;
import com.example.revenueshare.biz.mng.base.service.CmpnyMngService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CmpnyMngController", description = "회사 관리")
@RestController
@RequiredArgsConstructor
public class CmpnyMngController {

    private final CmpnyMngService cmpnyMngService;



    @Operation(summary = "회사 조회(다건)", description = "검색조건에 따른 회사 목록을 조회 한다.")
    @GetMapping(value = "/mng/base/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(CmpnySearchDTO searchDTO) {
        try {
            ResponseVO responseVO = cmpnyMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 조회(단건)", description = "회사 정보를 조회 한다.")
    @GetMapping(value = "/mng/base/cmpny/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = cmpnyMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 등록", description = "회사 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/base/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody CmpnyDTO dto) {
        try {
            ResponseVO responseVO = cmpnyMngService.add(dto);

//            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 수정", description = "회사 정보를 수정 한다.")
    @PutMapping(value = "/mng/base/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody CmpnyDTO dto) {
        try {
            cmpnyMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
