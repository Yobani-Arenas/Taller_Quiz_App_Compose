# Taller Práctico: Quiz Interactivo - Desarrollo Móvil

Este proyecto implementa un Quiz interactivo educativo sobre conceptos de Android y Kotlin, desarrollado bajo los estándares de la arquitectura MVVM y Jetpack Compose.

## 🛠️ Arquitectura: MVVM y Flujo de Datos Unidireccional (UDF)

La aplicación sigue estrictamente los principios de la arquitectura moderna de Android:

1. **Model**: La clase `Question` define la estructura de los datos (texto, opciones, respuesta correcta).
2. **ViewModel (`QuizViewModel`)**: Gestiona la lógica de negocio y el estado de la UI. Actúa como intermediario, procesando eventos del usuario y actualizando el estado.
3. **UI State (`QuizUiState`)**: Una clase de datos inmutable que encapsula todo lo que la pantalla necesita mostrar (pregunta actual, puntaje, progreso, feedback visual y estado de finalización).
4. **UI (Jetpack Compose)**: Las pantallas son funciones declarativas que reaccionan a los cambios del estado.

### Flujo Unidireccional (UDF):
- **Evento**: El usuario interactúa (selecciona opción, valida, avanza) -> La UI notifica al ViewModel.
- **Procesamiento**: El ViewModel actualiza el estado interno (`MutableStateFlow`).
- **Estado**: La UI observa el `StateFlow` mediante `collectAsState()` y se recompone automáticamente.

## 🕹️ Funcionamiento del Aplicativo

1. **Pantalla de Inicio**: Punto de entrada que invita al usuario a iniciar el desafío.
2. **Ciclo de Preguntas (10 Preguntas Técnicas)**:
   - **Selección**: El usuario elige una de las 4 opciones disponibles.
   - **Validación**: Al pulsar **"Validar respuesta"**, el sistema evalúa la opción y bloquea cambios.
   - **Feedback Visual (Nivel Intermedio)**: La opción seleccionada cambia a **Verde** si es correcta o **Rojo** si es incorrecta, brindando retroalimentación inmediata.
   - **Indicadores**: Se actualiza el puntaje y la **barra de progreso** visual.
3. **Navegación**: El usuario avanza con el botón "Siguiente", lo que limpia la selección previa y carga el nuevo estado.
4. **Finalización**: Tras la última pregunta, se dispara un **`AlertDialog`** que resume el desempeño final y permite reiniciar el quiz, devolviendo al usuario a la pantalla inicial.

## 📋 Requerimientos Técnicos Cumplidos

- **Reactividad**: Uso de `StateFlow` para una UI siempre sincronizada.
- **Inmutabilidad**: El estado nunca se modifica, se crea uno nuevo mediante `.copy()`, evitando efectos secundarios.
- **Persistencia**: El progreso se conserva ante cambios de configuración (rotación de pantalla).
- **Material 3**: Implementación de componentes modernos como Cards, Surfaces y Layouts dinámicos.

---
*Universidad de Caldas - Facultad de Ingeniería - 2026*
