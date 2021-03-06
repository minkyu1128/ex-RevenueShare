package com.example.revenueshare.init;

import com.example.revenueshare.biz.mng.base.code.RevnSeCd;
import com.example.revenueshare.biz.mng.base.domain.repository.CmpnyRepository;
import com.example.revenueshare.biz.mng.base.model.*;
import com.example.revenueshare.biz.mng.base.service.ChannelMngService;
import com.example.revenueshare.biz.mng.base.service.CmpnyMngService;
import com.example.revenueshare.biz.mng.base.service.CreatorMngService;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnSearchDTO;
import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMngService;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCmpnyMngService;
import com.example.revenueshare.biz.mng.cntrt.service.ContractCreatorMngService;
import com.example.revenueshare.biz.revn.model.RevnSettleDTO;
import com.example.revenueshare.biz.revn.service.RevnSettleService;
import com.example.revenueshare.core.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//@Component
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
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        /* ======================================================
        * ??????????????? ??????
        ====================================================== */
        //?????? ??????
        if (channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo().isEmpty())
            createChannelDTO().forEach(data -> channelMngService.add(data));
        //?????? ??????
        if (cmpnyMngService.findAllBy(CmpnySearchDTO.builder().build()).getResultInfo().isEmpty())
            createCmpnyDTO().forEach(data -> cmpnyMngService.add(data));
        //??????????????? ??????
        if (creatorMngService.findAllBy(CreatorSearchDTO.builder().build()).getResultInfo().isEmpty())
            createCreatorDTO().forEach(data -> creatorMngService.add(data));

        /* ======================================================
        * ???????????? ??????
        ====================================================== */
        //????????? ????????? ???????????? ??????
        if (contractCmpnyMngService.findAllBy(ContractCmpnySearchDTO.builder().build()).getResultInfo().isEmpty())
            channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
                    .forEach(row -> contractCmpnyMngService.add(createContractCmpnyDTO(cmpnyRepository.findByCmpnyNm("????????????").get().getCmpnyId(), row.getChannelId())));
        //?????????????????? ????????? ???????????? ??????
        if (contractCreatorMngService.findAllBy(ContractCreatorSearchDTO.builder().build()).getResultInfo().isEmpty())
            channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
                    .forEach(row -> createContractCreatorDTO(row.getChannelId(), creatorMngService.findAllBy(CreatorSearchDTO.builder().build()).getResultInfo()).forEach(data -> contractCreatorMngService.add(data))
                    );

        /* ======================================================
        * ???????????? ??????
        ====================================================== */
        //?????? ???????????? ??????
        if (channelRevnMngService.findAllBy(ChannelRevnSearchDTO.builder().build()).getResultInfo().isEmpty())
            channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
                    .forEach(row -> createChannelRevnDTO(row.getChannelId()).forEach(data -> channelRevnMngService.add(data)));

//        /* ======================================================
//        * ???????????? ??????
//        ====================================================== */
//        //??????????????????(?????? ??? ???????????????)
//        channelMngService.findAllBy(ChannelSearchDTO.builder().build()).getResultInfo()
//                .forEach(row -> createRevnSettleDTO(row.getChannelId()).forEach(data -> revnSettleService.add(data)));


        System.out.println("============================================================================================================");
        System.out.println("======== Initailize Information [OrgMng & TmpltMng] :: active profiles - " + System.getProperty("spring.profiles.active") + " ========");
        System.out.println("============================================================================================================");
    }


    private List<ChannelDTO> createChannelDTO() {
        List<ChannelDTO> list = new ArrayList<>();
        list.add(initChannelDTO("?????????TV", LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("??????TV", LocalDateTime.now().plusDays(9).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("?????????TV", LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        list.add(initChannelDTO("???????????????TV", LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")), null));
        return list;
    }

    private List<CmpnyDTO> createCmpnyDTO() {
        List<CmpnyDTO> list = new ArrayList<>();
        list.add(initCmpnyDTO("????????????"));
        list.add(initCmpnyDTO("??????"));
        list.add(initCmpnyDTO("?????????"));
        return list;
    }

    private List<CreatorDTO> createCreatorDTO() {
        List<CreatorDTO> list = new ArrayList<>();
        list.add(initCreatorDTO("?????????", 99, "1"));
        list.add(initCreatorDTO("??????", 99, "1"));
        list.add(initCreatorDTO("?????????", 99, "2"));
        list.add(initCreatorDTO("??????&??????", 99, "1"));
        return list;
    }

    private ContractCmpnyDTO createContractCmpnyDTO(Long cmpnyId, Long channelId) {
        return initContractCmpnyDTO(cmpnyId, channelId, LocalDateTime.now().minusDays(365).format(DateTimeFormatter.ofPattern("yyyyMMdd")), 40);
    }

    private List<ContractCreatorDTO> createContractCreatorDTO(Long channelId, List<CreatorDTO> creators) {
        List<ContractCreatorDTO> list = new ArrayList<>();

        for (CreatorDTO dto : creators) {
            String openDe = LocalDateTime.now().minusDays(365).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            Integer rsRate = 100 / creators.size();
            list.add(initContractCreatorDTO(dto.getCreatorId(), channelId, openDe, rsRate));
        }

        return list;
    }

    private List<ChannelRevnDTO> createChannelRevnDTO(Long channelId) {
        List<String> years = Arrays.asList("2021", "2022");

        List<ChannelRevnDTO> list = new ArrayList<>();

        for (String yyyy : years)
            for (int i = 0; i < 1000; i++)
                list.add(initChannelRevnDTO(channelId, yyyy + RandomUtils.randomDay(), RevnSeCd.HITS.getCode(), RandomUtils.randomMoney()));

        return list;
    }

    private List<RevnSettleDTO> createRevnSettleDTO(Long channelId) {
        List<String> years = Arrays.asList("2021", "2022");
        List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        List<RevnSettleDTO> list = new ArrayList<>();

        for (String yyyy : years)
            for (String mm : months)
                list.add(initRevnSettleDTO(channelId, yyyy + mm));

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