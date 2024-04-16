package hska.iwi.categories;

import org.springframework.data.repository.CrudRepository;

import hska.iwi.categories.Category;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}