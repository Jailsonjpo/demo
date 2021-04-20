package com.example.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carro{

    @ApiModelProperty(value = "Identificação do carro")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   /* @NotEmpty
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String marca;

    @NotEmpty
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String modelo;*/

    @NotNull
    @Column(nullable = false)
    private Integer ano;

    @Size(max = 20)
    @Column(length = 20)
    private String cor;

    @NotEmpty
    @Size(max =20)
    @Column(nullable = false, length = 10)
    private String placa;

    @ManyToOne
    private Modelo modelo;

}
