package br.com.saudepraja.service;

import br.com.saudepraja.api.core.security.authorizationserver.CustomUserDetailsService;
import br.com.saudepraja.api.core.security.authorizationserver.SaudeprajaSecurity;
import br.com.saudepraja.api.core.security.authorizationserver.UserDetailsInfo;
import br.com.saudepraja.domain.dto.order.SchedulingDTO;
import br.com.saudepraja.domain.model.order.Clinic;
import br.com.saudepraja.domain.model.order.Scheduling;
import br.com.saudepraja.domain.model.order.ServiceGuide;
import br.com.saudepraja.domain.model.user.Users;
import br.com.saudepraja.domain.model.util.Storable;
import br.com.saudepraja.domain.model.util.Template;
import br.com.saudepraja.domain.repository.ServiceGuideRepository;
import br.com.saudepraja.domain.repository.order.SchedulingRepository;
import br.com.saudepraja.domain.service.SaudePrajaUtils;
import br.com.saudepraja.exception.SaudePrajaBusinessException;
import br.com.saudepraja.exception.SaudePrajaNotFoundException;
import br.com.saudepraja.infrastructure.StorageAmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private UserService userService;

    private SchedulingRepository schedulingRepository;

    private ServiceGuideRepository serviceGuideRepository;

    private StorageAmazonS3Service storageAmazonS3Service;

    private CustomUserDetailsService customUserDetailsService;

    private PdfWriter pdfWriter;

    private static final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    public Scheduling findScheduling(final Long schedulingId) {
        return schedulingRepository.findById(schedulingId).orElseThrow(() -> new SaudePrajaNotFoundException("Agendamento: " + schedulingId + "não encontrado"));
    }

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
        assert !multipartFiles.isEmpty();

        if(multipartFiles.size() > 4 || multipartFiles.stream().anyMatch(i -> i.getSize() >= StorageAmazonS3Service.MAX_FILE_SIZE)) {
            throw new SaudePrajaBusinessException("Lot of files! Max files: 4");
        }

        multipartFiles.forEach(item -> {
            if(item.getContentType() == null || (!item.getContentType().equals(".png") && !item.getContentType().equals(".jpeg"))) {
                throw new SaudePrajaBusinessException("Extension type not allowed! Send files with the types .PNG or .JPEG");
            }
        });
    }

    public byte[] generateServiceGuidePdf(final Long schedulingId) throws SaudePrajaBusinessException, SaudePrajaNotFoundException {
        Scheduling scheduling = findScheduling(schedulingId);

        if(!scheduling.getStatus().isScheduled()) {
            throw new SaudePrajaBusinessException("A guia não pôde ser emitida! O status do agendamento deve ser (SCHEDULED). "
                    + "Status atual: " + scheduling.getStatus());
        }

        Users customer = userService.findUsersById(scheduling.getUserId());

        Authentication auth = SaudeprajaSecurity.getCredentials();
        UserDetailsInfo userDetailsInfo = customUserDetailsService.loadUserByUsername(auth.getName());

        String guideNumber = saveServiceGuide(scheduling, userDetailsInfo.getUserId());
        Map<String, String> mapInfo = buildMapInfo(customer, guideNumber, scheduling);
        return pdfWriter.generatePdfFromHtml(Template.SERVICE_GUIDE, mapInfo);
    }

    private Map<String, String> buildMapInfo(final Users customer, final String uuid, Scheduling scheduling) {
        assert customer != null;
        assert uuid != null;
        Clinic clinic = scheduling.getClinic();

        Map<String, String> mapInfo = new HashMap<>();
        mapInfo.put("CLIENTE", customer.getName());
        mapInfo.put("CPF", customer.getCustomer().getCpf());
        Integer age = LocalDate.now().getYear() - customer.getCustomer().getBirthday().getYear();
        mapInfo.put("IDADE", String.valueOf(age));

        mapInfo.put("DATA_AGENDAMENTO", SaudePrajaUtils.LocalDateTimeToString(scheduling.getDatScheduling(), SaudePrajaUtils.dayMontYearHourMinBRType));
        mapInfo.put("ENDERECO", clinic.getAdrress());
        mapInfo.put("CEP", clinic.getZipCode());
        mapInfo.put("CIDADE", clinic.getCity());
        mapInfo.put("DAT_EMISSAO", SaudePrajaUtils.LocalDateTimeToString(LocalDateTime.now(), SaudePrajaUtils.dayMontYearHourMinBRType));
        mapInfo.put("NUMERO_GUIA", uuid);

        return mapInfo;
    }

    private String saveServiceGuide(Scheduling scheduling, Long createdByUserId) {
        String guideNumberSaved = null;
        for(int x = 0 ; x < 5 ; x++) {
            try {
                String guideNumber = ServiceGuide.generateCode(10);
                ServiceGuide serviceGuide = new ServiceGuide(guideNumber, createdByUserId, scheduling);
                serviceGuide = serviceGuideRepository.save(serviceGuide);
                guideNumberSaved = serviceGuide.getId();
            } catch (DataIntegrityViolationException dex) {
                logger.info("Erro ao gerar o service guide! tentativa: " + x);
            }
        }

        return guideNumberSaved;
    }

}
