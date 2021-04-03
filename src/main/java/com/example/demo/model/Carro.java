package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
public class Carro{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String marca;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String modelo;

    @NotNull
    @Column(nullable = false)
    private Integer ano;

    @Column(length = 20)
    private String cor;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String placa;

}
