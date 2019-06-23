package com.medicine.pharmacy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "preparation")
public class Preparation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(length = 2048)
    private String description;

    @Column
    private Double price;

    @Column
    private boolean recipe;

    @Column
    private String INN;

    @OneToOne
    @JoinColumn(name = "subCategory_id")
    private SubCategoryPreparation subCategoryPreparation;

    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryPreparation categoryPreparation;
}
