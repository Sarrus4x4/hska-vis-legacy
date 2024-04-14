package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryRestController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CategoryRepository categoryRepository;



    //helloworld
    @GetMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }

    //Get Category
    //@RequestParam(value = "id") Integer id
    @GetMapping("/Category/{id}")
    public Optional<Category> getCategory(@PathVariable int id) {

        return categoryRepository.findById(id);

    }

    //Get Category
    @GetMapping("/Category-getall")
    public  @ResponseBody List<Category> getAllCategory() {

        List<Category> categoryList = new ArrayList<Category>();
        categoryRepository.findAll().forEach(category -> categoryList.add(category));
        return categoryList;
    }

    //Post Category
    //curl http://localhost:8080/Category -d name=First
    @PostMapping(path="/Category") // Map ONLY POST Requests
    public @ResponseBody String addNewCategory (@RequestParam String name) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Category n = new Category();
        n.setName(name);
        categoryRepository.save(n);
        return "Saved";
    }

    //Delete Category
    @DeleteMapping("/Category/{id}")
    public @ResponseBody String deleteCategory (@PathVariable int id) {
        categoryRepository.deleteById(id);
        return "Deleted";
    }




}