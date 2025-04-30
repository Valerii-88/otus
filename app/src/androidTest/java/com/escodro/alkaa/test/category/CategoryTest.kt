package com.escodro.alkaa.test.category

import androidx.compose.ui.test.junit4.createComposeRule
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

@Epic("Категории")
@Feature("Управление категориями")
@RunWith(AllureAndroidJUnit4::class)
class CategoryManagementTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    @Story("Добавление новой категории")
    fun testAddNewCategory() {

        val generatedCategoryName = TestStringGenerator.generateRandomWord(8)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Переход на экран категорий")
            onNodeWithText("Categories").performClick()

            Allure.step("Создание новой категории '$generatedCategoryName'")
            addCategory(generatedCategoryName)

            Allure.step("Проверка наличия созданной категории")
            onNodeWithText(generatedCategoryName).assertExists()
        }
    }

    @Test
    @Story("Удаление существующей категории")
    fun testRemoveExistingCategory() {
        val generatedCategoryName = TestStringGenerator.generateRandomWord(7)
        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Переход на экран категорий")
            onNodeWithText("Categories").performClick()

            Allure.step("Создание категории для последующего удаления")
            addCategory(generatedCategoryName)

            Allure.step("Выбор категории для удаления")
            onNodeWithText(generatedCategoryName).performClick()

            Allure.step("Процесс удаления категории")
            onNodeWithContentDescription("Remove").performClick()
            onNodeWithText("Remove").performClick()

            Allure.step("Проверка отсутствия удаленной категории")
            onNodeWithText(generatedCategoryName).assertDoesNotExist()
        }
    }


    private fun addCategory(categoryName: String) {
        testRule.onNodeWithContentDescription("Add category").performClick()
        testRule.onNodeWithText("Category").performTextInput(categoryName)
        testRule.onNodeWithText("Save").performClick()
    }
}
