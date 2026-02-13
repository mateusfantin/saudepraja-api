package br.com.saudepraja.service;

import br.com.saudepraja.domain.model.user.Medic;
import br.com.saudepraja.domain.model.user.Users;
import br.com.saudepraja.domain.dto.user.UserDTO.MedicDTO;
import br.com.saudepraja.domain.repository.user.MedicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class MedicService {

    private final MedicRepository medicRepository;

    protected void save(final Users users, @Validated(MedicDTO.class) final MedicDTO medicDTO) {

        Medic medic = new Medic();
        medic.setCrm(medicDTO.getCrm());
        medic.setMedicSpecialty(medicDTO.getMedicSpecialty());
        medic.setSubspecialty(medicDTO.getSubspecialty());
        users.setMedic(medic);

        medicRepository.save(medic);
    }
}
