package ru.nsu.sberlab.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "features")
@Data
@NoArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Long featureId;

    @Column(name = "date")
    private LocalDate dateTime;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "features")
    private List<Pet> pets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private PropertyType property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Feature(String description, PropertyType propertyType, User user) {
        this.description = description;
        this.property = propertyType;
        this.user = user;
    }

    @PrePersist
    private void initialization() {
        this.dateTime = LocalDate.now();
    }
}
