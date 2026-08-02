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

    val all = listOf(farmStory, rainbowStory, familyStory)

    fun storyById(id: String): Story? = all.find { it.id == id }
}
