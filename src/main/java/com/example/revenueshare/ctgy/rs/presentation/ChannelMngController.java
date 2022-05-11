package com.example.revenueshare.ctgy.rs.presentation;


import com.example.revenueshare.ctgy.rs.domain.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ChannelMngController {

    @GetMapping("/mng/channel")
    public ResponseEntity findAllBy(){

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
    @GetMapping("/mng/channel/{channelId}")
    public ResponseEntity findById(@PathVariable String channelId){

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
    @PostMapping("/mng/channel")
    public ResponseEntity add(){

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
    @PutMapping("/mng/channel")
    public ResponseEntity modify(){

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
    @DeleteMapping("/mng/channel")
    public ResponseEntity remove(){

        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
