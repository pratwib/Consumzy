package com.pratwib.consumzy2.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.pratwib.consumzy2.MyApplication
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.database.Food
import com.pratwib.consumzy2.databinding.ActivityAddFoodBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Suppress("NAME_SHADOWING")
class AddFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding

    private var list = mutableListOf<Category>()

    private var foodExpired: Long = 0

    private val addFoodViewModel: AddFoodViewModel by viewModels {
        AddFoodViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        datePicker()
    }

    private fun addFood() {
        binding.btnAddFood.setOnClickListener {
            val foodName = binding.etAddFoodName.text.toString()
            val foodQty = binding.etAddFoodQty.text.toString()
            val foodCat = binding.category.getTag(R.id.category) as Int? ?: 0
            val foodPlace = binding.etAddFoodPlace.text.toString()
            val timestamp = System.currentTimeMillis()

            if (foodName.isNotEmpty() && foodQty.isNotEmpty() && foodCat > -1 && foodExpired != 0L && foodPlace.isNotEmpty()) {
                addFoodViewModel.insertFood(
                    Food(
                        name = foodName,
                        quantity = foodQty,
                        cId = foodCat,
                        expiredDate = foodExpired,
                        place = foodPlace,
                        timestamp = timestamp,
                    )
                )
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                finish()
            } else {
                Toast.makeText(
                    this,
                    "Please fill in all the fields with valid data.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        addFood()
        dropdownCategory()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbAddFood)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun dropdownCategory() {
        addFoodViewModel.getAllCategory().observe(this) { it ->
            list.clear()
            list.addAll(it)

            val items = list.map {
                it.name
            }

            val adapter = ArrayAdapter(this, R.layout.item_category_dropdown, items)
            binding.category.setAdapter(adapter)
            binding.category.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val itemSelected = parent.getItemAtPosition(position)
                    val category = list.find { it.name == itemSelected }
                    val categoryId = category?.catId ?: 0

                    binding.category.setTag(R.id.category, categoryId)
                }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun datePicker() {
        binding.etAddFoodExpired.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val parsedDate: Date = dateFormat.parse(date) as Date
                    foodExpired = parsedDate.time

                    binding.etAddFoodExpired.setText(date)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}