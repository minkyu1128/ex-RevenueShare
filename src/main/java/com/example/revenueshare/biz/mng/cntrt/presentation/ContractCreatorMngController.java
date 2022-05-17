package com.example.revenueshare.biz.mng.cntrt.presentation;


import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCreatorMngService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ContractCreatorMngController", description = "크리에이터 채널계약 관리")
@RestController
@RequiredArgsConstructor
public class ContractCreatorMngController {

    private final ContractCreatorMngService contractCreatorMngService;



    @Operation(summary = "크리에이터 채널계약 조회(다건)", description = "검색조건에 따른 크리에이터 채널계약 목록을 조회 한다.")
    @GetMapping(value = "/mng/cntrt/ch/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ContractCreatorSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = contractCreatorMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 채널계약 조회(단건)", description = "크리에이터 채널계약 정보를 조회 한다.")
    @GetMapping(value = "/mng/cntrt/ch/creator/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = contractCreatorMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 채널계약 등록", description = "크리에이터 채널계약 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/cntrt/ch/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ContractCreatorDTO dto) {
        try {
            ResponseVO responseVO = contractCreatorMngService.add(dto);

//            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 채널계약 수정", description = "크리에이터 채널계약 정보를 수정 한다.")
    @PutMapping(value = "/mng/cntrt/ch/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ContractCreatorDTO dto) {
        try {
            contractCreatorMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
