package recipes.Models;

import recipes.Service.Recipes.Recipe;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeModel {
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
    private String category;
    private LocalDateTime date;

    public RecipeModel(Recipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients();
        this.directions = recipe.getDirections();
        this.category = recipe.getCategory();
        this.date = recipe.getDate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
