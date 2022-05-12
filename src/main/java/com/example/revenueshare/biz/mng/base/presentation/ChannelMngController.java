package com.example.revenueshare.biz.mng.base.presentation;


import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.biz.mng.base.model.ChannelDTO;
import com.example.revenueshare.biz.mng.base.model.ChannelSearchDTO;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChannelMngController", description = "채널 관리")
@RestController
@RequiredArgsConstructor
public class ChannelMngController {

    private final ChannelMngService channelMngService;



    @Operation(summary = "채널 조회(다건)", description = "검색조건에 따른 채널 목록을 조회 한다.")
    @GetMapping(value = "/mng/base/channel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllBy(ChannelSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = channelMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 조회(단건)", description = "채널 정보를 조회 한다.")
    @GetMapping(value = "/mng/base/channel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            ResponseVO responseVO = channelMngService.findById(id);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 등록", description = "채널 정보를 신규 등록 한다.")
    @PostMapping(value = "/mng/base/channel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.add(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 수정", description = "채널 정보를 수정 한다.")
    @PutMapping(value = "/mng/base/channel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "채널 삭제", description = "채널 정보를 삭제 한다. 논리적 삭제(use_yn='N')를 수행 한다.")
    @DeleteMapping(value = "/mng/base/channel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity remove(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.removeById(dto.getChannelId());

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).resultInfo(e.getData()).build(), e.getErrCd().getStatus());
        }
    }
}
