package br.com.saudepraja.service;

import br.com.saudepraja.domain.model.entity.order.SchedulingDTO;
import br.com.saudepraja.domain.model.repository.SchedulingRepository;
import br.com.saudepraja.domain.model.entity.order.Scheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    public void save(SchedulingDTO schedulingDTO) {

        Scheduling scheduling = Scheduling.builder()
                .datScheduling(schedulingDTO.datScheduling())
                .medicSpecialty(schedulingDTO.medicSpecialty())
                .obs(schedulingDTO.obs())
                .build();

        schedulingRepository.save(scheduling);

    }
}
