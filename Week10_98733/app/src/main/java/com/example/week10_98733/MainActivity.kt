package com.example.week10_98733

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.week10_98733.database.Total
import com.example.week10_98733.database.TotalDatabase
import com.example.week10_98733.database.TotalObject
import com.example.week10_98733.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val db: TotalDatabase by lazy { prepareDatabase() }
    
    private val viewModel: TotalViewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeValueFromDatabase()
        prepareViewModel()
    }

    override fun onStart() {
        super.onStart()
        val total = db.totalDao().getTotal(ID).first()
        Toast.makeText(this, "Last updated: ${total.total.date}", Toast.LENGTH_SHORT).show()
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if (total.isEmpty()) {
            db.totalDao().insert(Total(id = 1, total = TotalObject(0, Date().toString())))
        } else {
            viewModel.setTotal(total.first().total.value)
        }
    }
    
    companion object {
        const val ID: Long = 1
    }

    override fun onPause() {
        super.onPause()
        viewModel.total.value?.let {
            db.totalDao().update(Total(ID, TotalObject(it, Date().toString())))
        }
    }

    private fun prepareViewModel(){
        viewModel.total.observe(this) { total ->
            updateText(total)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text = getString(R.string.text_total, total)
    }
}