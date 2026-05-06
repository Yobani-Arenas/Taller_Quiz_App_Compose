package com.example.quizapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: String
)

data class QuizUiState(
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val selectedOption: String = "",
    val hasAnswered: Boolean = false,
    val isAnswerCorrect: Boolean = false,
    val isGameOver: Boolean = false,
    val questions: List<Question> = listOf(
        Question("¿Cuál es el lenguaje oficial recomendado para el desarrollo Android?", listOf("Java", "Kotlin", "Swift", "Dart"), "Kotlin"),
        Question("¿Qué componente de arquitectura se usa para conservar el estado ante cambios de configuración?", listOf("Activity", "Fragment", "ViewModel", "Intent"), "ViewModel"),
        Question("¿Jetpack Compose sigue principalmente qué paradigma de programación de UI?", listOf("Imperativo", "Declarativo", "Procedural", "Estructurado"), "Declarativo"),
        Question("¿Qué patrón de arquitectura favorece la separación entre la UI y la lógica de negocio?", listOf("MVC", "Singleton", "MVVM", "Adapter"), "MVVM"),
        Question("¿Qué función se usa para observar un StateFlow dentro de un Composable?", listOf("remember", "collectAsState()", "launch", "mutableStateOf()"), "collectAsState()"),
        Question("¿Qué anotación se usa para marcar una función como un componente de UI en Compose?", listOf("@Ui", "@Composable", "@Compose", "@View"), "@Composable"),
        Question("¿Cuál es el componente de layout en Compose que organiza elementos verticalmente?", listOf("Box", "Row", "Column", "ConstraintLayout"), "Column"),
        Question("¿Cómo se llama el archivo donde se declaran los componentes y permisos de la aplicación?", listOf("build.gradle", "settings.gradle", "AndroidManifest.xml", "local.properties"), "AndroidManifest.xml"),
        Question("¿Qué herramienta se usa para gestionar dependencias en Android?", listOf("NPM", "Maven", "Gradle", "CocoaPods"), "Gradle"),
        Question("¿Qué componente de Compose se usa para mostrar una lista de elementos de forma eficiente?", listOf("Column", "LazyColumn", "ScrollView", "ListView"), "LazyColumn")
    )
) {
    val currentQuestion get() = questions[currentQuestionIndex]
    val totalQuestions get() = questions.size
}

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun selectOption(option: String) {
        _uiState.update { it.copy(selectedOption = option) }
    }

    fun checkAnswer() {
        val currentState = _uiState.value
        val correct = currentState.selectedOption == currentState.currentQuestion.correctAnswer
        _uiState.update { 
            it.copy(
                hasAnswered = true,
                isAnswerCorrect = correct,
                score = if (correct) it.score + 1 else it.score
            )
        }
    }

    fun goToNextQuestion() {
        if (_uiState.value.currentQuestionIndex < _uiState.value.totalQuestions - 1) {
            _uiState.update { 
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedOption = "",
                    hasAnswered = false,
                    isAnswerCorrect = false
                )
            }
        } else {
            _uiState.update { it.copy(isGameOver = true) }
        }
    }

    fun restartGame() {
        _uiState.value = QuizUiState()
    }
}