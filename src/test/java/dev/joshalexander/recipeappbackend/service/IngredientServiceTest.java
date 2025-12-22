package dev.joshalexander.recipeappbackend.service;
import dev.joshalexander.recipeappbackend.dto.IngredientCreateDTO;
import dev.joshalexander.recipeappbackend.dto.IngredientDTO;
import dev.joshalexander.recipeappbackend.entity.Ingredient;
import dev.joshalexander.recipeappbackend.mapper.EntityMapper;
import dev.joshalexander.recipeappbackend.repository.IngredientRepository;
import dev.joshalexander.recipeappbackend.service.impl.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private EntityMapper ingredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getAllIngredients_WithTwoIngredientsInRepository_ReturnsListOfTwoDTOs() {
        // Arrange
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("tomato");
        ingredient1.setDefaultUnit("large");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("basil");
        ingredient2.setDefaultUnit("bunches");

        IngredientDTO dto1 = new IngredientDTO(1L, "tomato", "large");
        IngredientDTO dto2 = new IngredientDTO(2L, "basil", "bunches");

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));
        when(ingredientMapper.toIngredientDTO(ingredient1)).thenReturn(dto1);
        when(ingredientMapper.toIngredientDTO(ingredient2)).thenReturn(dto2);

        // Act
        List<IngredientDTO> result = ingredientService.getAllIngredients();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("tomato", result.get(0).getName());
        assertEquals("basil", result.get(1).getName());
        assertEquals("large", result.get(0).getDefaultUnit());
        assertEquals("bunches", result.get(1).getDefaultUnit());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertThat(result).containsExactly(dto1, dto2);

        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientMapper, times(2)).toIngredientDTO(any(Ingredient.class));
    }
    @Test
    void getAllIngredients_WithEmptyRepository_ReturnsEmptyList() {
        // Arrange
        when (ingredientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<IngredientDTO> result = ingredientService.getAllIngredients();

        assertTrue(result.isEmpty());
        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientMapper, never()).toIngredientDTO(any(Ingredient.class));

    }
    @Test
    void getIngredientById_WhenIngredientExists() {
        // Arrange
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("tomato");
        ingredient1.setDefaultUnit("large");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("basil");
        ingredient2.setDefaultUnit("bunches");

        IngredientDTO dto1 = new IngredientDTO(1L, "tomato", "large");
        IngredientDTO dto2 = new IngredientDTO(2L, "basil", "bunches");

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient2));
        when(ingredientMapper.toIngredientDTO(ingredient1)).thenReturn(dto1);
        when(ingredientMapper.toIngredientDTO(ingredient2)).thenReturn(dto2);

        // Act
        Optional<IngredientDTO> result1 = ingredientService.getIngredientById(1L);
        Optional<IngredientDTO> result2 = ingredientService.getIngredientById(2L);

        // Assert
        assertThat(result1).isPresent()
                .hasValueSatisfying(ingredient -> {
                    assertEquals("tomato", ingredient.getName());
                    assertEquals("large", ingredient.getDefaultUnit());
                    assertEquals(1L, ingredient.getId());
                } );
        assertThat(result2).isPresent()
                .hasValueSatisfying(ingredient -> {
                    assertEquals("basil", ingredient.getName());
                    assertEquals("bunches", ingredient.getDefaultUnit());
                    assertEquals(2L, ingredient.getId());
                } );
        verify(ingredientRepository, times(2)).findById(anyLong());
    }
    @Test
    void getIngredientById_WhenIngredientDoesNotExist() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());
        when(ingredientRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Optional<IngredientDTO> result1 = ingredientService.getIngredientById(1L);
        Optional<IngredientDTO> result2 = ingredientService.getIngredientById(2L);

        // Assert
        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
        verify(ingredientRepository, times(1)).findById(1L);
        verify(ingredientRepository, times(1)).findById(2L);
    }
    @Test
    void createIngredient_WithUppercaseInput_NormalizesToLowercase() {
        // Arrange
        IngredientCreateDTO createDTO = new IngredientCreateDTO();
        createDTO.setName("TOMATO");
        createDTO.setDefaultUnit("large");

        Ingredient mappedIngredient = new Ingredient();
        mappedIngredient.setName("tomato");
        mappedIngredient.setDefaultUnit("large");

        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(1L);
        savedIngredient.setName("tomato");
        savedIngredient.setDefaultUnit("large");

        IngredientDTO resultDTO = new IngredientDTO(1L, "tomato", "large");

        when(ingredientMapper.toIngredient(any(IngredientCreateDTO.class))).thenReturn(mappedIngredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(savedIngredient);
        when(ingredientMapper.toIngredientDTO(savedIngredient)).thenReturn(resultDTO);

        // Act
        IngredientDTO result = ingredientService.createIngredient(createDTO);

        // Assert
        assertEquals("tomato", result.getName());
        assertEquals(1L, result.getId());
        assertEquals("large", result.getDefaultUnit());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
        verify(ingredientMapper, times(1)).toIngredient(any(IngredientCreateDTO.class));
        verify(ingredientMapper, times(1)).toIngredientDTO(any(Ingredient.class));
    }

    @Test
    void createIngredient_WithMixedCaseInput_NormalizesToLowercase() {
        // Arrange
        IngredientCreateDTO createDTO = new IngredientCreateDTO();
        createDTO.setName("ToMaTo");
        createDTO.setDefaultUnit("large");

        Ingredient mappedIngredient = new Ingredient();
        mappedIngredient.setName("tomato");
        mappedIngredient.setDefaultUnit("large");

        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(2L);
        savedIngredient.setName("tomato");
        savedIngredient.setDefaultUnit("large");

        IngredientDTO resultDTO = new IngredientDTO(2L, "tomato", "large");

        when(ingredientMapper.toIngredient(any(IngredientCreateDTO.class))).thenReturn(mappedIngredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(savedIngredient);
        when(ingredientMapper.toIngredientDTO(savedIngredient)).thenReturn(resultDTO);

        // Act
        IngredientDTO result = ingredientService.createIngredient(createDTO);

        // Assert
        assertEquals("tomato", result.getName());
        assertEquals(2L, result.getId());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
        verify(ingredientMapper, times(1)).toIngredient(any(IngredientCreateDTO.class));
        verify(ingredientMapper, times(1)).toIngredientDTO(any(Ingredient.class));
    }

    @Test
    void createIngredient_WithLowercaseInput_RemainsLowercase() {
        // Arrange
        IngredientCreateDTO createDTO = new IngredientCreateDTO();
        createDTO.setName("basil");
        createDTO.setDefaultUnit("bunches");

        Ingredient mappedIngredient = new Ingredient();
        mappedIngredient.setName("basil");
        mappedIngredient.setDefaultUnit("bunches");

        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(3L);
        savedIngredient.setName("basil");
        savedIngredient.setDefaultUnit("bunches");

        IngredientDTO resultDTO = new IngredientDTO(3L, "basil", "bunches");

        when(ingredientMapper.toIngredient(any(IngredientCreateDTO.class))).thenReturn(mappedIngredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(savedIngredient);
        when(ingredientMapper.toIngredientDTO(savedIngredient)).thenReturn(resultDTO);

        // Act
        IngredientDTO result = ingredientService.createIngredient(createDTO);

        // Assert
        assertEquals("basil", result.getName());
        assertEquals(3L, result.getId());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
        verify(ingredientMapper, times(1)).toIngredient(any(IngredientCreateDTO.class));
        verify(ingredientMapper, times(1)).toIngredientDTO(any(Ingredient.class));
    }
}
