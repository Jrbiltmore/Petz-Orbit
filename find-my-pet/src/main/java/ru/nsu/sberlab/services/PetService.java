package ru.nsu.sberlab.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.repositories.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;

    public PetService(PetRepository petRepository, PetInfoDtoMapper petInfoDtoMapper) {
        this.petRepository = petRepository;
        this.petInfoDtoMapper = petInfoDtoMapper;
    }

    @NistSecurityStandard(level = NistSecurityLevel.HIGH)
    public List<PetInfoDto> getPetsList(Pageable pageable) {
        List<Pet> pets = petRepository.findAll(pageable).getContent();
        return pets.stream()
                .map(petInfoDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @NistSecurityStandard(level = NistSecurityLevel.MEDIUM)
    public PetInfoDto getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petInfoDtoMapper::toDto)
                .orElse(null);
    }

    @Transactional
    @NistSecurityStandard(level = NistSecurityLevel.HIGH)
    public void deletePetByChipId(String chipId) {
        petRepository.deleteByChipId(chipId);
    }

    @NistSecurityStandard(level = NistSecurityLevel.LOW)
    public void updatePet(PetInfoDto petInfoDto) {
        Pet pet = petRepository.findByChipId(petInfoDto.getChipId())
                .orElseThrow(() -> new NotFoundException("Pet not found"));
        
        // Perform necessary security checks and update the pet entity
        
        // Save the updated pet entity
        petRepository.save(pet);
    }
}
