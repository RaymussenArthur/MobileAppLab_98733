package com.example.lab_week_06

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_06.model.CatModel
import com.example.lab_week_06.model.CatBreed
import com.example.lab_week_06.model.Gender

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler_view) }

    private val catAdapter by lazy {
        CatAdapter(layoutInflater, GlideImageLoader(this), object : CatAdapter.OnClickListener {
            override fun onItemClick(cat: CatModel) = showSelectionDialog(cat)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = catAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(catAdapter.SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)

        catAdapter.setData(
            listOf(
                CatModel(Gender.Male, CatBreed.BalineseJavanese, "Fred", "Silent and deadly", "https://cdn2.thecatapi.com/images/7dj.jpg"),
                CatModel(Gender.Female, CatBreed.ExoticShorthair, "Wilma", "Cuddly assassin", "https://cdn2.thecatapi.com/images/egv.jpg"),
                CatModel(Gender.Unknown, CatBreed.AmericanCurl, "Curious George", "Award winning investigator", "https://cdn2.thecatapi.com/images/bar.jpg"),
                CatModel(Gender.Male, CatBreed.ExoticShorthair, "Tom", "Always chasing Jerry", "https://cdn2.thecatapi.com/images/8fj.jpg"),
                CatModel(Gender.Female, CatBreed.BalineseJavanese, "Lucy", "Loves climbing curtains", "https://cdn2.thecatapi.com/images/b1a.jpg"),
                CatModel(Gender.Male, CatBreed.AmericanCurl, "Oscar", "Lazy but lovable", "https://cdn2.thecatapi.com/images/b9b.jpg"),
                CatModel(Gender.Female, CatBreed.ExoticShorthair, "Molly", "Curious and playful", "https://cdn2.thecatapi.com/images/bc2.jpg"),
                CatModel(Gender.Unknown, CatBreed.BalineseJavanese, "Shadow", "Mysterious night prowler", "https://cdn2.thecatapi.com/images/bco.jpg"),
                CatModel(Gender.Male, CatBreed.AmericanCurl, "Simba", "The young king", "https://cdn2.thecatapi.com/images/df5.jpg"),
                CatModel(Gender.Female, CatBreed.ExoticShorthair, "Nala", "Queen of the pride", "https://cdn2.thecatapi.com/images/ed3.jpg")
            )
        )
    }

    private fun showSelectionDialog(cat: CatModel) {
        AlertDialog.Builder(this)
            .setTitle("Cat Selected")
            .setMessage("You have selected cat ${cat.name}")
            .setPositiveButton("OK", null)
            .show()
    }
}
