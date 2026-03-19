package com.enterprise.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;
    @Column(length = 500)
    private String description;
    private String imageUrl;
    private Boolean isActive=true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
