package com.example.demo.resource;

import com.example.demo.model.Carro;
import com.example.demo.model.Modelo;
import com.example.demo.repository.CarroRepository;
import com.example.demo.repository.ModeloRepository;
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
@RequestMapping(value = "/modelos")
public class ModeloResource {

    @Autowired
    private ModeloRepository repository;

    @ApiOperation(value = "Inclui o modelo na lista de modelos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o path do modelo"),
            @ApiResponse(code = 483, message = "Você não tem permissao"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Modelo modelo, HttpServletResponse response) {

        modelo.setId(null);
        modelo = repository.save(modelo);
        response.addHeader(HttpHeaders.LOCATION, "/modelos" + modelo.getId());
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public void update(@Valid @PathVariable Long id, @RequestBody Modelo modelo) {
        var old = get(id);
        BeanUtils.copyProperties(modelo, old, "id");
        repository.save(old);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var modelo = get(id);
        repository.delete(modelo);
    }

    @GetMapping
    public List<Modelo> list(){
          //  @RequestParam(name = "modelo", required = false) String modelo) {

     //   Example<Modelo> example = Example.of(Modelo.builder().build());
        //Example<Carro> example = Example.of(Carro.builder().cor(cor).marca(marca).modelo(modelo).build());

        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Modelo get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Modelo" + id));
    }
}
