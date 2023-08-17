package com.example.starbuzz.activity

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.example.starbuzz.R
import com.example.starbuzz.db.StarbuzzDatabaseHelper

class DrinkCategoryActivity : AppCompatActivity() {
	private lateinit var db: SQLiteDatabase
	private lateinit var cursor: Cursor

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_drink_category)

		val listView: ListView = findViewById(R.id.list_drinks)

		val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
		try {
			db = starbuzzDatabaseHelper.readableDatabase
			cursor = db.query(
				"drink",
				arrayOf("_id", "name"),
				null,
				null,
				null,
				null,
				null
			)
			val listAdapter = SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_1,
				cursor,
				arrayOf("name"),
				intArrayOf(android.R.id.text1),
				0
			)
			listView.adapter = listAdapter
		} catch (e: SQLiteException) {
			Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
		}

		val itemClickListener = OnItemClickListener { listDrinks, itemView, position, id ->
			val intent = Intent(this, DrinkActivity::class.java)
			intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
			startActivity(intent)
		}

		listView.onItemClickListener = itemClickListener
	}

	override fun onDestroy() {
		super.onDestroy()
		cursor.close()
		db.close()
	}
}
