package com.example.revenueshare.ctgy.rs.presentation;


import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.example.revenueshare.ctgy.rs.model.ChannelSearchDTO;
import com.example.revenueshare.ctgy.rs.service.ChannelMngService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChannelMngController", description = "채널 관리")
@RestController
@RequiredArgsConstructor
public class ChannelMngController {

    private final ChannelMngService channelMngService;

    @GetMapping("/mng/channel")
    public ResponseEntity findAllBy(@RequestBody ChannelSearchDTO searchDTO) {
        try {
            ResponseVO responseVO = channelMngService.findAllBy(searchDTO);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()), e.getErrCd().getStatus());
        }
    }

    @GetMapping("/mng/channel/{channelId}")
    public ResponseEntity findById(@PathVariable Long channelId) {
        try {
            ResponseVO responseVO = channelMngService.findById(channelId);

            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()), e.getErrCd().getStatus());
        }
    }

    @PostMapping("/mng/channel")
    public ResponseEntity add(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.add(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()), e.getErrCd().getStatus());
        }
    }

    @PutMapping("/mng/channel")
    public ResponseEntity modify(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.modify(dto);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()), e.getErrCd().getStatus());
        }
    }

    @DeleteMapping("/mng/channel")
    public ResponseEntity remove(@RequestBody ChannelDTO dto) {
        try {
            channelMngService.removeById(dto.getChannelId());

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()), e.getErrCd().getStatus());
        }
    }
}
