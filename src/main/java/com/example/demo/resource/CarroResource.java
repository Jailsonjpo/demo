package com.example.demo.resource;

import com.example.demo.model.Carro;
import com.example.demo.repository.CarroRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/carros")
public class CarroResource {

    @Autowired
    private CarroRepository repository;

    @ApiOperation(value = "Inclui o carro na lista de carros e o habilita")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o path do carro"),
            @ApiResponse(code = 483, message = "Você não tem permissao"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Carro carro, HttpServletResponse response) {

        carro.setId(null);
        carro = repository.save(carro);
        response.addHeader(HttpHeaders.LOCATION, "/carros" + carro.getId());
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public void update(@Valid @PathVariable Long id, @RequestBody Carro carro) {
        var old = get(id);
        BeanUtils.copyProperties(carro, old, "id");
        repository.save(old);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var carro = get(id);
        repository.delete(carro);
    }

    @GetMapping
    public List<Carro> list(
            @RequestParam(name = "cor", required = false) String cor,
            @RequestParam(name = "marca", required = false) String marca,
            @RequestParam(name = "modelo", required = false) String modelo,
            @RequestParam(name = "ano", required = false) Integer ano,
            @RequestParam(name = "page", required = false, defaultValue = "8") String page,
            @RequestParam(name = "size", required = false, defaultValue = "5") String size,
            @RequestParam(name = "sort", required = false, defaultValue = "ano") String sort,
            @RequestParam(name = "order", required = false, defaultValue = "DESC") String order) {

        Example<Carro> example = Example.of(Carro.builder().cor(cor).build());
        //Example<Carro> example = Example.of(Carro.builder().cor(cor).marca(marca).modelo(modelo).build());

        return repository.findAll(example);
    }

    @GetMapping(path = "/{id}")
    public Carro get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro" + id));
    }
}
