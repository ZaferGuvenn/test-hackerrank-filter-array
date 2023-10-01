package com.hackerrank.starter.options

import android.app.AlertDialog
import android.content.Context

import android.text.Editable

import android.text.TextWatcher

import android.view.LayoutInflater
import android.view.View

import android.widget.CheckBox
import android.widget.CompoundButton

import android.widget.EditText
import com.hackerrank.starter.EventListener
import com.hackerrank.starter.R


class FilterAlertDialog(context: Context?, listener: EventListener) :
    AlertDialog.Builder(context) {
    private var nameET: EditText? = null
    private var minSalaryET: EditText? = null
    private var maxSalaryET: EditText? = null
    private var checkBox1: CheckBox? = null
    private var checkBox2: CheckBox? = null
    private var checkBox3: CheckBox? = null
    private var checkBox4: CheckBox? = null
    private var checkBox5: CheckBox? = null
    lateinit var optionChooseListener: EventListener
    private var name = ""
    private var minSalary = 0f
    private var maxSalary = 0f
    private fun initView(listener: EventListener) {
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_options, null)
        setView(dialogView)
        optionChooseListener = listener
        nameET = dialogView.findViewById(R.id.name_ed)
        minSalaryET = dialogView.findViewById(R.id.min_salary_ed)
        maxSalaryET = dialogView.findViewById(R.id.max_salary_ed)
        checkBox1 = dialogView.findViewById(R.id.chb_1)
        checkBox2 = dialogView.findViewById(R.id.chb_2)
        checkBox3 = dialogView.findViewById(R.id.chb_3)
        checkBox4 = dialogView.findViewById(R.id.chb_4)
        checkBox5 = dialogView.findViewById(R.id.chb_5)
        checkBox1!!.isChecked = true
        updateText()
        setPositiveButton(context.getText(android.R.string.yes)) { dialog, which ->
            var filtersEnum: FiltersEnum = FiltersEnum.ALL
            if (checkBox1!!.isChecked) filtersEnum = FiltersEnum.ALL
            if (checkBox2!!.isChecked &&
                !checkBox4!!.isChecked &&
                !checkBox5!!.isChecked
            ) filtersEnum = FiltersEnum.NAME
            if (checkBox3!!.isChecked &&
                !checkBox4!!.isChecked &&
                !checkBox5!!.isChecked
            ) filtersEnum = FiltersEnum.PART_NAME
            if (checkBox4!!.isChecked &&
                !checkBox2!!.isChecked &&
                !checkBox3!!.isChecked
            ) filtersEnum = FiltersEnum.SALARY_LESS
            if (checkBox5!!.isChecked &&
                !checkBox2!!.isChecked &&
                !checkBox3!!.isChecked
            ) filtersEnum = FiltersEnum.SALARY_MORE
            filtersEnum.employeeName = name
            filtersEnum.minSalary =  minSalary
            filtersEnum.maxSalary = maxSalary
            optionChooseListener.chooseOption(filtersEnum)
        }
    }

    private fun initEditTextListeners() {
        nameET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                name = if (s.toString().isEmpty()) {
                    ""
                } else {
                    s.toString()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        minSalaryET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                minSalary = if (s.toString().isEmpty()) {
                    0f
                } else {
                    s.toString().toFloat()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        maxSalaryET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                maxSalary = if (s.toString().isEmpty()) {
                    0f
                } else {
                    s.toString().toFloat()
                }
                updateText()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun updateText() {
        updateName()
        updateMinSalary()
        updateMaxSalary()
    }

    private fun updateName() {
        checkBox2?.text = context.getString(R.string.chb_2, name)
        checkBox3?.text = context.getString(R.string.chb_3, name)
    }

    private fun updateMinSalary() {
        checkBox4?.text = context.getString(R.string.chb_4, maxSalary.toString())
    }

    private fun updateMaxSalary() {
        checkBox5?.text = context.getString(R.string.chb_5, minSalary.toString())
    }

    private fun initCheckBoxListeners() {
        checkBox1!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox2!!.isChecked = false
                checkBox3!!.isChecked = false
                checkBox4!!.isChecked = false
                checkBox5!!.isChecked = false
            }
        }
        checkBox2!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1!!.isChecked = false
                checkBox3!!.isChecked = false
                checkBox4!!.isChecked = false
                checkBox5!!.isChecked = false
            }
        }
        checkBox3!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1!!.isChecked = false
                checkBox2!!.isChecked = false
                checkBox4!!.isChecked = false
                checkBox5!!.isChecked = false
            }
        }
        checkBox4!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1!!.isChecked = false
                checkBox2!!.isChecked = false
                checkBox3!!.isChecked = false
                checkBox5!!.isChecked = false
            }
        }
        checkBox5!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                checkBox1!!.isChecked = false
                checkBox2!!.isChecked = false
                checkBox3!!.isChecked = false
                checkBox4!!.isChecked = false
            }
        }
    }

    init {
        initView(listener)
        initEditTextListeners()
        initCheckBoxListeners()
    }
}