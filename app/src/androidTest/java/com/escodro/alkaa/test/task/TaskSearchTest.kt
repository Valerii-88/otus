package com.escodro.alkaa.test.task

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
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
@Feature("Поиск задач")
@RunWith(AllureAndroidJUnit4::class)
class TaskSearchTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    @Story("Поиск существующей задачи")
    fun searchExistingTask() {

        val uniqueTaskName = TestStringGenerator.generateRandomWord(8)
        val commonTaskName = "обычная задача"
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание уникальной задачи")
            createNewTask(uniqueTaskName)

            Allure.step("Создание обычной задачи")
            createNewTask(commonTaskName)

            Allure.step("Переход к поиску")
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()

            Allure.step("Ввод поискового запроса: $uniqueTaskName")
            onNodeWithContentDescription("Search").performTextInput(uniqueTaskName)

            Allure.step("Проверка отображения найденной задачи")
            onAllNodesWithText(uniqueTaskName).filter(! isEditable()).get(0).assertIsDisplayed()

            Allure.step("Проверка отсутствия другой задачи в результатах")
            onNodeWithText(commonTaskName).assertIsNotDisplayed()

            Allure.step("Возврат к списку задач")
            onNodeWithText("Tasks").performClick()

            Allure.step("Завершение созданных задач")
            markTaskAsCompleted(uniqueTaskName)
            markTaskAsCompleted(commonTaskName)
        }
    }

    @Test
    @Story("Поиск несуществующей задачи")
    fun searchNonExistingTask() {

        val realTaskName = TestStringGenerator.generateRandomWord(8)
        val nonExistingTaskName = "несуществующая задача"
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Создание реальной задачи")
            createNewTask(realTaskName)

            Allure.step("Переход к поиску")
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()

            Allure.step("Поиск несуществующей задачи")
            onNodeWithContentDescription("Search").performTextInput(nonExistingTaskName)

            Allure.step("Проверка отображения сообщения об отсутствии задач")
            onNodeWithText("No tasks found").assertIsDisplayed()

            Allure.step("Возврат к списку задач")
            onNodeWithText("Tasks").performClick()

            Allure.step("Завершение созданной задачи")
            markTaskAsCompleted(realTaskName)
        }
    }

    /**
     * Создает новую задачу
     */
    private fun createNewTask(taskName: String) {
        testRule.onNodeWithContentDescription("Add task").performClick()
        testRule.onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(taskName)
        testRule.onNodeWithText("Add").performClick()
    }

    /**
     * Отмечает задачу как выполненную
     */
    private fun markTaskAsCompleted(taskName: String) {
        testRule.onNodeWithText(taskName).onChild().performClick()
    }
}
