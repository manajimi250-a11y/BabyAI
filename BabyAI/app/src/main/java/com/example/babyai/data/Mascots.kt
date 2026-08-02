package com.example.babyai.data

/**
 * یک ماسکات (همراه بچه توی اپ)
 */
data class Mascot(
    val id: String,
    val nameEn: String,
    val nameFa: String,
    val drawableName: String   // اسم فایل عکس ماسکات در res/drawable
)

object MascotRepository {
    val all = listOf(
        Mascot("bunny", "Bunny", "خرگوش", "mascot_bunny"),
        Mascot("bear", "Bear", "خرس", "mascot_bear"),
        Mascot("dragonling", "Dragonling", "اژدهای کوچولو", "mascot_dragonling"),
        Mascot("star_sprite", "Star Sprite", "پری ستاره", "mascot_star_sprite"),
    )
}
