package com.example.myrecipeapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myrecipeapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerViewAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            this@SearchActivity,
            AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipedata.db")
            .build()

        var daoObject = db.getDao()
        recipes = (daoObject.getAll() ?: emptyList()) as List<Recipe> // Ensure recipes is not null

        setUpRecyclerView()
        binding.backBtn.setOnClickListener{
            finish()
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    filterData(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed
            }
        })
    }

    private fun filterData(filterText: String) {
        val filterData = ArrayList<Recipe>()
        for (recipe in recipes) {
            if (recipe.tittle.lowercase().contains(filterText.lowercase())) {
                filterData.add(recipe)
            }
        }
        recyclerViewAdapter.filterList(filterData)
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        for (recipe in recipes) {
            if (recipe.category.contains("Popular")) {
                dataList.add(recipe)
            }
        }

        recyclerViewAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = recyclerViewAdapter
    }
}
