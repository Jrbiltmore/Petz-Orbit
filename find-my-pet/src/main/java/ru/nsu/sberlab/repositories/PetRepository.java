package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByChipId(String chipId);
    void deleteByChipId(String chipId);
    
    List<Pet> findByOwnerUsername(String username);
    
    List<Pet> findByBreed(String breed);
    
    Optional<Pet> findBySocialMediaTag(String tag);
    
    List<Pet> findByLocation(String location);
    
    List<Pet> findByHasSocialMediaAccount(boolean hasAccount);
    
    List<Pet> findByLastKnownLocationIsNull();
    
    List<Pet> findByLastKnownLocationNotNull();
    
    List<Pet> findLostPets();
    
    List<Pet> findFoundPets();
    
    void assignRescueFlag(String chipId, boolean rescueFlag);
    
    void updateInstagramPost(String chipId, String instagramPostUrl);
    
    void setReward(String chipId, double rewardAmount, String cryptocurrency);
    
    void claimReward(String chipId);
    
    void updateLostStatus(String chipId, boolean lostStatus);
    
    void updateStolenStatus(String chipId, boolean stolenStatus);
    
    void handleLostIncident(String chipId, String incidentDescription);
    
    void handleStolenIncident(String chipId, String incidentDescription);
    
    void updateMedicalHistory(String chipId, String medicalHistory);
    
    void updateCurrentVet(String chipId, String vetName, String vetContact);
    
    Optional<String> getMedicalHistory(String chipId);
    
    Optional<String> getCurrentVetName(String chipId);
    
    Optional<String> getCurrentVetContact(String chipId);
    
    void integrateWithWaze(String chipId);
    
    void integrateWithGoogleAssistant(String chipId);
    
    void integrateWithSiri(String chipId);
    
    void integrateWithAlexa(String chipId);
    
    void updatePetLocation(String chipId, double latitude, double longitude);
    
    Optional<Double> getPetLatitude(String chipId);
    
    Optional<Double> getPetLongitude(String chipId);
}
