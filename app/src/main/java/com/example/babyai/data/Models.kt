package com.example.babyai.data

/**
 * یک دسته (مثلاً حیوانات، رنگ‌ها، شکل‌ها، خانواده)
 * names: نقشه‌ی زبان -> اسم دسته، مثلاً "en" to "Animals", "fa" to "حیوانات"
 */
data class Category(
    val id: String,
    val names: Map<String, String>,
    val words: List<Word>
) {
    val nameEn: String get() = names["en"] ?: id
    val nameFa: String get() = names["fa"] ?: nameEn

    /** اسم این دسته به هر زبان؛ اگه ترجمه نبود، برمی‌گرده به انگلیسی */
    fun name(lang: String): String = names[lang] ?: nameEn
}

/**
 * یک کلمه با اسم فایل عکس و صوتش
 * names: نقشه‌ی زبان -> اسم کلمه، مثلاً "en" to "Dog", "fa" to "سگ", "sv" to "Hund", ...
 */
data class Word(
    val id: String,            // e.g. "dog"
    val categoryId: String,    // e.g. "animals"
    val names: Map<String, String>,
    val photoCount: Int        // چند تا عکس برای این کلمه داریم
) {
    val nameEn: String get() = names["en"] ?: id
    val nameFa: String get() = names["fa"] ?: nameEn

    /** اسم این کلمه به هر زبان؛ اگه ترجمه نبود، برمی‌گرده به انگلیسی */
    fun name(lang: String): String = names[lang] ?: nameEn

    /** لیست اسم فایل عکس‌ها برای این کلمه، مثلاً animals_dog_1.jpg */
    fun photoFileNames(): List<String> =
        (1..photoCount).map { "${categoryId}_${id}_$it.jpg" }
}

/**
 * زبان‌های پشتیبانی‌شده در کل اپ (کد دو حرفی استاندارد)
 * en=انگلیسی, fa=فارسی, sv=سوئدی, tr=ترکی, de=آلمانی, fr=فرانسوی,
 * es=اسپانیایی, ru=روسی, zh=چینی, hi=هندی, ar=عربی
 */
object SupportedLanguages {
    val codes = listOf("en", "fa", "sv", "tr", "de", "fr", "es", "ru", "zh", "hi", "ar")

    val displayNames = mapOf(
        "en" to "English",
        "fa" to "فارسی",
        "sv" to "Svenska",
        "tr" to "Türkçe",
        "de" to "Deutsch",
        "fr" to "Français",
        "es" to "Español",
        "ru" to "Русский",
        "zh" to "中文",
        "hi" to "हिन्दी",
        "ar" to "العربية"
    )

    /** زبان‌هایی که راست‌به‌چپ نوشته می‌شن */
    val rtlLanguages = setOf("fa", "ar")
}

/**
 * منبع مرکزی همه‌ی کلمات پروژه
 */
object WordRepository {

    /** میان‌بر برای ساخت یه Word با نقشه‌ی اسم‌ها، تا نوشتنش تمیزتر باشه */
    private fun w(id: String, categoryId: String, photoCount: Int, vararg names: Pair<String, String>): Word =
        Word(id, categoryId, names.toMap(), photoCount)

    val animals = Category(
        id = "animals",
        names = mapOf(
            "en" to "Animals", "fa" to "حیوانات", "sv" to "Djur", "tr" to "Hayvanlar",
            "de" to "Tiere", "fr" to "Animaux", "es" to "Animales", "ru" to "Животные",
            "zh" to "动物", "hi" to "जानवर", "ar" to "حيوانات"
        ),
        words = listOf(
            w("dog", "animals", 6,
                "en" to "Dog", "fa" to "سگ", "sv" to "Hund", "tr" to "Köpek", "de" to "Hund",
                "fr" to "Chien", "es" to "Perro", "ru" to "Собака", "zh" to "狗", "hi" to "कुत्ता", "ar" to "كلب"),
            w("cat", "animals", 4,
                "en" to "Cat", "fa" to "گربه", "sv" to "Katt", "tr" to "Kedi", "de" to "Katze",
                "fr" to "Chat", "es" to "Gato", "ru" to "Кошка", "zh" to "猫", "hi" to "बिल्ली", "ar" to "قطة"),
            w("cow", "animals", 6,
                "en" to "Cow", "fa" to "گاو", "sv" to "Ko", "tr" to "İnek", "de" to "Kuh",
                "fr" to "Vache", "es" to "Vaca", "ru" to "Корова", "zh" to "牛", "hi" to "गाय", "ar" to "بقرة"),
            w("chicken", "animals", 6,
                "en" to "Chicken", "fa" to "مرغ", "sv" to "Kyckling", "tr" to "Tavuk", "de" to "Huhn",
                "fr" to "Poule", "es" to "Gallina", "ru" to "Курица", "zh" to "鸡", "hi" to "मुर्गी", "ar" to "دجاجة"),
            w("duck", "animals", 6,
                "en" to "Duck", "fa" to "اردک", "sv" to "Anka", "tr" to "Ördek", "de" to "Ente",
                "fr" to "Canard", "es" to "Pato", "ru" to "Утка", "zh" to "鸭子", "hi" to "बत्तख", "ar" to "بطة"),
            w("sheep", "animals", 6,
                "en" to "Sheep", "fa" to "گوسفند", "sv" to "Får", "tr" to "Koyun", "de" to "Schaf",
                "fr" to "Mouton", "es" to "Oveja", "ru" to "Овца", "zh" to "绵羊", "hi" to "भेड़", "ar" to "خروف"),
            w("fish", "animals", 6,
                "en" to "Fish", "fa" to "ماهی", "sv" to "Fisk", "tr" to "Balık", "de" to "Fisch",
                "fr" to "Poisson", "es" to "Pez", "ru" to "Рыба", "zh" to "鱼", "hi" to "मछली", "ar" to "سمكة"),
            w("elephant", "animals", 6,
                "en" to "Elephant", "fa" to "فیل", "sv" to "Elefant", "tr" to "Fil", "de" to "Elefant",
                "fr" to "Éléphant", "es" to "Elefante", "ru" to "Слон", "zh" to "大象", "hi" to "हाथी", "ar" to "فيل"),
            w("turtle", "animals", 6,
                "en" to "Turtle", "fa" to "لاک‌پشت", "sv" to "Sköldpadda", "tr" to "Kaplumbağa", "de" to "Schildkröte",
                "fr" to "Tortue", "es" to "Tortuga", "ru" to "Черепаха", "zh" to "乌龟", "hi" to "कछुआ", "ar" to "سلحفاة"),
            w("horse", "animals", 6,
                "en" to "Horse", "fa" to "اسب", "sv" to "Häst", "tr" to "At", "de" to "Pferd",
                "fr" to "Cheval", "es" to "Caballo", "ru" to "Лошадь", "zh" to "马", "hi" to "घोड़ा", "ar" to "حصان"),
            w("goat", "animals", 6,
                "en" to "Goat", "fa" to "بز", "sv" to "Get", "tr" to "Keçi", "de" to "Ziege",
                "fr" to "Chèvre", "es" to "Cabra", "ru" to "Коза", "zh" to "山羊", "hi" to "बकरी", "ar" to "ماعز"),
            w("rabbit", "animals", 6,
                "en" to "Rabbit", "fa" to "خرگوش", "sv" to "Kanin", "tr" to "Tavşan", "de" to "Hase",
                "fr" to "Lapin", "es" to "Conejo", "ru" to "Кролик", "zh" to "兔子", "hi" to "खरगोश", "ar" to "أرنب"),
            w("pig", "animals", 6,
                "en" to "Pig", "fa" to "خوک", "sv" to "Gris", "tr" to "Domuz", "de" to "Schwein",
                "fr" to "Cochon", "es" to "Cerdo", "ru" to "Свинья", "zh" to "猪", "hi" to "सुअर", "ar" to "خنزير"),
        )
    )

    val colors = Category(
        id = "colors",
        names = mapOf(
            "en" to "Colors", "fa" to "رنگ‌ها", "sv" to "Färger", "tr" to "Renkler",
            "de" to "Farben", "fr" to "Couleurs", "es" to "Colores", "ru" to "Цвета",
            "zh" to "颜色", "hi" to "रंग", "ar" to "ألوان"
        ),
        words = listOf(
            w("red", "colors", 3,
                "en" to "Red", "fa" to "قرمز", "sv" to "Röd", "tr" to "Kırmızı", "de" to "Rot",
                "fr" to "Rouge", "es" to "Rojo", "ru" to "Красный", "zh" to "红色", "hi" to "लाल", "ar" to "أحمر"),
            w("blue", "colors", 3,
                "en" to "Blue", "fa" to "آبی", "sv" to "Blå", "tr" to "Mavi", "de" to "Blau",
                "fr" to "Bleu", "es" to "Azul", "ru" to "Синий", "zh" to "蓝色", "hi" to "नीला", "ar" to "أزرق"),
            w("yellow", "colors", 4,
                "en" to "Yellow", "fa" to "زرد", "sv" to "Gul", "tr" to "Sarı", "de" to "Gelb",
                "fr" to "Jaune", "es" to "Amarillo", "ru" to "Жёлтый", "zh" to "黄色", "hi" to "पीला", "ar" to "أصفر"),
            w("green", "colors", 4,
                "en" to "Green", "fa" to "سبز", "sv" to "Grön", "tr" to "Yeşil", "de" to "Grün",
                "fr" to "Vert", "es" to "Verde", "ru" to "Зелёный", "zh" to "绿色", "hi" to "हरा", "ar" to "أخضر"),
            w("orange", "colors", 4,
                "en" to "Orange", "fa" to "نارنجی", "sv" to "Orange", "tr" to "Turuncu", "de" to "Orange",
                "fr" to "Orange", "es" to "Naranja", "ru" to "Оранжевый", "zh" to "橙色", "hi" to "नारंगी", "ar" to "برتقالي"),
            w("purple", "colors", 4,
                "en" to "Purple", "fa" to "بنفش", "sv" to "Lila", "tr" to "Mor", "de" to "Lila",
                "fr" to "Violet", "es" to "Morado", "ru" to "Фиолетовый", "zh" to "紫色", "hi" to "बैंगनी", "ar" to "بنفسجي"),
            w("brown", "colors", 5,
                "en" to "Brown", "fa" to "قهوه‌ای", "sv" to "Brun", "tr" to "Kahverengi", "de" to "Braun",
                "fr" to "Marron", "es" to "Marrón", "ru" to "Коричневый", "zh" to "棕色", "hi" to "भूरा", "ar" to "بني"),
            w("black", "colors", 5,
                "en" to "Black", "fa" to "مشکی", "sv" to "Svart", "tr" to "Siyah", "de" to "Schwarz",
                "fr" to "Noir", "es" to "Negro", "ru" to "Чёрный", "zh" to "黑色", "hi" to "काला", "ar" to "أسود"),
            w("white", "colors", 5,
                "en" to "White", "fa" to "سفید", "sv" to "Vit", "tr" to "Beyaz", "de" to "Weiß",
                "fr" to "Blanc", "es" to "Blanco", "ru" to "Белый", "zh" to "白色", "hi" to "सफ़ेद", "ar" to "أبيض"),
            w("gray", "colors", 5,
                "en" to "Gray", "fa" to "خاکستری", "sv" to "Grå", "tr" to "Gri", "de" to "Grau",
                "fr" to "Gris", "es" to "Gris", "ru" to "Серый", "zh" to "灰色", "hi" to "धूसर", "ar" to "رمادي"),
            w("pink", "colors", 4,
                "en" to "Pink", "fa" to "صورتی", "sv" to "Rosa", "tr" to "Pembe", "de" to "Rosa",
                "fr" to "Rose", "es" to "Rosa", "ru" to "Розовый", "zh" to "粉色", "hi" to "गुलाबी", "ar" to "وردي"),
        )
    )

    val shapes = Category(
        id = "shapes",
        names = mapOf(
            "en" to "Shapes", "fa" to "شکل‌ها", "sv" to "Former", "tr" to "Şekiller",
            "de" to "Formen", "fr" to "Formes", "es" to "Formas", "ru" to "Формы",
            "zh" to "形状", "hi" to "आकृतियाँ", "ar" to "أشكال"
        ),
        words = listOf(
            w("circle", "shapes", 4,
                "en" to "Circle", "fa" to "دایره", "sv" to "Cirkel", "tr" to "Daire", "de" to "Kreis",
                "fr" to "Cercle", "es" to "Círculo", "ru" to "Круг", "zh" to "圆形", "hi" to "वृत्त", "ar" to "دائرة"),
            w("square", "shapes", 4,
                "en" to "Square", "fa" to "مربع", "sv" to "Fyrkant", "tr" to "Kare", "de" to "Quadrat",
                "fr" to "Carré", "es" to "Cuadrado", "ru" to "Квадрат", "zh" to "正方形", "hi" to "वर्ग", "ar" to "مربع"),
            w("triangle", "shapes", 4,
                "en" to "Triangle", "fa" to "مثلث", "sv" to "Triangel", "tr" to "Üçgen", "de" to "Dreieck",
                "fr" to "Triangle", "es" to "Triángulo", "ru" to "Треугольник", "zh" to "三角形", "hi" to "त्रिभुज", "ar" to "مثلث"),
            w("star", "shapes", 4,
                "en" to "Star", "fa" to "ستاره", "sv" to "Stjärna", "tr" to "Yıldız", "de" to "Stern",
                "fr" to "Étoile", "es" to "Estrella", "ru" to "Звезда", "zh" to "星星", "hi" to "तारा", "ar" to "نجمة"),
            w("diamond", "shapes", 5,
                "en" to "Diamond", "fa" to "لوزی", "sv" to "Diamant", "tr" to "Baklava", "de" to "Raute",
                "fr" to "Losange", "es" to "Rombo", "ru" to "Ромб", "zh" to "菱形", "hi" to "हीरा-आकृति", "ar" to "معين"),
            w("oval", "shapes", 5,
                "en" to "Oval", "fa" to "بیضی", "sv" to "Oval", "tr" to "Oval", "de" to "Oval",
                "fr" to "Ovale", "es" to "Óvalo", "ru" to "Овал", "zh" to "椭圆形", "hi" to "अंडाकार", "ar" to "بيضاوي"),
            w("rectangle", "shapes", 5,
                "en" to "Rectangle", "fa" to "مستطیل", "sv" to "Rektangel", "tr" to "Dikdörtgen", "de" to "Rechteck",
                "fr" to "Rectangle", "es" to "Rectángulo", "ru" to "Прямоугольник", "zh" to "长方形", "hi" to "आयत", "ar" to "مستطيل"),
            w("crescent", "shapes", 5,
                "en" to "Crescent Moon", "fa" to "هلال ماه", "sv" to "Månskära", "tr" to "Hilal", "de" to "Mondsichel",
                "fr" to "Croissant de lune", "es" to "Luna creciente", "ru" to "Полумесяц", "zh" to "月牙", "hi" to "अर्धचंद्र", "ar" to "هلال"),
            w("heart", "shapes", 4,
                "en" to "Heart", "fa" to "قلب", "sv" to "Hjärta", "tr" to "Kalp", "de" to "Herz",
                "fr" to "Cœur", "es" to "Corazón", "ru" to "Сердце", "zh" to "心形", "hi" to "दिल", "ar" to "قلب"),
        )
    )

    val people = Category(
        id = "people",
        names = mapOf(
            "en" to "Family", "fa" to "خانواده", "sv" to "Familj", "tr" to "Aile",
            "de" to "Familie", "fr" to "Famille", "es" to "Familia", "ru" to "Семья",
            "zh" to "家庭", "hi" to "परिवार", "ar" to "عائلة"
        ),
        words = listOf(
            w("mom", "people", 1,
                "en" to "Mom", "fa" to "مامان", "sv" to "Mamma", "tr" to "Anne", "de" to "Mama",
                "fr" to "Maman", "es" to "Mamá", "ru" to "Мама", "zh" to "妈妈", "hi" to "माँ", "ar" to "ماما"),
            w("dad", "people", 1,
                "en" to "Dad", "fa" to "بابا", "sv" to "Pappa", "tr" to "Baba", "de" to "Papa",
                "fr" to "Papa", "es" to "Papá", "ru" to "Папа", "zh" to "爸爸", "hi" to "पापा", "ar" to "بابا"),
            w("baby", "people", 2,
                "en" to "Baby", "fa" to "بچه", "sv" to "Baby", "tr" to "Bebek", "de" to "Baby",
                "fr" to "Bébé", "es" to "Bebé", "ru" to "Малыш", "zh" to "宝宝", "hi" to "बच्चा", "ar" to "طفل"),
            w("grandma", "people", 1,
                "en" to "Grandma", "fa" to "مادربزرگ", "sv" to "Mormor", "tr" to "Anneanne", "de" to "Oma",
                "fr" to "Grand-mère", "es" to "Abuela", "ru" to "Бабушка", "zh" to "奶奶", "hi" to "दादी", "ar" to "جدة"),
            w("grandpa", "people", 1,
                "en" to "Grandpa", "fa" to "پدربزرگ", "sv" to "Morfar", "tr" to "Dede", "de" to "Opa",
                "fr" to "Grand-père", "es" to "Abuelo", "ru" to "Дедушка", "zh" to "爷爷", "hi" to "दादा", "ar" to "جد"),
            w("sibling", "people", 1,
                "en" to "Sibling", "fa" to "خواهر/برادر", "sv" to "Syskon", "tr" to "Kardeş", "de" to "Geschwister",
                "fr" to "Frère/Sœur", "es" to "Hermano/a", "ru" to "Брат/Сестра", "zh" to "兄弟姐妹", "hi" to "भाई-बहन", "ar" to "أخ/أخت"),
            w("police", "people", 1,
                "en" to "Police Officer", "fa" to "پلیس", "sv" to "Polis", "tr" to "Polis", "de" to "Polizist",
                "fr" to "Policier", "es" to "Policía", "ru" to "Полицейский", "zh" to "警察", "hi" to "पुलिस", "ar" to "شرطي"),
            w("doctor", "people", 1,
                "en" to "Doctor", "fa" to "دکتر", "sv" to "Läkare", "tr" to "Doktor", "de" to "Arzt",
                "fr" to "Docteur", "es" to "Doctor", "ru" to "Врач", "zh" to "医生", "hi" to "डॉक्टर", "ar" to "طبيب"),
            w("nurse", "people", 1,
                "en" to "Nurse", "fa" to "پرستار", "sv" to "Sjuksköterska", "tr" to "Hemşire", "de" to "Krankenschwester",
                "fr" to "Infirmière", "es" to "Enfermera", "ru" to "Медсестра", "zh" to "护士", "hi" to "नर्स", "ar" to "ممرضة"),
            w("aunt", "people", 1,
                "en" to "Aunt", "fa" to "خاله", "sv" to "Moster", "tr" to "Teyze", "de" to "Tante",
                "fr" to "Tante", "es" to "Tía", "ru" to "Тётя", "zh" to "阿姨", "hi" to "मौसी", "ar" to "خالة"),
        )
    )

    val allCategories: List<Category> = listOf(animals, colors, shapes, people)

    fun categoryById(id: String): Category? = allCategories.find { it.id == id }

    fun wordById(id: String): Word? = allCategories.flatMap { it.words }.find { it.id == id }
}
