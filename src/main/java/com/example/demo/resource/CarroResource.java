package com.example.demo.resource;

import com.example.demo.model.Carro;
import com.example.demo.repository.CarroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/carros")
public class CarroResource {

    @Autowired
    private CarroRepository repository;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Carro carro, HttpServletResponse response){

        carro.setId(null);
        carro = repository.save(carro);
        response.addHeader(HttpHeaders.LOCATION, "/carros" + carro.getId());
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Carro carro){

        var old = get(id);
        BeanUtils.copyProperties(carro,old, "id");
        repository.save(old);

    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){

        var carro = get(id);
        repository.delete(carro);

    }

    @GetMapping
    public List<Carro> list(){

        return repository.findAll();

    }

    @GetMapping(path = "/{id}")
    public Carro get(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro" + id));
    }
}
