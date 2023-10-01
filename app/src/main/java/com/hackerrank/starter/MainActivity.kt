/*
 ******** Guidelines ********

 Click: Run > Build & Run
 Refer to README.md for additional information
 */
package com.hackerrank.starter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hackerrank.starter.adapter.EmployeeListAdapter
import com.hackerrank.starter.databinding.ActivityMainBinding
import com.hackerrank.starter.options.FilterAlertDialog
import com.hackerrank.starter.options.FiltersEnum
import com.hackerrank.starter.room.Employee
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

/*
******** Guidelines ********

Click: Run > Build & Run
Refer to README.md for additional information
*/

class MainActivity : AppCompatActivity(), EventListener {
    private var binding: ActivityMainBinding? = null
    lateinit var adapter: EmployeeListAdapter
    lateinit var filterAlertDialog: FilterAlertDialog
    lateinit var context: Context
    lateinit var eventListener: EventListener
    var fullEmployeeArrayList: MutableList<Employee> = mutableListOf()
    var filterEmployeeArrayList: MutableList<Employee> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.toolbar)
        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        context = this
        eventListener = this
        adapter = EmployeeListAdapter(mutableListOf(), eventListener)
        binding?.employeeRv?.adapter = adapter
        binding?.addEmployeeBtn?.setOnClickListener(View.OnClickListener {
            checkExistEmployee(
                binding?.employeeNameEt?.text.toString(),
                binding?.employeeSalaryEt?.text.toString()
            )
            binding?.employeeNameEt?.setText("")
            binding?.employeeSalaryEt?.setText("")
            hideKeyboard()
        })
        binding?.clearData?.setOnClickListener(View.OnClickListener { deleteAll() })
        binding?.options?.setOnClickListener(View.OnClickListener {
            filterAlertDialog = FilterAlertDialog(context, eventListener)
            filterAlertDialog.show()
        })
        initData()
    }

    fun deleteAll() {
        //Write code and add filters here
        fullEmployeeArrayList = mutableListOf()
        fullEmployeeArrayList.clear()
        updateScreen(fullEmployeeArrayList)
    }

    fun initData() {
        binding?.addEmployeeBtn?.isEnabled = false
        binding?.clearData?.isEnabled = false
        val newEmployeeOne: Employee = createNewEmployee("Jhon Loyd", 112.4f)
        insertEmployee(newEmployeeOne)
        val newEmployeeTwo: Employee = createNewEmployee("Bibi Bob", 8852.43f)
        insertEmployee(newEmployeeTwo)
        binding?.addEmployeeBtn?.isEnabled = true
        binding?.clearData?.isEnabled = true
        binding?.filterTv?.text = resources.getString(R.string.filter_all)
    }

    fun insertEmployee(employee: Employee) {
        binding?.addEmployeeBtn?.isEnabled = false
        //Write code and add filters here
        fullEmployeeArrayList.add(employee)
        updateScreen(fullEmployeeArrayList)
    }

    private fun checkExistEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        if (fullEmployeeArrayList.none { employee -> employee.name == employeeName && employee.salary == employeeSalary }) {
            addEmployee(name, salary)
        } else {
            createExistEmployeeDialog()
        }
    }

    private fun addEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        val newEmployee: Employee = createNewEmployee(employeeName, employeeSalary)
        insertEmployee(newEmployee)
    }

    fun updateScreen(employeeList: List<Employee>) {
        adapter.updateEmployeeListItems(employeeList)
        adapter.notifyDataSetChanged()
        binding?.addEmployeeBtn?.isEnabled = true
    }

    fun getAll() {
        //Write code and add filters here

        updateScreen(fullEmployeeArrayList)
    }

    fun getEmployeeEqualName(name: String) {
        //Write code and add filters here
        val listWithName=fullEmployeeArrayList.filter { it.name==name }
        filterEmployeeArrayList.addAll(listWithName)
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeContainsPartName(partName: String?) {
        //Write code and add filters here
        filterEmployeeArrayList = mutableListOf()
        val filteredWithPartName=fullEmployeeArrayList.filter { it.name.contains(partName?:"") }
        filterEmployeeArrayList.addAll(filteredWithPartName)
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeLessThan(maxSalary: Float) {
        //Write code and add filters here
        val maxSalaryList=fullEmployeeArrayList.filter { it.salary<maxSalary }
        filterEmployeeArrayList.clear()
        filterEmployeeArrayList.addAll(maxSalaryList)
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeMoreThan(minSalary: Float) {
        //Write code and add filters here
        val minSalaryList=fullEmployeeArrayList.filter { it.salary>minSalary }
        filterEmployeeArrayList.clear()
        filterEmployeeArrayList.addAll(minSalaryList)
        updateScreen(filterEmployeeArrayList)
    }

    fun deleteEmployeeById(id: Int) {
        Log.e("deleteItem",id.toString())
        //Write code and add filters here
        if (fullEmployeeArrayList.size >id){
            fullEmployeeArrayList.removeAt(id)
        }
        Toast.makeText(this,"Deleted!",Toast.LENGTH_SHORT).show()
        updateScreen(fullEmployeeArrayList)
    }

    private fun getSalary(salary: String): Float {
        return if (salary.isEmpty()) {
            0f
        } else {
            salary.toFloat()
        }
    }

    private fun getName(name: String): String {
        return if (name.isEmpty()) {
            "Unnamed Employee"
        } else {
            name
        }
    }

    private fun createNewEmployee(name: String, salary: Float): Employee {
        return Employee(name, salary)
    }

    override fun deleteEmployee(id: Int) {
        deleteEmployeeById(id)
    }

    override fun chooseOption(filtersEnum: FiltersEnum?) {
        when (filtersEnum) {
            FiltersEnum.ALL -> {
                binding?.filterTv?.text = resources.getString(R.string.filter_all)
                getAll()
            }
            FiltersEnum.NAME -> {
                val name: String = filtersEnum.employeeName
                binding?.filterTv?.text = resources.getString(R.string.filter_name, name)
                getEmployeeEqualName(name)
            }
            FiltersEnum.PART_NAME -> {
                val partName: String = filtersEnum.employeeName
                binding?.filterTv?.text = resources.getString(R.string.filter_part_name, partName)
                getEmployeeContainsPartName(partName)
            }
            FiltersEnum.SALARY_LESS -> {
                val maxSalary: Float = filtersEnum.maxSalary
                binding?.filterTv?.text = resources.getString(R.string.filter_less_than, maxSalary)
                getEmployeeLessThan(maxSalary)
            }
            FiltersEnum.SALARY_MORE -> {
                val minSalary: Float = filtersEnum.minSalary
                binding?.filterTv?.text = resources.getString(R.string.filter_more_than, minSalary)
                getEmployeeMoreThan(minSalary)
            }

            else -> {}
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun createExistEmployeeDialog() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.exist_dialog_title))
            .setMessage(resources.getString(R.string.dialog_message))
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which -> dialog.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}