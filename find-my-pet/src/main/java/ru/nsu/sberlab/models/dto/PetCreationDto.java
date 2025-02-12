package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.sberlab.models.enums.Sex;

import java.util.List;

@Data
@AllArgsConstructor
public class PetCreationDto {
    private final String chipId;
    private final String type;
    private final String breed;
    private final Sex sex;
    private final String name;
    private final List<FeatureCreationDto> features;
}
