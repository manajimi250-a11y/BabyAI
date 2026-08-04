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

        // GamesMenuScreen
        "games_title" to mapOf(
            "en" to "Games 🎮", "fa" to "بازی‌ها 🎮", "sv" to "Spel 🎮", "tr" to "Oyunlar 🎮",
            "de" to "Spiele 🎮", "fr" to "Jeux 🎮", "es" to "Juegos 🎮", "ru" to "Игры 🎮",
            "zh" to "游戏 🎮", "hi" to "खेल 🎮", "ar" to "الألعاب 🎮"
        ),
        "game_memory" to mapOf(
            "en" to "Memory Match", "fa" to "بازی حافظه", "sv" to "Memory", "tr" to "Hafıza Oyunu",
            "de" to "Memory-Spiel", "fr" to "Jeu de mémoire", "es" to "Juego de memoria", "ru" to "Игра на память",
            "zh" to "记忆配对", "hi" to "मेमोरी गेम", "ar" to "لعبة الذاكرة"
        ),
        "game_odd_one_out" to mapOf(
            "en" to "Odd One Out", "fa" to "پیدا کن فرقشه", "sv" to "Hitta skillnaden", "tr" to "Farklıyı Bul",
            "de" to "Was passt nicht?", "fr" to "Trouve l'intrus", "es" to "Encuentra el diferente", "ru" to "Найди лишнее",
            "zh" to "找不同", "hi" to "अलग खोजें", "ar" to "اكتشف المختلف"
        ),
        "game_sorting" to mapOf(
            "en" to "Sort it out", "fa" to "دسته‌بندی کن", "sv" to "Sortera", "tr" to "Sınıflandır",
            "de" to "Sortieren", "fr" to "Trie-les", "es" to "Clasifica", "ru" to "Сортируй",
            "zh" to "分类整理", "hi" to "छाँटें", "ar" to "صنّف"
        ),
        "game_counting" to mapOf(
            "en" to "Count them!", "fa" to "بشمار چندتا!", "sv" to "Räkna dem!", "tr" to "Say Bakalım!",
            "de" to "Zähl sie!", "fr" to "Compte-les !", "es" to "¡Cuéntalos!", "ru" to "Посчитай!",
            "zh" to "数一数！", "hi" to "गिनो!", "ar" to "عدّها!"
        ),
        "game_listen_tap" to mapOf(
            "en" to "Listen & Tap", "fa" to "گوش کن و لمس کن", "sv" to "Lyssna & tryck", "tr" to "Dinle ve Dokun",
            "de" to "Hören & Tippen", "fr" to "Écoute et touche", "es" to "Escucha y toca", "ru" to "Слушай и нажимай",
            "zh" to "听音点击", "hi" to "सुनो और टैप करो", "ar" to "استمع والمس"
        ),
        "game_speed_tap" to mapOf(
            "en" to "Speed Tap", "fa" to "لمس سریع", "sv" to "Snabbtryck", "tr" to "Hızlı Dokunuş",
            "de" to "Schnelles Tippen", "fr" to "Touche rapide", "es" to "Toque rápido", "ru" to "Быстрое нажатие",
            "zh" to "快速点击", "hi" to "तेज़ टैप", "ar" to "لمسة سريعة"
        ),
        "game_puzzle" to mapOf(
            "en" to "Little Puzzle", "fa" to "پازل کوچولو", "sv" to "Litet pussel", "tr" to "Küçük Yapboz",
            "de" to "Kleines Puzzle", "fr" to "Petit puzzle", "es" to "Pequeño rompecabezas", "ru" to "Маленький пазл",
            "zh" to "小小拼图", "hi" to "छोटा पहेली खेल", "ar" to "أحجية صغيرة"
        ),
        "game_balloons" to mapOf(
            "en" to "Balloons", "fa" to "بادکنک‌ها", "sv" to "Ballonger", "tr" to "Balonlar",
            "de" to "Luftballons", "fr" to "Ballons", "es" to "Globos", "ru" to "Воздушные шары",
            "zh" to "气球", "hi" to "गुब्बारे", "ar" to "بالونات"
        ),

        // StoriesMenuScreen
        "stories_title" to mapOf(
            "en" to "Stories 📖", "fa" to "داستان‌ها 📖", "sv" to "Sagor 📖", "tr" to "Masallar 📖",
            "de" to "Märchen 📖", "fr" to "Histoires 📖", "es" to "Historias 📖", "ru" to "Сказки 📖",
            "zh" to "故事 📖", "hi" to "कहानियां 📖", "ar" to "قصص 📖"
        ),

        // متن‌های مشترک بازی‌ها (فیدبک درست/غلط)
        "feedback_correct" to mapOf(
            "en" to "Correct! ✅", "fa" to "آفرین! ✅", "sv" to "Rätt! ✅", "tr" to "Doğru! ✅",
            "de" to "Richtig! ✅", "fr" to "Correct ! ✅", "es" to "¡Correcto! ✅", "ru" to "Правильно! ✅",
            "zh" to "正确！✅", "hi" to "सही! ✅", "ar" to "صحيح! ✅"
        ),
        "feedback_next" to mapOf(
            "en" to "Let's see this one 🙂", "fa" to "بیا این یکی رو ببینیم 🙂", "sv" to "Låt oss se den här 🙂",
            "tr" to "Şimdi buna bakalım 🙂", "de" to "Schauen wir uns das an 🙂", "fr" to "Regardons celui-ci 🙂",
            "es" to "Veamos este 🙂", "ru" to "Давай посмотрим на это 🙂", "zh" to "我们来看看这个吧 🙂",
            "hi" to "चलो इसे देखते हैं 🙂", "ar" to "لنرَ هذا 🙂"
        ),
        "feedback_listen_again" to mapOf(
            "en" to "Listen again 🎧", "fa" to "دوباره گوش کن 🎧", "sv" to "Lyssna igen 🎧", "tr" to "Tekrar dinle 🎧",
            "de" to "Nochmal hören 🎧", "fr" to "Écoute encore 🎧", "es" to "Escucha otra vez 🎧", "ru" to "Послушай ещё раз 🎧",
            "zh" to "再听一次 🎧", "hi" to "फिर से सुनो 🎧", "ar" to "استمع مرة أخرى 🎧"
        ),
        "find_prefix" to mapOf(
            "en" to "Find: ", "fa" to "پیدا کن: ", "sv" to "Hitta: ", "tr" to "Bul: ",
            "de" to "Finde: ", "fr" to "Trouve : ", "es" to "Encuentra: ", "ru" to "Найди: ",
            "zh" to "找到：", "hi" to "ढूंढो: ", "ar" to "ابحث عن: "
        ),

        // OddOneOutScreen
        "odd_one_out_title" to mapOf(
            "en" to "Odd One Out 🔍", "fa" to "پیدا کن فرقشه 🔍", "sv" to "Hitta skillnaden 🔍",
            "tr" to "Farklıyı Bul 🔍", "de" to "Was passt nicht? 🔍", "fr" to "Trouve l'intrus 🔍",
            "es" to "Encuentra el diferente 🔍", "ru" to "Найди лишнее 🔍", "zh" to "找不同 🔍",
            "hi" to "अलग खोजें 🔍", "ar" to "اكتشف المختلف 🔍"
        ),
        "odd_one_out_prompt_speech" to mapOf(
            "en" to "Let's see what's different", "fa" to "ببین چه فرقی دارن", "sv" to "Låt oss se vad som är annorlunda",
            "tr" to "Neyin farklı olduğuna bakalım", "de" to "Schauen wir, was anders ist", "fr" to "Voyons ce qui est différent",
            "es" to "Veamos qué es diferente", "ru" to "Давай посмотрим, что отличается", "zh" to "我们来看看有什么不同",
            "hi" to "देखते हैं क्या अलग है", "ar" to "لنرَ ما هو المختلف"
        ),

        // SortingGameScreen
        "sorting_title" to mapOf(
            "en" to "Sort it out 🗂️", "fa" to "دسته‌بندی کن 🗂️", "sv" to "Sortera 🗂️", "tr" to "Sınıflandır 🗂️",
            "de" to "Sortieren 🗂️", "fr" to "Trie-les 🗂️", "es" to "Clasifica 🗂️", "ru" to "Сортируй 🗂️",
            "zh" to "分类整理 🗂️", "hi" to "छाँटें 🗂️", "ar" to "صنّف 🗂️"
        ),

        // CountingGameScreen
        "counting_title" to mapOf(
            "en" to "Count them! 🔢", "fa" to "بشمار چندتا! 🔢", "sv" to "Räkna dem! 🔢", "tr" to "Say Bakalım! 🔢",
            "de" to "Zähl sie! 🔢", "fr" to "Compte-les ! 🔢", "es" to "¡Cuéntalos! 🔢", "ru" to "Посчитай! 🔢",
            "zh" to "数一数！🔢", "hi" to "गिनो! 🔢", "ar" to "عدّها! 🔢"
        ),
        "counting_question" to mapOf(
            "en" to "How many do you see?", "fa" to "چندتا عکس می‌بینی؟", "sv" to "Hur många ser du?",
            "tr" to "Kaç tane görüyorsun?", "de" to "Wie viele siehst du?", "fr" to "Combien en vois-tu ?",
            "es" to "¿Cuántos ves?", "ru" to "Сколько ты видишь?", "zh" to "你看到几个？",
            "hi" to "तुम कितने देखते हो?", "ar" to "كم عدد ما تراه؟"
        ),

        // ListenAndTapScreen
        "listen_tap_title" to mapOf(
            "en" to "Listen & Tap 🎧", "fa" to "گوش کن و لمس کن 🎧", "sv" to "Lyssna & tryck 🎧",
            "tr" to "Dinle ve Dokun 🎧", "de" to "Hören & Tippen 🎧", "fr" to "Écoute et touche 🎧",
            "es" to "Escucha y toca 🎧", "ru" to "Слушай и нажимай 🎧", "zh" to "听音点击 🎧",
            "hi" to "सुनो और टैप करो 🎧", "ar" to "استمع والمس 🎧"
        ),

        // SpeedTapScreen
        "speed_tap_title" to mapOf(
            "en" to "Speed Tap ⚡", "fa" to "لمس سریع ⚡", "sv" to "Snabbtryck ⚡", "tr" to "Hızlı Dokunuş ⚡",
            "de" to "Schnelles Tippen ⚡", "fr" to "Touche rapide ⚡", "es" to "Toque rápido ⚡", "ru" to "Быстрое нажатие ⚡",
            "zh" to "快速点击 ⚡", "hi" to "तेज़ टैप ⚡", "ar" to "لمسة سريعة ⚡"
        ),
        "time_up" to mapOf(
            "en" to "Time's up! ⏰", "fa" to "وقت تموم شد! ⏰", "sv" to "Tiden är slut! ⏰", "tr" to "Süre doldu! ⏰",
            "de" to "Zeit ist um! ⏰", "fr" to "Le temps est écoulé ! ⏰", "es" to "¡Se acabó el tiempo! ⏰",
            "ru" to "Время вышло! ⏰", "zh" to "时间到！⏰", "hi" to "समय समाप्त! ⏰", "ar" to "انتهى الوقت! ⏰"
        ),
        "try_again" to mapOf(
            "en" to "Try again", "fa" to "دوباره امتحان کن", "sv" to "Försök igen", "tr" to "Tekrar dene",
            "de" to "Nochmal versuchen", "fr" to "Réessaye", "es" to "Inténtalo de nuevo", "ru" to "Попробуй снова",
            "zh" to "再试一次", "hi" to "फिर कोशिश करो", "ar" to "حاول مرة أخرى"
        ),

        // PuzzleScreen
        "puzzle_title" to mapOf(
            "en" to "Little Puzzle 🧩", "fa" to "پازل کوچولو 🧩", "sv" to "Litet pussel 🧩", "tr" to "Küçük Yapboz 🧩",
            "de" to "Kleines Puzzle 🧩", "fr" to "Petit puzzle 🧩", "es" to "Pequeño rompecabezas 🧩",
            "ru" to "Маленький пазл 🧩", "zh" to "小小拼图 🧩", "hi" to "छोटा पहेली खेल 🧩", "ar" to "أحجية صغيرة 🧩"
        ),
        "puzzle_instruction" to mapOf(
            "en" to "Tap two pieces to swap them", "fa" to "دو تیکه رو لمس کن تا جاشون عوض بشه",
            "sv" to "Tryck på två bitar för att byta dem", "tr" to "Yer değiştirmek için iki parçaya dokun",
            "de" to "Tippe auf zwei Teile, um sie zu tauschen", "fr" to "Touche deux pièces pour les échanger",
            "es" to "Toca dos piezas para intercambiarlas", "ru" to "Нажми на две части, чтобы поменять их местами",
            "zh" to "点击两块拼图以交换位置", "hi" to "दो टुकड़ों को बदलने के लिए टैप करें",
            "ar" to "المس قطعتين لتبديلهما"
        ),

        // BalloonPopScreen
        "balloons_title" to mapOf(
            "en" to "Balloons 🎈", "fa" to "بادکنک‌ها 🎈", "sv" to "Ballonger 🎈", "tr" to "Balonlar 🎈",
            "de" to "Luftballons 🎈", "fr" to "Ballons 🎈", "es" to "Globos 🎈", "ru" to "Воздушные шары 🎈",
            "zh" to "气球 🎈", "hi" to "गुब्बारे 🎈", "ar" to "بالونات 🎈"
        ),

        // MemoryGameScreen
        "memory_title" to mapOf(
            "en" to "Memory Match 🧠", "fa" to "بازی حافظه 🧠", "sv" to "Memory 🧠", "tr" to "Hafıza Oyunu 🧠",
            "de" to "Memory-Spiel 🧠", "fr" to "Jeu de mémoire 🧠", "es" to "Juego de memoria 🧠",
            "ru" to "Игра на память 🧠", "zh" to "记忆配对 🧠", "hi" to "मेमोरी गेम 🧠", "ar" to "لعبة الذاكرة 🧠"
        ),
    )

    /** ترجمه‌ی متن با کد کلید؛ اگه زبون پیدا نشد میره سراغ انگلیسی؛ اگه کلید هم پیدا نشد خود کلید برمی‌گرده */
    fun t(key: String, lang: String): String =
        strings[key]?.get(lang) ?: strings[key]?.get("en") ?: key
}
