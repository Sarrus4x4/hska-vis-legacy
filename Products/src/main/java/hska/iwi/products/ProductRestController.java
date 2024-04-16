package hska.iwi.products;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProductRestController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private ProductRepository productRepository;



    //helloworld
    @GetMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }

    //Get Product
    //@RequestParam(value = "id") Integer id
    @GetMapping("/Product/{id}")
    public Optional<Product> getProduct(@PathVariable int id) {

        return productRepository.findById(id);

    }

    @GetMapping(path="/Product")
    public @ResponseBody Iterable<Product> getAllProducts() {
        // This returns a JSON or XML with the users
        return productRepository.findAll();
    }

    //Post Product
    //curl http://localhost:8080/Product -d name=First
    @PostMapping(path="/Product") // Map ONLY POST Requests
    public @ResponseBody String addNewProduct (@RequestParam String name,
                                               @RequestParam Double price,
                                               @RequestParam Integer categoryId,
                                               @RequestParam String details) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request


        //in die parameter vom addnewproduct @RequestBody String requestBody


        Product n = new Product();
        n.setName(name);
        n.setPrice(price);
        n.setCategoryId(categoryId);
        n.setDetails(details);
        productRepository.save(n);
        return "Saved";
    }

    //Delete Product
    @DeleteMapping("/Product/{id}")
    public @ResponseBody String deleteProduct (@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "Deleted";
    }




}