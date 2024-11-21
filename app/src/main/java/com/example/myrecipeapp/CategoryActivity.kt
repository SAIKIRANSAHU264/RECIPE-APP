package com.example.myrecipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecipeapp.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    //advance way to initialize binding
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        title=intent.getStringExtra("TITLE")
        setUpRecyclerView()
        binding.backBtn.setOnClickListener {
            finish()
        }

    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.categoryRv.layoutManager =
            LinearLayoutManager(this)
        var db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipedata.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                dataList.add(recipes[i]!!)

            }
            recyclerViewAdapter=CategoryAdapter(dataList,this)
            binding.categoryRv.adapter=recyclerViewAdapter
        }
    }
}