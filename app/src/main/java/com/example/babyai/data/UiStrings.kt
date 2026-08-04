package com.example.babyai.data

/**
 * منبع مرکزی متن‌های ثابت رابط کاربری (نه اسم کلمات، که تو Models.kt هست)
 * برای هر ۱۱ زبون. اضافه‌کردن یه صفحه‌ی جدید = اضافه‌کردن چندتا key این‌جا،
 * نه دستکاری if/else تو خود صفحه.
 */
object UiStrings {

    private val strings: Map<String, Map<String, String>> = mapOf(

        // دکمه‌ی انتخاب زبان (صفحه‌ی خوش‌آمدگویی)
        "language_button" to mapOf(
            "en" to "Language", "fa" to "زبان", "sv" to "Språk", "tr" to "Dil",
            "de" to "Sprache", "fr" to "Langue", "es" to "Idioma", "ru" to "Язык",
            "zh" to "语言", "hi" to "भाषा", "ar" to "اللغة"
        ),

        // ActivityHubScreen
        "hub_title" to mapOf(
            "en" to "What do you want to do?", "fa" to "چی می‌خوای بکنی؟", "sv" to "Vad vill du göra?",
            "tr" to "Ne yapmak istersin?", "de" to "Was möchtest du tun?", "fr" to "Que veux-tu faire ?",
            "es" to "¿Qué quieres hacer?", "ru" to "Что ты хочешь делать?", "zh" to "你想做什么？",
            "hi" to "तुम क्या करना चाहते हो?", "ar" to "ماذا تريد أن تفعل؟"
        ),
        "hub_learn_title" to mapOf(
            "en" to "Smart Learning", "fa" to "یادگیری هوشمند", "sv" to "Smart lärande",
            "tr" to "Akıllı Öğrenme", "de" to "Intelligentes Lernen", "fr" to "Apprentissage intelligent",
            "es" to "Aprendizaje inteligente", "ru" to "Умное обучение", "zh" to "智能学习",
            "hi" to "स्मार्ट लर्निंग", "ar" to "التعلم الذكي"
        ),
        "hub_learn_subtitle" to mapOf(
            "en" to "Animals, colors, shapes & family", "fa" to "حیوانات، رنگ‌ها، شکل‌ها و خانواده",
            "sv" to "Djur, färger, former och familj", "tr" to "Hayvanlar, renkler, şekiller ve aile",
            "de" to "Tiere, Farben, Formen und Familie", "fr" to "Animaux, couleurs, formes et famille",
            "es" to "Animales, colores, formas y familia", "ru" to "Животные, цвета, формы и семья",
            "zh" to "动物、颜色、形状和家庭", "hi" to "जानवर, रंग, आकृतियाँ और परिवार",
            "ar" to "الحيوانات والألوان والأشكال والعائلة"
        ),
        "hub_games_title" to mapOf(
            "en" to "Games", "fa" to "بازی‌ها", "sv" to "Spel", "tr" to "Oyunlar",
            "de" to "Spiele", "fr" to "Jeux", "es" to "Juegos", "ru" to "Игры",
            "zh" to "游戏", "hi" to "खेल", "ar" to "الألعاب"
        ),
        "hub_games_subtitle" to mapOf(
            "en" to "Memory match & more games", "fa" to "بازی حافظه و بازی‌های بیشتر",
            "sv" to "Memory och fler spel", "tr" to "Hafıza oyunu ve daha fazlası",
            "de" to "Memory und mehr Spiele", "fr" to "Memory et plus de jeux",
            "es" to "Memoria y más juegos", "ru" to "Игра на память и другие игры",
            "zh" to "记忆配对及更多游戏", "hi" to "मेमोरी गेम और अधिक खेल",
            "ar" to "لعبة الذاكرة والمزيد من الألعاب"
        ),
        "hub_stories_title" to mapOf(
            "en" to "Stories", "fa" to "داستان‌ها", "sv" to "Sagor", "tr" to "Masallar",
            "de" to "Märchen", "fr" to "Histoires", "es" to "Historias", "ru" to "Сказки",
            "zh" to "故事", "hi" to "कहानियां", "ar" to "قصص"
        ),
        "hub_stories_subtitle" to mapOf(
            "en" to "Fun interactive stories", "fa" to "داستان‌های تعاملی و بامزه",
            "sv" to "Roliga interaktiva sagor", "tr" to "Eğlenceli etkileşimli masallar",
            "de" to "Lustige interaktive Geschichten", "fr" to "Histoires interactives amusantes",
            "es" to "Historias interactivas divertidas", "ru" to "Весёлые интерактивные сказки",
            "zh" to "有趣的互动故事", "hi" to "मज़ेदार इंटरैक्टिव कहानियां",
            "ar" to "قصص تفاعلية ممتعة"
        ),
    )

    /** ترجمه‌ی متن با کد کلید؛ اگه زبون پیدا نشد میره سراغ انگلیسی؛ اگه کلید هم پیدا نشد خود کلید برمی‌گرده */
    fun t(key: String, lang: String): String =
        strings[key]?.get(lang) ?: strings[key]?.get("en") ?: key
}
