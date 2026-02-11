package br.com.saudepraja.service;

import br.com.saudepraja.domain.model.entity.user.Medic;
import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO.MedicDTO;
import br.com.saudepraja.domain.model.repository.user.MedicRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
