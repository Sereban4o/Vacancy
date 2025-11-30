package ru.practicum.android.diploma.presentation.vacancydetails

// Константа для эвристики заголовков.
private const val HEADER_MAX_CHARS = 25

/**
 * Тип элемента описания вакансии.
 *
 * HEADER - подзаголовок (без буллета, отдельным стилем)
 * BULLET - пункт списка (строка с буллетом)
 * SPACER - пустая строка / отступ между блоками
 */
enum class DescriptionItemType {
    HEADER,
    BULLET,
    SPACER
}

/**
 * Один элемент структурированного описания вакансии.
 *
 * @param type тип элемента (заголовок / пункт / отступ)
 * @param text текст для заголовка или пункта (для SPACER может быть пустым)
 */
data class DescriptionItem(
    val type: DescriptionItemType,
    val text: String = ""
)

/**
 * Парсит сырой текст описания вакансии в список структурированных элементов.
 *
 * На вход: обычная строка с \n
 * На выход: список DescriptionItem (HEADER / BULLET / SPACER)
 *
 */
fun parseVacancyDescription(text: String): List<DescriptionItem> {
    val lines = text.split("\n")
    val result = mutableListOf<DescriptionItem>()

    lines.forEachIndexed { index, rawLine ->
        val line = rawLine.trim()

        // Пустая строка -> просто отступ
        if (line.isEmpty()) {
            result += DescriptionItem(DescriptionItemType.SPACER)
        } else {
            // Следующая "сырая" строка (для проверки \n\n и т.п.)
            val nextRawLine = lines.getOrNull(index + 1)

            // 1) заканчивается на ":"
            val endsWithColon = line.endsWith(":")

            // 2) короче HEADER_MAX_CHARS, без "-" / "•" в начале,
            //    и после неё в тексте идёт пустая строка
            val isShortWithEmptyAfter =
                line.length < HEADER_MAX_CHARS &&
                    index < lines.lastIndex &&
                    !line.startsWith("•") &&
                    !line.startsWith("-") &&
                    nextRawLine.isNullOrBlank()

            val isHeader = endsWithColon || isShortWithEmptyAfter

            if (isHeader) {
                // Подзаголовок: убираем ":" в конце, если есть
                val headerText = line
                    .removeSuffix(":")
                    .trimEnd()

                result += DescriptionItem(
                    type = DescriptionItemType.HEADER,
                    text = headerText
                )
            } else {
                // Пункт списка: убираем возможные "•" / "-" в начале
                val cleanedText = line
                    .removePrefix("•")
                    .removePrefix("-")
                    .trimStart()

                result += DescriptionItem(
                    type = DescriptionItemType.BULLET,
                    text = cleanedText
                )
            }
        }
    }

    return result
}
