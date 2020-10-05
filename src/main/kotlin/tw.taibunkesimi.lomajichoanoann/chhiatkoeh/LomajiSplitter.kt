package tw.taibunkesimi.lomajichoanoann.chhiatkoeh

import tw.taibunkesimi.lomajichoanoann.kiamcha.LomajiImchatRegex

object LomajiSplitter {
    fun splitPojWithInput(kuikuString: String): Sequence<MatchResult> {
        return Regex(LomajiImchatRegex.POJ_INPUT_REGEX, RegexOption.IGNORE_CASE).findAll(kuikuString)
    }

    fun splitKipWithInput(kuikuString: String): Sequence<MatchResult> {
        return Regex(LomajiImchatRegex.KIP_INPUT_REGEX, RegexOption.IGNORE_CASE).findAll(kuikuString)
    }

    fun splitPojWithUnicode(kuikuString: String): Sequence<MatchResult> {
        return Regex(LomajiImchatRegex.POJ_UNICODE_REGEX, RegexOption.IGNORE_CASE).findAll(kuikuString)
    }

    fun splitKipWithUnicode(kuikuString: String): Sequence<MatchResult> {
        return Regex(LomajiImchatRegex.KIP_UNICODE_REGEX, RegexOption.IGNORE_CASE).findAll(kuikuString)
    }
}