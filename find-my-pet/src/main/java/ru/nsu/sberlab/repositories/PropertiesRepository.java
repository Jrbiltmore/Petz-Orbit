package ru.nsu.sberlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.sberlab.models.entities.PropertyType;

import java.util.List;
import java.util.Optional;

public interface PropertiesRepository extends JpaRepository<PropertyType, Long> {
    Optional<PropertyType> findByPropertyName(String propertyName);
    
    List<PropertyType> findByEnabled(boolean enabled);
    
    List<PropertyType> findByCategory(String category);
    
    List<PropertyType> findByCategoryAndEnabled(String category, boolean enabled);
    
    List<PropertyType> findByPropertyNameContainingIgnoreCase(String keyword);
    
    List<PropertyType> findByDescriptionContainingIgnoreCase(String keyword);
    
    List<PropertyType> findByCategoryContainingIgnoreCase(String keyword);
    
    List<PropertyType> findByEnabledAndCategoryContainingIgnoreCase(boolean enabled, String keyword);
    
    List<PropertyType> findByPropertyNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String propertyKeyword, String descriptionKeyword);
    
    List<PropertyType> findByEnabledAndCategoryIn(boolean enabled, List<String> categories);
    
    List<PropertyType> findByCategoryIn(List<String> categories);
    
    List<PropertyType> findByEnabledAndCategoryAndPropertyNameContainingIgnoreCase(boolean enabled, String category, String keyword);
    
    List<PropertyType> findByEnabledAndCategoryAndDescriptionContainingIgnoreCase(boolean enabled, String category, String keyword);
    
    List<PropertyType> findByEnabledAndCategoryAndPropertyNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(boolean enabled, String category, String propertyKeyword, String descriptionKeyword);
}
