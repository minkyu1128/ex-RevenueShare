package com.example.revenueshare.init;

import com.example.revenueshare.biz.mng.base.domain.repository.CmpnyRepository;
import com.example.revenueshare.biz.mng.base.model.*;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import com.example.revenueshare.biz.mng.base.service.CmpnyMngService;
import com.example.revenueshare.biz.mng.base.service.CreatorMngService;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMngService;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCmpnyMngService;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCreatorMngService;
import com.example.revenueshare.biz.revnsett.model.RevnSettleDTO;
import com.example.revenueshare.biz.revnsett.service.RevnSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
@Transactional
public class TestDataJpaRunner implements ApplicationRunner {

    @Autowired
    private CmpnyRepository cmpnyRepository;


    /* ================================================
    * Base Mng
    ================================================ */
    @Autowired
    private ChannelMngService channelMngService;
    @Autowired
    private CmpnyMngService cmpnyMngService;
    @Autowired
    private CreatorMngService creatorMngService;
    /* ================================================
    * Contract Mng
    ================================================ */
    @Autowired
    private ContractCmpnyMngService contractCmpnyMngService;
    @Autowired
    private ContractCreatorMngService contractCreatorMngService;
    /* ================================================
    * Channel Revenue Mng
    ================================================ */
    @Autowired
    private ChannelRevnMngService channelRevnMngService;
    /* ================================================
    * Revenue Settle
    ================================================ */
    @Autowired
    private RevnSettleService revnSettleService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /* ======================================================
        * 기초데이터 등록
        ====================================================== */
        //채널 등록
        createChannelDTO().forEach(data -> channelMngService.add(data));
        //회사 등록
        createCmpnyDTO().forEach(data -> cmpnyMngService.add(data));
        //크리에이터 등록
        createCreatorDTO().forEach(data -> creatorMngService.add(data));

        /* ======================================================
        * 계약정보 등록
        ====================================================== */
        //회사와 채널간 계약정보 등록
        channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
                .forEach(row -> contractCmpnyMngService.add(createContractCmpnyDTO(cmpnyRepository.findByCmpnyNm("샌드박스").get().getCmpnyId(), row.getChannelId())));
        //크리에이터와 채널간 계약정보 등록
        channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
                .forEach(row -> createContractCreatorDTO(row.getChannelId(), creatorMngService.findAllBy(CreatorSearchDTO.builder().build()).getResultInfo()).forEach(data -> contractCreatorMngService.add(data))
                );

        /* ======================================================
        * 채널수익 등록
        ====================================================== */
        //일별 채널수익 등록
        createChannelRevnDTO().forEach(data -> channelRevnMngService.add(data));

        /* ======================================================
        * 채널수익 정산
        ====================================================== */
        //채널수익정산(회사 및 크리에이터)
        createRevnSettleDTO().forEach(data -> revnSettleService.add(data));


        System.out.println("============================================================================================================");
        System.out.println("======== Initailize Information [OrgMng & TmpltMng] :: active profiles - " + System.getProperty("spring.profiles.active") + " ========");
        System.out.println(orgMngResult.toString());
        System.out.println(tmpltMngResult.toString());
        System.out.println("============================================================================================================");
    }


    private List<ChannelDTO> createChannelDTO() {
        List<ChannelDTO> list = new ArrayList<>();
        list.add(initChannelDTO("홍길동TV", LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("도티TV", LocalDateTime.now().plusDays(9).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("춘향이TV", LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("철수와미애TV", LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        return list;
    }

    private List<CmpnyDTO> createCmpnyDTO() {
        List<CmpnyDTO> list = new ArrayList<>();
        list.add(initCmpnyDTO("샌드박스"));
        list.add(initCmpnyDTO("유령"));
        list.add(initCmpnyDTO("페이퍼"));
        return list;
    }

    private List<CreatorDTO> createCreatorDTO() {
        List<CreatorDTO> list = new ArrayList<>();
        list.add(initCreatorDTO("홍길동", 99, "1"));
        list.add(initCreatorDTO("도티", 99, "1"));
        list.add(initCreatorDTO("춘향이", 99, "2"));
        list.add(initCreatorDTO("철수&미애", 99, "1"));
        return list;
    }

    private ContractCmpnyDTO createContractCmpnyDTO(Long cmpnyId, Long channelId) {
        return initContractCmpnyDTO(cmpnyId, channelId, LocalDateTime.now().minusDays(365).format(DateTimeFormatter.ofPattern("yyyyMMdd")), 40);
    }

    private List<ContractCreatorDTO> createContractCreatorDTO(Long channelId, List<CreatorDTO> creators) {
        List<ContractCreatorDTO> list = new ArrayList<>();

        for (CreatorDTO dto : creators){
            String openDe = LocalDateTime.now().minusDays(365).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            Integer rsRate = 100 / creators.size();
            list.add(initContractCreatorDTO(dto.getCreatorId(), channelId, openDe, rsRate));
        }

        return list;
    }

    private List<ChannelRevnDTO> createChannelRevnDTO(List<ChannelDTO> channels, String yyyy) {
        List<ChannelRevnDTO> list = new ArrayList<>();

        for(ChannelDTO dto : channels){
            Long channelId, String revnDe, String revnSeCd, Long revnAmt
            list.add(initChannelRevnDTO(dto.getChannelId(), yyyy+""));
        }
        return list;
    }

    private List<RevnSettleDTO> createRevnSettleDTO() {
        List<RevnSettleDTO> list = new ArrayList<>();
        list.add(RevnSettleDTO.builder().build());
        return list;
    }

    private ChannelDTO initChannelDTO(String channelNm, String openDe, String closeDe) {
        return ChannelDTO.builder()
                .channelId(null)
                .channelNm(channelNm)
                .openDe(openDe)
                .closeDe(closeDe)
                .build();
    }

    private CmpnyDTO initCmpnyDTO(String cmpnyNm) {
        return CmpnyDTO.builder()
                .cmpnyId(null)
                .cmpnyNm(cmpnyNm)
                .build();
    }

    private CreatorDTO initCreatorDTO(String creatorNm, Integer age, String sex) {
        return CreatorDTO.builder()
                .creatorId(null)
                .creatorNm(creatorNm)
                .age(age)
                .sex(sex)
                .build();
    }

    private ContractCmpnyDTO initContractCmpnyDTO(Long cmpnyId, Long channelId, String cntrDe, Integer rsRate) {
        return ContractCmpnyDTO.builder()
                .cntrCmpId(null)
                .cmpnyId(cmpnyId)
                .channelId(channelId)
                .cntrDe(cntrDe)
                .rsRate(rsRate)
                .build();
    }

    private ContractCreatorDTO initContractCreatorDTO(Long creatorId, Long channelId, String cntrDe, Integer rsRate) {
        return ContractCreatorDTO.builder()
                .cntrCrtId(null)
                .creatorId(creatorId)
                .channelId(channelId)
                .cntrDe(cntrDe)
                .rsRate(rsRate)
                .build();
    }

    private ChannelRevnDTO initChannelRevnDTO(Long channelId, String revnDe, String revnSeCd, Long revnAmt) {
        return ChannelRevnDTO.builder()
                .chRevnId(null)
                .channelId(channelId)
                .revnDe(revnDe)
                .revnSeCd(revnSeCd)
                .revnAmt(revnAmt)
                .build();
    }

    private RevnSettleDTO initRevnSettleDTO(Long channelId, String calYm) {
        return RevnSettleDTO.builder()
                .channelId(channelId)
                .calYm(calYm)
                .build();
    }


}