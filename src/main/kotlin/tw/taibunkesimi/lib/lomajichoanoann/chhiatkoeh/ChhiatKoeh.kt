package tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh

import tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.koeh.DelimiterKoeh
import tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.koeh.LomajiKoeh

enum class HybridType {
    HYBRID_TYPE_POJ_INPUT,
    HYBRID_TYPE_KIP_INPUT,
    HYBRID_TYPE_POJ_UNICODE,
    HYBRID_TYPE_KIP_UNICODE
}

object ChhiatKoeh {
    private val IMCHAT_DELIMITERS_REGEX = "( |-|·|\\.|!|\\?|,|\\/|\\\\|:|;|'|\"|”|`|~|#|\\*|\\(|\\)|_|\\+|=|\\[|\\]|\\n|…)".toRegex()

    fun chhiatHybridInputWithRegex(kuikuString: String, hybridType: tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.HybridType): ArrayList<LomajiKoeh> {
        val koehArrayList = ArrayList<LomajiKoeh>()

        val matchSequence: Sequence<MatchResult>
        when(hybridType) {
            HybridType.HYBRID_TYPE_POJ_INPUT -> {
                matchSequence = LomajiSplitter.splitPojWithInput(kuikuString)
            }

            HybridType.HYBRID_TYPE_KIP_INPUT -> {
                matchSequence = LomajiSplitter.splitKipWithInput(kuikuString)
            }

            HybridType.HYBRID_TYPE_POJ_UNICODE -> {
                matchSequence = LomajiSplitter.splitPojWithUnicode(kuikuString)
            }

            HybridType.HYBRID_TYPE_KIP_UNICODE -> {
                matchSequence = LomajiSplitter.splitKipWithUnicode(kuikuString)
            }
        }

        val matchList = matchSequence.toList()
        val size = matchList.size

        var lastPos = 0
        for (i in 0 until size) {
            val matchResult: MatchResult = matchList[i]

            val nonLomajiString = kuikuString.substring(lastPos, matchResult.range.first)
            if (nonLomajiString.isNotEmpty()) {
                val currentKoeh = LomajiKoeh(nonLomajiString, false)
                koehArrayList.add(currentKoeh)

//                println("nonLomajiString: \"$nonLomajiString\"")
            }

            val lomajiString = kuikuString.substring(matchResult.range.first, matchResult.range.last + 1)
            if (lomajiString.isNotEmpty()) {
                val currentKoeh = LomajiKoeh(lomajiString, true)
                koehArrayList.add(currentKoeh)

//                println("lomajiString: \"$lomajiString\"")

                if (tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.DEBUG) {
                    if (currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(0, currentKoeh.string.length - 1).matches(".*\\d.*".toRegex())) {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuString")
                    }
                    if (currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(currentKoeh.string.length - 1) == "N") {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuString")
                    }
                }
            }

            lastPos = matchResult.range.last + 1

            if (i == size - 1) {
                val trailingNonLomajiString = kuikuString.substring(matchResult.range.last + 1)
                if (trailingNonLomajiString.isNotEmpty()) {
                    val currentKoeh = LomajiKoeh(trailingNonLomajiString, false)
                    koehArrayList.add(currentKoeh)

//                    println("trailingNonLomajiString: \"$trailingNonLomajiString\"")
                }
            }
        }

        return koehArrayList
    }

    fun chhiatOnlyLomajiWithDelimiter(kuikuInputOrUnicodeString: String): ArrayList<DelimiterKoeh> {
        val koehArrayList = ArrayList<DelimiterKoeh>()
        val codepoints = kuikuInputOrUnicodeString.codePoints().toArray()
        var isDelimiter = true
        val stringBuilder = StringBuilder()
        val codepointAmount = codepoints.size


        for (i in 0 until codepointAmount) {
            val currentCodepointString = String(codepoints, i, 1)
//            println("currentCodepointString=$currentCodepointString")

            val isCurrentCodepointDelimiter = currentCodepointString.matches(tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.ChhiatKoeh.IMCHAT_DELIMITERS_REGEX)
            if (i == 0) {
                isDelimiter = isCurrentCodepointDelimiter
            }

            if (isCurrentCodepointDelimiter == isDelimiter) {
                stringBuilder.append(currentCodepointString)
            } else {
                val currentKoeh = DelimiterKoeh(stringBuilder.toString(), isDelimiter)
                koehArrayList.add(currentKoeh)
//                println("new koeh=${currentKoeh.string}, isDelimiter=${currentKoeh.isDelimiter}")

                if (tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.DEBUG) {
                    if (!currentKoeh.isDelimiter
                            && currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(0, currentKoeh.string.length - 1).matches(".*\\d.*".toRegex())) {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuInputOrUnicodeString")
                    }
                    if (!currentKoeh.isDelimiter
                            && currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(currentKoeh.string.length - 1) == "N") {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuInputOrUnicodeString")
                    }
                }

                stringBuilder.clear()
                stringBuilder.append(currentCodepointString)
            }

            if (i == codepointAmount - 1) {
                val currentKoeh = DelimiterKoeh(stringBuilder.toString(), isDelimiter)
                koehArrayList.add(currentKoeh)

                if (tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.DEBUG) {
                    if (!currentKoeh.isDelimiter
                            && currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(0, currentKoeh.string.length - 1).matches(".*\\d.*".toRegex())) {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuInputOrUnicodeString")
                    }
                    if (!currentKoeh.isDelimiter
                            && currentKoeh.string.length >= 2
                            && currentKoeh.string.substring(currentKoeh.string.length - 1) == "N") {
                        println("Lô-má-jī keh-sek būn-tê=${currentKoeh.string}, kuiku=$kuikuInputOrUnicodeString")
                    }
                }
            }

            isDelimiter = isCurrentCodepointDelimiter
        }

        return koehArrayList
    }
}