package com.example.babyai.data

/**
 * یک صفحه از داستان.
 * اگه targetWordId خالی باشه، یعنی صفحه‌ی روایی ساده‌ست (بدون تعامل لمسی).
 * اگه پر باشه، یعنی باید از بین targetWordId + distractorWordIds، بچه درست‌ش رو لمس کنه.
 */
data class StoryPage(
    val textEn: String,
    val textFa: String,
    val targetWordId: String? = null,
    val distractorWordIds: List<String> = emptyList()
)

data class Story(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val emoji: String,
    val pages: List<StoryPage>
)

object StoryRepository {

    val farmStory = Story(
        id = "farm",
        titleEn = "A Day at the Farm",
        titleFa = "یه روز توی مزرعه",
        emoji = "🐄",
        pages = listOf(
            StoryPage(
                textEn = "One sunny morning, our friend went to visit a farm full of animals!",
                textFa = "یه روز آفتابی، دوست ما رفت به یه مزرعه پر از حیوانات!"
            ),
            StoryPage(
                textEn = "First, a cow said hello! Can you find the cow?",
                textFa = "اول یه گاو بهش سلام کرد! می‌تونی گاو رو پیدا کنی؟",
                targetWordId = "cow",
                distractorWordIds = listOf("chicken", "duck")
            ),
            StoryPage(
                textEn = "Then a chicken came running by! Can you find the chicken?",
                textFa = "بعد یه مرغ دوان‌دوان اومد! می‌تونی مرغ رو پیدا کنی؟",
                targetWordId = "chicken",
                distractorWordIds = listOf("cow", "sheep")
            ),
            StoryPage(
                textEn = "A duck was swimming in the pond! Can you find the duck?",
                textFa = "یه اردک توی برکه شنا می‌کرد! می‌تونی اردک رو پیدا کنی؟",
                targetWordId = "duck",
                distractorWordIds = listOf("sheep", "cow")
            ),
            StoryPage(
                textEn = "Last but not least, a fluffy sheep waved hi! Can you find the sheep?",
                textFa = "در آخر، یه گوسفند پشمالو دست تکون داد! می‌تونی گوسفند رو پیدا کنی؟",
                targetWordId = "sheep",
                distractorWordIds = listOf("duck", "chicken")
            ),
            StoryPage(
                textEn = "What a wonderful day at the farm! Great job!",
                textFa = "چه روز فوق‌العاده‌ای توی مزرعه بود! آفرین بهت!"
            ),
        )
    )

    val rainbowStory = Story(
        id = "rainbow",
        titleEn = "The Magic Rainbow",
        titleFa = "رنگین‌کمون جادویی",
        emoji = "🌈",
        pages = listOf(
            StoryPage(
                textEn = "After the rain, a magic rainbow appeared in the sky!",
                textFa = "بعد از بارون، یه رنگین‌کمون جادویی توی آسمون ظاهر شد!"
            ),
            StoryPage(
                textEn = "The first color was bright and warm. Can you find red?",
                textFa = "اولین رنگ گرم و روشن بود. می‌تونی قرمز رو پیدا کنی؟",
                targetWordId = "red",
                distractorWordIds = listOf("blue", "yellow")
            ),
            StoryPage(
                textEn = "Next came a color like the sky. Can you find blue?",
                textFa = "بعدش رنگی مثل آسمون اومد. می‌تونی آبی رو پیدا کنی؟",
                targetWordId = "blue",
                distractorWordIds = listOf("green", "red")
            ),
            StoryPage(
                textEn = "Then a bright color like the sun. Can you find yellow?",
                textFa = "بعد یه رنگ روشن مثل خورشید. می‌تونی زرد رو پیدا کنی؟",
                targetWordId = "yellow",
                distractorWordIds = listOf("green", "blue")
            ),
            StoryPage(
                textEn = "Last, a color like fresh grass. Can you find green?",
                textFa = "در آخر، رنگی مثل چمن تازه. می‌تونی سبز رو پیدا کنی؟",
                targetWordId = "green",
                distractorWordIds = listOf("yellow", "red")
            ),
            StoryPage(
                textEn = "The rainbow was full of beautiful colors! Great job!",
                textFa = "رنگین‌کمون پر از رنگ‌های زیبا بود! آفرین بهت!"
            ),
        )
    )

    val familyStory = Story(
        id = "family",
        titleEn = "Family Gathering",
        titleFa = "مهمونی خانوادگی",
        emoji = "👨‍👩‍👧",
        pages = listOf(
            StoryPage(
                textEn = "Today the whole family came together for a big gathering!",
                textFa = "امروز کل خانواده برای یه مهمونی بزرگ دور هم جمع شدن!"
            ),
            StoryPage(
                textEn = "The one who baked cookies was so happy to see everyone. Can you find mom?",
                textFa = "کسی که کلوچه پخته بود از دیدن همه خیلی خوشحال بود. می‌تونی مامان رو پیدا کنی؟",
                targetWordId = "mom",
                distractorWordIds = listOf("dad", "grandma")
            ),
            StoryPage(
                textEn = "Someone played games with everyone in the yard. Can you find dad?",
                textFa = "یه نفر توی حیاط با همه بازی می‌کرد. می‌تونی بابا رو پیدا کنی؟",
                targetWordId = "dad",
                distractorWordIds = listOf("grandpa", "mom")
            ),
            StoryPage(
                textEn = "Someone told the best old stories. Can you find grandma?",
                textFa = "یه نفر بهترین قصه‌های قدیمی رو تعریف کرد. می‌تونی مادربزرگ رو پیدا کنی؟",
                targetWordId = "grandma",
                distractorWordIds = listOf("grandpa", "dad")
            ),
            StoryPage(
                textEn = "And someone gave everyone the warmest hugs. Can you find grandpa?",
                textFa = "و یه نفر به همه گرم‌ترین بغل‌ها رو داد. می‌تونی پدربزرگ رو پیدا کنی؟",
                targetWordId = "grandpa",
                distractorWordIds = listOf("mom", "grandma")
            ),
            StoryPage(
                textEn = "It was the best family day ever! Great job!",
                textFa = "بهترین روز خانوادگی بود! آفرین بهت!"
            ),
        )
    )

    val shapeKingdomStory = Story(
        id = "shapes",
        titleEn = "Shape Kingdom",
        titleFa = "قلمرو شکل‌ها",
        emoji = "🔺",
        pages = listOf(
            StoryPage(
                textEn = "Our friend found a magic door to the Shape Kingdom!",
                textFa = "دوست ما یه در جادویی به قلمرو شکل‌ها پیدا کرد!"
            ),
            StoryPage(
                textEn = "The first guard was perfectly round. Can you find the circle?",
                textFa = "اولین نگهبان کاملاً گرد بود. می‌تونی دایره رو پیدا کنی؟",
                targetWordId = "circle",
                distractorWordIds = listOf("square", "triangle")
            ),
            StoryPage(
                textEn = "The second guard had four equal sides. Can you find the square?",
                textFa = "نگهبان دوم چهار ضلع مساوی داشت. می‌تونی مربع رو پیدا کنی؟",
                targetWordId = "square",
                distractorWordIds = listOf("circle", "diamond")
            ),
            StoryPage(
                textEn = "The third guard had three pointy corners. Can you find the triangle?",
                textFa = "نگهبان سوم سه گوشه‌ی نوک‌تیز داشت. می‌تونی مثلث رو پیدا کنی؟",
                targetWordId = "triangle",
                distractorWordIds = listOf("square", "diamond")
            ),
            StoryPage(
                textEn = "The last guard looked like a tilted square. Can you find the diamond?",
                textFa = "آخرین نگهبان مثل یه مربع کج بود. می‌تونی لوزی رو پیدا کنی؟",
                targetWordId = "diamond",
                distractorWordIds = listOf("circle", "triangle")
            ),
            StoryPage(
                textEn = "Our friend passed through the Shape Kingdom! Great job!",
                textFa = "دوست ما از قلمرو شکل‌ها رد شد! آفرین بهت!"
            ),
        )
    )

    val seaAdventureStory = Story(
        id = "sea",
        titleEn = "Sea Adventure",
        titleFa = "ماجراجویی توی دریا",
        emoji = "🌊",
        pages = listOf(
            StoryPage(
                textEn = "Our friend put on a snorkel and jumped into the deep blue sea!",
                textFa = "دوست ما یه اسنورکل زد و توی دریای آبی عمیق پرید!"
            ),
            StoryPage(
                textEn = "A colorful little swimmer passed by. Can you find the fish?",
                textFa = "یه شناگر کوچولوی رنگارنگ از کنارش رد شد. می‌تونی ماهی رو پیدا کنی؟",
                targetWordId = "fish",
                distractorWordIds = listOf("turtle", "duck")
            ),
            StoryPage(
                textEn = "A slow friend with a hard shell said hi. Can you find the turtle?",
                textFa = "یه دوست آروم با لاک سفت بهش سلام کرد. می‌تونی لاک‌پشت رو پیدا کنی؟",
                targetWordId = "turtle",
                distractorWordIds = listOf("fish", "elephant")
            ),
            StoryPage(
                textEn = "Someone was floating happily on the water. Can you find the duck?",
                textFa = "یکی داشت خوشحال روی آب شناور بود. می‌تونی اردک رو پیدا کنی؟",
                targetWordId = "duck",
                distractorWordIds = listOf("turtle", "fish")
            ),
            StoryPage(
                textEn = "A big friend sprayed water with its trunk to cool off. Can you find the elephant?",
                textFa = "یه دوست بزرگ با خرطومش آب پاشید تا خنک بشه. می‌تونی فیل رو پیدا کنی؟",
                targetWordId = "elephant",
                distractorWordIds = listOf("duck", "fish")
            ),
            StoryPage(
                textEn = "What a splashy, wonderful sea adventure! Great job!",
                textFa = "چه ماجراجویی آب‌بازی و فوق‌العاده‌ای بود! آفرین بهت!"
            ),
        )
    )

    val littleAnimalsHomeStory = Story(
        id = "little_animals_home",
        titleEn = "Little Animals' Home",
        titleFa = "خونه‌ی حیوانات کوچولو",
        emoji = "🏡",
        pages = listOf(
            StoryPage(
                textEn = "Our friend visited a cozy little farmhouse full of new animal friends!",
                textFa = "دوست ما به یه مزرعه‌ی کوچیک و دنج پر از دوستان حیوانی جدید سر زد!"
            ),
            StoryPage(
                textEn = "A strong friend with a long mane greeted them. Can you find the horse?",
                textFa = "یه دوست قوی با یال بلند بهشون خوش‌آمد گفت. می‌تونی اسب رو پیدا کنی؟",
                targetWordId = "horse",
                distractorWordIds = listOf("goat", "pig")
            ),
            StoryPage(
                textEn = "A friend with little horns climbed on the rocks. Can you find the goat?",
                textFa = "یه دوست با شاخ‌های کوچیک روی سنگ‌ها بالا رفت. می‌تونی بز رو پیدا کنی؟",
                targetWordId = "goat",
                distractorWordIds = listOf("rabbit", "horse")
            ),
            StoryPage(
                textEn = "A friend with long ears hopped by quickly. Can you find the rabbit?",
                textFa = "یه دوست با گوش‌های دراز به‌سرعت جهید و رد شد. می‌تونی خرگوش رو پیدا کنی؟",
                targetWordId = "rabbit",
                distractorWordIds = listOf("goat", "pig")
            ),
            StoryPage(
                textEn = "A pink friend rolled happily in the mud. Can you find the pig?",
                textFa = "یه دوست صورتی با خوشحالی توی گل غلت زد. می‌تونی خوک رو پیدا کنی؟",
                targetWordId = "pig",
                distractorWordIds = listOf("horse", "rabbit")
            ),
            StoryPage(
                textEn = "It was such a warm and friendly little home! Great job!",
                textFa = "چه خونه‌ی کوچیک و گرم و صمیمی‌ای بود! آفرین بهت!"
            ),
        )
    )

    val goodnightStarsStory = Story(
        id = "goodnight_stars",
        titleEn = "Goodnight Stars",
        titleFa = "شب‌بخیر ستاره‌ها",
        emoji = "⭐",
        pages = listOf(
            StoryPage(
                textEn = "As the sky turned dark, our friend looked up and saw twinkling shapes!",
                textFa = "وقتی آسمون تاریک شد، دوست ما نگاه کرد و شکل‌های چشمک‌زن رو دید!"
            ),
            StoryPage(
                textEn = "The brightest twinkling shape shined above. Can you find the star?",
                textFa = "درخشان‌ترین شکل چشمک‌زن بالای سرش می‌درخشید. می‌تونی ستاره رو پیدا کنی؟",
                targetWordId = "star",
                distractorWordIds = listOf("crescent", "oval")
            ),
            StoryPage(
                textEn = "Next to it was a curvy glowing moon shape. Can you find the crescent?",
                textFa = "کنارش یه شکل ماه‌مانند و منحنی می‌درخشید. می‌تونی هلال رو پیدا کنی؟",
                targetWordId = "crescent",
                distractorWordIds = listOf("star", "rectangle")
            ),
            StoryPage(
                textEn = "A cloud floated by, long and gently rounded. Can you find the oval?",
                textFa = "یه ابر شناور رد شد، دراز و آروم و گرد. می‌تونی بیضی رو پیدا کنی؟",
                targetWordId = "oval",
                distractorWordIds = listOf("star", "rectangle")
            ),
            StoryPage(
                textEn = "A tall window glowed with warm light. Can you find the rectangle?",
                textFa = "یه پنجره‌ی بلند با نور گرم می‌درخشید. می‌تونی مستطیل رو پیدا کنی؟",
                targetWordId = "rectangle",
                distractorWordIds = listOf("oval", "crescent")
            ),
            StoryPage(
                textEn = "Our friend fell asleep under the twinkling sky. Goodnight!",
                textFa = "دوست ما زیر آسمون چشمک‌زن به خواب رفت. شب‌بخیر!"
            ),
        )
    )

    val all = listOf(
        farmStory, rainbowStory, familyStory,
        shapeKingdomStory, seaAdventureStory, littleAnimalsHomeStory, goodnightStarsStory
    )

    fun storyById(id: String): Story? = all.find { it.id == id }
}
