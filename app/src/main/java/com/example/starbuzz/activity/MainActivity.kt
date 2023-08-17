package com.example.starbuzz.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.starbuzz.R

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val itemClickListener = OnItemClickListener { listView, itemView, position, id ->
			if (position == 0) {
				val intent = Intent(this, DrinkCategoryActivity::class.java)
				startActivity(intent)
			}
		}

		val listView: ListView = findViewById(R.id.list_options)

		listView.onItemClickListener = itemClickListener
	}
}
