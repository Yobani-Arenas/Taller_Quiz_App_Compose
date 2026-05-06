package com.example.quizapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QuizScreen(navController: NavController, viewModel: QuizViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    // RF-09 & RF-10: AlertDialog de resultado final
    if (uiState.isGameOver) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Quiz Finalizado!") },
            text = { Text("Has completado el taller.\nTu puntaje final es: ${uiState.score} / ${uiState.totalQuestions}") },
            confirmButton = {
                Button(onClick = {
                    viewModel.restartGame()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }) {
                    Text("Reiniciar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Taller Quiz Android", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(16.dp))

        // RF-07: Progreso con barra (Sintaxis corregida para evitar deprecation)
        Text(text = "Pregunta ${uiState.currentQuestionIndex + 1} de ${uiState.totalQuestions}")
        LinearProgressIndicator(
            progress = { (uiState.currentQuestionIndex + 1).toFloat() / uiState.totalQuestions },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Text(text = "Puntaje: ${uiState.score}", style = MaterialTheme.typography.titleMedium)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = uiState.currentQuestion.text, style = MaterialTheme.typography.titleLarge)
                
                Spacer(modifier = Modifier.height(16.dp))

                uiState.currentQuestion.options.forEach { option ->
                    val isSelected = uiState.selectedOption == option
                    
                    // RF-05: Feedback visual diferenciado
                    val containerColor = if (uiState.hasAnswered) {
                        if (option == uiState.currentQuestion.correctAnswer) Color(0xFFC8E6C9) 
                        else if (isSelected) Color(0xFFFFCDD2)
                        else MaterialTheme.colorScheme.surface
                    } else {
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                        else MaterialTheme.colorScheme.surface
                    }

                    Surface(
                        onClick = { if (!uiState.hasAnswered) viewModel.selectOption(option) },
                        color = containerColor,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
                            RadioButton(selected = isSelected, onClick = null)
                            Text(text = option, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            }
        }

        Button(
            onClick = { viewModel.checkAnswer() },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.selectedOption.isNotEmpty() && !uiState.hasAnswered
        ) {
            Text("Validar respuesta")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = { viewModel.goToNextQuestion() },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.hasAnswered
        ) {
            Text(if (uiState.currentQuestionIndex == uiState.totalQuestions - 1) "Finalizar" else "Siguiente")
        }

        if (uiState.hasAnswered) {
            Text(
                text = if (uiState.isAnswerCorrect) "¡Correcto! ✨" else "Incorrecto ❌",
                color = if (uiState.isAnswerCorrect) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}