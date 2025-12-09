package br.com.saudepraja.service;

import br.com.saudepraja.domain.exception.SaudePrajaBusinessException;
import br.com.saudepraja.domain.model.entity.order.SchedulingDTO;
import br.com.saudepraja.domain.model.entity.util.Storable;
import br.com.saudepraja.domain.model.repository.SchedulingRepository;
import br.com.saudepraja.domain.model.entity.order.Scheduling;
import br.com.saudepraja.infrastructure.StorageAmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private StorageAmazonS3Service storageAmazonS3Service;

    public void save(SchedulingDTO schedulingDTO) {

        Scheduling scheduling = Scheduling.builder()
                .datScheduling(schedulingDTO.datScheduling())
                .medicSpecialty(schedulingDTO.medicSpecialty())
                .obs(schedulingDTO.obs())
                .build();

        schedulingRepository.save(scheduling);

    }

    public void upload(Long schedulingId, List<MultipartFile> multipartFiles) {
        Scheduling scheduling = schedulingRepository.findById(schedulingId)
                .orElseThrow(() -> new SaudePrajaBusinessException("Scheduling not found"));

        List<Storable> storables = new ArrayList<>();
        this.verifyUploadFiles(multipartFiles);

        AtomicInteger x = new AtomicInteger();
        multipartFiles.forEach(item -> {
            try {
                String newName = "SCHEDULING_" + schedulingId + "_" + x.getAndIncrement();
                Storable storable = new Storable(item.getName(), item.getInputStream());
                storables.add(storable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        storables.forEach(storable -> {
            storageAmazonS3Service.store(storable);
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
}
