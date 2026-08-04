package com.example.babyai.data

/**
 * یک صفحه از داستان.
 * اگه targetWordId خالی باشه، یعنی صفحه‌ی روایی ساده‌ست (بدون تعامل لمسی).
 * اگه پر باشه، یعنی باید از بین targetWordId + distractorWordIds، بچه درست‌ش رو لمس کنه.
 * texts: نقشه‌ی زبان -> متن اون صفحه
 */
data class StoryPage(
    val texts: Map<String, String>,
    val targetWordId: String? = null,
    val distractorWordIds: List<String> = emptyList()
) {
    val textEn: String get() = texts["en"] ?: ""
    val textFa: String get() = texts["fa"] ?: textEn

    fun text(lang: String): String = texts[lang] ?: textEn
}

data class Story(
    val id: String,
    val titles: Map<String, String>,
    val emoji: String,
    val pages: List<StoryPage>
) {
    val titleEn: String get() = titles["en"] ?: id
    val titleFa: String get() = titles["fa"] ?: titleEn

    fun title(lang: String): String = titles[lang] ?: titleEn
}

object StoryRepository {

    private fun p(
        targetWordId: String? = null,
        distractorWordIds: List<String> = emptyList(),
        vararg texts: Pair<String, String>
    ): StoryPage = StoryPage(texts.toMap(), targetWordId, distractorWordIds)

    val farmStory = Story(
        id = "farm",
        titles = mapOf(
            "en" to "A Day at the Farm", "fa" to "یه روز توی مزرعه", "sv" to "En dag på bondgården",
            "tr" to "Çiftlikte Bir Gün", "de" to "Ein Tag auf dem Bauernhof", "fr" to "Une journée à la ferme",
            "es" to "Un día en la granja", "ru" to "День на ферме", "zh" to "农场的一天",
            "hi" to "खेत में एक दिन", "ar" to "يوم في المزرعة"
        ),
        emoji = "🐄",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "One sunny morning, our friend went to visit a farm full of animals!",
                    "fa" to "یه روز آفتابی، دوست ما رفت به یه مزرعه پر از حیوانات!",
                    "sv" to "En solig morgon besökte vår vän en bondgård full med djur!",
                    "tr" to "Güneşli bir sabah, arkadaşımız hayvanlarla dolu bir çiftliği ziyaret etti!",
                    "de" to "An einem sonnigen Morgen besuchte unser Freund einen Bauernhof voller Tiere!",
                    "fr" to "Par un matin ensoleillé, notre ami est allé visiter une ferme pleine d'animaux !",
                    "es" to "Una mañana soleada, ¡nuestro amigo fue a visitar una granja llena de animales!",
                    "ru" to "Одним солнечным утром наш друг отправился на ферму, полную животных!",
                    "zh" to "在一个阳光明媚的早晨，我们的朋友去参观了一个满是动物的农场！",
                    "hi" to "एक धूप भरी सुबह, हमारा दोस्त जानवरों से भरे एक खेत में गया!",
                    "ar" to "في صباح مشمس، ذهب صديقنا لزيارة مزرعة مليئة بالحيوانات!"
                )
            ),
            p(
                targetWordId = "cow", distractorWordIds = listOf("chicken", "duck"),
                texts = *arrayOf(
                    "en" to "First, a cow said hello! Can you find the cow?",
                    "fa" to "اول یه گاو بهش سلام کرد! می‌تونی گاو رو پیدا کنی؟",
                    "sv" to "Först sa en ko hej! Kan du hitta kon?",
                    "tr" to "İlk önce bir inek merhaba dedi! İneği bulabilir misin?",
                    "de" to "Zuerst sagte eine Kuh hallo! Kannst du die Kuh finden?",
                    "fr" to "D'abord, une vache a dit bonjour ! Peux-tu trouver la vache ?",
                    "es" to "Primero, ¡una vaca dijo hola! ¿Puedes encontrar la vaca?",
                    "ru" to "Сначала корова сказала привет! Сможешь найти корову?",
                    "zh" to "首先，一头奶牛打了招呼！你能找到奶牛吗？",
                    "hi" to "पहले, एक गाय ने नमस्ते कहा! क्या तुम गाय को ढूंढ सकते हो?",
                    "ar" to "أولاً، قالت بقرة مرحباً! هل يمكنك إيجاد البقرة؟"
                )
            ),
            p(
                targetWordId = "chicken", distractorWordIds = listOf("cow", "sheep"),
                texts = *arrayOf(
                    "en" to "Then a chicken came running by! Can you find the chicken?",
                    "fa" to "بعد یه مرغ دوان‌دوان اومد! می‌تونی مرغ رو پیدا کنی؟",
                    "sv" to "Sedan kom en höna springande förbi! Kan du hitta hönan?",
                    "tr" to "Sonra bir tavuk koşarak geldi! Tavuğu bulabilir misin?",
                    "de" to "Dann kam ein Huhn vorbeigelaufen! Kannst du das Huhn finden?",
                    "fr" to "Puis une poule est passée en courant ! Peux-tu trouver la poule ?",
                    "es" to "Luego una gallina pasó corriendo! ¿Puedes encontrar la gallina?",
                    "ru" to "Потом мимо пробежала курица! Сможешь найти курицу?",
                    "zh" to "然后一只鸡跑了过来！你能找到鸡吗？",
                    "hi" to "फिर एक मुर्गी दौड़ती हुई आई! क्या तुम मुर्गी को ढूंढ सकते हो?",
                    "ar" to "ثم جاءت دجاجة تركض! هل يمكنك إيجاد الدجاجة؟"
                )
            ),
            p(
                targetWordId = "duck", distractorWordIds = listOf("sheep", "cow"),
                texts = *arrayOf(
                    "en" to "A duck was swimming in the pond! Can you find the duck?",
                    "fa" to "یه اردک توی برکه شنا می‌کرد! می‌تونی اردک رو پیدا کنی؟",
                    "sv" to "En anka simmade i dammen! Kan du hitta ankan?",
                    "tr" to "Bir ördek gölette yüzüyordu! Ördeği bulabilir misin?",
                    "de" to "Eine Ente schwamm im Teich! Kannst du die Ente finden?",
                    "fr" to "Un canard nageait dans l'étang ! Peux-tu trouver le canard ?",
                    "es" to "Un pato nadaba en el estanque! ¿Puedes encontrar el pato?",
                    "ru" to "Утка плавала в пруду! Сможешь найти утку?",
                    "zh" to "一只鸭子在池塘里游泳！你能找到鸭子吗？",
                    "hi" to "एक बत्तख तालाब में तैर रही थी! क्या तुम बत्तख को ढूंढ सकते हो?",
                    "ar" to "كانت بطة تسبح في البركة! هل يمكنك إيجاد البطة؟"
                )
            ),
            p(
                targetWordId = "sheep", distractorWordIds = listOf("duck", "chicken"),
                texts = *arrayOf(
                    "en" to "Last but not least, a fluffy sheep waved hi! Can you find the sheep?",
                    "fa" to "در آخر، یه گوسفند پشمالو دست تکون داد! می‌تونی گوسفند رو پیدا کنی؟",
                    "sv" to "Sist men inte minst vinkade ett lurvigt får hej! Kan du hitta fåret?",
                    "tr" to "Son olarak, tüylü bir koyun elini salladı! Koyunu bulabilir misin?",
                    "de" to "Zu guter Letzt winkte ein flauschiges Schaf! Kannst du das Schaf finden?",
                    "fr" to "Enfin, un mouton tout doux a fait coucou ! Peux-tu trouver le mouton ?",
                    "es" to "Por último, ¡una oveja peluda saludó! ¿Puedes encontrar la oveja?",
                    "ru" to "И наконец, пушистая овца помахала привет! Сможешь найти овцу?",
                    "zh" to "最后，一只毛茸茸的绵羊挥手打招呼！你能找到绵羊吗？",
                    "hi" to "आखिर में, एक रोएंदार भेड़ ने हाथ हिलाया! क्या तुम भेड़ को ढूंढ सकते हो?",
                    "ar" to "وأخيراً، لوّح خروف كثيف الصوف بيده! هل يمكنك إيجاد الخروف؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "What a wonderful day at the farm! Great job!",
                    "fa" to "چه روز فوق‌العاده‌ای توی مزرعه بود! آفرین بهت!",
                    "sv" to "Vilken underbar dag på bondgården! Bra jobbat!",
                    "tr" to "Çiftlikte ne harika bir gündü! Aferin sana!",
                    "de" to "Was für ein wunderbarer Tag auf dem Bauernhof! Gut gemacht!",
                    "fr" to "Quelle merveilleuse journée à la ferme ! Bravo !",
                    "es" to "¡Qué día tan maravilloso en la granja! ¡Buen trabajo!",
                    "ru" to "Какой прекрасный день на ферме! Молодец!",
                    "zh" to "在农场度过了多么美好的一天！做得好！",
                    "hi" to "खेत में क्या शानदार दिन था! शाबाश!",
                    "ar" to "يا له من يوم رائع في المزرعة! أحسنت!"
                )
            ),
        )
    )

    val rainbowStory = Story(
        id = "rainbow",
        titles = mapOf(
            "en" to "The Magic Rainbow", "fa" to "رنگین‌کمون جادویی", "sv" to "Den magiska regnbågen",
            "tr" to "Sihirli Gökkuşağı", "de" to "Der magische Regenbogen", "fr" to "L'arc-en-ciel magique",
            "es" to "El arcoíris mágico", "ru" to "Волшебная радуга", "zh" to "神奇的彩虹",
            "hi" to "जादुई इंद्रधनुष", "ar" to "قوس قزح السحري"
        ),
        emoji = "🌈",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "After the rain, a magic rainbow appeared in the sky!",
                    "fa" to "بعد از بارون، یه رنگین‌کمون جادویی توی آسمون ظاهر شد!",
                    "sv" to "Efter regnet dök en magisk regnbåge upp på himlen!",
                    "tr" to "Yağmurdan sonra gökyüzünde sihirli bir gökkuşağı belirdi!",
                    "de" to "Nach dem Regen erschien ein magischer Regenbogen am Himmel!",
                    "fr" to "Après la pluie, un arc-en-ciel magique est apparu dans le ciel !",
                    "es" to "Después de la lluvia, ¡apareció un arcoíris mágico en el cielo!",
                    "ru" to "После дождя на небе появилась волшебная радуга!",
                    "zh" to "雨后，天空中出现了一道神奇的彩虹！",
                    "hi" to "बारिश के बाद, आकाश में एक जादुई इंद्रधनुष दिखाई दिया!",
                    "ar" to "بعد المطر، ظهر قوس قزح سحري في السماء!"
                )
            ),
            p(
                targetWordId = "red", distractorWordIds = listOf("blue", "yellow"),
                texts = *arrayOf(
                    "en" to "The first color was bright and warm. Can you find red?",
                    "fa" to "اولین رنگ گرم و روشن بود. می‌تونی قرمز رو پیدا کنی؟",
                    "sv" to "Den första färgen var ljus och varm. Kan du hitta röd?",
                    "tr" to "İlk renk parlak ve sıcaktı. Kırmızıyı bulabilir misin?",
                    "de" to "Die erste Farbe war hell und warm. Kannst du Rot finden?",
                    "fr" to "La première couleur était vive et chaude. Peux-tu trouver le rouge ?",
                    "es" to "El primer color era brillante y cálido. ¿Puedes encontrar el rojo?",
                    "ru" to "Первый цвет был ярким и тёплым. Сможешь найти красный?",
                    "zh" to "第一种颜色明亮而温暖。你能找到红色吗？",
                    "hi" to "पहला रंग चमकीला और गर्म था। क्या तुम लाल रंग ढूंढ सकते हो?",
                    "ar" to "كان اللون الأول ساطعاً ودافئاً. هل يمكنك إيجاد اللون الأحمر؟"
                )
            ),
            p(
                targetWordId = "blue", distractorWordIds = listOf("green", "red"),
                texts = *arrayOf(
                    "en" to "Next came a color like the sky. Can you find blue?",
                    "fa" to "بعدش رنگی مثل آسمون اومد. می‌تونی آبی رو پیدا کنی؟",
                    "sv" to "Sedan kom en färg som himlen. Kan du hitta blå?",
                    "tr" to "Sonra gökyüzü gibi bir renk geldi. Maviyi bulabilir misin?",
                    "de" to "Dann kam eine Farbe wie der Himmel. Kannst du Blau finden?",
                    "fr" to "Puis vint une couleur comme le ciel. Peux-tu trouver le bleu ?",
                    "es" to "Luego vino un color como el cielo. ¿Puedes encontrar el azul?",
                    "ru" to "Затем появился цвет, как небо. Сможешь найти синий?",
                    "zh" to "接着出现了像天空一样的颜色。你能找到蓝色吗？",
                    "hi" to "फिर आकाश जैसा एक रंग आया। क्या तुम नीला रंग ढूंढ सकते हो?",
                    "ar" to "ثم جاء لون مثل السماء. هل يمكنك إيجاد اللون الأزرق؟"
                )
            ),
            p(
                targetWordId = "yellow", distractorWordIds = listOf("green", "blue"),
                texts = *arrayOf(
                    "en" to "Then a bright color like the sun. Can you find yellow?",
                    "fa" to "بعد یه رنگ روشن مثل خورشید. می‌تونی زرد رو پیدا کنی؟",
                    "sv" to "Sedan en ljus färg som solen. Kan du hitta gul?",
                    "tr" to "Sonra güneş gibi parlak bir renk. Sarıyı bulabilir misin?",
                    "de" to "Dann eine helle Farbe wie die Sonne. Kannst du Gelb finden?",
                    "fr" to "Puis une couleur vive comme le soleil. Peux-tu trouver le jaune ?",
                    "es" to "Luego un color brillante como el sol. ¿Puedes encontrar el amarillo?",
                    "ru" to "Потом яркий цвет, как солнце. Сможешь найти жёлтый?",
                    "zh" to "然后是像太阳一样明亮的颜色。你能找到黄色吗？",
                    "hi" to "फिर सूरज जैसा एक चमकीला रंग। क्या तुम पीला रंग ढूंढ सकते हो?",
                    "ar" to "ثم لون ساطع مثل الشمس. هل يمكنك إيجاد اللون الأصفر؟"
                )
            ),
            p(
                targetWordId = "green", distractorWordIds = listOf("yellow", "red"),
                texts = *arrayOf(
                    "en" to "Last, a color like fresh grass. Can you find green?",
                    "fa" to "در آخر، رنگی مثل چمن تازه. می‌تونی سبز رو پیدا کنی؟",
                    "sv" to "Sist, en färg som färskt gräs. Kan du hitta grön?",
                    "tr" to "Son olarak, taze çimen gibi bir renk. Yeşili bulabilir misin?",
                    "de" to "Zuletzt eine Farbe wie frisches Gras. Kannst du Grün finden?",
                    "fr" to "Enfin, une couleur comme l'herbe fraîche. Peux-tu trouver le vert ?",
                    "es" to "Por último, un color como la hierba fresca. ¿Puedes encontrar el verde?",
                    "ru" to "Наконец, цвет, как свежая трава. Сможешь найти зелёный?",
                    "zh" to "最后，一种像新鲜青草一样的颜色。你能找到绿色吗？",
                    "hi" to "आखिर में, ताज़ी घास जैसा एक रंग। क्या तुम हरा रंग ढूंढ सकते हो?",
                    "ar" to "أخيراً، لون مثل العشب الطازج. هل يمكنك إيجاد اللون الأخضر؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "The rainbow was full of beautiful colors! Great job!",
                    "fa" to "رنگین‌کمون پر از رنگ‌های زیبا بود! آفرین بهت!",
                    "sv" to "Regnbågen var full av vackra färger! Bra jobbat!",
                    "tr" to "Gökkuşağı güzel renklerle doluydu! Aferin sana!",
                    "de" to "Der Regenbogen war voller schöner Farben! Gut gemacht!",
                    "fr" to "L'arc-en-ciel était plein de belles couleurs ! Bravo !",
                    "es" to "¡El arcoíris estaba lleno de hermosos colores! ¡Buen trabajo!",
                    "ru" to "Радуга была полна прекрасных цветов! Молодец!",
                    "zh" to "彩虹充满了美丽的颜色！做得好！",
                    "hi" to "इंद्रधनुष सुंदर रंगों से भरा था! शाबाश!",
                    "ar" to "كان قوس قزح مليئاً بالألوان الجميلة! أحسنت!"
                )
            ),
        )
    )

    val familyStory = Story(
        id = "family",
        titles = mapOf(
            "en" to "Family Gathering", "fa" to "مهمونی خانوادگی", "sv" to "Familjeträff",
            "tr" to "Aile Toplantısı", "de" to "Familientreffen", "fr" to "Réunion de famille",
            "es" to "Reunión familiar", "ru" to "Семейная встреча", "zh" to "家庭聚会",
            "hi" to "पारिवारिक मिलन", "ar" to "لقاء عائلي"
        ),
        emoji = "👨‍👩‍👧",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "Today the whole family came together for a big gathering!",
                    "fa" to "امروز کل خانواده برای یه مهمونی بزرگ دور هم جمع شدن!",
                    "sv" to "Idag samlades hela familjen för en stor träff!",
                    "tr" to "Bugün bütün aile büyük bir toplantı için bir araya geldi!",
                    "de" to "Heute kam die ganze Familie zu einem großen Treffen zusammen!",
                    "fr" to "Aujourd'hui, toute la famille s'est réunie pour un grand rassemblement !",
                    "es" to "¡Hoy toda la familia se reunió para una gran reunión!",
                    "ru" to "Сегодня вся семья собралась на большую встречу!",
                    "zh" to "今天全家人聚在一起举行了一场大聚会！",
                    "hi" to "आज पूरा परिवार एक बड़े मिलन के लिए इकट्ठा हुआ!",
                    "ar" to "اليوم اجتمعت العائلة كلها في لقاء كبير!"
                )
            ),
            p(
                targetWordId = "mom", distractorWordIds = listOf("dad", "grandma"),
                texts = *arrayOf(
                    "en" to "The one who baked cookies was so happy to see everyone. Can you find mom?",
                    "fa" to "کسی که کلوچه پخته بود از دیدن همه خیلی خوشحال بود. می‌تونی مامان رو پیدا کنی؟",
                    "sv" to "Den som bakade kakor var så glad att se alla. Kan du hitta mamma?",
                    "tr" to "Kurabiye pişiren kişi herkesi görmekten çok mutluydu. Anneyi bulabilir misin?",
                    "de" to "Diejenige, die Kekse gebacken hat, war so glücklich, alle zu sehen. Kannst du Mama finden?",
                    "fr" to "Celle qui a fait des biscuits était si heureuse de voir tout le monde. Peux-tu trouver maman ?",
                    "es" to "La que horneó galletas estaba muy feliz de ver a todos. ¿Puedes encontrar a mamá?",
                    "ru" to "Та, кто испекла печенье, была так рада всех видеть. Сможешь найти маму?",
                    "zh" to "烤饼干的人见到大家都很开心。你能找到妈妈吗？",
                    "hi" to "जिसने कुकीज़ बनाईं वह सबको देखकर बहुत खुश था। क्या तुम माँ को ढूंढ सकते हो?",
                    "ar" to "كانت من خبزت الكعك سعيدة جداً برؤية الجميع. هل يمكنك إيجاد ماما؟"
                )
            ),
            p(
                targetWordId = "dad", distractorWordIds = listOf("grandpa", "mom"),
                texts = *arrayOf(
                    "en" to "Someone played games with everyone in the yard. Can you find dad?",
                    "fa" to "یه نفر توی حیاط با همه بازی می‌کرد. می‌تونی بابا رو پیدا کنی؟",
                    "sv" to "Någon lekte lekar med alla på gården. Kan du hitta pappa?",
                    "tr" to "Biri bahçede herkesle oyunlar oynadı. Babayı bulabilir misin?",
                    "de" to "Jemand spielte mit allen im Garten. Kannst du Papa finden?",
                    "fr" to "Quelqu'un a joué à des jeux avec tout le monde dans la cour. Peux-tu trouver papa ?",
                    "es" to "Alguien jugó juegos con todos en el patio. ¿Puedes encontrar a papá?",
                    "ru" to "Кто-то играл в игры со всеми во дворе. Сможешь найти папу?",
                    "zh" to "有人在院子里和大家一起玩游戏。你能找到爸爸吗？",
                    "hi" to "किसी ने आंगन में सबके साथ खेल खेले। क्या तुम पापा को ढूंढ सकते हो?",
                    "ar" to "لعب أحدهم ألعاباً مع الجميع في الفناء. هل يمكنك إيجاد بابا؟"
                )
            ),
            p(
                targetWordId = "grandma", distractorWordIds = listOf("grandpa", "dad"),
                texts = *arrayOf(
                    "en" to "Someone told the best old stories. Can you find grandma?",
                    "fa" to "یه نفر بهترین قصه‌های قدیمی رو تعریف کرد. می‌تونی مادربزرگ رو پیدا کنی؟",
                    "sv" to "Någon berättade de bästa gamla historierna. Kan du hitta mormor?",
                    "tr" to "Biri en güzel eski hikayeleri anlattı. Anneanneyi bulabilir misin?",
                    "de" to "Jemand erzählte die besten alten Geschichten. Kannst du Oma finden?",
                    "fr" to "Quelqu'un a raconté les meilleures vieilles histoires. Peux-tu trouver grand-mère ?",
                    "es" to "Alguien contó las mejores historias antiguas. ¿Puedes encontrar a la abuela?",
                    "ru" to "Кто-то рассказывал лучшие старые истории. Сможешь найти бабушку?",
                    "zh" to "有人讲了最棒的老故事。你能找到奶奶吗？",
                    "hi" to "किसी ने सबसे अच्छी पुरानी कहानियाँ सुनाईं। क्या तुम दादी को ढूंढ सकते हो?",
                    "ar" to "روى أحدهم أفضل القصص القديمة. هل يمكنك إيجاد الجدة؟"
                )
            ),
            p(
                targetWordId = "grandpa", distractorWordIds = listOf("mom", "grandma"),
                texts = *arrayOf(
                    "en" to "And someone gave everyone the warmest hugs. Can you find grandpa?",
                    "fa" to "و یه نفر به همه گرم‌ترین بغل‌ها رو داد. می‌تونی پدربزرگ رو پیدا کنی؟",
                    "sv" to "Och någon gav alla de varmaste kramarna. Kan du hitta morfar?",
                    "tr" to "Ve biri herkese en sıcak kucaklamaları verdi. Dedeyi bulabilir misin?",
                    "de" to "Und jemand gab allen die wärmsten Umarmungen. Kannst du Opa finden?",
                    "fr" to "Et quelqu'un a fait les câlins les plus chaleureux à tout le monde. Peux-tu trouver grand-père ?",
                    "es" to "Y alguien le dio a todos los abrazos más cálidos. ¿Puedes encontrar al abuelo?",
                    "ru" to "А кто-то обнимал всех самыми тёплыми объятиями. Сможешь найти дедушку?",
                    "zh" to "还有人给了大家最温暖的拥抱。你能找到爷爷吗？",
                    "hi" to "और किसी ने सबको सबसे गर्मजोशी भरा गले लगाया। क्या तुम दादा को ढूंढ सकते हो?",
                    "ar" to "وأعطى أحدهم الجميع أدفأ العناقات. هل يمكنك إيجاد الجد؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "It was the best family day ever! Great job!",
                    "fa" to "بهترین روز خانوادگی بود! آفرین بهت!",
                    "sv" to "Det var den bästa familjedagen någonsin! Bra jobbat!",
                    "tr" to "Bu şimdiye kadarki en güzel aile günüydü! Aferin sana!",
                    "de" to "Es war der beste Familientag überhaupt! Gut gemacht!",
                    "fr" to "C'était la meilleure journée en famille de tous les temps ! Bravo !",
                    "es" to "¡Fue el mejor día familiar de todos! ¡Buen trabajo!",
                    "ru" to "Это был лучший семейный день! Молодец!",
                    "zh" to "这是有史以来最棒的家庭日！做得好！",
                    "hi" to "यह अब तक का सबसे अच्छा पारिवारिक दिन था! शाबाश!",
                    "ar" to "لقد كان أفضل يوم عائلي على الإطلاق! أحسنت!"
                )
            ),
        )
    )

    val all = listOf(farmStory, rainbowStory, familyStory)

    fun storyById(id: String): Story? = all.find { it.id == id }
}
