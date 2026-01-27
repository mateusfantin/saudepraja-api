package br.com.saudepraja.service;

import br.com.saudepraja.api.core.security.authorizationserver.CustomUserDetailsService;
import br.com.saudepraja.api.core.security.authorizationserver.SaudeprajaSecurity;
import br.com.saudepraja.api.core.security.authorizationserver.UserDetailsInfo;
import br.com.saudepraja.domain.exception.SaudePrajaBusinessException;
import br.com.saudepraja.domain.model.entity.order.Scheduling;
import br.com.saudepraja.domain.model.entity.order.SchedulingDTO;
import br.com.saudepraja.domain.model.entity.order.SchedulingInfoDTO;
import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.util.Storable;
import br.com.saudepraja.domain.model.repository.SchedulingRepository;
import br.com.saudepraja.domain.service.user.utils.SaudePrajaUtils;
import br.com.saudepraja.infrastructure.StorageAmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SchedulingService {

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private StorageAmazonS3Service storageAmazonS3Service;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PdfWriter pdfWriter;


    public void save(SchedulingDTO schedulingDTO) {
        Long userId = null;
        boolean isAuthenticated = SaudeprajaSecurity.isAuthenticated();

        if(isAuthenticated) {
            Authentication auth = SaudeprajaSecurity.getCredentials();
            UserDetailsInfo userDetailsInfo = customUserDetailsService.loadUserByUsername(auth.getName());
            userId = userDetailsInfo.getUserId();
        }

        Scheduling scheduling = Scheduling.builder()
                .userId(userId)
                .datScheduling(schedulingDTO.datScheduling())
                .medicSpecialty(schedulingDTO.medicSpecialty())
                .obs(schedulingDTO.obs())
                .telephone(schedulingDTO.telefone())
                .build();

        schedulingRepository.save(scheduling);

    }

    public void upload(Long schedulingId, MultipartFile multipartFile) {
        this.upload(schedulingId, List.of(multipartFile));
    }

    public void upload(Long schedulingId, List<MultipartFile> multipartFiles) {
        if(!SaudeprajaSecurity.isAdmin()) {
            Authentication auth = SaudeprajaSecurity.getCredentials();
            UserDetailsInfo userDetailsInfo = customUserDetailsService.loadUserByUsername(auth.getName());

            Scheduling scheduling = schedulingRepository.findById_AndUserId(schedulingId, userDetailsInfo.getUserId())
                    .orElseThrow(() -> new SaudePrajaBusinessException("Scheduling not found"));
        }

        List<Storable> storables = new ArrayList<>();
        this.verifyUploadFiles(multipartFiles);

        AtomicInteger x = new AtomicInteger();
        multipartFiles.forEach(item -> {
            try {
                String newName = "SCHEDULING_" + schedulingId + "_" + x.getAndIncrement();
                Storable storable = new Storable(item.getName(), item.getInputStream(), item.getContentType());
                storables.add(storable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        storables.forEach(storable -> {
//            storageAmazonS3Service.store(storable);
        });
    }

    private void verifyUploadFiles(List<MultipartFile> multipartFiles) {
        if(multipartFiles.size() >= 3 || multipartFiles.stream().anyMatch(i -> i.getSize() >= StorageAmazonS3Service.MAX_FILE_SIZE)) {
            throw new SaudePrajaBusinessException("It's not possible to send many files.");
        }

        multipartFiles.forEach(item -> {
            if(item.getContentType() == null || (!item.getContentType().equals(".png") && !item.getContentType().equals(".jpeg"))) {
                throw new SaudePrajaBusinessException("Extension type not allowed.");
            }
        });
    }

    public byte[] buildServiceGuide(SchedulingInfoDTO schedulingInfoDTO) throws SaudePrajaBusinessException {
        Users user = userService.findUsersById(schedulingInfoDTO.customerId());
//        Clinic clinic = clinicService.findById();
        String uuid = String.valueOf(UUID.randomUUID());
        Map<String, String> mapInfo = buildMapInfo(user, uuid, schedulingInfoDTO);
        //implementar a busca do html no banco de dados
        // implementar o salvamento da guia
        return pdfWriter.generatePdf(null, mapInfo);
    }

    private Map<String, String> buildMapInfo(final Users user, final String uuid, SchedulingInfoDTO schedulingInfoDTO /*, final Clinic clinic*/) {
        Map<String, String> mapInfo = new HashMap<>();
        mapInfo.put("CLIENTE", user.getName());
        mapInfo.put("CPF", user.getCustomer().getCpf());
        Integer age = LocalDate.now().getYear() - user.getCustomer().getBirthday().getYear();
        mapInfo.put("IDADE", String.valueOf(age));

        mapInfo.put("DATA_AGENDAMENTO", SaudePrajaUtils.LocalDateTimeToString(schedulingInfoDTO.datScheduling(), SaudePrajaUtils.dayMontYearHourMinBRType));
//        mapInfo.put("ENDERECO", clinic.getAdrress());
//        mapInfo.put("CEP", clinic.getZipCode());
//        mapInfo.put("CIDADE", clinic.getCity());
        mapInfo.put("DAT_EMISSAO", SaudePrajaUtils.LocalDateTimeToString(LocalDateTime.now(), SaudePrajaUtils.dayMontYearHourMinBRType));
        mapInfo.put("NUMERO_GUIA", uuid);

        return mapInfo;
    }
}
