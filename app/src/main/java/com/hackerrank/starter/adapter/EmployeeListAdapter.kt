package com.hackerrank.starter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hackerrank.starter.EventListener
import com.hackerrank.starter.databinding.ItemEmployeeBinding
import com.hackerrank.starter.room.Employee


class EmployeeListAdapter(
    private val employeeList: MutableList<Employee>,
    private val listener: EventListener
) :
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemEmployeeBinding =
            ItemEmployeeBinding.inflate(layoutInflater, parent, false)
        return EmployeeViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employeeList[position], position)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    fun updateEmployeeListItems(employees: List<Employee>) {
        val diffCallback = EmployeeCallback(employeeList, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        employeeList.clear()
        employeeList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class EmployeeViewHolder(
        val binding: ItemEmployeeBinding,
        val listener: EventListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee, position: Int) {
            binding.employeeNameText.text = employee.name
            binding.employeeSalaryText.text = employee.salary.toString()
            binding.deleteEmployeeBtn.setOnClickListener { v -> listener.deleteEmployee(position) }
        }

    }

}

internal class EmployeeCallback(
    private val mOldEmployeeList: List<Employee>,
    private val mNewEmployeeList: List<Employee>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].name == mNewEmployeeList[newItemPosition].name
                && mOldEmployeeList[oldItemPosition].salary == mNewEmployeeList[newItemPosition].salary

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldEmployeeList[oldItemPosition]
        val newEmployee = mNewEmployeeList[newItemPosition]
        return oldEmployee.name == newEmployee.name
    }
}