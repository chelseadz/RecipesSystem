package recipes.Service.Recipes;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.Models.RecipeModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public RecipeModel getRecipeById(Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return new RecipeModel(recipe.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    public Recipe findRecipeById(Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        recipeRepository.deleteById(id);
    }

    public void updateRecipe(Integer id, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setCategory(updatedRecipe.getCategory());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setIngredients(updatedRecipe.getIngredients());
        existingRecipe.setDirections(updatedRecipe.getDirections());

        recipeRepository.save(existingRecipe);
    }

    public List<RecipeModel> searchByCategory(String category) {
        List<Recipe> recipes = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipes.stream()
                .map(recipe -> new RecipeModel(recipe))
                .collect(Collectors.toList());
    }

    public List<RecipeModel> searchByName(String name) {
        List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        return recipes.stream()
                .map(recipe -> new RecipeModel(recipe))
                .collect(Collectors.toList());
    }
}