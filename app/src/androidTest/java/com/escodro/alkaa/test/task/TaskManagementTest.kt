package com.escodro.alkaa.test.task

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.escodro.alkaa.test.utils.TestStringGenerator
import com.escodro.shared.MainView
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Epic("Задачи")
@Feature("Управление задачами")
@RunWith(AllureAndroidJUnit4::class)
class TaskManagementTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    @Story("Невозможность создания пустой задачи")
    fun verifyEmptyTaskCreationIsNotAllowed() {
        // Подготовка
        testRule.setContent { MainView() }

        // Выполнение
        testRule.apply {
            Allure.step("Попытка создания пустой задачи")
            onNodeWithContentDescription("Add task").performClick()
            onNodeWithText("Add").performClick()

            // Проверка
            Allure.step("Проверка отображения сообщения о выполнении всех задач")
            onNodeWithText("Wow! All tasks are completed!").assertIsDisplayed()
        }
    }

    @Test
    @Story("Создание задачи без категории")
    fun createTaskWithoutCategory() {
        val taskName = TestStringGenerator.generateRandomWord(8)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание задачи без категории")
            createTask(taskName)

            Allure.step("Проверка наличия созданной задачи")
            onNodeWithText(taskName).assertIsDisplayed()

            Allure.step("Завершение созданной задачи")
            completeTask(taskName)
        }
    }

    @Test
    @Story("Создание задачи с категорией")
    fun createTaskWithCategory() {
        val taskName = TestStringGenerator.generateRandomWord(8)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание задачи с категорией")
            onNodeWithContentDescription("Add task").performClick()
            onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(taskName)
            onAllNodes(hasText("Work", substring = true)).get(1).performClick()
            onNodeWithText("Add").performClick()

            Allure.step("Проверка наличия задачи в общем списке")
            onNodeWithText(taskName).assertIsDisplayed()

            Allure.step("Проверка наличия задачи в категории 'Work'")
            onNodeWithText("Work").performClick()
            onNodeWithText(taskName).assertIsDisplayed()

            Allure.step("Проверка отсутствия задачи в категории 'Personal'")
            onNodeWithText("Personal").performClick()
            onNodeWithText(taskName).assertDoesNotExist()

            Allure.step("Возврат к категории 'Work'")
            onNodeWithText("Work").performClick()

            Allure.step("Завершение созданной задачи")
            completeTask(taskName)
        }
    }

    @Test
    @Story("Добавление описания к задаче")
    fun addDescriptionToTask() {

        val taskName = TestStringGenerator.generateRandomWord(8)
        val description = TestStringGenerator.generateRandomSentence(3)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание задачи")
            createTask(taskName)

            Allure.step("Открытие детальной информации о задаче")
            onNodeWithText(taskName).performClick()

            Allure.step("Добавление описания к задаче")
            onNodeWithContentDescription("Description").performClick()
            onNodeWithContentDescription("Description").performTextInput(description)

            Allure.step("Возврат к списку задач")
            onNodeWithContentDescription("Back").performClick()

            Allure.step("Повторное открытие задачи для проверки")
            onNodeWithText(taskName).performClick()

            Allure.step("Проверка сохранения описания")
            onNodeWithContentDescription("Description").assertTextEquals(description)

            Allure.step("Возврат к списку задач")
            onNodeWithContentDescription("Back").performClick()

            Allure.step("Завершение созданной задачи")
            completeTask(taskName)
        }
    }

    @Test
    @Story("Завершение задачи")
    fun markTaskAsCompleted() {

        val taskName = TestStringGenerator.generateRandomWord(8)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание задачи")
            createTask(taskName)

            Allure.step("Завершение задачи")
            completeTask(taskName)

            Allure.step("Проверка отображения сообщения о завершении задачи")
            onNodeWithText("Task completed").assertIsDisplayed()

            Allure.step("Проверка отсутствия задачи в основном списке")
            onNodeWithText(taskName).assertIsNotDisplayed()

            Allure.step("Проверка наличия задачи в поиске")
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()
            onNodeWithText(taskName).assertIsDisplayed()
        }
    }

    /**
     * Создает новую задачу
     */
    private fun createTask(name: String) {
        testRule.onNodeWithContentDescription("Add task").performClick()
        testRule.onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(name)
        testRule.onNodeWithText("Add").performClick()
    }

    /**
     * Отмечает задачу как выполненную
     */
    private fun completeTask(name: String) {
        testRule.onNodeWithText(name).onChild().performClick()
    }
}
