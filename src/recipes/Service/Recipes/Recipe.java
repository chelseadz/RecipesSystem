package recipes.Service.Recipes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import recipes.Service.Users.AppUser;
import recipes.Service.Users.AppUserAdapter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @ElementCollection
    @NotEmpty(message = "Ingredients must have at least one item")
    private List<String> ingredients;

    @ElementCollection
    @NotEmpty(message = "Directions must have at least one item")
    private List<String> directions;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser author;

    public @NotBlank(message = "Category cannot be blank") String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank(message = "Category cannot be blank") String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.date = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Description cannot be blank") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description cannot be blank") String description) {
        this.description = description;
    }

    public @NotEmpty(message = "Ingredients must have at least one item") List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NotEmpty(message = "Ingredients must have at least one item") List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public @NotEmpty(message = "Directions must have at least one item") List<String> getDirections() {
        return directions;
    }

    public void setDirections(@NotEmpty(message = "Directions must have at least one item") List<String> directions) {
        this.directions = directions;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }
}
