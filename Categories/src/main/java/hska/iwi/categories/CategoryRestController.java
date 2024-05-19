package hska.iwi.categories;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

@RestController
public class CategoryRestController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CategoryRepository categoryRepository;

    @Value("${endpoints.products.hostname}")
    private String productsHost;

    //helloworld
    @GetMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }

    //Get Category
    //@RequestParam(value = "id") Integer id
    @GetMapping("/Category/{id}")
    public Optional<Category> getCategory(@PathVariable int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "category not found"
            );
        }
        return category;
    }

    //Get Category
    @GetMapping("/Category")
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

        //get all products with that category
        RestTemplate getCategories = new RestTemplate();
        ResponseEntity<JsonNode> getCategoriesResponse = getCategories.
                getForEntity("http://"+ productsHost + ":8080/Product", JsonNode.class);
        JsonNode getCategoriesResponseBody = getCategoriesResponse.getBody();

        for (JsonNode json : getCategoriesResponseBody) {
            // Read category id
            int categoryId = json.get("categoryId").asInt();
            //if category matches then delete the product
            if(categoryId == id){
                // Read product id
                int productId = json.get("id").asInt();
                //make delete call
                RestTemplate deleteProducts = new RestTemplate();
                // Make the DELETE request
                deleteProducts.delete("http://" + productsHost + ":8080/Product/" + productId);
            }
        }

        return "deleted";
    }




}