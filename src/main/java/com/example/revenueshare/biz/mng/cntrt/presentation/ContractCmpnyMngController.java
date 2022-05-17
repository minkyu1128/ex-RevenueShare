package com.example.revenueshare.biz.mng.cntrt.presentation;


import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCmpnyMngService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ContractCmpnyMngController", description = "회사 채널계약 관리")
@RestController
@RequiredArgsConstructor
public class ContractCmpnyMngController {

    private final ContractCmpnyMngService contractCmpnyMngService;



    @Operation(summary = "회사 채널계약 조회(다건)", description = "검색조건에 따른 회사 채널계약 목록을 조회 한다.")
    @GetMapping(value = "/mng/cntrt/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ContractCmpnySearchDTO searchDTO) {
        try {
            ResponseVO responseVO = contractCmpnyMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널계약 조회(단건)", description = "회사 채널계약 정보를 조회 한다.")
    @GetMapping(value = "/mng/cntrt/ch/cmpny/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = contractCmpnyMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널계약 등록", description = "회사 채널계약 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/cntrt/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ContractCmpnyDTO dto) {
        try {
            ResponseVO responseVO = contractCmpnyMngService.add(dto);

//            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널계약 수정", description = "회사 채널계약 정보를 수정 한다.")
    @PutMapping(value = "/mng/cntrt/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ContractCmpnyDTO dto) {
        try {
            contractCmpnyMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
