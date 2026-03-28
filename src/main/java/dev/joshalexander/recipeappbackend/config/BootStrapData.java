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
import java.util.Optional;

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
    }
    if (ingredientRepository.count() == 0) {
      // Create master ingredient catalog in batch
      List<Ingredient> masterIngredients = createMasterIngredients();
      ingredientRepository.saveAll(masterIngredients);
    }
    if (recipeRepository.count() == 0) {
      // Create sample recipe with RecipeIngredients
      Optional<User> user = userRepository.findById(1L);
      createSampleRecipe(user.orElse(null));
      createSampleRecipe2(user.orElse(null));
      createSampleRecipe3(user.orElse(null));
      createSampleRecipe4(user.orElse(null));
      createSampleRecipe5(user.orElse(null));
      createSampleRecipe6(user.orElse(null));
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
        {"cornmeal", "cup"},
        {"canadian bacon", "slice"},
        {"english muffin", "whole"},
        {"spaghetti", "oz"},
        {"guanciale", "oz"},
        {"tortilla", "whole"},
        {"linguine", "oz"},
        {"mirin", "tbsp"},
        {"sesame seeds", "tbsp"}
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
    Ingredient canadianBacon = ingredientRepository.findByNameIgnoreCase("canadian bacon")
        .orElseThrow();
    Ingredient greenOnion = ingredientRepository.findByNameIgnoreCase("green onion").orElseThrow();
    Ingredient butter = ingredientRepository.findByNameIgnoreCase("butter").orElseThrow();
    Ingredient englishMuffin = ingredientRepository.findByNameIgnoreCase("english muffin")
        .orElseThrow();
    Ingredient cheddarCheese = ingredientRepository.findByNameIgnoreCase("cheddar cheese")
        .orElseThrow();
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

  private void createSampleRecipe2(User user) {
    Recipe recipe = new Recipe();
    recipe.setName("Classic Spaghetti Carbonara");
    recipe.setDescription("Traditional Italian pasta with eggs, cheese, and guanciale");
    recipe.setRecipeUrl("https://www.bonappetit.com/recipe/simple-carbonara");
    recipe.setUser(user);

    Ingredient spaghetti = ingredientRepository.findByNameIgnoreCase("spaghetti").orElseThrow();
    Ingredient guanciale = ingredientRepository.findByNameIgnoreCase("guanciale").orElseThrow();
    Ingredient egg = ingredientRepository.findByNameIgnoreCase("egg").orElseThrow();
    Ingredient parmesanCheese = ingredientRepository.findByNameIgnoreCase("parmesan cheese")
        .orElseThrow();
    Ingredient blackPepper = ingredientRepository.findByNameIgnoreCase("black pepper")
        .orElseThrow();

    RecipeIngredient ri1 = new RecipeIngredient();
    ri1.setName("spaghetti");
    ri1.setQuantity("12");
    ri1.setUnit("oz");
    ri1.setOrderIndex(1);
    ri1.setIngredient(spaghetti);
    ri1.setRecipe(recipe);

    RecipeIngredient ri2 = new RecipeIngredient();
    ri2.setName("guanciale");
    ri2.setQuantity("4");
    ri2.setUnit("oz");
    ri2.setOrderIndex(2);
    ri2.setIngredient(guanciale);
    ri2.setRecipe(recipe);

    RecipeIngredient ri3 = new RecipeIngredient();
    ri3.setName("egg");
    ri3.setQuantity("4");
    ri3.setUnit("large");
    ri3.setOrderIndex(3);
    ri3.setIngredient(egg);
    ri3.setRecipe(recipe);

    RecipeIngredient ri4 = new RecipeIngredient();
    ri4.setName("parmesan cheese");
    ri4.setQuantity("1");
    ri4.setUnit("cup");
    ri4.setOrderIndex(4);
    ri4.setIngredient(parmesanCheese);
    ri4.setRecipe(recipe);

    RecipeIngredient ri5 = new RecipeIngredient();
    ri5.setName("black pepper");
    ri5.setQuantity("1");
    ri5.setUnit("tsp");
    ri5.setOrderIndex(5);
    ri5.setIngredient(blackPepper);
    ri5.setRecipe(recipe);

    recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5));
    recipeRepository.save(recipe);
  }

  private void createSampleRecipe3(User user) {
    Recipe recipe = new Recipe();
    recipe.setName("Sheet Pan Chicken Fajitas");
    recipe.setDescription("Easy one-pan Mexican-inspired chicken with peppers and onions");
    recipe.setRecipeUrl("https://www.allrecipes.com/recipe/257938/sheet-pan-chicken-fajitas");
    recipe.setUser(user);

    Ingredient chickenBreast = ingredientRepository.findByNameIgnoreCase("chicken breast")
        .orElseThrow();
    Ingredient bellPepper = ingredientRepository.findByNameIgnoreCase("bell pepper").orElseThrow();
    Ingredient onion = ingredientRepository.findByNameIgnoreCase("onion").orElseThrow();
    Ingredient oliveOil = ingredientRepository.findByNameIgnoreCase("olive oil").orElseThrow();
    Ingredient chiliPowder = ingredientRepository.findByNameIgnoreCase("chili powder")
        .orElseThrow();
    Ingredient cumin = ingredientRepository.findByNameIgnoreCase("cumin").orElseThrow();
    Ingredient lime = ingredientRepository.findByNameIgnoreCase("lime").orElseThrow();
    Ingredient tortilla = ingredientRepository.findByNameIgnoreCase("tortilla").orElseThrow();

    RecipeIngredient ri1 = new RecipeIngredient();
    ri1.setName("chicken breast");
    ri1.setQuantity("1.5");
    ri1.setUnit("lbs");
    ri1.setOrderIndex(1);
    ri1.setIngredient(chickenBreast);
    ri1.setRecipe(recipe);

    RecipeIngredient ri2 = new RecipeIngredient();
    ri2.setName("bell pepper");
    ri2.setQuantity("3");
    ri2.setUnit("whole");
    ri2.setOrderIndex(2);
    ri2.setIngredient(bellPepper);
    ri2.setRecipe(recipe);

    RecipeIngredient ri3 = new RecipeIngredient();
    ri3.setName("onion");
    ri3.setQuantity("1");
    ri3.setUnit("large");
    ri3.setOrderIndex(3);
    ri3.setIngredient(onion);
    ri3.setRecipe(recipe);

    RecipeIngredient ri4 = new RecipeIngredient();
    ri4.setName("olive oil");
    ri4.setQuantity("3");
    ri4.setUnit("tbsp");
    ri4.setOrderIndex(4);
    ri4.setIngredient(oliveOil);
    ri4.setRecipe(recipe);

    RecipeIngredient ri5 = new RecipeIngredient();
    ri5.setName("chili powder");
    ri5.setQuantity("2");
    ri5.setUnit("tsp");
    ri5.setOrderIndex(5);
    ri5.setIngredient(chiliPowder);
    ri5.setRecipe(recipe);

    RecipeIngredient ri6 = new RecipeIngredient();
    ri6.setName("cumin");
    ri6.setQuantity("1");
    ri6.setUnit("tsp");
    ri6.setOrderIndex(6);
    ri6.setIngredient(cumin);
    ri6.setRecipe(recipe);

    RecipeIngredient ri7 = new RecipeIngredient();
    ri7.setName("lime");
    ri7.setQuantity("2");
    ri7.setUnit("whole");
    ri7.setOrderIndex(7);
    ri7.setIngredient(lime);
    ri7.setRecipe(recipe);

    RecipeIngredient ri8 = new RecipeIngredient();
    ri8.setName("tortilla");
    ri8.setQuantity("8");
    ri8.setUnit("whole");
    ri8.setOrderIndex(8);
    ri8.setIngredient(tortilla);
    ri8.setRecipe(recipe);

    recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6, ri7, ri8));
    recipeRepository.save(recipe);
  }

  private void createSampleRecipe4(User user) {
    Recipe recipe = new Recipe();
    recipe.setName("Garlic Butter Shrimp Pasta");
    recipe.setDescription("Quick 15-minute pasta with garlic butter shrimp");
    recipe.setRecipeUrl("https://www.youtube.com/watch?v=qBcI-bLH5YU");
    recipe.setUser(user);

    Ingredient shrimp = ingredientRepository.findByNameIgnoreCase("shrimp").orElseThrow();
    Ingredient linguine = ingredientRepository.findByNameIgnoreCase("linguine").orElseThrow();
    Ingredient butter = ingredientRepository.findByNameIgnoreCase("butter").orElseThrow();
    Ingredient garlic = ingredientRepository.findByNameIgnoreCase("garlic").orElseThrow();
    Ingredient parsley = ingredientRepository.findByNameIgnoreCase("parsley").orElseThrow();
    Ingredient lemon = ingredientRepository.findByNameIgnoreCase("lemon").orElseThrow();
    Ingredient redPepperFlakes = ingredientRepository.findByNameIgnoreCase("red pepper flakes")
        .orElseThrow();

    RecipeIngredient ri1 = new RecipeIngredient();
    ri1.setName("shrimp");
    ri1.setQuantity("1");
    ri1.setUnit("lb");
    ri1.setOrderIndex(1);
    ri1.setIngredient(shrimp);
    ri1.setRecipe(recipe);

    RecipeIngredient ri2 = new RecipeIngredient();
    ri2.setName("linguine");
    ri2.setQuantity("12");
    ri2.setUnit("oz");
    ri2.setOrderIndex(2);
    ri2.setIngredient(linguine);
    ri2.setRecipe(recipe);

    RecipeIngredient ri3 = new RecipeIngredient();
    ri3.setName("butter");
    ri3.setQuantity("4");
    ri3.setUnit("tbsp");
    ri3.setOrderIndex(3);
    ri3.setIngredient(butter);
    ri3.setRecipe(recipe);

    RecipeIngredient ri4 = new RecipeIngredient();
    ri4.setName("garlic");
    ri4.setQuantity("6");
    ri4.setUnit("cloves");
    ri4.setOrderIndex(4);
    ri4.setIngredient(garlic);
    ri4.setRecipe(recipe);

    RecipeIngredient ri5 = new RecipeIngredient();
    ri5.setName("parsley");
    ri5.setQuantity("1/4");
    ri5.setUnit("cup");
    ri5.setOrderIndex(5);
    ri5.setIngredient(parsley);
    ri5.setRecipe(recipe);

    RecipeIngredient ri6 = new RecipeIngredient();
    ri6.setName("lemon");
    ri6.setQuantity("1");
    ri6.setUnit("whole");
    ri6.setOrderIndex(6);
    ri6.setIngredient(lemon);
    ri6.setRecipe(recipe);

    RecipeIngredient ri7 = new RecipeIngredient();
    ri7.setName("red pepper flakes");
    ri7.setQuantity("1/2");
    ri7.setUnit("tsp");
    ri7.setOrderIndex(7);
    ri7.setIngredient(redPepperFlakes);
    ri7.setRecipe(recipe);

    recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6, ri7));
    recipeRepository.save(recipe);
  }

  private void createSampleRecipe5(User user) {
    Recipe recipe = new Recipe();
    recipe.setName("Crispy Oven Roasted Potatoes");
    recipe.setDescription("Perfectly crispy roasted potatoes with herbs");
    recipe.setRecipeUrl("https://www.seriouseats.com/the-best-roast-potatoes-ever-recipe");
    recipe.setUser(user);

    Ingredient potato = ingredientRepository.findByNameIgnoreCase("potato").orElseThrow();
    Ingredient oliveOil = ingredientRepository.findByNameIgnoreCase("olive oil").orElseThrow();
    Ingredient rosemary = ingredientRepository.findByNameIgnoreCase("rosemary").orElseThrow();
    Ingredient thyme = ingredientRepository.findByNameIgnoreCase("thyme").orElseThrow();
    Ingredient garlic = ingredientRepository.findByNameIgnoreCase("garlic").orElseThrow();
    Ingredient salt = ingredientRepository.findByNameIgnoreCase("salt").orElseThrow();

    RecipeIngredient ri1 = new RecipeIngredient();
    ri1.setName("potato");
    ri1.setQuantity("2");
    ri1.setUnit("lbs");
    ri1.setOrderIndex(1);
    ri1.setIngredient(potato);
    ri1.setRecipe(recipe);

    RecipeIngredient ri2 = new RecipeIngredient();
    ri2.setName("olive oil");
    ri2.setQuantity("1/4");
    ri2.setUnit("cup");
    ri2.setOrderIndex(2);
    ri2.setIngredient(oliveOil);
    ri2.setRecipe(recipe);

    RecipeIngredient ri3 = new RecipeIngredient();
    ri3.setName("rosemary");
    ri3.setQuantity("2");
    ri3.setUnit("sprigs");
    ri3.setOrderIndex(3);
    ri3.setIngredient(rosemary);
    ri3.setRecipe(recipe);

    RecipeIngredient ri4 = new RecipeIngredient();
    ri4.setName("thyme");
    ri4.setQuantity("3");
    ri4.setUnit("sprigs");
    ri4.setOrderIndex(4);
    ri4.setIngredient(thyme);
    ri4.setRecipe(recipe);

    RecipeIngredient ri5 = new RecipeIngredient();
    ri5.setName("garlic");
    ri5.setQuantity("4");
    ri5.setUnit("cloves");
    ri5.setOrderIndex(5);
    ri5.setIngredient(garlic);
    ri5.setRecipe(recipe);

    RecipeIngredient ri6 = new RecipeIngredient();
    ri6.setName("salt");
    ri6.setQuantity("1");
    ri6.setUnit("tsp");
    ri6.setOrderIndex(6);
    ri6.setIngredient(salt);
    ri6.setRecipe(recipe);

    recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6));
    recipeRepository.save(recipe);
  }

  private void createSampleRecipe6(User user) {
    Recipe recipe = new Recipe();
    recipe.setName("Chicken Teriyaki Bowl");
    recipe.setDescription("Japanese-style teriyaki chicken over rice with vegetables");
    recipe.setRecipeUrl("https://www.justonecookbook.com/teriyaki-chicken");
    recipe.setUser(user);

    Ingredient chickenThigh = ingredientRepository.findByNameIgnoreCase("chicken thigh")
        .orElseThrow();
    Ingredient rice = ingredientRepository.findByNameIgnoreCase("rice").orElseThrow();
    Ingredient soySauce = ingredientRepository.findByNameIgnoreCase("soy sauce").orElseThrow();
    Ingredient mirin = ingredientRepository.findByNameIgnoreCase("mirin").orElseThrow();
    Ingredient sugar = ingredientRepository.findByNameIgnoreCase("sugar").orElseThrow();
    Ingredient broccoli = ingredientRepository.findByNameIgnoreCase("broccoli").orElseThrow();
    Ingredient sesameSeeds = ingredientRepository.findByNameIgnoreCase("sesame seeds")
        .orElseThrow();
    Ingredient greenOnion = ingredientRepository.findByNameIgnoreCase("green onion").orElseThrow();

    RecipeIngredient ri1 = new RecipeIngredient();
    ri1.setName("chicken thigh");
    ri1.setQuantity("1.5");
    ri1.setUnit("lbs");
    ri1.setOrderIndex(1);
    ri1.setIngredient(chickenThigh);
    ri1.setRecipe(recipe);

    RecipeIngredient ri2 = new RecipeIngredient();
    ri2.setName("rice");
    ri2.setQuantity("2");
    ri2.setUnit("cups");
    ri2.setOrderIndex(2);
    ri2.setIngredient(rice);
    ri2.setRecipe(recipe);

    RecipeIngredient ri3 = new RecipeIngredient();
    ri3.setName("soy sauce");
    ri3.setQuantity("1/4");
    ri3.setUnit("cup");
    ri3.setOrderIndex(3);
    ri3.setIngredient(soySauce);
    ri3.setRecipe(recipe);

    RecipeIngredient ri4 = new RecipeIngredient();
    ri4.setName("mirin");
    ri4.setQuantity("2");
    ri4.setUnit("tbsp");
    ri4.setOrderIndex(4);
    ri4.setIngredient(mirin);
    ri4.setRecipe(recipe);

    RecipeIngredient ri5 = new RecipeIngredient();
    ri5.setName("sugar");
    ri5.setQuantity("2");
    ri5.setUnit("tbsp");
    ri5.setOrderIndex(5);
    ri5.setIngredient(sugar);
    ri5.setRecipe(recipe);

    RecipeIngredient ri6 = new RecipeIngredient();
    ri6.setName("broccoli");
    ri6.setQuantity("2");
    ri6.setUnit("cups");
    ri6.setOrderIndex(6);
    ri6.setIngredient(broccoli);
    ri6.setRecipe(recipe);

    RecipeIngredient ri7 = new RecipeIngredient();
    ri7.setName("sesame seeds");
    ri7.setQuantity("1");
    ri7.setUnit("tbsp");
    ri7.setOrderIndex(7);
    ri7.setIngredient(sesameSeeds);
    ri7.setRecipe(recipe);

    RecipeIngredient ri8 = new RecipeIngredient();
    ri8.setName("green onion");
    ri8.setQuantity("2");
    ri8.setUnit("stalks");
    ri8.setOrderIndex(8);
    ri8.setIngredient(greenOnion);
    ri8.setRecipe(recipe);

    recipe.setRecipeIngredients(Arrays.asList(ri1, ri2, ri3, ri4, ri5, ri6, ri7, ri8));
    recipeRepository.save(recipe);
  }
}