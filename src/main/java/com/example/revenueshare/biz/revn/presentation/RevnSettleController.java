package com.example.revenueshare.biz.revn.presentation;

import com.example.revenueshare.biz.revn.model.RevnSettleDTO;
import com.example.revenueshare.biz.revn.service.RevnSettleService;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "RevnSettleController", description = "수익 정산")
@RestController
@RequiredArgsConstructor
public class RevnSettleController {

    private final RevnSettleService revnSettleService;




    @Operation(summary = "수익 정산 등록", description = "채널의 수익을 계약한 요율만큼 회사와 크리에이터에게 배분 한다.")
    @PostMapping(value = "/revn/settle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody RevnSettleDTO dto) {
        try {
            ResponseVO responseVO = revnSettleService.add(dto);

//            return new ResponseEntity<ResponseVO>(ResponseVO.okBuilder().build(), HttpStatus.OK);
            return new ResponseEntity<ResponseVO>(responseVO, responseVO.getErrCd().getStatus());
        } catch (RsException e) {
            return new ResponseEntity(ResponseVO.errBuilder().errCd(e.getErrCd()).errMsg(e.getMessage()).build(), e.getErrCd().getStatus());
        }
    }


}
