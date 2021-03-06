package com.example.revenueshare.biz.mng.chrevn.presentation;


import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMngService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChannelRevnMngController", description = "채널수익 관리")
@RestController
@RequiredArgsConstructor
public class ChannelRevnMngController {

    private final ChannelRevnMngService channelRevnMngService;



    @Operation(summary = "채널수익 조회(다건)", description = "검색조건에 따른 채널수익 목록을 조회 한다.")
    @GetMapping(value = "/mng/ch/revn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ChannelRevnSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = channelRevnMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널수익 조회(단건)", description = "채널수익 정보를 조회 한다.")
    @GetMapping(value = "/mng/ch/revn/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = channelRevnMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널수익 등록", description = "채널수익 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/ch/revn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ChannelRevnDTO dto) {
        try {
            ResponseVO responseVO = channelRevnMngService.add(dto);

//            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널수익 수정", description = "채널수익 정보를 수정 한다.")
    @PutMapping(value = "/mng/ch/revn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ChannelRevnDTO dto) {
        try {
            channelRevnMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

}
