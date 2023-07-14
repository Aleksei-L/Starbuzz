package com.example.starbuzz

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class DrinkCategoryActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_drink_category)

		val listAdapter = ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks)
		val listView: ListView = findViewById(R.id.list_drinks)
		listView.adapter = listAdapter

		val itemClickListener = OnItemClickListener { listDrinks, itemView, position, id ->
			val intent = Intent(this, DrinkActivity::class.java)
			intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
			startActivity(intent)
		}

		listView.onItemClickListener = itemClickListener
	}
}
