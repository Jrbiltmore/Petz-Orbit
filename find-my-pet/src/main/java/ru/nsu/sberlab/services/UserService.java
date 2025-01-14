package ru.nsu.sberlab.services;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.models.dto.*;
import ru.nsu.sberlab.models.entities.*;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.repositories.DeletedUserRepository;
import ru.nsu.sberlab.repositories.PropertiesRepository;
import ru.nsu.sberlab.repositories.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final PropertiesRepository propertiesRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final PropertyResolverUtils propertyResolver;

    @Transactional
    @NistSecurityStandard(level = NistSecurityLevel.HIGH)
    public void createUser(UserRegistrationDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getFirstName(),
                userDto.getPassword()
        );
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isPresent() && currentUser.get().isActive()) {
            throw new FailedUserCreationException();
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
    }

    @Transactional
    @NistSecurityStandard(level = NistSecurityLevel.HIGH)
    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        DeletedUser deletedUser = new DeletedUser(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getDateOfCreated()
        );
        deletedUserRepository.save(deletedUser);
        userRepository.deleteById(user.getUserId());
    }

    @Transactional
    @NistSecurityStandard(level = NistSecurityLevel.MEDIUM)
    public void createPet(User principal, PetCreationDto petCreationDto) {
        User user = userRepository.findUserByUserId(principal.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
        user.getPets().add(
                new Pet(
                        petCreationDto.getChipId(),
                        petCreationDto.getType(),
                        petCreationDto.getBreed(),
                        petCreationDto.getSex(),
                        petCreationDto.getName(),
                        parseFeaturesFromPetCreationDto(principal, petCreationDto.getFeatures())
                )
        );
        userRepository.save(user);
    }

    @NistSecurityStandard(level = NistSecurityLevel.LOW)
    public List<PetInfoDto> petsListByUserId(Long userId) {
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                        () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
                );
        return user.getPets().stream()
                .map(petInfoDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @NistSecurityStandard(level = NistSecurityLevel.HIGH)
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(message("api.server.error.user-not-found"))
        );
    }

    private List<Feature> parseFeaturesFromPetCreationDto(User principal, List<FeatureCreationDto> featureCreationDtoList) {
        List<PropertyType> properties = propertiesRepository.findAll();
        return IntStream.range(0, properties.size())
                .filter(i -> !featureCreationDtoList.get(i).getDescription().isEmpty())
                .mapToObj(
                        i -> new Feature(
                                featureCreationDtoList.get(i).getDescription(),
                                properties.get(i),
                                principal
                        )
                )
                .toList();
    }

    private String message(String property) {
        return propertyResolver.resolve(property, Locale.getDefault());
    }
}
