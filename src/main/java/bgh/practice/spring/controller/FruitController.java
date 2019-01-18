package bgh.practice.spring.controller;

import bgh.practice.spring.models.Fruit;
import bgh.practice.spring.repo.FruitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;

@RestController
@Repository//TODO
@RequestMapping(path="/fruitstore")
public class FruitController extends HttpServlet {

    @Autowired
    FruitRepo fruitRepo;
    private static final long serialVersionUID = 1L;


    @GetMapping(value = "/test")
    public @ResponseBody
    String testRest() {
        return "test rest";
    }

    @GetMapping(value="/getFruits", produces="application/json")
    public @ResponseBody
    List<Fruit> getAll(){

        List<Fruit> fruitList = fruitRepo.findAll();
        return fruitList;
    }

    @GetMapping(value="/getFruit/{id}",
            produces="application/json")
    public @ResponseBody
    ResponseEntity<Fruit> getFruit(@PathVariable("id") int id){//TODO id should be integer

        Optional<Fruit> fruitOptional = fruitRepo.findById(id);
        return ResponseEntity.ok(fruitOptional.orElse(null));
    }

    @PostMapping(value="/store/fruit",
            consumes="application/json",
            produces="application/json")
    public @ResponseBody
    int storeFruit(@RequestBody Fruit fruit){

        Fruit fruitRes = fruitRepo.save(fruit);
        return fruitRes.getId();
    }

    @PutMapping(value="/update")
    public void update(@RequestBody Fruit fruit){

        Fruit fruitRes = fruitRepo.save(fruit);
    }
}
