package com.example.quizapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Instanciamos el ViewModel aquí para que sea compartido entre las pantallas del grafo
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("quiz") { QuizScreen(navController, quizViewModel) }
        composable("result") { ResultScreen(navController, quizViewModel) }
    }
}