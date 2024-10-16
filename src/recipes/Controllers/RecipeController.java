package recipes.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.Models.RecipeModel;
import recipes.Service.Recipes.Recipe;
import recipes.Service.Recipes.RecipeService;
import recipes.Service.Users.AppUser;
import recipes.Service.Users.AppUserAdapter;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@RequestMapping("/api/recipe")
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> createRecipe(
            @AuthenticationPrincipal AppUserAdapter auth,
            @Valid @RequestBody Recipe recipe
    ) {

        recipe.setAuthor(auth.getUser());

        Recipe savedRecipe = recipeService.addRecipe(recipe);

        return ResponseEntity.ok(Map.of("id", ((Recipe) savedRecipe).getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeModel> getRecipeById(
            @AuthenticationPrincipal AppUserAdapter auth,
            @PathVariable int id
    ) {
        RecipeModel recipe = recipeService.getRecipeById(id);

        if (recipe == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(
            @AuthenticationPrincipal AppUserAdapter auth,
            @PathVariable Integer id
    ) {
        try {
            Recipe existingRecipe = recipeService.findRecipeById(id);

            if (!existingRecipe.getAuthor().getEmail().equals(auth.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Deletion Forbidden"));
            }else {
                recipeService.deleteRecipe(id);
                return ResponseEntity.noContent().build();
            }

        }catch (ResponseStatusException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", Objects.requireNonNull(e.getReason())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(
            @AuthenticationPrincipal AppUserAdapter auth,
            @PathVariable Integer id,
            @Valid @RequestBody Recipe updatedRecipe
    ) {
        try {
            Recipe existingRecipe = recipeService.findRecipeById(id);

            if (!existingRecipe.getAuthor().getEmail().equals(auth.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Update Forbidden"));
            }else {
                recipeService.updateRecipe(id, updatedRecipe);
                return ResponseEntity.noContent().build();
            }

        }catch (ResponseStatusException e){
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("error", Objects.requireNonNull(e.getReason())));
        }
    }

    @GetMapping("/search/")
    public ResponseEntity<List<RecipeModel>> searchRecipes(
            @AuthenticationPrincipal AppUserAdapter auth,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            return ResponseEntity.badRequest().build();
        }

        List<RecipeModel> recipes;
        if (category != null) {
            recipes = recipeService.searchByCategory(category);
        } else {
            recipes = recipeService.searchByName(name);
        }

        return ResponseEntity.ok(recipes);
    }
}

