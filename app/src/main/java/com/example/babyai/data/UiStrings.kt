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

        // SettingsScreen
        "settings_title" to mapOf(
            "en" to "Settings", "fa" to "تنظیمات", "sv" to "Inställningar", "tr" to "Ayarlar",
            "de" to "Einstellungen", "fr" to "Paramètres", "es" to "Ajustes", "ru" to "Настройки",
            "zh" to "设置", "hi" to "सेटिंग्स", "ar" to "الإعدادات"
        ),
        "settings_photo_size" to mapOf(
            "en" to "Photo Size", "fa" to "سایز عکس‌ها", "sv" to "Bildstorlek", "tr" to "Fotoğraf Boyutu",
            "de" to "Fotogröße", "fr" to "Taille des photos", "es" to "Tamaño de fotos", "ru" to "Размер фото",
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
            "en" to "Child's Age", "fa" to "سن فرزندتون", "sv" to "Barnets ålder", "tr" to "Çocuğunuzun Yaşı",
            "de" to "Alter des Kindes", "fr" to "Âge de l'enfant", "es" to "Edad del niño", "ru" to "Возраст ребёнка",
            "zh" to "孩子的年龄", "hi" to "बच्चे की उम्र", "ar" to "عمر الطفل"
        ),
        "years_suffix" to mapOf(
            "en" to "yrs", "fa" to "سال", "sv" to "år", "tr" to "yaş", "de" to "Jahre",
            "fr" to "ans", "es" to "años", "ru" to "лет", "zh" to "岁", "hi" to "वर्ष", "ar" to "سنوات"
        ),
        "settings_parental_gate" to mapOf(
            "en" to "Parental Gate", "fa" to "قفل والدین", "sv" to "Föräldralås", "tr" to "Ebeveyn Kilidi",
            "de" to "Elternsperre", "fr" to "Verrou parental", "es" to "Control parental", "ru" to "Родительский контроль",
            "zh" to "家长锁", "hi" to "पेरेंटल गेट", "ar" to "بوابة الوالدين"
        ),
        "settings_music" to mapOf(
            "en" to "Background Music", "fa" to "موزیک پس‌زمینه", "sv" to "Bakgrundsmusik", "tr" to "Arka Plan Müziği",
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
            "de" to "Elterndashboard", "fr" to "Tableau de bord parental", "es" to "Panel de padres", "ru" to "Панель родителей",
            "zh" to "家长面板", "hi" to "पेरेंट डैशबोर्ड", "ar" to "لوحة الوالدين"
        ),
        "settings_golden_dreams" to mapOf(
            "en" to "Golden Dreams", "fa" to "خواب‌های طلایی", "sv" to "Gyllene drömmar", "tr" to "Altın Rüyalar",
            "de" to "Goldene Träume", "fr" to "Rêves dorés", "es" to "Sueños dorados", "ru" to "Золотые сны",
            "zh" to "黄金梦境", "hi" to "सुनहरे सपने", "ar" to "أحلام ذهبية"
        ),
        "settings_record_instruction" to mapOf(
            "en" to "To record your own voice for a word, open that word and tap the microphone icon.",
            "fa" to "برای ضبط صدای خودتون برای هر کلمه، وارد همون کلمه بشید و روی آیکون میکروفون بزنید.",
            "sv" to "För att spela in din egen röst för ett ord, öppna ordet och tryck på mikrofonikonen.",
            "tr" to "Bir kelime için kendi sesinizi kaydetmek üzere o kelimeyi açın ve mikrofon simgesine dokunun.",
            "de" to "Um deine eigene Stimme für ein Wort aufzunehmen, öffne das Wort und tippe auf das Mikrofon-Symbol.",
            "fr" to "Pour enregistrer ta propre voix pour un mot, ouvre ce mot et touche l'icône du microphone.",
            "es" to "Para grabar tu propia voz para una palabra, abre esa palabra y toca el ícono del micrófono.",
            "ru" to "Чтобы записать свой голос для слова, откройте это слово и нажмите на значок микрофона.",
            "zh" to "要为某个单词录制您自己的声音，请打开该单词并点击麦克风图标。",
            "hi" to "किसी शब्द के लिए अपनी आवाज़ रिकॉर्ड करने के लिए, वह शब्द खोलें और माइक्रोफ़ोन आइकन पर टैप करें।",
            "ar" to "لتسجيل صوتك لكلمة ما، افتح تلك الكلمة واضغط على أيقونة الميكروفون."
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
            "sv" to "Jag skulle vilja veta ditt namn!", "tr" to "Adını bilmek isterim!",
            "de" to "Ich würde gerne deinen Namen wissen!", "fr" to "J'aimerais connaître ton prénom !",
            "es" to "¡Me encantaría saber tu nombre!", "ru" to "Я хочу узнать твоё имя!",
            "zh" to "我想知道你的名字！", "hi" to "मैं तुम्हारा नाम जानना चाहता हूँ!",
            "ar" to "أحب أن أعرف اسمك!"
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
            "en" to "Choose your friend!", "fa" to "دوستت رو انتخاب کن!", "sv" to "Välj din vän!",
            "tr" to "Arkadaşını seç!", "de" to "Wähle deinen Freund!", "fr" to "Choisis ton ami !",
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
            "sv" to "{name}s framsteg raderas för alltid.",
            "tr" to "{name} adlı oyuncunun ilerlemesi kalıcı olarak silinecek.",
            "de" to "Der Fortschritt von {name} wird dauerhaft gelöscht.",
            "fr" to "Les progrès de {name} seront supprimés pour toujours.",
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
    )

    /** ترجمه‌ی متن با کد کلید؛ اگه زبون پیدا نشد میره سراغ انگلیسی؛ اگه کلید هم پیدا نشد خود کلید برمی‌گرده */
    fun t(key: String, lang: String): String =
        strings[key]?.get(lang) ?: strings[key]?.get("en") ?: key
}
