package com.newlifetech.babyhey.data

/**
 * یک ماسکات (همراه بچه توی اپ)
 * names: نقشه‌ی زبان -> اسم ماسکات
 */
data class Mascot(
    val id: String,
    val names: Map<String, String>,
    val drawableName: String   // اسم فایل عکس ماسکات در res/drawable
) {
    val nameEn: String get() = names["en"] ?: id
    val nameFa: String get() = names["fa"] ?: nameEn

    fun name(lang: String): String = names[lang] ?: nameEn
}

object MascotRepository {

    private fun m(id: String, drawableName: String, vararg names: Pair<String, String>): Mascot =
        Mascot(id, names.toMap(), drawableName)

    val all = listOf(
        m("bunny", "mascot_bunny",
            "en" to "Bunny", "fa" to "خرگوش", "sv" to "Kanin", "tr" to "Tavşan", "de" to "Hase",
            "fr" to "Lapin", "es" to "Conejo", "ru" to "Кролик", "zh" to "兔子", "hi" to "खरगोश", "ar" to "أرنب"),
        m("bear", "mascot_bear",
            "en" to "Bear", "fa" to "خرس", "sv" to "Björn", "tr" to "Ayı", "de" to "Bär",
            "fr" to "Ours", "es" to "Oso", "ru" to "Медведь", "zh" to "熊", "hi" to "भालू", "ar" to "دب"),
        m("dragonling", "mascot_dragonling",
            "en" to "Dragonling", "fa" to "اژدهای کوچولو", "sv" to "Lilla draken", "tr" to "Küçük Ejder",
            "de" to "Kleiner Drache", "fr" to "Petit dragon", "es" to "Pequeño dragón", "ru" to "Дракончик",
            "zh" to "小龙", "hi" to "छोटा ड्रैगन", "ar" to "تنين صغير"),
        m("puppy", "mascot_puppy",
            "en" to "Puppy", "fa" to "توله سگ", "sv" to "Valp", "tr" to "Yavru Köpek", "de" to "Welpe",
            "fr" to "Chiot", "es" to "Cachorro", "ru" to "Щенок", "zh" to "小狗", "hi" to "पिल्ला", "ar" to "جرو"),
        m("kitten", "mascot_kitten",
            "en" to "Kitten", "fa" to "بچه‌گربه", "sv" to "Kattunge", "tr" to "Yavru Kedi", "de" to "Kätzchen",
            "fr" to "Chaton", "es" to "Gatito", "ru" to "Котёнок", "zh" to "小猫", "hi" to "बिल्ली का बच्चा", "ar" to "قطة صغيرة"),
        m("babydeer", "mascot_babydeer",
            "en" to "Baby Deer", "fa" to "بچه‌آهو", "sv" to "Hjortkalv", "tr" to "Yavru Geyik", "de" to "Rehkitz",
            "fr" to "Faon", "es" to "Cervatillo", "ru" to "Оленёнок", "zh" to "小鹿", "hi" to "हिरण का बच्चा", "ar" to "غزال صغير"),
        m("duckling", "mascot_duckling",
            "en" to "Duckling", "fa" to "جوجه اردک", "sv" to "Ankunge", "tr" to "Yavru Ördek", "de" to "Entlein",
            "fr" to "Caneton", "es" to "Patito", "ru" to "Утёнок", "zh" to "小鸭", "hi" to "बत्तख का बच्चा", "ar" to "بطة صغيرة"),
        m("tigercub", "mascot_tigercub",
            "en" to "Tiger Cub", "fa" to "بچه‌ببر", "sv" to "Tigerunge", "tr" to "Yavru Kaplan", "de" to "Tigerjunges",
            "fr" to "Bébé tigre", "es" to "Cachorro de tigre", "ru" to "Тигрёнок", "zh" to "小老虎", "hi" to "बाघ का बच्चा", "ar" to "نمر صغير"),
        m("raccoonkit", "mascot_raccoonkit",
            "en" to "Baby Raccoon", "fa" to "راکون", "sv" to "Tvättbjörnsunge", "tr" to "Yavru Rakun", "de" to "Waschbärjunges",
            "fr" to "Bébé raton laveur", "es" to "Mapache bebé", "ru" to "Енотик", "zh" to "小浣熊", "hi" to "रैकून का बच्चा", "ar" to "راكون صغير"),
        m("babyotter", "mascot_babyotter",
            "en" to "Baby Otter", "fa" to "سمور آبی", "sv" to "Utterunge", "tr" to "Yavru Su Samuru", "de" to "Otterjunges",
            "fr" to "Bébé loutre", "es" to "Nutria bebé", "ru" to "Выдрёнок", "zh" to "小水獭", "hi" to "ऊदबिलाव का बच्चा", "ar" to "قندس صغير"),
        m("babydolphin", "mascot_babydolphin",
            "en" to "Baby Dolphin", "fa" to "دلفین کوچولو", "sv" to "Delfinunge", "tr" to "Yavru Yunus", "de" to "Delfinbaby",
            "fr" to "Bébé dauphin", "es" to "Delfín bebé", "ru" to "Дельфинёнок", "zh" to "小海豚", "hi" to "डॉल्फ़िन का बच्चा", "ar" to "دلفين صغير"),
        m("unicornfoal", "mascot_unicornfoal",
            "en" to "Baby Unicorn", "fa" to "تک‌شاخ", "sv" to "Enhörningsföl", "tr" to "Yavru Tekboynuz", "de" to "Einhornfohlen",
            "fr" to "Bébé licorne", "es" to "Unicornio bebé", "ru" to "Единорожек", "zh" to "小独角兽", "hi" to "यूनिकॉर्न का बच्चा", "ar" to "يونيكورن صغير"),
        m("babysheep", "mascot_babysheep",
            "en" to "Sheep", "fa" to "بره", "sv" to "Lamm", "tr" to "Kuzu", "de" to "Lamm",
            "fr" to "Agneau", "es" to "Cordero", "ru" to "Ягнёнок", "zh" to "小羊", "hi" to "मेमना", "ar" to "حمل"),
        m("foxcub", "mascot_foxcub",
            "en" to "Fox Cub", "fa" to "بچه‌روباه", "sv" to "Rävunge", "tr" to "Yavru Tilki", "de" to "Fuchsjunges",
            "fr" to "Bébé renard", "es" to "Zorro bebé", "ru" to "Лисёнок", "zh" to "小狐狸", "hi" to "लोमड़ी का बच्चा", "ar" to "ثعلب صغير"),
        m("pandacub", "mascot_pandacub",
            "en" to "Panda Cub", "fa" to "بچه‌پاندا", "sv" to "Pandaunge", "tr" to "Yavru Panda", "de" to "Pandajunges",
            "fr" to "Bébé panda", "es" to "Panda bebé", "ru" to "Панда", "zh" to "熊猫宝宝", "hi" to "पांडा का बच्चा", "ar" to "باندا صغير"),
        m("penguinchick", "mascot_penguinchick",
            "en" to "Baby Penguin", "fa" to "جوجه پنگوئن", "sv" to "Pingvinunge", "tr" to "Yavru Penguen", "de" to "Pinguinküken",
            "fr" to "Bébé pingouin", "es" to "Pingüino bebé", "ru" to "Пингвинёнок", "zh" to "小企鹅", "hi" to "पेंगुइन का बच्चा", "ar" to "بطريق صغير"),
        m("koalababy", "mascot_koalababy",
            "en" to "Baby Koala", "fa" to "کوالا", "sv" to "Koalaunge", "tr" to "Yavru Koala", "de" to "Koalababy",
            "fr" to "Bébé koala", "es" to "Koala bebé", "ru" to "Коала", "zh" to "考拉宝宝", "hi" to "कोआला का बच्चा", "ar" to "كوالا صغير"),
        m("owlchick", "mascot_owlchick",
            "en" to "Baby Owl", "fa" to "جوجه جغد", "sv" to "Ugglunge", "tr" to "Yavru Baykuş", "de" to "Eulenküken",
            "fr" to "Bébé hibou", "es" to "Búho bebé", "ru" to "Совёнок", "zh" to "小猫头鹰", "hi" to "उल्लू का बच्चा", "ar" to "بومة صغيرة"),
        m("lioncub", "mascot_lioncub",
            "en" to "Lion Cub", "fa" to "بچه‌شیر", "sv" to "Lejonunge", "tr" to "Yavru Aslan", "de" to "Löwenjunges",
            "fr" to "Bébé lion", "es" to "Cachorro de león", "ru" to "Львёнок", "zh" to "小狮子", "hi" to "शेर का बच्चा", "ar" to "أسد صغير"),
        m("babyelephant2", "mascot_babyelephant2",
            "en" to "Baby Elephant", "fa" to "فیل کوچولو", "sv" to "Liten elefant", "tr" to "Yavru Fil", "de" to "Elefantenbaby",
            "fr" to "Bébé éléphant", "es" to "Elefante bebé", "ru" to "Слонёнок", "zh" to "小象", "hi" to "हाथी का बच्चा", "ar" to "فيل صغير"),
        m("babygiraffe", "mascot_babygiraffe",
            "en" to "Baby Giraffe", "fa" to "بچه‌زرافه", "sv" to "Giraffunge", "tr" to "Yavru Zürafa", "de" to "Giraffenbaby",
            "fr" to "Bébé girafe", "es" to "Jirafa bebé", "ru" to "Жирафёнок", "zh" to "小长颈鹿", "hi" to "जिराफ़ का बच्चा", "ar" to "زرافة صغيرة"),
        m("hedgehog", "mascot_hedgehog",
            "en" to "Hedgehog", "fa" to "جوجه‌تیغی", "sv" to "Igelkott", "tr" to "Kirpi", "de" to "Igel",
            "fr" to "Hérisson", "es" to "Erizo", "ru" to "Ёжик", "zh" to "刺猬", "hi" to "हेजहॉग", "ar" to "قنفذ"),
        m("babymonkey", "mascot_babymonkey",
            "en" to "Baby Monkey", "fa" to "بچه‌میمون", "sv" to "Apunge", "tr" to "Yavru Maymun", "de" to "Affenbaby",
            "fr" to "Bébé singe", "es" to "Mono bebé", "ru" to "Обезьянка", "zh" to "小猴子", "hi" to "बंदर का बच्चा", "ar" to "قرد صغير"),
    )
}
