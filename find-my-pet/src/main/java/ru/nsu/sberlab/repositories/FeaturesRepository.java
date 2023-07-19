package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.Feature;

import java.util.List;
import java.util.Optional;

public interface FeaturesRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByFeatureName(String featureName);
    
    List<Feature> findByEnabled(boolean enabled);
    
    List<Feature> findByExpirationDateIsNull();
    
    List<Feature> findByExpirationDateIsNotNull();
    
    List<Feature> findByExpirationDateBefore(String date);
    
    List<Feature> findByExpirationDateAfter(String date);
    
    List<Feature> findByFeatureNameContainingIgnoreCase(String keyword);
    
    List<Feature> findByDescriptionContainingIgnoreCase(String keyword);
    
    List<Feature> findByFeatureNameAndEnabled(String featureName, boolean enabled);
    
    List<Feature> findByFeatureNameAndExpirationDateIsNull(String featureName);
    
    List<Feature> findByFeatureNameAndExpirationDateIsNotNull(String featureName);
    
    List<Feature> findByFeatureNameAndExpirationDateBefore(String featureName, String date);
    
    List<Feature> findByFeatureNameAndExpirationDateAfter(String featureName, String date);
    
    List<Feature> findByFeatureNameAndEnabledAndExpirationDateIsNull(String featureName, boolean enabled);
    
    List<Feature> findByFeatureNameAndEnabledAndExpirationDateIsNotNull(String featureName, boolean enabled);
    
    List<Feature> findByFeatureNameAndEnabledAndExpirationDateBefore(String featureName, boolean enabled, String date);
    
    List<Feature> findByFeatureNameAndEnabledAndExpirationDateAfter(String featureName, boolean enabled, String date);
    
    List<Feature> findByFeatureNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String featureKeyword, String descriptionKeyword);
    
    List<Feature> findByEnabledAndExpirationDateIsNull(boolean enabled);
    
    List<Feature> findByEnabledAndExpirationDateIsNotNull(boolean enabled);
    
    List<Feature> findByEnabledAndExpirationDateBefore(boolean enabled, String date);
    
    List<Feature> findByEnabledAndExpirationDateAfter(boolean enabled, String date);
    
    List<Feature> findByEnabledAndFeatureNameContainingIgnoreCase(boolean enabled, String keyword);
}
