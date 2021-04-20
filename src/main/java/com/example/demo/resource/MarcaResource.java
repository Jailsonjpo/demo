package com.example.demo.resource;

import com.example.demo.model.Marca;
import com.example.demo.repository.MarcaRepository;
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
@RequestMapping(value = "/marca")
public class MarcaResource {

    @Autowired
    private MarcaRepository repository;

    @ApiOperation(value = "Inclui a marca na lista de marcas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o path da marca"),
            @ApiResponse(code = 483, message = "Você não tem permissao"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Marca marca, HttpServletResponse response) {

        marca.setId(null);
        marca = repository.save(marca);
        response.addHeader(HttpHeaders.LOCATION, "/marcas" + marca.getId());
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public void update(@Valid @PathVariable Long id, @RequestBody Marca marca) {
        var old = get(id);
        BeanUtils.copyProperties(marca, old, "id");
        repository.save(old);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var marca = get(id);
        repository.delete(marca);
    }

    @GetMapping
    public List<Marca> list(){
            //@RequestParam(name = "nome", required = false) String nome) {

       // Example<Marca> example = Example.of(Marca.builder().build());
        //Example<Carro> example = Example.of(Carro.builder().cor(cor).marca(marca).marca(marca).build());

        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Marca get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Marca" + id));
    }
}
