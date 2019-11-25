package com.example.baolema.DAO;

import com.example.baolema.bean.Recipe;

public interface RecipeDao {
    // url/Recipe/getRecipe?RecipeId=
    Recipe getRecipe(int ReciprId);
}
