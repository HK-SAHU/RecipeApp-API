package eu.tutorials.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    // global state which will be of type mutableStateOf RecipeState
    private val _categorieState = mutableStateOf(RecipeState())

    val categoriesState: State<RecipeState> = _categorieState
    /*categoriesState is a state of type RecipeState and this will be whatever the _categoryState is.
     here the state is the basically means that we want the user interface to be updated whenever we
     actually change the state of our recipe state object
     so once our object is updated or like the list is updated, we have downloaded the list and we finally
     have it available. we want to display it on the screen, and in order to display something on the screen, we need to change
     the state or the state machine*/


    init {
        fetchCategories()
    }


    // to fetch the categories
    private fun fetchCategories(){
        /* viewModelScope provides a launch/ start for suspend functions to be processed
         if you want to start a suspend function, you actually have to start it inside a coroutine
         in order this to happen we use viewModelScope
         so the coroutines allow you to run a routine in the background*/
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categorieState.value = _categorieState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
                /*
                    with this is reponse we are getting the implementation of Apiservices interface
                    which has to get http request from the URL and the suspend function is returning categoriesResponse
                     */


            }catch (e: Exception){
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
                /*
                if there's an error then display "Error fetching Categories ${e.message}"
                this error is pointing to the RecipeState through the .copy function
                _categoryState.value.copy this is overriding and say if you are not loading anymore loading = false,
                this display the error message
                 */

            }
        }
    }

    /*data class will take care of the state of the recipe, so we need to know whether we have
     recipe or not and at which state of basically the recipe downloading
     do we have a list or not, error or not, loading or not*/

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
        // we don't have an error, then it is null.
        /*so the error is null which means it is empty.
        if you want a variable to be null or you want a variable to be able to null
        then you should use the question mark after the data type "String?"*/
    )

}

// we have seen that we can use mutable state of with all kinds of data types