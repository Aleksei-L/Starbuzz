package com.example.starbuzz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StarbuzzDatabaseHelper(context: Context) :
	SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
	override fun onCreate(db: SQLiteDatabase?) {
		updateMyDatabase(db, 0, DB_VERSION)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		updateMyDatabase(db, oldVersion, newVersion)
	}

	private fun updateMyDatabase(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		if (db == null)
			return
		if (oldVersion < 1) {
			db.execSQL("CREATE TABLE drink(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, description TEXT, image_resource_id INTEGER);")

			insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte)
			insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino)
			insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter)
		}
		if (oldVersion < 2) {
			db.execSQL("ALTER TABLE drink ADD COLUMN favorite NUMERIC;")
		}
	}

	private fun insertDrink(db: SQLiteDatabase?, name: String, description: String, resourceId: Int) {
		if (db == null)
			return
		val drinkValues = ContentValues()
		drinkValues.put("name", name)
		drinkValues.put("description", description)
		drinkValues.put("image_resource_id", resourceId)
		db.insert("drink", null, drinkValues)
	}

	companion object {
		const val DB_NAME = "starbuzz"
		const val DB_VERSION = 2
	}
}
