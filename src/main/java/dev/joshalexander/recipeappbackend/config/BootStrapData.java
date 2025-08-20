package dev.joshalexander.recipeappbackend.config;

import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.entity.Recipe;
import dev.joshalexander.recipeappbackend.entity.User;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.repository.RecipeRepository;
import dev.joshalexander.recipeappbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile({"dev", "test"}) // Add this annotation
public class BootStrapData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create test user
        var user = new User();
        user.setTodoistUserId("bootstrap123");
        user.setTodoistAccessToken("fake_token_abc123");
        user.setName("Joshua Alexander");
        user.setEmail("joshuaalexandertb@gmail.com");
        User savedUser = userRepository.save(user);

        // Create test recipe
        Recipe recipe = new Recipe();
        recipe.setName("One Pan Egg Sandwich");
        recipe.setDescription("Breakfast sandwich made in one pan by Kenji López-Alt");
        recipe.setRecipeUrl("https://www.youtube.com/watch?v=zIfxBh0RF30&t=33s&ab_channel=J.KenjiL%C3%B3pez-Alt");
        recipe.setUser(savedUser);
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Create ingredients

        Ingredient bread = new Ingredient();
        bread.setName("English muffin or bread");
        bread.setQuantity("2");
        bread.setUnit("slices");
        bread.setOrderIndex(1);
        bread.setRecipe(savedRecipe);

        Ingredient egg = new Ingredient();
        egg.setName("large egg");
        egg.setQuantity("2");
        egg.setOrderIndex(2);
        egg.setRecipe(savedRecipe);

        Ingredient canadianBacon =  new Ingredient();
        canadianBacon.setName("canadian bacon");
        canadianBacon.setQuantity("2");
        canadianBacon.setUnit("slices");
        canadianBacon.setOrderIndex(3);
        canadianBacon.setRecipe(savedRecipe);

        Ingredient cheddarCheese = new Ingredient();
        cheddarCheese.setName("cheddar cheese");
        cheddarCheese.setQuantity("3");
        cheddarCheese.setUnit("oz");
        cheddarCheese.setOrderIndex(4);
        cheddarCheese.setRecipe(savedRecipe);

        Ingredient butter = new Ingredient();
        butter.setName("butter");
        butter.setQuantity("1");
        butter.setUnit("tbsp");
        butter.setOrderIndex(5);
        butter.setRecipe(savedRecipe);

        Ingredient jalapeno = new Ingredient();
        jalapeno.setName("jalapeño");
        jalapeno.setQuantity("1");
        jalapeno.setOrderIndex(6);
        jalapeno.setRecipe(savedRecipe);

        Ingredient greenOnion = new Ingredient();
        greenOnion.setName("green onion");
        greenOnion.setQuantity("2");
        greenOnion.setUnit("stalks");
        greenOnion.setOrderIndex(7);
        greenOnion.setRecipe(savedRecipe);

        // Instead of saving ingredients individually
        List<Ingredient> ingredients = Arrays.asList(egg, jalapeno, greenOnion, butter, canadianBacon, cheddarCheese);
        ingredientRepository.saveAll(ingredients);

    }


}
