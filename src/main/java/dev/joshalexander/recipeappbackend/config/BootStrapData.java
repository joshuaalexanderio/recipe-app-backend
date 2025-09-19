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
@Profile({"dev", "test"})
public class BootStrapData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
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

            Ingredient oliveOil = new Ingredient();
            oliveOil.setName("olive oil");
            oliveOil.setDefaultUnit("tbsp");
            oliveOil.setRecipe(savedRecipe);

            Ingredient allPurposeFlour = new Ingredient();
            allPurposeFlour.setName("all purpose flour");
            allPurposeFlour.setDefaultUnit("cup");
            allPurposeFlour.setRecipe(savedRecipe);

            Ingredient butter = new Ingredient();
            butter.setName("butter");
            butter.setDefaultUnit("tbsp");
            butter.setRecipe(savedRecipe);

            Ingredient chicken = new Ingredient();
            chicken.setName("chicken");
            chicken.setDefaultUnit("lb");
            chicken.setRecipe(savedRecipe);

            Ingredient sugar = new Ingredient();
            sugar.setName("sugar");
            sugar.setDefaultUnit("cup");
            sugar.setRecipe(savedRecipe);

            Ingredient salt = new Ingredient();
            salt.setName("salt");
            salt.setDefaultUnit("tsp");
            salt.setRecipe(savedRecipe);

            Ingredient egg = new Ingredient();
            egg.setName("egg");
            egg.setDefaultUnit("large");
            egg.setRecipe(savedRecipe);

            Ingredient rice = new Ingredient();
            rice.setName("rice");
            rice.setDefaultUnit("cup");
            rice.setRecipe(savedRecipe);

            Ingredient vegetableOil = new Ingredient();
            vegetableOil.setName("vegetable oil");
            vegetableOil.setDefaultUnit("tbsp");
            vegetableOil.setRecipe(savedRecipe);

            Ingredient pork = new Ingredient();
            pork.setName("pork");
            pork.setDefaultUnit("lb");
            pork.setRecipe(savedRecipe);

            Ingredient beef = new Ingredient();
            beef.setName("beef");
            beef.setDefaultUnit("lb");
            beef.setRecipe(savedRecipe);

            Ingredient cheese = new Ingredient();
            cheese.setName("cheese");
            cheese.setDefaultUnit("cup");
            cheese.setRecipe(savedRecipe);

            Ingredient garlic = new Ingredient();
            garlic.setName("garlic");
            garlic.setDefaultUnit("clove");
            garlic.setRecipe(savedRecipe);

            Ingredient onion = new Ingredient();
            onion.setName("onion");
            onion.setDefaultUnit("medium");
            onion.setRecipe(savedRecipe);

            Ingredient wholeMilk = new Ingredient();
            wholeMilk.setName("whole milk");
            wholeMilk.setDefaultUnit("cup");
            wholeMilk.setRecipe(savedRecipe);

            Ingredient blackPepper = new Ingredient();
            blackPepper.setName("black pepper");
            blackPepper.setDefaultUnit("tsp");
            blackPepper.setRecipe(savedRecipe);

            Ingredient tomatoes = new Ingredient();
            tomatoes.setName("tomatoes");
            tomatoes.setDefaultUnit("can");
            tomatoes.setRecipe(savedRecipe);

            Ingredient lemonJuice = new Ingredient();
            lemonJuice.setName("lemon juice");
            lemonJuice.setDefaultUnit("tbsp");
            lemonJuice.setRecipe(savedRecipe);

            Ingredient vanillaExtract = new Ingredient();
            vanillaExtract.setName("vanilla extract");
            vanillaExtract.setDefaultUnit("tsp");
            vanillaExtract.setRecipe(savedRecipe);

            Ingredient bakingPowder = new Ingredient();
            bakingPowder.setName("baking powder");
            bakingPowder.setDefaultUnit("tsp");
            bakingPowder.setRecipe(savedRecipe);

            Ingredient pasta = new Ingredient();
            pasta.setName("pasta");
            pasta.setDefaultUnit("lb");
            pasta.setRecipe(savedRecipe);

            Ingredient potatoes = new Ingredient();
            potatoes.setName("potatoes");
            potatoes.setDefaultUnit("lb");
            potatoes.setRecipe(savedRecipe);

            Ingredient carrots = new Ingredient();
            carrots.setName("carrots");
            carrots.setDefaultUnit("medium");
            carrots.setRecipe(savedRecipe);

            Ingredient celery = new Ingredient();
            celery.setName("celery");
            celery.setDefaultUnit("stalk");
            celery.setRecipe(savedRecipe);

            Ingredient bellPeppers = new Ingredient();
            bellPeppers.setName("bell peppers");
            bellPeppers.setDefaultUnit("medium");
            bellPeppers.setRecipe(savedRecipe);

            Ingredient mushrooms = new Ingredient();
            mushrooms.setName("mushrooms");
            mushrooms.setDefaultUnit("cup");
            mushrooms.setRecipe(savedRecipe);

            Ingredient bacon = new Ingredient();
            bacon.setName("bacon");
            bacon.setDefaultUnit("slice");
            bacon.setRecipe(savedRecipe);

            Ingredient heavyCream = new Ingredient();
            heavyCream.setName("heavy cream");
            heavyCream.setDefaultUnit("cup");
            heavyCream.setRecipe(savedRecipe);

            Ingredient chickenStock = new Ingredient();
            chickenStock.setName("chicken stock");
            chickenStock.setDefaultUnit("cup");
            chickenStock.setRecipe(savedRecipe);

            Ingredient groundBeef = new Ingredient();
            groundBeef.setName("ground beef");
            groundBeef.setDefaultUnit("lb");
            groundBeef.setRecipe(savedRecipe);

            Ingredient paprika = new Ingredient();
            paprika.setName("paprika");
            paprika.setDefaultUnit("tsp");
            paprika.setRecipe(savedRecipe);

            Ingredient cumin = new Ingredient();
            cumin.setName("cumin");
            cumin.setDefaultUnit("tsp");
            cumin.setRecipe(savedRecipe);

            Ingredient oregano = new Ingredient();
            oregano.setName("oregano");
            oregano.setDefaultUnit("tsp");
            oregano.setRecipe(savedRecipe);

            Ingredient basil = new Ingredient();
            basil.setName("basil");
            basil.setDefaultUnit("tsp");
            basil.setRecipe(savedRecipe);

            Ingredient thyme = new Ingredient();
            thyme.setName("thyme");
            thyme.setDefaultUnit("tsp");
            thyme.setRecipe(savedRecipe);

            Ingredient parsley = new Ingredient();
            parsley.setName("parsley");
            parsley.setDefaultUnit("tbsp");
            parsley.setRecipe(savedRecipe);

            Ingredient cinnamon = new Ingredient();
            cinnamon.setName("cinnamon");
            cinnamon.setDefaultUnit("tsp");
            cinnamon.setRecipe(savedRecipe);

            Ingredient ginger = new Ingredient();
            ginger.setName("ginger");
            ginger.setDefaultUnit("tsp");
            ginger.setRecipe(savedRecipe);

            Ingredient redPepperFlakes = new Ingredient();
            redPepperFlakes.setName("red pepper flakes");
            redPepperFlakes.setDefaultUnit("tsp");
            redPepperFlakes.setRecipe(savedRecipe);

            Ingredient lemon = new Ingredient();
            lemon.setName("lemon");
            lemon.setDefaultUnit("whole");
            lemon.setRecipe(savedRecipe);

            Ingredient spinach = new Ingredient();
            spinach.setName("spinach");
            spinach.setDefaultUnit("cup");
            spinach.setRecipe(savedRecipe);

            Ingredient broccoli = new Ingredient();
            broccoli.setName("broccoli");
            broccoli.setDefaultUnit("head");
            broccoli.setRecipe(savedRecipe);

            Ingredient groundTurkey = new Ingredient();
            groundTurkey.setName("ground turkey");
            groundTurkey.setDefaultUnit("lb");
            groundTurkey.setRecipe(savedRecipe);

            Ingredient sourCream = new Ingredient();
            sourCream.setName("sour cream");
            sourCream.setDefaultUnit("cup");
            sourCream.setRecipe(savedRecipe);

            Ingredient mayonnaise = new Ingredient();
            mayonnaise.setName("mayonnaise");
            mayonnaise.setDefaultUnit("tbsp");
            mayonnaise.setRecipe(savedRecipe);

            Ingredient shrimp = new Ingredient();
            shrimp.setName("shrimp");
            shrimp.setDefaultUnit("lb");
            shrimp.setRecipe(savedRecipe);

            Ingredient salmon = new Ingredient();
            salmon.setName("salmon");
            salmon.setDefaultUnit("fillet");
            salmon.setRecipe(savedRecipe);

            Ingredient cheddarCheese = new Ingredient();
            cheddarCheese.setName("cheddar cheese");
            cheddarCheese.setDefaultUnit("cup");
            cheddarCheese.setRecipe(savedRecipe);

            Ingredient parmesanCheese = new Ingredient();
            parmesanCheese.setName("parmesan cheese");
            parmesanCheese.setDefaultUnit("cup");
            parmesanCheese.setRecipe(savedRecipe);

            Ingredient breadCrumbs = new Ingredient();
            breadCrumbs.setName("bread crumbs");
            breadCrumbs.setDefaultUnit("cup");
            breadCrumbs.setRecipe(savedRecipe);

            Ingredient brownSugar = new Ingredient();
            brownSugar.setName("brown sugar");
            brownSugar.setDefaultUnit("cup");
            brownSugar.setRecipe(savedRecipe);

            // Save all ingredients to the database
            List<Ingredient> ingredients = Arrays.asList(
                    oliveOil, allPurposeFlour, butter, chicken, sugar, salt, egg, rice,
                    vegetableOil, pork, beef, cheese, garlic, onion, wholeMilk, blackPepper,
                    tomatoes, lemonJuice, vanillaExtract, bakingPowder, pasta, potatoes,
                    carrots, celery, bellPeppers, mushrooms, bacon, heavyCream, chickenStock,
                    groundBeef, paprika, cumin, oregano, basil, thyme, parsley, cinnamon,
                    ginger, redPepperFlakes, lemon, spinach, broccoli, groundTurkey,
                    sourCream, mayonnaise, shrimp, salmon, cheddarCheese, parmesanCheese,
                    breadCrumbs, brownSugar
            );
            ingredientRepository.saveAll(ingredients);

        }

    }
}