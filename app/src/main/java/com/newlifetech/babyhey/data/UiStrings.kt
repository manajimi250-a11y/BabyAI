package com.newlifetech.babyhey.data

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
            "en" to "Smart Learning", "fa" to "یادگیری هوشمند", "sv" to "Lär dig och ha kul",
            "tr" to "Akıllı Öğrenme", "de" to "Spielerisch lernen", "fr" to "Apprendre en s'amusant",
            "es" to "Aprende jugando", "ru" to "Развивающие занятия", "zh" to "智能学习",
            "hi" to "स्मार्ट लर्निंग", "ar" to "التعلّم الممتع"
        ),
        "hub_learn_subtitle" to mapOf(
            "en" to "Animals, colors, shapes & family", "fa" to "حیوانات، رنگ‌ها، شکل‌ها و خانواده",
            "sv" to "Djur, färger, former och familjen", "tr" to "Hayvanlar, renkler, şekiller ve aile",
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
            "sv" to "Memoryspel och fler roliga spel", "tr" to "Hafıza oyunu ve daha fazlası",
            "de" to "Memory und mehr Spiele", "fr" to "Jeu de mémoire et bien d'autres jeux",
            "es" to "Juego de memoria y mucho más", "ru" to "Игра на память и другие игры",
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
            "sv" to "Roliga sagor att leka med", "tr" to "Eğlenceli etkileşimli masallar",
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
            "de" to "Memory-Spiel", "fr" to "Jeu de mémoire", "es" to "Juego de memoria", "ru" to "Игра на запоминание",
            "zh" to "记忆配对", "hi" to "मेमोरी गेम", "ar" to "لعبة الذاكرة"
        ),
        "game_odd_one_out" to mapOf(
            "en" to "Odd One Out", "fa" to "یکی که فرق داره", "sv" to "Hitta den som är annorlunda", "tr" to "Farklıyı Bul",
            "de" to "Was passt nicht?", "fr" to "Trouve l'intrus", "es" to "Encuentra el intruso", "ru" to "Найди лишнее",
            "zh" to "找不同", "hi" to "अलग चीज़ ढूंढो", "ar" to "اكتشف المختلف"
        ),
        "game_sorting" to mapOf(
            "en" to "Sort It Out", "fa" to "دسته‌بندی کن", "sv" to "Sortera", "tr" to "Sınıflandır",
            "de" to "Sortierspiel", "fr" to "Trie-les", "es" to "Clasifica", "ru" to "Сортируй",
            "zh" to "分类整理", "hi" to "छाँटें", "ar" to "لعبة التصنيف"
        ),
        "game_counting" to mapOf(
            "en" to "Count Them!", "fa" to "چندتاشو بشمار!", "sv" to "Räkna dem!", "tr" to "Haydi sayalım!",
            "de" to "Zähl sie!", "fr" to "Compte-les !", "es" to "¡Cuéntalos!", "ru" to "Посчитай!",
            "zh" to "数一数！", "hi" to "गिनो!", "ar" to "عدّها!"
        ),
        "game_listen_tap" to mapOf(
            "en" to "Listen & Tap", "fa" to "گوش کن و بزن", "sv" to "Lyssna & tryck", "tr" to "Dinle ve Dokun",
            "de" to "Hör zu & tippe", "fr" to "Écoute et touche", "es" to "Escucha y toca", "ru" to "Слушай и нажимай",
            "zh" to "听音点击", "hi" to "सुनो और टैप करो", "ar" to "استمع واضغط"
        ),
        "game_speed_tap" to mapOf(
            "en" to "Speed Tap!", "fa" to "لمس سریع", "sv" to "Snabbtryck", "tr" to "Hızlı Dokunuş",
            "de" to "Schnelles Tippen", "fr" to "Tape vite", "es" to "Toque rápido", "ru" to "Быстрое нажатие",
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
            "en" to "Correct! ✅", "fa" to "آفرین! ✅", "sv" to "Rätt! ✅", "tr" to "Harika! ✅",
            "de" to "Richtig! ✅", "fr" to "Correct ! ✅", "es" to "¡Correcto! ✅", "ru" to "Правильно! ✅",
            "zh" to "正确！✅", "hi" to "सही! ✅", "ar" to "صحيح! ✅"
        ),
        "feedback_next" to mapOf(
            "en" to "Let's try this one! 🙂", "fa" to "بیا این یکی رو ببینیم 🙂", "sv" to "Nu tar vi nästa 🙂",
            "tr" to "Hadi buna bakalım 🙂", "de" to "Schauen wir uns das an 🙂", "fr" to "Regardons celui-ci 🙂",
            "es" to "¡Veamos este! 🙂", "ru" to "Давай посмотрим на это 🙂", "zh" to "来看看这个吧 🙂",
            "hi" to "चलो इसे देखते हैं 🙂", "ar" to "هيا نرى هذه! 🙂"
        ),
        "feedback_listen_again" to mapOf(
            "en" to "Listen again 🎧", "fa" to "دوباره گوش کن 🎧", "sv" to "Lyssna igen 🎧", "tr" to "Tekrar dinle 🎧",
            "de" to "Hör nochmal zu 🎧", "fr" to "Écoute encore une fois 🎧", "es" to "¡Escucha otra vez! 🎧", "ru" to "Послушай ещё раз 🎧",
            "zh" to "再听一次 🎧", "hi" to "फिर से सुनो 🎧", "ar" to "استمع مرةً أخرى 🎧"
        ),
        "find_prefix" to mapOf(
            "en" to "Find: ", "fa" to "پیدا کن: ", "sv" to "Hitta: ", "tr" to "Bul: ",
            "de" to "Finde: ", "fr" to "Trouve : ", "es" to "Encuentra: ", "ru" to "Найди: ",
            "zh" to "找到：", "hi" to "ढूंढो: ", "ar" to "ابحث عن: "
        ),

        // OddOneOutScreen
        "odd_one_out_title" to mapOf(
            "en" to "Odd One Out 🔍", "fa" to "یکی که فرق داره 🔍", "sv" to "Hitta den som är annorlunda 🔍",
            "tr" to "Farklıyı Bul 🔍", "de" to "Was passt nicht? 🔍", "fr" to "Trouve l'intrus 🔍",
            "es" to "Encuentra el intruso 🔍", "ru" to "Найди лишнее 🔍", "zh" to "找不同 🔍",
            "hi" to "अलग चीज़ ढूंढो 🔍", "ar" to "اكتشف المختلف 🔍"
        ),
        "odd_one_out_prompt_speech" to mapOf(
            "en" to "Let's see what's different", "fa" to "ببین چه فرقی دارن", "sv" to "Nu ska vi se vad som är annorlunda",
            "tr" to "Neyin farklı olduğuna bakalım", "de" to "Schauen wir, was anders ist", "fr" to "Voyons ce qui est différent",
            "es" to "Veamos qué es diferente", "ru" to "Давай посмотрим, что отличается", "zh" to "我们来找找哪里不一样吧",
            "hi" to "देखते हैं क्या अलग है", "ar" to "لنرَ ما هو المختلف"
        ),

        // SortingGameScreen
        "sorting_title" to mapOf(
            "en" to "Sort it out 🗂️", "fa" to "دسته‌بندی کن 🗂️", "sv" to "Sortera 🗂️", "tr" to "Sınıflandır 🗂️",
            "de" to "Sortierspiel 🗂️", "fr" to "Trie-les 🗂️", "es" to "Clasifica 🗂️", "ru" to "Сортируй 🗂️",
            "zh" to "分类整理 🗂️", "hi" to "छाँटें 🗂️", "ar" to "لعبة التصنيف 🗂️"
        ),

        // CountingGameScreen
        "counting_title" to mapOf(
            "en" to "Count them! 🔢", "fa" to "چندتاشو بشمار! 🔢", "sv" to "Räkna dem! 🔢", "tr" to "Haydi sayalım! 🔢",
            "de" to "Zähl sie! 🔢", "fr" to "Compte-les ! 🔢", "es" to "¡Cuéntalos! 🔢", "ru" to "Посчитай! 🔢",
            "zh" to "数一数！🔢", "hi" to "गिनो! 🔢", "ar" to "عدّها! 🔢"
        ),
        "counting_question" to mapOf(
            "en" to "How many can you see?", "fa" to "چندتا می‌بینی؟", "sv" to "Hur många ser du?",
            "tr" to "Kaç tane görüyorsun?", "de" to "Wie viele siehst du?", "fr" to "Combien en vois-tu ?",
            "es" to "¿Cuántos ves?", "ru" to "Сколько ты видишь?", "zh" to "你看到几个？",
            "hi" to "तुम्हें कितनी चीज़ें दिख रही हैं?", "ar" to "كم عدد ما تراه؟"
        ),

        // ListenAndTapScreen
        "listen_tap_title" to mapOf(
            "en" to "Listen & Tap 🎧", "fa" to "گوش کن و بزن 🎧", "sv" to "Lyssna & tryck 🎧",
            "tr" to "Dinle ve Dokun 🎧", "de" to "Hör zu & tippe 🎧", "fr" to "Écoute et touche 🎧",
            "es" to "Escucha y toca 🎧", "ru" to "Слушай и нажимай 🎧", "zh" to "听音点击 🎧",
            "hi" to "सुनो और टैप करो 🎧", "ar" to "استمع واضغط 🎧"
        ),

        // SpeedTapScreen
        "speed_tap_title" to mapOf(
            "en" to "Speed Tap ⚡", "fa" to "لمس سریع ⚡", "sv" to "Snabbtryck ⚡", "tr" to "Hızlı Dokunuş ⚡",
            "de" to "Schnelles Tippen ⚡", "fr" to "Tape vite ⚡", "es" to "Toque rápido ⚡", "ru" to "Быстрое нажатие ⚡",
            "zh" to "快速点击 ⚡", "hi" to "तेज़ टैप ⚡", "ar" to "لمسة سريعة ⚡"
        ),
        "time_up" to mapOf(
            "en" to "Time's up! ⏰", "fa" to "وقت تموم شد! ⏰", "sv" to "Tiden är ute! ⏰", "tr" to "Süre doldu! ⏰",
            "de" to "Zeit ist um! ⏰", "fr" to "Le temps est écoulé ! ⏰", "es" to "¡Se acabó el tiempo! ⏰",
            "ru" to "Время вышло! ⏰", "zh" to "时间到！⏰", "hi" to "समय समाप्त! ⏰", "ar" to "انتهى الوقت! ⏰"
        ),
        "try_again" to mapOf(
            "en" to "Try again", "fa" to "دوباره امتحان کن", "sv" to "Försök igen", "tr" to "Tekrar dene",
            "de" to "Versuch's nochmal", "fr" to "Réessaye", "es" to "Inténtalo de nuevo", "ru" to "Попробуй снова",
            "zh" to "再试一次", "hi" to "फिर कोशिश करो", "ar" to "جرّب مرةً أخرى"
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
            "de" to "Tippe zwei Puzzleteile an, um sie zu tauschen.", "fr" to "Touche deux pièces pour les échanger",
            "es" to "Toca dos piezas para intercambiarlas", "ru" to "Нажми на две части, чтобы поменять их местами",
            "zh" to "点击两块拼图以交换位置", "hi" to "दो टुकड़ों को बदलने के लिए टैप करें",
            "ar" to "المس قطعتين من الأحجية لتبديل مكانيهما."
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

        // SettingsScreen
        "settings_title" to mapOf(
            "en" to "Settings", "fa" to "تنظیمات", "sv" to "Inställningar", "tr" to "Ayarlar",
            "de" to "Einstellungen", "fr" to "Paramètres", "es" to "Ajustes", "ru" to "Настройки",
            "zh" to "设置", "hi" to "सेटिंग्स", "ar" to "الإعدادات"
        ),
        "settings_photo_size" to mapOf(
            "en" to "Photo Size", "fa" to "اندازه‌ی عکس‌ها", "sv" to "Bildstorlek", "tr" to "Fotoğraf Boyutu",
            "de" to "Fotogröße", "fr" to "Taille des photos", "es" to "Tamaño de las fotos", "ru" to "Размер фото",
            "zh" to "图片大小", "hi" to "फ़ोटो का आकार", "ar" to "حجم الصور"
        ),
        "photo_small" to mapOf(
            "en" to "Small", "fa" to "کوچک", "sv" to "Liten", "tr" to "Küçük", "de" to "Klein",
            "fr" to "Petit", "es" to "Pequeño", "ru" to "Маленький", "zh" to "小", "hi" to "छोटा", "ar" to "صغير"
        ),
        "photo_medium" to mapOf(
            "en" to "Medium", "fa" to "متوسط", "sv" to "Medel", "tr" to "Orta", "de" to "Mittel",
            "fr" to "Moyen", "es" to "Mediano", "ru" to "Средний", "zh" to "中", "hi" to "मध्यम", "ar" to "متوسط"
        ),
        "photo_large" to mapOf(
            "en" to "Large", "fa" to "بزرگ", "sv" to "Stor", "tr" to "Büyük", "de" to "Groß",
            "fr" to "Grand", "es" to "Grande", "ru" to "Большой", "zh" to "大", "hi" to "बड़ा", "ar" to "كبير"
        ),
        "settings_child_age" to mapOf(
            "en" to "Child's Age", "fa" to "سن بچه‌تون", "sv" to "Barnets ålder", "tr" to "Çocuğunuzun Yaşı",
            "de" to "Alter des Kindes", "fr" to "Âge de l'enfant", "es" to "Edad del niño", "ru" to "Возраст ребёнка",
            "zh" to "孩子的年龄", "hi" to "बच्चे की उम्र", "ar" to "عمر الطفل"
        ),
        "years_suffix" to mapOf(
            "en" to "yrs", "fa" to "سال", "sv" to "år", "tr" to "yaş", "de" to "Jahre",
            "fr" to "ans", "es" to "años", "ru" to "лет", "zh" to "岁", "hi" to "वर्ष", "ar" to "سنوات"
        ),
        "settings_parental_gate" to mapOf(
            "en" to "Parental Gate", "fa" to "قفل والدین", "sv" to "Föräldralås", "tr" to "Ebeveyn Kilidi",
            "de" to "Elternsperre", "fr" to "Verrou parental", "es" to "Control parental", "ru" to "Родительский доступ",
            "zh" to "家长锁", "hi" to "पेरेंटल गेट", "ar" to "قفل الوالدين"
        ),
        "settings_parental_gate_always_on" to mapOf(
            "en" to "Parental Gate is always on to protect your child",
            "fa" to "قفل والدین همیشه فعاله تا از بچه‌تون محافظت کنه",
            "sv" to "Föräldralåset är alltid på för att skydda ditt barn",
            "tr" to "Ebeveyn kilidi çocuğunuzu korumak için her zaman açıktır",
            "de" to "Die Elternsperre ist immer aktiv, um dein Kind zu schützen",
            "fr" to "Le verrou parental est toujours activé pour protéger votre enfant",
            "es" to "El control parental siempre está activado para proteger a tu hijo",
            "ru" to "Родительский контроль всегда включён для защиты вашего ребёнка",
            "zh" to "家长锁始终开启，以保护您的孩子",
            "hi" to "आपके बच्चे की सुरक्षा के लिए पेरेंटल गेट हमेशा चालू रहता है",
            "ar" to "بوابة الوالدين مفعّلة دائماً لحماية طفلك"
        ),
        "settings_privacy_policy" to mapOf(
            "en" to "Privacy Policy", "fa" to "حریم خصوصی و شرایط استفاده", "sv" to "Integritetspolicy", "tr" to "Gizlilik Politikası",
            "de" to "Datenschutzerklärung", "fr" to "Politique de confidentialité", "es" to "Política de privacidad",
            "ru" to "Политика конфиденциальности", "zh" to "隐私政策", "hi" to "गोपनीयता नीति", "ar" to "سياسة الخصوصية"
        ),
        "record_dialog_title" to mapOf(
            "en" to "Record voice for", "fa" to "ضبط صدا برای", "sv" to "Spela in röst för", "tr" to "Ses kaydet:",
            "de" to "Stimme aufnehmen für", "fr" to "Enregistrer la voix pour", "es" to "Grabar voz para",
            "ru" to "Записать голос для", "zh" to "录制语音：", "hi" to "आवाज़ रिकॉर्ड करें:", "ar" to "سجّل الصوت لـ"
        ),
        "record_dialog_close" to mapOf(
            "en" to "Close", "fa" to "بستن", "sv" to "Stäng", "tr" to "Kapat", "de" to "Schließen",
            "fr" to "Fermer", "es" to "Cerrar", "ru" to "Закрыть", "zh" to "关闭", "hi" to "बंद करें", "ar" to "إغلاق"
        ),

        // ParentalGateDialog
        "gate_title" to mapOf(
            "en" to "Parent Verification", "fa" to "تأیید والدین", "sv" to "Föräldraverifiering", "tr" to "Ebeveyn Doğrulaması",
            "de" to "Elternbestätigung", "fr" to "Vérification parentale", "es" to "Verificación de padres", "ru" to "Подтверждение родителя",
            "zh" to "家长验证", "hi" to "अभिभावक सत्यापन", "ar" to "تأكيد الوالدين"
        ),
        "gate_prompt" to mapOf(
            "en" to "To continue, please solve this sum:", "fa" to "برای ادامه، این جمع رو حل کن:",
            "sv" to "För att fortsätta, lös denna summa:", "tr" to "Devam etmek için bu toplamı çözün:",
            "de" to "Um fortzufahren, löse bitte diese Summe:", "fr" to "Pour continuer, résolvez cette addition :",
            "es" to "Para continuar, resuelve esta suma:", "ru" to "Чтобы продолжить, решите пример:",
            "zh" to "要继续，请解出这道题：", "hi" to "जारी रखने के लिए, कृपया यह जोड़ हल करें:",
            "ar" to "للمتابعة، يرجى حل هذا الجمع:"
        ),
        "gate_answer_label" to mapOf(
            "en" to "Answer", "fa" to "جواب", "sv" to "Svar", "tr" to "Cevap", "de" to "Antwort",
            "fr" to "Réponse", "es" to "Respuesta", "ru" to "Ответ", "zh" to "答案", "hi" to "उत्तर", "ar" to "الإجابة"
        ),
        "gate_wrong_answer" to mapOf(
            "en" to "That's not right, try again.", "fa" to "جواب درست نیست، دوباره امتحان کن.",
            "sv" to "Det stämmer inte, försök igen.", "tr" to "Bu doğru değil, tekrar deneyin.",
            "de" to "Das ist nicht richtig, versuche es erneut.", "fr" to "Ce n'est pas correct, réessayez.",
            "es" to "Eso no es correcto, inténtalo de nuevo.", "ru" to "Это неверно, попробуйте снова.",
            "zh" to "不对哦，再试一次。", "hi" to "यह सही नहीं है, फिर कोशिश करें।",
            "ar" to "هذا غير صحيح، حاول مرة أخرى."
        ),
        "gate_confirm" to mapOf(
            "en" to "Confirm", "fa" to "تأیید", "sv" to "Bekräfta", "tr" to "Onayla", "de" to "Bestätigen",
            "fr" to "Confirmer", "es" to "Confirmar", "ru" to "Подтвердить", "zh" to "确认", "hi" to "पुष्टि करें", "ar" to "تأكيد"
        ),
        "settings_music" to mapOf(
            "en" to "Background Music", "fa" to "موسیقی پس‌زمینه", "sv" to "Bakgrundsmusik", "tr" to "Arka Plan Müziği",
            "de" to "Hintergrundmusik", "fr" to "Musique de fond", "es" to "Música de fondo", "ru" to "Фоновая музыка",
            "zh" to "背景音乐", "hi" to "बैकग्राउंड संगीत", "ar" to "موسيقى الخلفية"
        ),
        "settings_night_mode" to mapOf(
            "en" to "Night Mode 🌙", "fa" to "حالت شب 🌙", "sv" to "Nattläge 🌙", "tr" to "Gece Modu 🌙",
            "de" to "Nachtmodus 🌙", "fr" to "Mode nuit 🌙", "es" to "Modo noche 🌙", "ru" to "Ночной режим 🌙",
            "zh" to "夜间模式 🌙", "hi" to "नाइट मोड 🌙", "ar" to "الوضع الليلي 🌙"
        ),
        "settings_parent_dashboard" to mapOf(
            "en" to "Parent Dashboard", "fa" to "داشبورد والدین", "sv" to "Föräldrapanel", "tr" to "Ebeveyn Paneli",
            "de" to "Elterndashboard", "fr" to "Tableau de bord parental", "es" to "Panel para padres", "ru" to "Родительская панель",
            "zh" to "家长面板", "hi" to "अभिभावकों का डैशबोर्ड", "ar" to "لوحة الوالدين"
        ),
        "settings_golden_dreams" to mapOf(
            "en" to "Golden Dreams", "fa" to "خواب‌های طلایی", "sv" to "Gyllene drömmar", "tr" to "Altın Rüyalar",
            "de" to "Goldene Träume", "fr" to "Rêves dorés", "es" to "Sueños dorados", "ru" to "Золотые сны",
            "zh" to "黄金梦境", "hi" to "सुनहरे सपने", "ar" to "أحلام ذهبية"
        ),
        "settings_record_instruction" to mapOf(
            "en" to "To record your own voice for a word, open the word and tap the microphone icon.",
            "fa" to "برای ضبط صدای خودتون برای هر کلمه، وارد همون کلمه بشید و روی آیکون میکروفون بزنید.",
            "sv" to "För att spela in din egen röst för ett ord, öppna ordet och tryck på mikrofonikonen.",
            "tr" to "Bir kelime için kendi sesini kaydetmek istersen, o kelimeyi açıp mikrofon simgesine dokun.",
            "de" to "Um deine eigene Stimme für ein Wort aufzunehmen, öffne das Wort und tippe auf das Mikrofonsymbol.",
            "fr" to "Pour enregistrer ta voix pour un mot, ouvre ce mot et touche l'icône du microphone.",
            "es" to "Para grabar tu voz en una palabra, abre esa palabra y toca el ícono del micrófono.",
            "ru" to "Чтобы записать свой голос для слова, откройте это слово и нажмите на значок микрофона.",
            "zh" to "要为某个单词录制您自己的声音，请打开该单词并点击麦克风图标。",
            "hi" to "किसी शब्द के लिए अपनी आवाज़ रिकॉर्ड करने के लिए, वह शब्द खोलें और माइक्रोफ़ोन आइकन पर टैप करें।",
            "ar" to "لتسجيل صوتك لكلمة، افتح تلك الكلمة ثم اضغط على أيقونة الميكروفون."
        ),

        // زیرساخت مشترک onboarding
        "continue_button" to mapOf(
            "en" to "Continue →", "fa" to "ادامه →", "sv" to "Fortsätt →", "tr" to "Devam Et →",
            "de" to "Weiter →", "fr" to "Continuer →", "es" to "Continuar →", "ru" to "Продолжить →",
            "zh" to "继续 →", "hi" to "जारी रखें →", "ar" to "← متابعة"
        ),

        // NameInputScreen
        "name_prompt" to mapOf(
            "en" to "I'd love to know your name!", "fa" to "دوست دارم اسمت رو بدونم!",
            "sv" to "Vad heter du?", "tr" to "Adın ne?",
            "de" to "Wie heißt du?", "fr" to "J'aimerais connaître ton prénom !",
            "es" to "¡Me encantaría saber tu nombre!", "ru" to "Я хочу узнать твоё имя!",
            "zh" to "我想知道你的名字！", "hi" to "मैं तुम्हारा नाम जानना चाहता हूँ!",
            "ar" to "أودّ أن أعرف اسمك!"
        ),
        "name_label" to mapOf(
            "en" to "Name", "fa" to "اسم", "sv" to "Namn", "tr" to "İsim", "de" to "Name",
            "fr" to "Prénom", "es" to "Nombre", "ru" to "Имя", "zh" to "名字", "hi" to "नाम", "ar" to "الاسم"
        ),

        // AgeSelectScreen
        "age_prompt" to mapOf(
            "en" to "How old are you? 🎂", "fa" to "چند سالته؟ 🎂", "sv" to "Hur gammal är du? 🎂",
            "tr" to "Kaç yaşındasın? 🎂", "de" to "Wie alt bist du? 🎂", "fr" to "Quel âge as-tu ? 🎂",
            "es" to "¿Cuántos años tienes? 🎂", "ru" to "Сколько тебе лет? 🎂", "zh" to "你几岁了？🎂",
            "hi" to "तुम्हारी उम्र क्या है? 🎂", "ar" to "كم عمرك؟ 🎂"
        ),
        "years_old_suffix" to mapOf(
            "en" to "years old", "fa" to "سال", "sv" to "år gammal", "tr" to "yaşında",
            "de" to "Jahre alt", "fr" to "ans", "es" to "años", "ru" to "лет",
            "zh" to "岁", "hi" to "साल का", "ar" to "سنوات"
        ),

        // MascotSelectScreen
        "mascot_prompt" to mapOf(
            "en" to "Choose your friend!", "fa" to "دوستت رو انتخاب کن!", "sv" to "Välj en kompis!",
            "tr" to "Bir arkadaş seç!", "de" to "Such dir einen Freund aus!", "fr" to "Choisis ton ami !",
            "es" to "¡Elige a tu amigo!", "ru" to "Выбери своего друга!", "zh" to "选择你的朋友！",
            "hi" to "अपना दोस्त चुनो!", "ar" to "اختر صديقك!"
        ),

        // ProfileSelectScreen
        "profile_prompt" to mapOf(
            "en" to "Who's playing?", "fa" to "کی بازی می‌کنه؟", "sv" to "Vem spelar?", "tr" to "Kim oynuyor?",
            "de" to "Wer spielt?", "fr" to "Qui joue ?", "es" to "¿Quién juega?", "ru" to "Кто играет?",
            "zh" to "谁在玩？", "hi" to "कौन खेल रहा है?", "ar" to "من يلعب؟"
        ),
        "delete_player_title" to mapOf(
            "en" to "Delete player?", "fa" to "حذف بازیکن؟", "sv" to "Ta bort spelare?", "tr" to "Oyuncu silinsin mi?",
            "de" to "Spieler löschen?", "fr" to "Supprimer le joueur ?", "es" to "¿Eliminar jugador?",
            "ru" to "Удалить игрока?", "zh" to "删除玩家？", "hi" to "खिलाड़ी हटाएं?", "ar" to "حذف اللاعب؟"
        ),
        "delete_player_body" to mapOf(
            "en" to "{name}'s progress will be deleted forever.",
            "fa" to "پیشرفت «{name}» برای همیشه پاک می‌شه.",
            "sv" to "{name}s framsteg kommer att raderas för alltid.",
            "tr" to "{name} adlı oyuncunun ilerlemesi kalıcı olarak silinecek.",
            "de" to "Der Fortschritt von {name} wird dauerhaft gelöscht.",
            "fr" to "Les progrès de {name} seront supprimés définitivement.",
            "es" to "El progreso de {name} se eliminará para siempre.",
            "ru" to "Прогресс {name} будет удалён навсегда.",
            "zh" to "{name}的进度将被永久删除。",
            "hi" to "{name} की प्रगति हमेशा के लिए मिट जाएगी।",
            "ar" to "سيتم حذف تقدم {name} إلى الأبد."
        ),
        "delete_confirm" to mapOf(
            "en" to "Delete", "fa" to "حذف کن", "sv" to "Ta bort", "tr" to "Sil", "de" to "Löschen",
            "fr" to "Supprimer", "es" to "Eliminar", "ru" to "Удалить", "zh" to "删除", "hi" to "हटाएं", "ar" to "حذف"
        ),
        "cancel_button" to mapOf(
            "en" to "Cancel", "fa" to "انصراف", "sv" to "Avbryt", "tr" to "İptal", "de" to "Abbrechen",
            "fr" to "Annuler", "es" to "Cancelar", "ru" to "Отмена", "zh" to "取消", "hi" to "रद्द करें", "ar" to "إلغاء"
        ),
        "add_button" to mapOf(
            "en" to "Add", "fa" to "افزودن", "sv" to "Lägg till", "tr" to "Ekle", "de" to "Hinzufügen",
            "fr" to "Ajouter", "es" to "Añadir", "ru" to "Добавить", "zh" to "添加", "hi" to "जोड़ें", "ar" to "إضافة"
        ),

        // ParentDashboardScreen
        "parent_dashboard_title" to mapOf(
            "en" to "Parent Dashboard", "fa" to "داشبورد والدین", "sv" to "Föräldrapanel", "tr" to "Ebeveyn Paneli",
            "de" to "Elterndashboard", "fr" to "Tableau de bord parental", "es" to "Panel para padres", "ru" to "Родительская панель",
            "zh" to "家长面板", "hi" to "अभिभावकों का डैशबोर्ड", "ar" to "لوحة الوالدين"
        ),
        "progress_report" to mapOf(
            "en" to "{name}'s progress report", "fa" to "گزارش پیشرفت {name}", "sv" to "{name}s framsteg",
            "tr" to "{name} ilerleme raporu", "de" to "{name}s Fortschrittsbericht", "fr" to "Rapport de progression de {name}",
            "es" to "Informe de progreso de {name}", "ru" to "Отчёт о прогрессе {name}",
            "zh" to "{name}的进度报告", "hi" to "{name} की प्रगति रिपोर्ट", "ar" to "تقرير تقدّم {name}"
        ),
        "total_stars" to mapOf(
            "en" to "Total Stars", "fa" to "کل ستاره‌ها", "sv" to "Totalt antal stjärnor", "tr" to "Toplam Yıldız",
            "de" to "Sterne insgesamt", "fr" to "Total des étoiles", "es" to "Total de estrellas", "ru" to "Всего звёзд",
            "zh" to "总星数", "hi" to "कुल सितारे", "ar" to "إجمالي النجوم"
        ),
        "words_learned" to mapOf(
            "en" to "Words Learned", "fa" to "کلمه‌های یادگرفته‌شده", "sv" to "Inlärda ord", "tr" to "Öğrenilen Kelimeler",
            "de" to "Gelernte Wörter", "fr" to "Mots appris", "es" to "Palabras aprendidas", "ru" to "Выученные слова",
            "zh" to "已学单词", "hi" to "सीखे गए शब्द", "ar" to "الكلمات المتعلمة"
        ),
        "minutes_suffix" to mapOf(
            "en" to "min", "fa" to "دقیقه", "sv" to "min", "tr" to "dk", "de" to "Min", "fr" to "min",
            "es" to "min", "ru" to "мин", "zh" to "分钟", "hi" to "मिनट", "ar" to "دقيقة"
        ),
        "today_label" to mapOf(
            "en" to "Today", "fa" to "امروز", "sv" to "Idag", "tr" to "Bugün", "de" to "Heute",
            "fr" to "Aujourd'hui", "es" to "Hoy", "ru" to "Сегодня", "zh" to "今天", "hi" to "आज", "ar" to "اليوم"
        ),
        "all_time_label" to mapOf(
            "en" to "All Time", "fa" to "همهٔ زمان‌ها", "sv" to "Totalt", "tr" to "Tüm Zamanlar", "de" to "Insgesamt",
            "fr" to "Depuis le début", "es" to "Total", "ru" to "За всё время", "zh" to "累计", "hi" to "कुल मिलाकर", "ar" to "منذ البداية"
        ),
        "progress_by_category" to mapOf(
            "en" to "Progress by Category", "fa" to "پیشرفت در هر دسته", "sv" to "Framsteg per kategori",
            "tr" to "Kategoriye Göre İlerleme", "de" to "Fortschritt nach Kategorie", "fr" to "Progrès par catégorie",
            "es" to "Progreso por categoría", "ru" to "Прогресс по категориям", "zh" to "各类别进度",
            "hi" to "श्रेणी अनुसार प्रगति", "ar" to "التقدم حسب الفئة"
        ),

        // LullabiesScreen
        "lullabies_title" to mapOf(            "en" to "Golden Dreams 🌙", "fa" to "خواب‌های طلایی 🌙", "sv" to "Gyllene drömmar 🌙",
            "tr" to "Altın Rüyalar 🌙", "de" to "Goldene Träume 🌙", "fr" to "Rêves dorés 🌙",
            "es" to "Sueños dorados 🌙", "ru" to "Золотые сны 🌙", "zh" to "黄金梦境 🌙",
            "hi" to "सुनहरे सपने 🌙", "ar" to "أحلام ذهبية 🌙"
        ),
        "sleep_timer_label" to mapOf(
            "en" to "Sleep Timer", "fa" to "تایمر خواب", "sv" to "Sömntimer", "tr" to "Uyku Zamanlayıcısı",
            "de" to "Schlaf-Timer", "fr" to "Minuteur de sommeil", "es" to "Temporizador de sueño",
            "ru" to "Таймер сна", "zh" to "睡眠计时器", "hi" to "स्लीप टाइमर", "ar" to "مؤقت النوم"
        ),
        "next_button" to mapOf(
            "en" to "Next →", "fa" to "بعدی →", "sv" to "Nästa →", "tr" to "Sonraki →",
            "de" to "Weiter →", "fr" to "Suivant →", "es" to "Siguiente →", "ru" to "Далее →",
            "zh" to "下一个 →", "hi" to "अगला →", "ar" to "← التالي"
        ),
        "balloons_intro_speech" to mapOf(
            "en" to "Let's pop some balloons!", "fa" to "بیا بادکنک‌ها رو بترکونیم!",
            "sv" to "Nu poppar vi ballonger!", "tr" to "Hadi balonları patlatalım!",
            "de" to "Lass uns ein paar Luftballons platzen lassen!", "fr" to "Allons faire éclater des ballons !",
            "es" to "¡Vamos a reventar globos!", "ru" to "Давай лопать шарики!",
            "zh" to "我们来戳气球吧！", "hi" to "चलो गुब्बारे फोड़ते हैं!", "ar" to "لنفرقع بعض البالونات!"
        ),
    )

    /** ترجمه‌ی متن با کد کلید؛ اگه زبون پیدا نشد میره سراغ انگلیسی؛ اگه کلید هم پیدا نشد خود کلید برمی‌گرده */
    fun t(key: String, lang: String): String =
        strings[key]?.get(lang) ?: strings[key]?.get("en") ?: key
}
