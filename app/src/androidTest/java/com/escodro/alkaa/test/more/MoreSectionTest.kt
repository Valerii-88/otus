package com.escodro.alkaa.test.more

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.escodro.shared.MainView
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Epic("Навигация")
@Feature("Раздел 'Еще'")
@RunWith(AllureAndroidJUnit4::class)
class MoreSectionTest {

    @get:Rule
    val testRule = createComposeRule()

    @Test
    @Story("Проверка элементов раздела 'Еще'")
    fun verifyMoreSectionElements() {

        testRule.setContent { MainView() }

        testRule.apply {
            Allure.step("Переход в раздел 'Еще'")
            onNodeWithText("More").performClick()

            Allure.step("Проверка наличия заголовка 'FEATURES'")
            onNodeWithText("FEATURES").assertIsDisplayed()

            Allure.step("Проверка наличия элемента 'Task Tracker'")
            onNodeWithText("Task Tracker").assertIsDisplayed()

            Allure.step("Проверка наличия заголовка 'SETTINGS'")
            onNodeWithText("SETTINGS").assertIsDisplayed()

            Allure.step("Проверка наличия настройки темы приложения")
            onNodeWithText("App theme").assertIsDisplayed()

            Allure.step("Проверка наличия раздела 'About'")
            onNodeWithText("About").assertIsDisplayed()

            Allure.step("Проверка наличия раздела лицензий")
            onNodeWithText("Open source licenses").assertIsDisplayed()

            Allure.step("Проверка наличия информации о версии")
            onNodeWithText("Version").assertIsDisplayed()
        }
    }
}
