package com.example.starbuzz

class Drink(
	private val name: String,
	private val description: String,
	private val imageResource: Int
) {
	public fun getName(): String = name
	public fun getDescription(): String = description
	public fun getImageResource(): Int = imageResource
	public override fun toString(): String = name
	val drinks = arrayOf<Drink>(
		Drink(
			"Latte",
			"A couple of espresso shots with steamed milk",
			R.drawable.latte
		),
		Drink(
			"Cappuccino",
			"Espresso, hot milk, and a steamed milk foam",
			R.drawable.cappuccino
		),
		Drink(
			"Filter",
			"Highest quality beans roasted and brewed fresh",
			R.drawable.filter
		)
	)
}
