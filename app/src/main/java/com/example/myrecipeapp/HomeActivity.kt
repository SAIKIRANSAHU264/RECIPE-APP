package com.example.myrecipeapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerViewAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.search.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }


        binding.salad.setOnClickListener{
            var myintent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myintent.putExtra("TITLE","Salad")
            myintent.putExtra("CATEGORY","Salad")
            startActivity(myintent)
        }
        binding.maindish.setOnClickListener{
            var myintent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myintent.putExtra("TITLE","Main Dish")
            myintent.putExtra("CATEGORY","Dish")
            startActivity(myintent)
        }
        binding.drinks.setOnClickListener {
            var myintent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myintent.putExtra("TITLE","Drinks")
            myintent.putExtra("CATEGORY","Drinks")
            startActivity(myintent)
        }
        binding.more.setOnClickListener {
            var dialog= Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)

            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
        binding.desserts.setOnClickListener {
            var myintent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myintent.putExtra("TITLE","Desserts")
            myintent.putExtra("CATEGORY","Desserts")
            startActivity(myintent)
        }

    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.recyclerViewPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipedata.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)

            }
            recyclerViewAdapter=PopularAdapter(dataList,this)
            binding.recyclerViewPopular.adapter=recyclerViewAdapter
        }
    }
}