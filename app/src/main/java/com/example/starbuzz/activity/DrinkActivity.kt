package com.example.starbuzz.activity

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.starbuzz.R
import com.example.starbuzz.db.StarbuzzDatabaseHelper

class DrinkActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_drink)

		val drinkId = intent.getIntExtra(EXTRA_DRINKID, 0)

		val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
		try {
			val db = starbuzzDatabaseHelper.readableDatabase
			val cursor = db.query(
					"drink",
					arrayOf("name", "description", "image_resource_id"),
					"_id = ?",
					arrayOf((drinkId + 1).toString()),
					null,
					null,
					null
			)
			if (cursor.moveToFirst()) {
				val name: TextView = findViewById(R.id.name)
				val description: TextView = findViewById(R.id.description)
				val photo: ImageView = findViewById(R.id.photo)
				name.text = cursor.getString(0)
				description.text = cursor.getString(1)
				photo.setImageResource(cursor.getInt(2))
				photo.contentDescription = name.text
			}
			cursor.close()
			db.close()
		} catch (e: SQLiteException) {
			Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
		}
	}

	companion object {
		const val EXTRA_DRINKID = "drinkId"
	}
}
