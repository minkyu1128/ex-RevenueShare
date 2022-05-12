package com.example.revenueshare.biz.mng.revn.presentation;


import com.example.revenueshare.biz.mng.revn.service.ChannelRsMastMngService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRsMastIds;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChannelRsMastMngController", description = "채널 수익배분 대장 관리")
@RestController
@RequiredArgsConstructor
public class ChannelRsMastMngController {

    private final ChannelRsMastMngService channelRsMastMngService;



    @Operation(summary = "채널 수익배분 대장 조회(다건)", description = "검색조건에 따른 채널 수익배분 대장 목록을 조회 한다.")
    @GetMapping(value = "/mng/revn/ch/rs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ChannelRsMastSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = channelRsMastMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 수익배분 대장 조회(단건)", description = "채널 수익배분 대장 정보를 조회 한다.")
    @GetMapping(value = "/mng/revn/ch/rs/{channelId}/{calYm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long channelId, String calYm) {
        ChannelRsMastIds id = ChannelRsMastIds.builder()
                .channel(channelId)
                .calYm(calYm)
                .build();
        try {
            ResponseVO responseVO = channelRsMastMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 수익배분 대장 등록", description = "채널 수익배분 대장 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/revn/ch/rs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ChannelRsMastDTO dto) {
        try {
            channelRsMastMngService.add(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 수익배분 대장 수정", description = "채널 수익배분 대장 정보를 수정 한다.")
    @PutMapping(value = "/mng/revn/ch/rs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ChannelRsMastDTO dto) {
        try {
            channelRsMastMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
