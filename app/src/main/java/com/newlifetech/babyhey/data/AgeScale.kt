package com.newlifetech.babyhey.data

/**
 * تنظیمات سختی بازی‌ها بر اساس سن بچه — یه‌جا و مرکزی که همه‌ی بازی‌ها ازش استفاده کنن.
 */
object AgeScale {
    fun roundsForAge(age: Int): Int = when {
        age <= 2 -> 4
        age == 3 -> 5
        age == 4 -> 6
        age == 5 -> 7
        else -> 8
    }

    fun optionsForAge(age: Int): Int = when {
        age <= 2 -> 2
        age == 3 -> 3
        age == 4 -> 4
        age == 5 -> 4
        else -> 5
    }

    /** تعداد کل خونه‌های گرید توی بازی لمس سریع */
    fun speedGridSizeForAge(age: Int): Int = when {
        age <= 3 -> 6
        age <= 5 -> 9
        else -> 12
    }

    /** چندتا از خونه‌ها باید هدف باشن (بقیه مزاحمن) */
    fun speedTargetsForAge(age: Int): Int = when {
        age <= 3 -> 2
        age <= 5 -> 3
        else -> 4
    }

    fun speedTimeLimitSeconds(age: Int): Int = when {
        age <= 2 -> 40
        age == 3 -> 32
        age == 4 -> 26
        age == 5 -> 22
        else -> 18
    }

    /** تعداد تیکه‌های پازل: ۴ (۲×۲) برای کوچیک‌ترها، ۹ (۳×۳) برای بزرگ‌ترها */
    fun puzzlePiecesForAge(age: Int): Int = when {
        age <= 4 -> 4
        else -> 9
    }
}
