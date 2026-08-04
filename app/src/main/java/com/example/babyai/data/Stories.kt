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

    val seaStory = Story(
        id = "sea",
        titles = mapOf(
            "en" to "Under the Sea", "fa" to "زیر دریا", "sv" to "Under havet", "tr" to "Deniz Altında",
            "de" to "Unter dem Meer", "fr" to "Sous la mer", "es" to "Bajo el mar", "ru" to "Под водой",
            "zh" to "海底世界", "hi" to "समुद्र के नीचे", "ar" to "تحت البحر"
        ),
        emoji = "🌊",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "Our friend put on a snorkel and dove into the deep blue sea!",
                    "fa" to "دوست ما ماسک شنا زد و توی دریای آبی عمیق شیرجه رفت!",
                    "sv" to "Vår vän tog på sig snorkeln och dök ner i det djupa blå havet!",
                    "tr" to "Arkadaşımız şnorkelini taktı ve derin mavi denize daldı!",
                    "de" to "Unser Freund setzte die Schnorchelmaske auf und tauchte ins tiefblaue Meer!",
                    "fr" to "Notre ami a mis son tuba et a plongé dans la mer bleue profonde !",
                    "es" to "¡Nuestro amigo se puso el esnórquel y se sumergió en el mar azul profundo!",
                    "ru" to "Наш друг надел маску для подводного плавания и нырнул в глубокое синее море!",
                    "zh" to "我们的朋友戴上潜水镜，跳进了深蓝色的大海！",
                    "hi" to "हमारे दोस्त ने स्नॉर्कल पहना और गहरे नीले समुद्र में गोता लगाया!",
                    "ar" to "ارتدى صديقنا قناع الغوص وغطس في البحر الأزرق العميق!"
                )
            ),
            p(
                targetWordId = "fish", distractorWordIds = listOf("turtle", "duck"),
                texts = *arrayOf(
                    "en" to "A colorful fish swam right by! Can you find the fish?",
                    "fa" to "یه ماهی رنگارنگ از کنارش شنا کرد! می‌تونی ماهی رو پیدا کنی؟",
                    "sv" to "En färgglad fisk simmade förbi! Kan du hitta fisken?",
                    "tr" to "Renkli bir balık yanından yüzdü! Balığı bulabilir misin?",
                    "de" to "Ein bunter Fisch schwamm direkt vorbei! Kannst du den Fisch finden?",
                    "fr" to "Un poisson coloré a nagé juste à côté ! Peux-tu trouver le poisson ?",
                    "es" to "¡Un pez colorido nadó justo al lado! ¿Puedes encontrar el pez?",
                    "ru" to "Мимо проплыла яркая рыбка! Сможешь найти рыбку?",
                    "zh" to "一条五彩斑斓的鱼游了过来！你能找到鱼吗？",
                    "hi" to "एक रंगीन मछली पास से तैर गई! क्या तुम मछली को ढूंढ सकते हो?",
                    "ar" to "سبحت سمكة ملونة بجانبه! هل يمكنك إيجاد السمكة؟"
                )
            ),
            p(
                targetWordId = "turtle", distractorWordIds = listOf("fish", "duck"),
                texts = *arrayOf(
                    "en" to "A gentle turtle glided through the water. Can you find the turtle?",
                    "fa" to "یه لاک‌پشت آروم توی آب شنا می‌کرد. می‌تونی لاک‌پشت رو پیدا کنی؟",
                    "sv" to "En lugn sköldpadda gled genom vattnet. Kan du hitta sköldpaddan?",
                    "tr" to "Sakin bir kaplumbağa suda süzülüyordu. Kaplumbağayı bulabilir misin?",
                    "de" to "Eine sanfte Schildkröte glitt durchs Wasser. Kannst du die Schildkröte finden?",
                    "fr" to "Une douce tortue glissait dans l'eau. Peux-tu trouver la tortue ?",
                    "es" to "Una tortuga tranquila se deslizaba por el agua. ¿Puedes encontrar la tortuga?",
                    "ru" to "Спокойная черепаха плавно скользила по воде. Сможешь найти черепаху?",
                    "zh" to "一只温和的海龟在水中滑行。你能找到海龟吗？",
                    "hi" to "एक शांत कछुआ पानी में तैर रहा था। क्या तुम कछुए को ढूंढ सकते हो?",
                    "ar" to "انزلقت سلحفاة هادئة عبر الماء. هل يمكنك إيجاد السلحفاة؟"
                )
            ),
            p(
                targetWordId = "duck", distractorWordIds = listOf("fish", "turtle"),
                texts = *arrayOf(
                    "en" to "A duck was paddling happily on top of the waves. Can you find the duck?",
                    "fa" to "یه اردک با خوشحالی روی موج‌ها شنا می‌کرد. می‌تونی اردک رو پیدا کنی؟",
                    "sv" to "En anka paddlade glatt ovanpå vågorna. Kan du hitta ankan?",
                    "tr" to "Bir ördek dalgaların üzerinde mutlulukla yüzüyordu. Ördeği bulabilir misin?",
                    "de" to "Eine Ente paddelte fröhlich auf den Wellen. Kannst du die Ente finden?",
                    "fr" to "Un canard pagayait joyeusement sur les vagues. Peux-tu trouver le canard ?",
                    "es" to "Un pato remaba felizmente sobre las olas. ¿Puedes encontrar el pato?",
                    "ru" to "Утка радостно плыла по волнам. Сможешь найти утку?",
                    "zh" to "一只鸭子快乐地在浪花上划水。你能找到鸭子吗？",
                    "hi" to "एक बत्तख खुशी से लहरों के ऊपर तैर रही थी। क्या तुम बत्तख को ढूंढ सकते हो?",
                    "ar" to "كانت بطة تجدف بسعادة فوق الأمواج. هل يمكنك إيجاد البطة؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "The sun sparkled on the water as our friend swam back to shore.",
                    "fa" to "خورشید روی آب می‌درخشید و دوست ما به سمت ساحل شنا کرد.",
                    "sv" to "Solen glittrade på vattnet när vår vän simmade tillbaka till stranden.",
                    "tr" to "Arkadaşımız kıyıya doğru yüzerken güneş suyun üzerinde parlıyordu.",
                    "de" to "Die Sonne glitzerte auf dem Wasser, während unser Freund zurück ans Ufer schwamm.",
                    "fr" to "Le soleil scintillait sur l'eau tandis que notre ami nageait vers le rivage.",
                    "es" to "El sol brillaba en el agua mientras nuestro amigo nadaba de regreso a la orilla.",
                    "ru" to "Солнце сверкало на воде, пока наш друг плыл обратно к берегу.",
                    "zh" to "阳光在水面上闪烁，我们的朋友游回了岸边。",
                    "hi" to "सूरज पानी पर चमक रहा था जब हमारा दोस्त किनारे की ओर तैर रहा था।",
                    "ar" to "تلألأت الشمس على الماء بينما سبح صديقنا عائداً إلى الشاطئ."
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "What an amazing adventure under the sea! Great job!",
                    "fa" to "چه ماجراجویی فوق‌العاده‌ای زیر دریا بود! آفرین بهت!",
                    "sv" to "Vilket fantastiskt äventyr under havet! Bra jobbat!",
                    "tr" to "Deniz altında ne muhteşem bir maceraydı! Aferin sana!",
                    "de" to "Was für ein tolles Abenteuer unter dem Meer! Gut gemacht!",
                    "fr" to "Quelle aventure incroyable sous la mer ! Bravo !",
                    "es" to "¡Qué aventura tan increíble bajo el mar! ¡Buen trabajo!",
                    "ru" to "Какое удивительное приключение под водой! Молодец!",
                    "zh" to "多么精彩的海底冒险啊！做得好！",
                    "hi" to "समुद्र के नीचे क्या अद्भुत रोमांच था! शाबाश!",
                    "ar" to "يا لها من مغامرة رائعة تحت البحر! أحسنت!"
                )
            ),
        )
    )

    val shapesStory = Story(
        id = "shapes",
        titles = mapOf(
            "en" to "Shapes Everywhere", "fa" to "شکل‌ها همه‌جا", "sv" to "Former överallt", "tr" to "Her Yerde Şekiller",
            "de" to "Formen überall", "fr" to "Des formes partout", "es" to "Formas por todas partes", "ru" to "Формы повсюду",
            "zh" to "到处都是形状", "hi" to "हर जगह आकृतियाँ", "ar" to "أشكال في كل مكان"
        ),
        emoji = "🔷",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "Our friend put on their explorer hat to go looking for shapes!",
                    "fa" to "دوست ما کلاه کاوشگریش رو گذاشت تا دنبال شکل‌ها بگرده!",
                    "sv" to "Vår vän tog på sig upptäckarhatten för att leta efter former!",
                    "tr" to "Arkadaşımız şekilleri aramak için kaşif şapkasını taktı!",
                    "de" to "Unser Freund setzte den Entdeckerhut auf, um nach Formen zu suchen!",
                    "fr" to "Notre ami a mis son chapeau d'explorateur pour chercher des formes !",
                    "es" to "¡Nuestro amigo se puso el sombrero de explorador para buscar formas!",
                    "ru" to "Наш друг надел шляпу исследователя, чтобы искать формы!",
                    "zh" to "我们的朋友戴上探险帽去寻找形状啦！",
                    "hi" to "हमारे दोस्त ने आकृतियाँ खोजने के लिए अपनी खोजी टोपी पहनी!",
                    "ar" to "ارتدى صديقنا قبعة المستكشف للبحث عن الأشكال!"
                )
            ),
            p(
                targetWordId = "circle", distractorWordIds = listOf("square", "triangle"),
                texts = *arrayOf(
                    "en" to "First, a round shape rolled by. Can you find the circle?",
                    "fa" to "اول یه شکل گرد از کنارش رد شد. می‌تونی دایره رو پیدا کنی؟",
                    "sv" to "Först rullade en rund form förbi. Kan du hitta cirkeln?",
                    "tr" to "İlk önce yuvarlak bir şekil yuvarlandı. Daireyi bulabilir misin?",
                    "de" to "Zuerst rollte eine runde Form vorbei. Kannst du den Kreis finden?",
                    "fr" to "D'abord, une forme ronde a roulé. Peux-tu trouver le cercle ?",
                    "es" to "Primero, una forma redonda pasó rodando. ¿Puedes encontrar el círculo?",
                    "ru" to "Сначала мимо прокатилась круглая фигура. Сможешь найти круг?",
                    "zh" to "首先，一个圆形滚了过来。你能找到圆形吗？",
                    "hi" to "पहले, एक गोल आकृति लुढ़कती हुई आई। क्या तुम वृत्त ढूंढ सकते हो?",
                    "ar" to "أولاً، تدحرج شكل دائري. هل يمكنك إيجاد الدائرة؟"
                )
            ),
            p(
                targetWordId = "square", distractorWordIds = listOf("circle", "star"),
                texts = *arrayOf(
                    "en" to "Then a shape with four equal sides appeared. Can you find the square?",
                    "fa" to "بعد یه شکل با چهار ضلع مساوی ظاهر شد. می‌تونی مربع رو پیدا کنی؟",
                    "sv" to "Sedan dök en form med fyra lika sidor upp. Kan du hitta kvadraten?",
                    "tr" to "Sonra dört eşit kenarlı bir şekil belirdi. Kareyi bulabilir misin?",
                    "de" to "Dann erschien eine Form mit vier gleichen Seiten. Kannst du das Quadrat finden?",
                    "fr" to "Puis une forme à quatre côtés égaux est apparue. Peux-tu trouver le carré ?",
                    "es" to "Luego apareció una forma con cuatro lados iguales. ¿Puedes encontrar el cuadrado?",
                    "ru" to "Затем появилась фигура с четырьмя равными сторонами. Сможешь найти квадрат?",
                    "zh" to "然后出现了一个四边相等的形状。你能找到正方形吗？",
                    "hi" to "फिर चार बराबर भुजाओं वाली एक आकृति दिखाई दी। क्या तुम वर्ग ढूंढ सकते हो?",
                    "ar" to "ثم ظهر شكل بأربعة أضلاع متساوية. هل يمكنك إيجاد المربع؟"
                )
            ),
            p(
                targetWordId = "triangle", distractorWordIds = listOf("square", "circle"),
                texts = *arrayOf(
                    "en" to "Next, a shape with three pointy corners showed up. Can you find the triangle?",
                    "fa" to "بعدش یه شکل با سه گوشه‌ی نوک‌تیز پیدا شد. می‌تونی مثلث رو پیدا کنی؟",
                    "sv" to "Sedan dök en form med tre spetsiga hörn upp. Kan du hitta triangeln?",
                    "tr" to "Sonra üç sivri köşeli bir şekil ortaya çıktı. Üçgeni bulabilir misin?",
                    "de" to "Als Nächstes tauchte eine Form mit drei spitzen Ecken auf. Kannst du das Dreieck finden?",
                    "fr" to "Ensuite, une forme à trois coins pointus est apparue. Peux-tu trouver le triangle ?",
                    "es" to "Luego, apareció una forma con tres esquinas puntiagudas. ¿Puedes encontrar el triángulo?",
                    "ru" to "Затем появилась фигура с тремя острыми углами. Сможешь найти треугольник?",
                    "zh" to "接着出现了一个有三个尖角的形状。你能找到三角形吗？",
                    "hi" to "फिर तीन नुकीले कोनों वाली एक आकृति दिखी। क्या तुम त्रिभुज ढूंढ सकते हो?",
                    "ar" to "ثم ظهر شكل بثلاث زوايا مدببة. هل يمكنك إيجاد المثلث؟"
                )
            ),
            p(
                targetWordId = "star", distractorWordIds = listOf("triangle", "square"),
                texts = *arrayOf(
                    "en" to "Last, a sparkly shape twinkled in the sky. Can you find the star?",
                    "fa" to "در آخر، یه شکل درخشان توی آسمون چشمک زد. می‌تونی ستاره رو پیدا کنی؟",
                    "sv" to "Sist blinkade en gnistrande form på himlen. Kan du hitta stjärnan?",
                    "tr" to "Son olarak, parıldayan bir şekil gökyüzünde parladı. Yıldızı bulabilir misin?",
                    "de" to "Zuletzt funkelte eine glitzernde Form am Himmel. Kannst du den Stern finden?",
                    "fr" to "Enfin, une forme scintillante a brillé dans le ciel. Peux-tu trouver l'étoile ?",
                    "es" to "Por último, una forma brillante centelleó en el cielo. ¿Puedes encontrar la estrella?",
                    "ru" to "Наконец, в небе засверкала блестящая фигура. Сможешь найти звезду?",
                    "zh" to "最后，天空中闪烁着一个闪亮的形状。你能找到星星吗？",
                    "hi" to "आखिर में, आकाश में एक चमकदार आकृति टिमटिमाई। क्या तुम तारा ढूंढ सकते हो?",
                    "ar" to "أخيراً، تلألأ شكل بريق في السماء. هل يمكنك إيجاد النجمة؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "What a fun day finding shapes everywhere! Great job!",
                    "fa" to "چه روز باحالی بود، شکل‌ها رو همه‌جا پیدا کردیم! آفرین بهت!",
                    "sv" to "Vilken rolig dag att hitta former överallt! Bra jobbat!",
                    "tr" to "Her yerde şekiller bulmak ne eğlenceli bir gündü! Aferin sana!",
                    "de" to "Was für ein lustiger Tag, überall Formen zu finden! Gut gemacht!",
                    "fr" to "Quelle journée amusante à trouver des formes partout ! Bravo !",
                    "es" to "¡Qué día tan divertido encontrando formas por todas partes! ¡Buen trabajo!",
                    "ru" to "Какой весёлый день, находить формы повсюду! Молодец!",
                    "zh" to "到处寻找形状的一天真有趣！做得好！",
                    "hi" to "हर जगह आकृतियाँ ढूंढने का कितना मज़ेदार दिन था! शाबाश!",
                    "ar" to "يا له من يوم ممتع في إيجاد الأشكال في كل مكان! أحسنت!"
                )
            ),
        )
    )

    val goodnightStarsStory = Story(
        id = "goodnight_stars",
        titles = mapOf(
            "en" to "Goodnight, Stars", "fa" to "شب‌بخیر ستاره‌ها", "sv" to "God natt, stjärnor", "tr" to "İyi Geceler Yıldızlar",
            "de" to "Gute Nacht, Sterne", "fr" to "Bonne nuit, les étoiles", "es" to "Buenas noches, estrellas", "ru" to "Спокойной ночи, звёзды",
            "zh" to "晚安，星星", "hi" to "शुभरात्रि, सितारों", "ar" to "تصبحون على خير أيتها النجوم"
        ),
        emoji = "🌙",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "As the sky turned dark, it was time to say goodnight to the family.",
                    "fa" to "وقتی آسمون تاریک شد، وقتش بود که به خانواده شب‌بخیر بگیم.",
                    "sv" to "När himlen blev mörk var det dags att säga god natt till familjen.",
                    "tr" to "Gökyüzü karardığında aileye iyi geceler deme vakti gelmişti.",
                    "de" to "Als der Himmel dunkel wurde, war es Zeit, der Familie gute Nacht zu sagen.",
                    "fr" to "Alors que le ciel s'assombrissait, il était temps de dire bonne nuit à la famille.",
                    "es" to "Cuando el cielo oscureció, era hora de decirle buenas noches a la familia.",
                    "ru" to "Когда небо потемнело, пришло время пожелать семье спокойной ночи.",
                    "zh" to "天色渐暗，是时候向家人道晚安了。",
                    "hi" to "जब आकाश अंधेरा हो गया, तो परिवार को शुभरात्रि कहने का समय हो गया।",
                    "ar" to "عندما أظلمت السماء، حان وقت قول تصبحون على خير للعائلة."
                )
            ),
            p(
                targetWordId = "mom", distractorWordIds = listOf("dad", "grandma"),
                texts = *arrayOf(
                    "en" to "Mom tucked everyone in with a warm blanket. Can you find mom?",
                    "fa" to "مامان همه رو با یه پتوی گرم خوابوند. می‌تونی مامان رو پیدا کنی؟",
                    "sv" to "Mamma bäddade in alla med en varm filt. Kan du hitta mamma?",
                    "tr" to "Anne herkesi sıcak bir battaniyeyle yatırdı. Anneyi bulabilir misin?",
                    "de" to "Mama deckte alle mit einer warmen Decke zu. Kannst du Mama finden?",
                    "fr" to "Maman a bordé tout le monde avec une couverture chaude. Peux-tu trouver maman ?",
                    "es" to "Mamá arropó a todos con una manta calentita. ¿Puedes encontrar a mamá?",
                    "ru" to "Мама укутала всех тёплым одеялом. Сможешь найти маму?",
                    "zh" to "妈妈用温暖的毯子给大家盖好被子。你能找到妈妈吗？",
                    "hi" to "माँ ने सबको गर्म कंबल से ढक दिया। क्या तुम माँ को ढूंढ सकते हो?",
                    "ar" to "غطت ماما الجميع ببطانية دافئة. هل يمكنك إيجاد ماما؟"
                )
            ),
            p(
                targetWordId = "dad", distractorWordIds = listOf("grandpa", "mom"),
                texts = *arrayOf(
                    "en" to "Dad read a bedtime story in a soft, sleepy voice. Can you find dad?",
                    "fa" to "بابا با صدای آروم و خواب‌آلود یه قصه‌ی شب خوند. می‌تونی بابا رو پیدا کنی؟",
                    "sv" to "Pappa läste en godnattsaga med en mjuk, sömnig röst. Kan du hitta pappa?",
                    "tr" to "Baba yumuşak, uykulu bir sesle bir uyku hikayesi okudu. Babayı bulabilir misin?",
                    "de" to "Papa las mit sanfter, schläfriger Stimme eine Gutenachtgeschichte. Kannst du Papa finden?",
                    "fr" to "Papa a lu une histoire du soir d'une voix douce et endormie. Peux-tu trouver papa ?",
                    "es" to "Papá leyó un cuento antes de dormir con una voz suave y somnolienta. ¿Puedes encontrar a papá?",
                    "ru" to "Папа читал сказку на ночь тихим, сонным голосом. Сможешь найти папу?",
                    "zh" to "爸爸用轻柔、困倦的声音读了一个睡前故事。你能找到爸爸吗？",
                    "hi" to "पापा ने धीमी, नींद भरी आवाज़ में एक कहानी सुनाई। क्या तुम पापा को ढूंढ सकते हो?",
                    "ar" to "قرأ بابا قصة ما قبل النوم بصوت هادئ ونعسان. هل يمكنك إيجاد بابا؟"
                )
            ),
            p(
                targetWordId = "grandma", distractorWordIds = listOf("grandpa", "dad"),
                texts = *arrayOf(
                    "en" to "Grandma hummed a soft lullaby by the bed. Can you find grandma?",
                    "fa" to "مادربزرگ کنار تخت یه لالایی آروم زمزمه کرد. می‌تونی مادربزرگ رو پیدا کنی؟",
                    "sv" to "Mormor nynnade en mjuk vaggvisa vid sängen. Kan du hitta mormor?",
                    "tr" to "Anneanne yatağın yanında yumuşak bir ninni mırıldandı. Anneanneyi bulabilir misin?",
                    "de" to "Oma summte am Bett ein sanftes Schlaflied. Kannst du Oma finden?",
                    "fr" to "Grand-mère a fredonné une douce berceuse près du lit. Peux-tu trouver grand-mère ?",
                    "es" to "La abuela tarareó una suave canción de cuna junto a la cama. ¿Puedes encontrar a la abuela?",
                    "ru" to "Бабушка тихонько напевала колыбельную у кровати. Сможешь найти бабушку?",
                    "zh" to "奶奶在床边轻轻哼唱摇篮曲。你能找到奶奶吗？",
                    "hi" to "दादी ने बिस्तर के पास धीरे से लोरी गुनगुनाई। क्या तुम दादी को ढूंढ सकते हो?",
                    "ar" to "دندنت الجدة تهويدة هادئة بجانب السرير. هل يمكنك إيجاد الجدة؟"
                )
            ),
            p(
                targetWordId = "grandpa", distractorWordIds = listOf("mom", "grandma"),
                texts = *arrayOf(
                    "en" to "Grandpa turned off the lights and whispered sweet dreams. Can you find grandpa?",
                    "fa" to "پدربزرگ چراغ‌ها رو خاموش کرد و آروم گفت خواب‌های خوش. می‌تونی پدربزرگ رو پیدا کنی؟",
                    "sv" to "Morfar släckte lamporna och viskade sov gott. Kan du hitta morfar?",
                    "tr" to "Dede ışıkları kapattı ve tatlı rüyalar diye fısıldadı. Dedeyi bulabilir misin?",
                    "de" to "Opa schaltete das Licht aus und flüsterte süße Träume. Kannst du Opa finden?",
                    "fr" to "Grand-père a éteint les lumières et a chuchoté fais de beaux rêves. Peux-tu trouver grand-père ?",
                    "es" to "El abuelo apagó las luces y susurró dulces sueños. ¿Puedes encontrar al abuelo?",
                    "ru" to "Дедушка выключил свет и прошептал сладких снов. Сможешь найти дедушку?",
                    "zh" to "爷爷关了灯，轻声说了声好梦。你能找到爷爷吗？",
                    "hi" to "दादा ने बत्तियाँ बंद कीं और धीरे से मीठे सपनों की कामना की। क्या तुम दादा को ढूंढ सकते हो?",
                    "ar" to "أطفأ الجد الأنوار وهمس بأحلام سعيدة. هل يمكنك إيجاد الجد؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "The whole family said goodnight under the twinkling stars! Sweet dreams!",
                    "fa" to "کل خانواده زیر ستاره‌های چشمک‌زن شب‌بخیر گفتن! خواب‌های شیرین!",
                    "sv" to "Hela familjen sa god natt under de blinkande stjärnorna! Sov gott!",
                    "tr" to "Bütün aile parıldayan yıldızların altında iyi geceler dedi! Tatlı rüyalar!",
                    "de" to "Die ganze Familie sagte unter den funkelnden Sternen gute Nacht! Süße Träume!",
                    "fr" to "Toute la famille a dit bonne nuit sous les étoiles scintillantes ! Fais de beaux rêves !",
                    "es" to "¡Toda la familia dijo buenas noches bajo las estrellas titilantes! ¡Dulces sueños!",
                    "ru" to "Вся семья пожелала спокойной ночи под мерцающими звёздами! Сладких снов!",
                    "zh" to "全家人在闪烁的星星下互道晚安！做个好梦！",
                    "hi" to "पूरे परिवार ने टिमटिमाते सितारों के नीचे शुभरात्रि कही! मीठे सपने!",
                    "ar" to "قالت العائلة كلها تصبحون على خير تحت النجوم المتلألئة! أحلاماً سعيدة!"
                )
            ),
        )
    )

    val littleAnimalsHomeStory = Story(
        id = "little_animals_home",
        titles = mapOf(
            "en" to "Little Animals' Home", "fa" to "خونه‌ی حیوانات کوچولو", "sv" to "De små djurens hem", "tr" to "Küçük Hayvanların Evi",
            "de" to "Zuhause der kleinen Tiere", "fr" to "La maison des petits animaux", "es" to "El hogar de los animalitos", "ru" to "Дом маленьких зверят",
            "zh" to "小动物之家", "hi" to "छोटे जानवरों का घर", "ar" to "منزل الحيوانات الصغيرة"
        ),
        emoji = "🏡",
        pages = listOf(
            p(
                texts = *arrayOf(
                    "en" to "Our friend visited a cozy home where lots of little animals lived together!",
                    "fa" to "دوست ما به یه خونه‌ی دنج سر زد که خیلی از حیوانات کوچولو باهم زندگی می‌کردن!",
                    "sv" to "Vår vän besökte ett mysigt hem där massor av små djur bodde tillsammans!",
                    "tr" to "Arkadaşımız birçok küçük hayvanın birlikte yaşadığı sıcak bir eve gitti!",
                    "de" to "Unser Freund besuchte ein gemütliches Zuhause, in dem viele kleine Tiere zusammenlebten!",
                    "fr" to "Notre ami a visité une maison douillette où vivaient plein de petits animaux !",
                    "es" to "¡Nuestro amigo visitó un hogar acogedor donde vivían juntos muchos animalitos!",
                    "ru" to "Наш друг посетил уютный дом, где вместе жило много маленьких зверят!",
                    "zh" to "我们的朋友拜访了一个温馨的家，里面住着好多小动物！",
                    "hi" to "हमारा दोस्त एक आरामदायक घर गया जहाँ बहुत से छोटे जानवर साथ रहते थे!",
                    "ar" to "زار صديقنا منزلاً دافئاً يعيش فيه الكثير من الحيوانات الصغيرة معاً!"
                )
            ),
            p(
                targetWordId = "dog", distractorWordIds = listOf("cat", "rabbit"),
                texts = *arrayOf(
                    "en" to "A friendly puppy wagged its tail at the door. Can you find the dog?",
                    "fa" to "یه توله‌سگ مهربون دم درب دمش رو تکون داد. می‌تونی سگ رو پیدا کنی؟",
                    "sv" to "En vänlig valp viftade på svansen vid dörren. Kan du hitta hunden?",
                    "tr" to "Kapıda dost canlısı bir yavru köpek kuyruğunu salladı. Köpeği bulabilir misin?",
                    "de" to "Ein freundlicher Welpe wedelte an der Tür mit dem Schwanz. Kannst du den Hund finden?",
                    "fr" to "Un chiot amical remuait la queue à la porte. Peux-tu trouver le chien ?",
                    "es" to "Un cachorro amistoso movía la cola en la puerta. ¿Puedes encontrar al perro?",
                    "ru" to "Дружелюбный щенок вилял хвостом у двери. Сможешь найти собаку?",
                    "zh" to "一只友好的小狗在门口摇着尾巴。你能找到狗吗？",
                    "hi" to "एक दोस्ताना पिल्ला दरवाज़े पर पूंछ हिला रहा था। क्या तुम कुत्ते को ढूंढ सकते हो?",
                    "ar" to "لوّح جرو ودود بذيله عند الباب. هل يمكنك إيجاد الكلب؟"
                )
            ),
            p(
                targetWordId = "cat", distractorWordIds = listOf("dog", "rabbit"),
                texts = *arrayOf(
                    "en" to "A sleepy cat curled up on a soft pillow. Can you find the cat?",
                    "fa" to "یه گربه‌ی خواب‌آلود روی یه بالش نرم لوله شده بود. می‌تونی گربه رو پیدا کنی؟",
                    "sv" to "En sömnig katt rullade ihop sig på en mjuk kudde. Kan du hitta katten?",
                    "tr" to "Uykulu bir kedi yumuşak bir yastıkta kıvrılmıştı. Kediyi bulabilir misin?",
                    "de" to "Eine schläfrige Katze rollte sich auf einem weichen Kissen zusammen. Kannst du die Katze finden?",
                    "fr" to "Un chat endormi s'est roulé en boule sur un coussin doux. Peux-tu trouver le chat ?",
                    "es" to "Un gato somnoliento se acurrucó en una almohada suave. ¿Puedes encontrar al gato?",
                    "ru" to "Сонная кошка свернулась клубочком на мягкой подушке. Сможешь найти кошку?",
                    "zh" to "一只困倦的猫咪蜷缩在柔软的枕头上。你能找到猫吗？",
                    "hi" to "एक नींद भरी बिल्ली एक नरम तकिए पर सिमट गई। क्या तुम बिल्ली को ढूंढ सकते हो?",
                    "ar" to "تكورت قطة نعسانة على وسادة ناعمة. هل يمكنك إيجاد القطة؟"
                )
            ),
            p(
                targetWordId = "rabbit", distractorWordIds = listOf("dog", "cat"),
                texts = *arrayOf(
                    "en" to "A fluffy rabbit hopped around looking for carrots. Can you find the rabbit?",
                    "fa" to "یه خرگوش پشمالو دنبال هویج این‌ور اون‌ور می‌پرید. می‌تونی خرگوش رو پیدا کنی؟",
                    "sv" to "En luden kanin hoppade omkring och letade efter morötter. Kan du hitta kaninen?",
                    "tr" to "Tüylü bir tavşan havuç ararken zıplayıp duruyordu. Tavşanı bulabilir misin?",
                    "de" to "Ein flauschiges Kaninchen hüpfte auf der Suche nach Karotten herum. Kannst du das Kaninchen finden?",
                    "fr" to "Un lapin tout doux sautillait à la recherche de carottes. Peux-tu trouver le lapin ?",
                    "es" to "Un conejo peludo saltaba buscando zanahorias. ¿Puedes encontrar al conejo?",
                    "ru" to "Пушистый кролик прыгал в поисках морковки. Сможешь найти кролика?",
                    "zh" to "一只毛茸茸的兔子跳来跳去找胡萝卜。你能找到兔子吗？",
                    "hi" to "एक रोएंदार खरगोश गाजर ढूंढते हुए उछल-कूद कर रहा था। क्या तुम खरगोश को ढूंढ सकते हो?",
                    "ar" to "قفز أرنب كثيف الفراء بحثاً عن الجزر. هل يمكنك إيجاد الأرنب؟"
                )
            ),
            p(
                targetWordId = "chicken", distractorWordIds = listOf("dog", "cat"),
                texts = *arrayOf(
                    "en" to "A little chicken pecked happily at some seeds. Can you find the chicken?",
                    "fa" to "یه جوجه‌مرغ کوچولو با خوشحالی دونه‌ها رو نوک می‌زد. می‌تونی مرغ رو پیدا کنی؟",
                    "sv" to "En liten höna pickade glatt på några frön. Kan du hitta hönan?",
                    "tr" to "Küçük bir tavuk mutlulukla tohumları gagalıyordu. Tavuğu bulabilir misin?",
                    "de" to "Ein kleines Huhn pickte fröhlich nach ein paar Samen. Kannst du das Huhn finden?",
                    "fr" to "Une petite poule picorait joyeusement des graines. Peux-tu trouver la poule ?",
                    "es" to "Una pequeña gallina picoteaba felizmente algunas semillas. ¿Puedes encontrar la gallina?",
                    "ru" to "Маленькая курочка радостно клевала зёрнышки. Сможешь найти курицу?",
                    "zh" to "一只小鸡开心地啄着种子。你能找到鸡吗？",
                    "hi" to "एक छोटी मुर्गी खुशी से बीज चुग रही थी। क्या तुम मुर्गी को ढूंढ सकते हो?",
                    "ar" to "نقرت دجاجة صغيرة الحبوب بسعادة. هل يمكنك إيجاد الدجاجة؟"
                )
            ),
            p(
                texts = *arrayOf(
                    "en" to "All the little animals were happy together in their cozy home! Great job!",
                    "fa" to "همه‌ی حیوانات کوچولو باهم تو خونه‌ی دنجشون خوشحال بودن! آفرین بهت!",
                    "sv" to "Alla de små djuren var glada tillsammans i sitt mysiga hem! Bra jobbat!",
                    "tr" to "Tüm küçük hayvanlar sıcak evlerinde birlikte mutluydu! Aferin sana!",
                    "de" to "Alle kleinen Tiere waren zusammen glücklich in ihrem gemütlichen Zuhause! Gut gemacht!",
                    "fr" to "Tous les petits animaux étaient heureux ensemble dans leur maison douillette ! Bravo !",
                    "es" to "¡Todos los animalitos estaban felices juntos en su hogar acogedor! ¡Buen trabajo!",
                    "ru" to "Все маленькие зверята были счастливы вместе в своём уютном доме! Молодец!",
                    "zh" to "所有的小动物都在温馨的家里快乐地在一起！做得好！",
                    "hi" to "सभी छोटे जानवर अपने आरामदायक घर में साथ खुश थे! शाबाश!",
                    "ar" to "كانت كل الحيوانات الصغيرة سعيدة معاً في منزلها الدافئ! أحسنت!"
                )
            ),
        )
    )

    val all = listOf(farmStory, rainbowStory, familyStory, seaStory, shapesStory, goodnightStarsStory, littleAnimalsHomeStory)

    fun storyById(id: String): Story? = all.find { it.id == id }
}
