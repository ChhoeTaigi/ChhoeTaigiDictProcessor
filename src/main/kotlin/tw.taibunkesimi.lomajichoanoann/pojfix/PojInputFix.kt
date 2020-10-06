package tw.taibunkesimi.lomajichoanoann.pojfix

import tw.taibunkesimi.lomajichoanoann.chhiatkoeh.ChhiatKoeh
import tw.taibunkesimi.lomajichoanoann.extensions.isNumeric
import java.lang.IllegalArgumentException
import java.util.*


object PojInputFix {

    fun fixKuikuOnlyPojWithDelimiter(str: String, inputFixTypes: EnumSet<PojInputFixType>): String {
        val koehArrayList = ChhiatKoeh.chhiatOnlyLomajiWithDelimiter(str)
        val stringBuilder = StringBuilder()

        for (koeh in koehArrayList) {
            if (!koeh.isDelimiter) {
                val imchatString = fixImchat(koeh.string, inputFixTypes)
                stringBuilder.append(imchatString)
            } else {
                stringBuilder.append(koeh.string)
            }
        }
        return stringBuilder.toString()
    }

    fun fixImchat(str: String, inputFixTypes: EnumSet<PojInputFixType>): String {
        var fixedStr = str

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.SIANNTIAU_SOOJI_BO_TI_IMCHAT_BOE)) {
            if (fixedStr.length >= 3) {
                val strWithoutLastChar = fixedStr.substring(0, fixedStr.length - 1)
                if (strWithoutLastChar.contains(".*\\d.*".toRegex())) {
                    val matchSequence = Regex("[0-9]").findAll(strWithoutLastChar)
                    val matchList = matchSequence.toList()
                    val count = matchList.size

                    if (count == 1) {
                        val matchResult: MatchResult = matchList[0]
                        fixedStr = fixedStr.substring(0, matchResult.range.first) +
                                fixedStr.substring(matchResult.range.last + 1) +
                                fixedStr.substring(matchResult.range.first, matchResult.range.last + 1)
                    } else if (count > 1) {
                        throw IllegalArgumentException("Pe̍h-ōe-jī im-chat keh-sek chhò-gō͘:$str")
                    }
                }
            }
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.OO_SIA_CHO_OU)) {
            fixedStr = fixedStr.replace("ou", "oo")
                    .replace("Ou", "Oo")
                    .replace("OU", "OO")
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.CH_SIA_CHO_TS)) {
            fixedStr = fixedStr.replace("ts", "ch")
                    .replace("Ts", "Ch")
                    .replace("TS", "CH")
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.IR_KAI_CHO_UR)) {
            fixedStr = fixedStr.replace("ir", "ur")
                    .replace("Ir", "Ur")
                    .replace("IR", "UR")
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.ER_KAI_CHO_OR)) {
            fixedStr = fixedStr.replace("er", "or")
                    .replace("Er", "Or")
                    .replace("ER", "OR")
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.PHINNIM_NN_SIA_CHO_TOASIA_N)) {
            if (fixedStr.length >= 2) {
                var testString = fixedStr
                var trailingSianntiauString = ""
                if (fixedStr.substring(fixedStr.length - 1).isNumeric()) {
                    testString = fixedStr.substring(0, fixedStr.length - 1)
                    trailingSianntiauString = fixedStr.substring(fixedStr.length - 1)
                }

                if (testString.substring(testString.length - 1) == "N") {
                    val testLowerString = testString.substring(1, testString.length - 1)
                    if (testLowerString.toLowerCase() == testLowerString) {
                        val index = fixedStr.indexOf("N")
                        fixedStr = fixedStr.substring(0, index) + "nn" + trailingSianntiauString
                    } else {
                        throw IllegalArgumentException("Pe̍h-ōe-jī im-chat keh-sek chhò-gō͘:$str")
                    }
                }
            }
        }

        if (inputFixTypes.contains(PojInputFixType.ALL) ||
                inputFixTypes.contains(PojInputFixType.ONN_SIA_CHO_OONN)) {
            fixedStr = fixedStr.replace("oonn", "onn")
                    .replace("Oonn", "Onn")
                    .replace("OONN", "ONN")
        }

        return fixedStr
    }
}