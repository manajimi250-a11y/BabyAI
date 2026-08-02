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
        Mascot("puppy", "Puppy", "توله سگ", "mascot_puppy"),
        Mascot("kitten", "Kitten", "بچه‌گربه", "mascot_kitten"),
        Mascot("babydeer", "Baby Deer", "بچه‌آهو", "mascot_babydeer"),
        Mascot("duckling", "Duckling", "جوجه اردک", "mascot_duckling"),
        Mascot("tigercub", "Tiger Cub", "بچه‌ببر", "mascot_tigercub"),
        Mascot("raccoonkit", "Raccoon", "راکون", "mascot_raccoonkit"),
        Mascot("babyotter", "Otter", "سمور آبی", "mascot_babyotter"),
        Mascot("babydolphin", "Dolphin", "دلفین کوچولو", "mascot_babydolphin"),
        Mascot("unicornfoal", "Unicorn", "تک‌شاخ", "mascot_unicornfoal"),
        Mascot("babysheep", "Sheep", "بره", "mascot_babysheep"),
        Mascot("foxcub", "Fox Cub", "بچه‌روباه", "mascot_foxcub"),
        Mascot("pandacub", "Panda Cub", "بچه‌پاندا", "mascot_pandacub"),
        Mascot("penguinchick", "Penguin", "جوجه پنگوئن", "mascot_penguinchick"),
        Mascot("koalababy", "Koala", "کوالا", "mascot_koalababy"),
        Mascot("owlchick", "Owl", "جوجه جغد", "mascot_owlchick"),
        Mascot("lioncub", "Lion Cub", "بچه‌شیر", "mascot_lioncub"),
        Mascot("babyelephant2", "Elephant", "فیل کوچولو", "mascot_babyelephant2"),
        Mascot("babygiraffe", "Giraffe", "بچه‌زرافه", "mascot_babygiraffe"),
        Mascot("hedgehog", "Hedgehog", "جوجه‌تیغی", "mascot_hedgehog"),
        Mascot("babymonkey", "Monkey", "بچه‌میمون", "mascot_babymonkey"),
    )
}
