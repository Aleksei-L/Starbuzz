package com.example.starbuzz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DrinkActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_drink)

		val drinkId = intent.getIntExtra(EXTRA_DRINKID, 0)
		val drink = Drink.drinks[drinkId]

		val photo: ImageView = findViewById(R.id.photo)
		val name: TextView = findViewById(R.id.name)
		val description: TextView = findViewById(R.id.description)

		photo.setImageResource(drink.getImageResource())
		photo.contentDescription = drink.getName()
		name.text = drink.getName()
		description.text = drink.getDescription()
	}

	companion object {
		const val EXTRA_DRINKID = "drinkId"
	}
}
