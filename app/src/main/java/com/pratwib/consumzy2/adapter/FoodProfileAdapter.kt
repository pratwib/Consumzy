package com.pratwib.consumzy2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pratwib.consumzy2.R
import com.pratwib.consumzy2.database.FoodProfile
import com.pratwib.consumzy2.databinding.ItemFoodBinding
import com.pratwib.consumzy2.ui.home.EditFoodActivity
import com.pratwib.consumzy2.util.convertLongToDate
import com.pratwib.consumzy2.util.getDayDifference
import com.pratwib.consumzy2.util.progressBarHorizontal
import java.util.Calendar

class FoodProfileAdapter(private val context: Context, private val list: List<FoodProfile>) :
    RecyclerView.Adapter<FoodProfileAdapter.ViewHolder>() {

    companion object {
        const val FOOD_PROFILE = "food_profile"
    }

    inner class ViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodProfile = list[position]

        holder.binding.ivCategoryColor.setBackgroundColor(foodProfile.category.color)
        holder.binding.tvFoodName.text = foodProfile.food.name
        holder.binding.tvFoodQuatity.text = foodProfile.food.quantity

        val convertedDate = convertLongToDate(foodProfile.food.expiredDate)
        holder.binding.tvFoodExpiredDate.text = convertedDate

        val addedDate = foodProfile.food.timestamp
        val expiredDate = foodProfile.food.expiredDate
        val remainingDays = getDayDifference(addedDate, expiredDate)
        holder.binding.tvFoodDaysLeft.text = "$remainingDays days left"

        val nowDate = Calendar.getInstance().timeInMillis
        val progress = progressBarHorizontal(addedDate, nowDate, expiredDate)
        holder.binding.pbFood.progress = progress

        val desiredDate = expiredDate - (3 * 24 * 60 * 60 * 1000) // 3 hari dalam milidetik
        val desiredPercentage = progressBarHorizontal(addedDate, desiredDate, expiredDate)
        val progressTintList = when {
            holder.binding.pbFood.progress >= desiredPercentage -> {
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
            }

            else -> {
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.neon_carrot
                    )
                )
            }
        }
        holder.binding.pbFood.progressTintList = progressTintList

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, EditFoodActivity::class.java)
            intent.putExtra(FOOD_PROFILE, foodProfile)
            context.startActivity(intent)
        }
    }
}