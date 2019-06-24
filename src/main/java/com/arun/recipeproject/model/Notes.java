package com.arun.recipeproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@lob means data will be put as clob field
    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;
}
