package com.example.demo.repository;

import com.example.demo.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    //List<Carro> findByCorAndMarcaAndModelo(String cor, String ano, String marca, String modelo);


}
