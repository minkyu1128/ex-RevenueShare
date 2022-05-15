package com.example.revenueshare.biz.revnsett.presentation;

import com.example.revenueshare.biz.revnsett.model.RevnFndSearchDTO;
import com.example.revenueshare.biz.revnsett.service.RevnFndService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Tag(name = "RevnSettleFinderController", description = "수익 정산 조회")
@RestController
@RequiredArgsConstructor
public class RevnFndController {

    private final RevnFndService revnFndService;


    @Operation(summary = "채널기준 수익금액 조회", description = " ")
    @GetMapping(value = "/revn/fnd/ch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllByCh(RevnFndSearchDTO searchDTO) {
        try {
            List<Map<String, Object>> resultInfo = revnFndService.findAllByCh(searchDTO);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().resultInfo(resultInfo).build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "회사 기준 수익금액 조회", description = " ")
    @GetMapping(value = "/revn/fnd/cmpny", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllByCmpny(RevnFndSearchDTO searchDTO) {
        try {
            List<Map<String, Object>> resultInfo = revnFndService.findAllByCmpny(searchDTO);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().resultInfo(resultInfo).build(), HttpStatus.OK);
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }

    @Operation(summary = "크리에이터 기준 정산금액 조회", description = " ")
    @GetMapping(value = "/revn/fnd/creator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findAllByCreator(RevnFndSearchDTO searchDTO) {
        try {
            List<Map<String, Object>> resultInfo = revnFndService.findAllByCrt(searchDTO);

            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().resultInfo(resultInfo).build(), HttpStatus.OK);
        } catch (
                RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }


}
