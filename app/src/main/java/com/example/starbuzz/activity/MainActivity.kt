package com.example.starbuzz.activity

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starbuzz.R
import com.example.starbuzz.db.StarbuzzDatabaseHelper

class MainActivity : AppCompatActivity() {
	private lateinit var db: SQLiteDatabase
	private lateinit var favoritesCursor: Cursor

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupOptionsListView()
		setupFavoritesListView()
	}

	override fun onRestart() {
		super.onRestart()
		val newCursor = db.query(
			"drink",
			arrayOf("_id", "name"),
			"favorite = 1",
			null,
			null,
			null,
			null
		)
		val listFavorites: ListView = findViewById(R.id.list_favorites)
		val adapter = listFavorites.adapter as SimpleCursorAdapter
		adapter.changeCursor(newCursor)
		favoritesCursor = newCursor
	}

	private fun setupOptionsListView() {
		val itemClickListener = OnItemClickListener { listView, itemView, position, id ->
			if (position == 0) {
				val intent = Intent(this, DrinkCategoryActivity::class.java)
				startActivity(intent)
			}
		}

		val listView: ListView = findViewById(R.id.list_options)
		listView.onItemClickListener = itemClickListener
	}

	private fun setupFavoritesListView() {
		val listFavorites: ListView = findViewById(R.id.list_favorites)

		val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
		try {
			db = starbuzzDatabaseHelper.readableDatabase
			favoritesCursor = db.query(
				"drink",
				arrayOf("_id", "name"),
				"favorite = 1",
				null,
				null,
				null,
				null
			)

			val cursorAdapter = SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_1,
				favoritesCursor,
				arrayOf("name"),
				intArrayOf(android.R.id.text1),
				0
			)
			listFavorites.adapter = cursorAdapter
		} catch (e: SQLiteException) {
			Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
		}

		listFavorites.onItemClickListener =
			OnItemClickListener { listView, v, position, id ->
				val intent = Intent(this, DrinkActivity::class.java)
				intent.putExtra(DrinkActivity.EXTRA_DRINKID, id.toInt())
				startActivity(intent)
			}
	}

	override fun onDestroy() {
		super.onDestroy()
		favoritesCursor.close()
		db.close()
	}
}
