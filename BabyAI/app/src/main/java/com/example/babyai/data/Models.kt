package com.example.babyai.data

/**
 * یک دسته (مثلاً حیوانات، رنگ‌ها، شکل‌ها، خانواده)
 */
data class Category(
    val id: String,
    val nameEn: String,
    val nameFa: String,
    val words: List<Word>
)

/**
 * یک کلمه با اسم فایل عکس و صوتش
 */
data class Word(
    val id: String,            // e.g. "dog"
    val categoryId: String,    // e.g. "animals"
    val nameEn: String,
    val nameFa: String,
    val photoCount: Int        // چند تا عکس برای این کلمه داریم
) {
    /** لیست اسم فایل عکس‌ها برای این کلمه، مثلاً animals_dog_1.jpg */
    fun photoFileNames(): List<String> =
        (1..photoCount).map { "${categoryId}_${id}_$it.jpg" }
}

/**
 * منبع مرکزی همه‌ی کلمات پروژه (طبق بریف: ۲۴ کلمه در ۴ دسته)
 */
object WordRepository {

    val animals = Category(
        id = "animals",
        nameEn = "Animals",
        nameFa = "حیوانات",
        words = listOf(
            Word("dog", "animals", "Dog", "سگ", 6),
            Word("cat", "animals", "Cat", "گربه", 4),
            Word("cow", "animals", "Cow", "گاو", 6),
            Word("chicken", "animals", "Chicken", "مرغ", 6),
            Word("duck", "animals", "Duck", "اردک", 6),
            Word("sheep", "animals", "Sheep", "گوسفند", 6),
            Word("fish", "animals", "Fish", "ماهی", 6),
            Word("elephant", "animals", "Elephant", "فیل", 6),
        )
    )

    val colors = Category(
        id = "colors",
        nameEn = "Colors",
        nameFa = "رنگ‌ها",
        words = listOf(
            Word("red", "colors", "Red", "قرمز", 3),
            Word("blue", "colors", "Blue", "آبی", 3),
            Word("yellow", "colors", "Yellow", "زرد", 4),
            Word("green", "colors", "Green", "سبز", 4),
            Word("orange", "colors", "Orange", "نارنجی", 4),
            Word("purple", "colors", "Purple", "بنفش", 4),
        )
    )

    val shapes = Category(
        id = "shapes",
        nameEn = "Shapes",
        nameFa = "شکل‌ها",
        words = listOf(
            Word("circle", "shapes", "Circle", "دایره", 4),
            Word("square", "shapes", "Square", "مربع", 4),
            Word("triangle", "shapes", "Triangle", "مثلث", 4),
            Word("star", "shapes", "Star", "ستاره", 4),
        )
    )

    val people = Category(
        id = "people",
        nameEn = "Family",
        nameFa = "خانواده",
        words = listOf(
            Word("mom", "people", "Mom", "مامان", 1),
            Word("dad", "people", "Dad", "بابا", 1),
            Word("baby", "people", "Baby", "بچه", 2),
            Word("grandma", "people", "Grandma", "مادربزرگ", 1),
            Word("grandpa", "people", "Grandpa", "پدربزرگ", 1),
            Word("sibling", "people", "Sibling", "خواهر/برادر", 2),
        )
    )

    val allCategories: List<Category> = listOf(animals, colors, shapes, people)

    fun categoryById(id: String): Category? = allCategories.find { it.id == id }
}
