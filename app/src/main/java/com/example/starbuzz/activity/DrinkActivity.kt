package com.example.starbuzz.activity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
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

		val flag: CheckBox = findViewById(R.id.favorite)
		flag.setOnClickListener { onFavoriteClicked() }

		val drinkId = intent.getIntExtra(EXTRA_DRINKID, 0)

		val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(this)
		try {
			val db = starbuzzDatabaseHelper.readableDatabase
			val cursor = db.query(
				"drink",
				arrayOf("name", "description", "image_resource_id", "favorite"),
				"_id = ?",
				arrayOf(drinkId.toString()),
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
				flag.isChecked = cursor.getInt(3) == 1
			}
			cursor.close()
			db.close()
		} catch (e: SQLiteException) {
			Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
		}
	}

	private fun onFavoriteClicked() {
		val drinkId = intent.getIntExtra(EXTRA_DRINKID, 0)
		UpdateDrinkTask(this, findViewById(R.id.ll)).execute(drinkId)
	}

	companion object {
		const val EXTRA_DRINKID = "drinkId"
	}

	private class UpdateDrinkTask(val context: Context, val view: View) :
		AsyncTask<Int, Void, Boolean>() {
		private lateinit var drinkValues: ContentValues

		override fun onPreExecute() {
			super.onPreExecute()
			val favorite: CheckBox = view.findViewById(R.id.favorite)
			drinkValues = ContentValues()
			drinkValues.put("favorite", favorite.isChecked)
		}

		override fun doInBackground(vararg p0: Int?): Boolean {
			val drinkId = p0[0]
			val starbuzzDatabaseHelper = StarbuzzDatabaseHelper(context)
			return try {
				val db = starbuzzDatabaseHelper.writableDatabase
				db.update(
					"drink",
					drinkValues,
					"_id = ?",
					arrayOf(drinkId.toString())
				)
				db.close()
				true
			} catch (e: SQLiteException) {
				false
			}
		}

		override fun onPostExecute(result: Boolean) {
			super.onPostExecute(result)
			if (!result)
				Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT).show()
		}
	}
}
