package com.hackerrank.starter

import androidx.test.core.app.ActivityScenario
import com.google.common.truth.Truth.assertThat
import com.hackerrank.starter.room.Employee
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    private val EMPLOYEE_ONE = Employee("Jhon Loyd", 112.4f)
    private val EMPLOYEE_TWO = Employee("Bibi Bob", 8852.43f)
    private val EMPLOYEE_THREE = Employee("Test_Test", 0f)
    private val EMPLOYEE_FOUR = Employee("Iki Jhon-Natan", 884f)

    @Test
    fun checkInsertEmployee() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 3 &&
                                activity.filterEmployeeArrayList[0] == EMPLOYEE_ONE &&
                                activity.filterEmployeeArrayList[1] == EMPLOYEE_TWO &&
                                activity.filterEmployeeArrayList[2] == EMPLOYEE_THREE
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkDeleteAllEmployee() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    assertThat(
                        activity.filterEmployeeArrayList.size == 0
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkEmployeeEqualName() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.getEmployeeEqualName(EMPLOYEE_TWO.name)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 1 &&
                                activity.filterEmployeeArrayList[0] == EMPLOYEE_TWO
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkEmployeeContains() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.getEmployeeContainsPartName("Jhon")
                    assertThat(
                        activity.filterEmployeeArrayList.size == 2 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FOUR)
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkEmployeeMoreThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.getEmployeeMoreThan(8000f)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 1 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_TWO)
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkEmployeeLessThan() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.getEmployeeLessThan(8000f)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 3 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_THREE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FOUR)
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkDeleteEmployeeById() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.deleteEmployeeById(2)
                    assertThat(
                        activity.filterEmployeeArrayList.size == 3 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_TWO) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FOUR)
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkGetAll() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    activity.insertEmployee(EMPLOYEE_ONE)
                    activity.insertEmployee(EMPLOYEE_TWO)
                    activity.insertEmployee(EMPLOYEE_THREE)
                    activity.insertEmployee(EMPLOYEE_FOUR)
                    activity.getAll()
                    assertThat(
                        activity.filterEmployeeArrayList.size == 4 &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_ONE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_TWO) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_THREE) &&
                                activity.filterEmployeeArrayList.contains(EMPLOYEE_FOUR)
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

    @Test
    fun checkDeleteAll() {
        try {
            ActivityScenario.launch(MainActivity::class.java).use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    activity.deleteAll()
                    assertThat(
                        activity.filterEmployeeArrayList.size == 0
                    ).isTrue()
                }
            }
        } catch (e: Exception) {
            assert(false)
            e.printStackTrace()
        }
    }

}