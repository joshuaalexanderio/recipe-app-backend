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
            oliveOil.setQuantity("1");
            oliveOil.setDefaultUnit("tbsp");
            oliveOil.setOrderIndex(1);
            oliveOil.setRecipe(savedRecipe);

            Ingredient allPurposeFlour = new Ingredient();
            allPurposeFlour.setName("all purpose flour");
            allPurposeFlour.setQuantity("1");
            allPurposeFlour.setDefaultUnit("cup");
            allPurposeFlour.setOrderIndex(1);
            allPurposeFlour.setRecipe(savedRecipe);

            Ingredient butter = new Ingredient();
            butter.setName("butter");
            butter.setQuantity("1");
            butter.setDefaultUnit("tbsp");
            butter.setOrderIndex(1);
            butter.setRecipe(savedRecipe);

            Ingredient chicken = new Ingredient();
            chicken.setName("chicken");
            chicken.setQuantity("1");
            chicken.setDefaultUnit("lb");
            chicken.setOrderIndex(1);
            chicken.setRecipe(savedRecipe);

            Ingredient sugar = new Ingredient();
            sugar.setName("sugar");
            sugar.setQuantity("1");
            sugar.setDefaultUnit("cup");
            sugar.setOrderIndex(1);
            sugar.setRecipe(savedRecipe);

            Ingredient salt = new Ingredient();
            salt.setName("salt");
            salt.setQuantity("1");
            salt.setDefaultUnit("tsp");
            salt.setOrderIndex(1);
            salt.setRecipe(savedRecipe);

            Ingredient egg = new Ingredient();
            egg.setName("egg");
            egg.setQuantity("1");
            egg.setDefaultUnit("large");
            egg.setOrderIndex(1);
            egg.setRecipe(savedRecipe);

            Ingredient rice = new Ingredient();
            rice.setName("rice");
            rice.setQuantity("1");
            rice.setDefaultUnit("cup");
            rice.setOrderIndex(1);
            rice.setRecipe(savedRecipe);

            Ingredient vegetableOil = new Ingredient();
            vegetableOil.setName("vegetable oil");
            vegetableOil.setQuantity("1");
            vegetableOil.setDefaultUnit("tbsp");
            vegetableOil.setOrderIndex(1);
            vegetableOil.setRecipe(savedRecipe);

            Ingredient pork = new Ingredient();
            pork.setName("pork");
            pork.setQuantity("1");
            pork.setDefaultUnit("lb");
            pork.setOrderIndex(1);
            pork.setRecipe(savedRecipe);

            Ingredient beef = new Ingredient();
            beef.setName("beef");
            beef.setQuantity("1");
            beef.setDefaultUnit("lb");
            beef.setOrderIndex(1);
            beef.setRecipe(savedRecipe);

            Ingredient cheese = new Ingredient();
            cheese.setName("cheese");
            cheese.setQuantity("1");
            cheese.setDefaultUnit("cup");
            cheese.setOrderIndex(1);
            cheese.setRecipe(savedRecipe);

            Ingredient garlic = new Ingredient();
            garlic.setName("garlic");
            garlic.setQuantity("1");
            garlic.setDefaultUnit("clove");
            garlic.setOrderIndex(1);
            garlic.setRecipe(savedRecipe);

            Ingredient onion = new Ingredient();
            onion.setName("onion");
            onion.setQuantity("1");
            onion.setDefaultUnit("medium");
            onion.setOrderIndex(1);
            onion.setRecipe(savedRecipe);

            Ingredient wholeMilk = new Ingredient();
            wholeMilk.setName("whole milk");
            wholeMilk.setQuantity("1");
            wholeMilk.setDefaultUnit("cup");
            wholeMilk.setOrderIndex(1);
            wholeMilk.setRecipe(savedRecipe);

            Ingredient blackPepper = new Ingredient();
            blackPepper.setName("black pepper");
            blackPepper.setQuantity("1");
            blackPepper.setDefaultUnit("tsp");
            blackPepper.setOrderIndex(1);
            blackPepper.setRecipe(savedRecipe);

            Ingredient tomatoes = new Ingredient();
            tomatoes.setName("tomatoes");
            tomatoes.setQuantity("1");
            tomatoes.setDefaultUnit("can");
            tomatoes.setOrderIndex(1);
            tomatoes.setRecipe(savedRecipe);

            Ingredient lemonJuice = new Ingredient();
            lemonJuice.setName("lemon juice");
            lemonJuice.setQuantity("1");
            lemonJuice.setDefaultUnit("tbsp");
            lemonJuice.setOrderIndex(1);
            lemonJuice.setRecipe(savedRecipe);

            Ingredient vanillaExtract = new Ingredient();
            vanillaExtract.setName("vanilla extract");
            vanillaExtract.setQuantity("1");
            vanillaExtract.setDefaultUnit("tsp");
            vanillaExtract.setOrderIndex(1);
            vanillaExtract.setRecipe(savedRecipe);

            Ingredient bakingPowder = new Ingredient();
            bakingPowder.setName("baking powder");
            bakingPowder.setQuantity("1");
            bakingPowder.setDefaultUnit("tsp");
            bakingPowder.setOrderIndex(1);
            bakingPowder.setRecipe(savedRecipe);

            Ingredient pasta = new Ingredient();
            pasta.setName("pasta");
            pasta.setQuantity("1");
            pasta.setDefaultUnit("lb");
            pasta.setOrderIndex(1);
            pasta.setRecipe(savedRecipe);

            Ingredient potatoes = new Ingredient();
            potatoes.setName("potatoes");
            potatoes.setQuantity("1");
            potatoes.setDefaultUnit("lb");
            potatoes.setOrderIndex(1);
            potatoes.setRecipe(savedRecipe);

            Ingredient carrots = new Ingredient();
            carrots.setName("carrots");
            carrots.setQuantity("1");
            carrots.setDefaultUnit("medium");
            carrots.setOrderIndex(1);
            carrots.setRecipe(savedRecipe);

            Ingredient celery = new Ingredient();
            celery.setName("celery");
            celery.setQuantity("1");
            celery.setDefaultUnit("stalk");
            celery.setOrderIndex(1);
            celery.setRecipe(savedRecipe);

            Ingredient bellPeppers = new Ingredient();
            bellPeppers.setName("bell peppers");
            bellPeppers.setQuantity("1");
            bellPeppers.setDefaultUnit("medium");
            bellPeppers.setOrderIndex(1);
            bellPeppers.setRecipe(savedRecipe);

            Ingredient mushrooms = new Ingredient();
            mushrooms.setName("mushrooms");
            mushrooms.setQuantity("1");
            mushrooms.setDefaultUnit("cup");
            mushrooms.setOrderIndex(1);
            mushrooms.setRecipe(savedRecipe);

            Ingredient bacon = new Ingredient();
            bacon.setName("bacon");
            bacon.setQuantity("1");
            bacon.setDefaultUnit("slice");
            bacon.setOrderIndex(1);
            bacon.setRecipe(savedRecipe);

            Ingredient heavyCream = new Ingredient();
            heavyCream.setName("heavy cream");
            heavyCream.setQuantity("1");
            heavyCream.setDefaultUnit("cup");
            heavyCream.setOrderIndex(1);
            heavyCream.setRecipe(savedRecipe);

            Ingredient chickenStock = new Ingredient();
            chickenStock.setName("chicken stock");
            chickenStock.setQuantity("1");
            chickenStock.setDefaultUnit("cup");
            chickenStock.setOrderIndex(1);
            chickenStock.setRecipe(savedRecipe);

            Ingredient groundBeef = new Ingredient();
            groundBeef.setName("ground beef");
            groundBeef.setQuantity("1");
            groundBeef.setDefaultUnit("lb");
            groundBeef.setOrderIndex(1);
            groundBeef.setRecipe(savedRecipe);

            Ingredient paprika = new Ingredient();
            paprika.setName("paprika");
            paprika.setQuantity("1");
            paprika.setDefaultUnit("tsp");
            paprika.setOrderIndex(1);
            paprika.setRecipe(savedRecipe);

            Ingredient cumin = new Ingredient();
            cumin.setName("cumin");
            cumin.setQuantity("1");
            cumin.setDefaultUnit("tsp");
            cumin.setOrderIndex(1);
            cumin.setRecipe(savedRecipe);

            Ingredient oregano = new Ingredient();
            oregano.setName("oregano");
            oregano.setQuantity("1");
            oregano.setDefaultUnit("tsp");
            oregano.setOrderIndex(1);
            oregano.setRecipe(savedRecipe);

            Ingredient basil = new Ingredient();
            basil.setName("basil");
            basil.setQuantity("1");
            basil.setDefaultUnit("tsp");
            basil.setOrderIndex(1);
            basil.setRecipe(savedRecipe);

            Ingredient thyme = new Ingredient();
            thyme.setName("thyme");
            thyme.setQuantity("1");
            thyme.setDefaultUnit("tsp");
            thyme.setOrderIndex(1);
            thyme.setRecipe(savedRecipe);

            Ingredient parsley = new Ingredient();
            parsley.setName("parsley");
            parsley.setQuantity("1");
            parsley.setDefaultUnit("tbsp");
            parsley.setOrderIndex(1);
            parsley.setRecipe(savedRecipe);

            Ingredient cinnamon = new Ingredient();
            cinnamon.setName("cinnamon");
            cinnamon.setQuantity("1");
            cinnamon.setDefaultUnit("tsp");
            cinnamon.setOrderIndex(1);
            cinnamon.setRecipe(savedRecipe);

            Ingredient ginger = new Ingredient();
            ginger.setName("ginger");
            ginger.setQuantity("1");
            ginger.setDefaultUnit("tsp");
            ginger.setOrderIndex(1);
            ginger.setRecipe(savedRecipe);

            Ingredient redPepperFlakes = new Ingredient();
            redPepperFlakes.setName("red pepper flakes");
            redPepperFlakes.setQuantity("1");
            redPepperFlakes.setDefaultUnit("tsp");
            redPepperFlakes.setOrderIndex(1);
            redPepperFlakes.setRecipe(savedRecipe);

            Ingredient lemon = new Ingredient();
            lemon.setName("lemon");
            lemon.setQuantity("1");
            lemon.setDefaultUnit("whole");
            lemon.setOrderIndex(1);
            lemon.setRecipe(savedRecipe);

            Ingredient spinach = new Ingredient();
            spinach.setName("spinach");
            spinach.setQuantity("1");
            spinach.setDefaultUnit("cup");
            spinach.setOrderIndex(1);
            spinach.setRecipe(savedRecipe);

            Ingredient broccoli = new Ingredient();
            broccoli.setName("broccoli");
            broccoli.setQuantity("1");
            broccoli.setDefaultUnit("head");
            broccoli.setOrderIndex(1);
            broccoli.setRecipe(savedRecipe);

            Ingredient groundTurkey = new Ingredient();
            groundTurkey.setName("ground turkey");
            groundTurkey.setQuantity("1");
            groundTurkey.setDefaultUnit("lb");
            groundTurkey.setOrderIndex(1);
            groundTurkey.setRecipe(savedRecipe);

            Ingredient sourCream = new Ingredient();
            sourCream.setName("sour cream");
            sourCream.setQuantity("1");
            sourCream.setDefaultUnit("cup");
            sourCream.setOrderIndex(1);
            sourCream.setRecipe(savedRecipe);

            Ingredient mayonnaise = new Ingredient();
            mayonnaise.setName("mayonnaise");
            mayonnaise.setQuantity("1");
            mayonnaise.setDefaultUnit("tbsp");
            mayonnaise.setOrderIndex(1);
            mayonnaise.setRecipe(savedRecipe);

            Ingredient shrimp = new Ingredient();
            shrimp.setName("shrimp");
            shrimp.setQuantity("1");
            shrimp.setDefaultUnit("lb");
            shrimp.setOrderIndex(1);
            shrimp.setRecipe(savedRecipe);

            Ingredient salmon = new Ingredient();
            salmon.setName("salmon");
            salmon.setQuantity("1");
            salmon.setDefaultUnit("fillet");
            salmon.setOrderIndex(1);
            salmon.setRecipe(savedRecipe);

            Ingredient cheddarCheese = new Ingredient();
            cheddarCheese.setName("cheddar cheese");
            cheddarCheese.setQuantity("1");
            cheddarCheese.setDefaultUnit("cup");
            cheddarCheese.setOrderIndex(1);
            cheddarCheese.setRecipe(savedRecipe);

            Ingredient parmesanCheese = new Ingredient();
            parmesanCheese.setName("parmesan cheese");
            parmesanCheese.setQuantity("1");
            parmesanCheese.setDefaultUnit("cup");
            parmesanCheese.setOrderIndex(1);
            parmesanCheese.setRecipe(savedRecipe);

            Ingredient breadCrumbs = new Ingredient();
            breadCrumbs.setName("bread crumbs");
            breadCrumbs.setQuantity("1");
            breadCrumbs.setDefaultUnit("cup");
            breadCrumbs.setOrderIndex(1);
            breadCrumbs.setRecipe(savedRecipe);

            Ingredient brownSugar = new Ingredient();
            brownSugar.setName("brown sugar");
            brownSugar.setQuantity("1");
            brownSugar.setDefaultUnit("cup");
            brownSugar.setOrderIndex(1);
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
            // Instead of saving ingredients individually
            ingredientRepository.saveAll(ingredients);

        }

    }
}
