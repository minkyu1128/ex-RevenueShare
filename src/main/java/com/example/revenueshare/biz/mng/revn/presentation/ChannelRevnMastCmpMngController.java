package com.example.revenueshare.biz.mng.revn.presentation;


import com.example.revenueshare.biz.mng.revn.service.ChannelRevnMastCmpMngService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRevenueMastCmpIds;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChannelRevnMastCmpMngController", description = "회사 채널수익대장 관리")
@RestController
@RequiredArgsConstructor
public class ChannelRevnMastCmpMngController {

    private final ChannelRevnMastCmpMngService contractCmpnyMngService;



    @Operation(summary = "회사 채널수익대장 조회(다건)", description = "검색조건에 따른 회사 채널수익대장 목록을 조회 한다.")
    @GetMapping(value = "/mng/revn/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ChannelRevnMastCmpSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = contractCmpnyMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널수익대장 조회(단건)", description = "회사 채널수익대장 정보를 조회 한다.")
    @GetMapping(value = "/mng/revn/ch/cmpny/{cntrCmpId}/{calYm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long cntrCmpId, @PathVariable String calYm) {
        ChannelRevenueMastCmpIds id = ChannelRevenueMastCmpIds.builder()
                .contractCmpny(cntrCmpId)
                .calYm(calYm)
                .build();
        try {
            ResponseVO responseVO = contractCmpnyMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널수익대장 등록", description = "회사 채널수익대장 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/revn/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ChannelRevnMastCmpDTO dto) {
        try {
            contractCmpnyMngService.add(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 채널수익대장 수정", description = "회사 채널수익대장 정보를 수정 한다.")
    @PutMapping(value = "/mng/revn/ch/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ChannelRevnMastCmpDTO dto) {
        try {
            contractCmpnyMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}