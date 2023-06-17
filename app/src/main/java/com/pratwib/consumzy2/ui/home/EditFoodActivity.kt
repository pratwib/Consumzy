package com.pratwib.consumzy2.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.pratwib.consumzy2.MyApplication
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.database.Food
import com.pratwib.consumzy2.database.FoodProfile
import com.pratwib.consumzy2.databinding.ActivityEditFoodBinding
import com.pratwib.consumzy2.util.timeStamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Suppress("NAME_SHADOWING", "DEPRECATION")
class EditFoodActivity : AppCompatActivity() {

    companion object {
        const val FOOD_PROFILE = "food_profile"
    }

    private lateinit var binding: ActivityEditFoodBinding

    private var list = mutableListOf<Category>()

    private var foodId: Int = 0
    private var foodExpired: Long = 0
    private var foodTimestamp: Long = 0

    private val editFoodViewModel: EditFoodViewModel by viewModels {
        EditFoodViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        datePicker()
    }

    override fun onResume() {
        super.onResume()

        getFood()
        editFood()
        dropdownCategory()
    }

    private fun getFood() {
        val foodProfile = intent.getParcelableExtra<FoodProfile>(FOOD_PROFILE)
        foodProfile?.let {
            foodId = it.food.foodId
            binding.etEditFoodName.setText(it.food.name)
            binding.etEditFoodQty.setText(it.food.quantity)
            binding.category.setText(it.category.name)

            foodExpired = it.food.expiredDate
            val date = Date(it.food.expiredDate)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val parsedDate = dateFormat.format(date)
            binding.etEditFoodExpired.setText(parsedDate)

            binding.etEditFoodPlace.setText(foodProfile.food.place)
            foodTimestamp = it.food.timestamp
        }
    }

    private fun editFood() {
        binding.btnEditFood.setOnClickListener {
            val foodName = binding.etEditFoodName.text.toString()
            val foodQty = binding.etEditFoodQty.text.toString()

            val foodCat = binding.category.text.toString()
            val category = list.find { it.name == foodCat }
            val categoryId = category?.catId ?: 0

            val foodPlace = binding.etEditFoodPlace.text.toString()

            if (foodName.isNotEmpty() && foodQty.isNotEmpty() && categoryId > -1 && foodExpired != 0L && foodPlace.isNotEmpty()) {
                editFoodViewModel.updateFood(
                    Food(
                        foodId = foodId,
                        name = foodName,
                        quantity = foodQty,
                        cId = categoryId,
                        expiredDate = foodExpired,
                        place = foodPlace,
                        timestamp = foodTimestamp,
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

    private fun setupToolbar() {
        setSupportActionBar(binding.tbEditFood)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun datePicker() {
        binding.etEditFoodExpired.setOnClickListener {
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

                    binding.etEditFoodExpired.setText(date)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun dropdownCategory() {
        editFoodViewModel.getAllCategory().observe(this) { it ->
            list.clear()
            list.addAll(it)

            val items = list.map {
                it.name
            }

            val adapter = ArrayAdapter(this, R.layout.item_category_dropdown, items)
            binding.category.setAdapter(adapter)
            binding.category.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val itemSelected = parent.getItemAtPosition(position).toString()

                    binding.category.setText(itemSelected)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_food_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                editFoodViewModel.deleteFoodById(foodId)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}