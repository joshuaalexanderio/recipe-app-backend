package dev.joshalexander.recipeappbackend.config;

import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.RecipeIngredient;
import dev.joshalexander.recipeappbackend.entity.User;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.repository.RecipeIngredientRepository;
import dev.joshalexander.recipeappbackend.repository.RecipeRepository;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile({"dev", "test"})
public class BootStrapData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create test user
            User user = new User();
            user.setTodoistUserId("bootstrap123");
            user.setTodoistAccessToken("fake_token_abc123");
            user.setName("Joshua Alexander");
            user.setEmail("joshuaalexandertb@gmail.com");
            User savedUser = userRepository.save(user);

            // Create master ingredient catalog in batch
            List<Ingredient> masterIngredients = createMasterIngredients();
            ingredientRepository.saveAll(masterIngredients);

            // Create sample recipe with RecipeIngredients
            createSampleRecipe(savedUser);
        }
    }

    private List<Ingredient> createMasterIngredients() {
        String[][] ingredientData = {
        {"salt", "tsp"},
        {"black pepper", "tsp"},
        {"olive oil", "tbsp"},
        {"garlic", "clove"},
        {"onion", "medium"},
        {"butter", "tbsp"},
        {"flour", "cup"},
        {"sugar", "cup"},
        {"egg", "large"},
        {"milk", "cup"},
        {"water", "cup"},
        {"vegetable oil", "tbsp"},
        {"chicken breast", "lb"},
        {"tomato", "medium"},
        {"lemon juice", "tbsp"},
        {"garlic powder", "tsp"},
        {"paprika", "tsp"},
        {"cumin", "tsp"},
        {"oregano", "tsp"},
        {"basil", "tsp"},
        {"parsley", "tbsp"},
        {"thyme", "tsp"},
        {"rosemary", "tsp"},
        {"bay leaf", "whole"},
        {"red pepper flakes", "tsp"},
        {"cayenne pepper", "tsp"},
        {"chili powder", "tsp"},
        {"cinnamon", "tsp"},
        {"nutmeg", "tsp"},
        {"ginger", "tsp"},
        {"vanilla extract", "tsp"},
        {"baking powder", "tsp"},
        {"baking soda", "tsp"},
        {"brown sugar", "cup"},
        {"honey", "tbsp"},
        {"soy sauce", "tbsp"},
        {"worcestershire sauce", "tbsp"},
        {"vinegar", "tbsp"},
        {"chicken broth", "cup"},
        {"beef broth", "cup"},
        {"tomato paste", "tbsp"},
        {"tomato sauce", "cup"},
        {"heavy cream", "cup"},
        {"sour cream", "cup"},
        {"cream cheese", "oz"},
        {"parmesan cheese", "cup"},
        {"cheddar cheese", "cup"},
        {"mozzarella cheese", "cup"},
        {"rice", "cup"},
        {"pasta", "oz"},
        {"bread crumbs", "cup"},
        {"cornstarch", "tbsp"},
        {"all-purpose flour", "cup"},
        {"carrot", "medium"},
        {"celery", "stalk"},
        {"bell pepper", "medium"},
        {"potato", "medium"},
        {"mushroom", "cup"},
        {"spinach", "cup"},
        {"broccoli", "cup"},
        {"green beans", "cup"},
        {"corn", "cup"},
        {"peas", "cup"},
        {"lemon", "whole"},
        {"lime", "whole"},
        {"cilantro", "tbsp"},
        {"green onion", "stalk"},
        {"shallot", "medium"},
        {"jalapeno", "whole"},
        {"ginger root", "tbsp"},
        {"bacon", "slice"},
        {"ground beef", "lb"},
        {"chicken thigh", "lb"},
        {"pork chop", "lb"},
        {"shrimp", "lb"},
        {"salmon", "lb"},
        {"white wine", "cup"},
        {"red wine", "cup"},
        {"dijon mustard", "tbsp"},
        {"mayonnaise", "tbsp"},
        {"ketchup", "tbsp"},
        {"hot sauce", "tsp"},
        {"sesame oil", "tsp"},
        {"peanut butter", "tbsp"},
        {"coconut milk", "cup"},
        {"maple syrup", "tbsp"},
        {"powdered sugar", "cup"},
        {"cocoa powder", "tbsp"},
        {"chocolate chips", "cup"},
        {"rolled oats", "cup"},
        {"breadcrumbs", "cup"},
        {"panko", "cup"},
        {"crackers", "oz"},
        {"nuts", "cup"},
        {"raisins", "cup"},
        {"coconut", "cup"},
        {"yeast", "tsp"},
        {"cornmeal", "cup"}
        };

        return Arrays.stream(ingredientData)
                .map(data -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(data[0]);
                    ingredient.setDefaultUnit(data[1]);
                    return ingredient;
                })
                .toList();
    }

    private void createSampleRecipe(User user) {
        Recipe recipe = new Recipe();
        recipe.setName("One Pan Egg Sandwich");
        recipe.setDescription("Breakfast sandwich made in one pan by Kenji López-Alt");
        recipe.setRecipeUrl("https://www.youtube.com/watch?v=zIfxBh0RF30");
        recipe.setUser(user);

        // Get ingredients from database
        Ingredient egg = ingredientRepository.findByNameIgnoreCase("egg").orElseThrow();
        Ingredient canadianBacon = ingredientRepository.findByNameIgnoreCase("canadian bacon").orElseThrow();
        Ingredient greenOnion = ingredientRepository.findByNameIgnoreCase("green onion").orElseThrow();
        Ingredient butter = ingredientRepository.findByNameIgnoreCase("butter").orElseThrow();
        Ingredient englishMuffin = ingredientRepository.findByNameIgnoreCase("english muffin").orElseThrow();
        Ingredient cheddarCheese = ingredientRepository.findByNameIgnoreCase("cheddar cheese").orElseThrow();
        Ingredient jalapeno = ingredientRepository.findByNameIgnoreCase("jalapeno").orElseThrow();

        // Create RecipeIngredients
        RecipeIngredient ri1 = new RecipeIngredient();
        ri1.setName("egg");
        ri1.setQuantity("2");
        ri1.setUnit("large");
        ri1.setOrderIndex(1);
        ri1.setIngredient(egg);
        ri1.setRecipe(recipe);

        RecipeIngredient ri2 = new RecipeIngredient();
        ri2.setName("canadian bacon");
        ri2.setQuantity("2");
        ri2.setUnit("slices");
        ri2.setOrderIndex(2);
        ri2.setIngredient(canadianBacon);
        ri2.setRecipe(recipe);

        RecipeIngredient ri3 = new RecipeIngredient();
        ri3.setName("green onion");
        ri3.setQuantity("1/2");
        ri3.setUnit("stalk");
        ri3.setOrderIndex(3);
        ri3.setIngredient(greenOnion);
        ri3.setRecipe(recipe);

        RecipeIngredient ri4 = new RecipeIngredient();
        ri4.setName("butter");
        ri4.setQuantity("1");
        ri4.setUnit("tbsp");
        ri4.setOrderIndex(4);
        ri4.setIngredient(butter);
        ri4.setRecipe(recipe);

        RecipeIngredient ri5 = new RecipeIngredient();
        ri5.setName("english muffin");
        ri5.setQuantity("1");
        ri5.setUnit("whole");
        ri5.setOrderIndex(5);
        ri5.setIngredient(englishMuffin);
        ri5.setRecipe(recipe);

        RecipeIngredient ri6 = new RecipeIngredient();
        ri6.setName("cheddar cheese");
        ri6.setQuantity("1");
        ri6.setUnit("oz");
        ri6.setOrderIndex(6);
        ri6.setIngredient(cheddarCheese);
        ri6.setRecipe(recipe);

        RecipeIngredient ri7 = new RecipeIngredient();
        ri7.setName("jalapeno");
        ri7.setQuantity("1");
        ri7.setUnit("whole");
        ri7.setOrderIndex(7);
        ri7.setIngredient(jalapeno);
        ri7.setRecipe(recipe);

        recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6, ri7));
        recipeRepository.save(recipe);
    }
}