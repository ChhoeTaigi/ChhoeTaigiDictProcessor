package com.taccotap.chhoetaigi.lomajiutils

import com.taccotap.chhoetaigi.extensions.isNumeric

object LomajiConverter {

    enum class ConvertLomajiInputStringCase {
        CASE_POJ_INPUT_FIX,
        CASE_POJ_INPUT_TO_TAILO_INPUT,
        CASE_POJ_INPUT_TO_POJ_UNICODE,
        CASE_POJ_INPUT_TO_TAILO_UNICODE,
        CASE_TAILO_INPUT_TO_POJ_INPUT,
        CASE_TAILO_INPUT_TO_TAILO_UNICODE,
        CASE_TAILO_INPUT_TO_POJ_UNICODE,
    }

    enum class ConvertLomajiUnicodeStringCase {
        CASE_TAILO_UNICODE_TO_TAILO_INPUT,
        CASE_POJ_UNICODE_TO_POJ_INPUT,
    }

    fun pojInputStringFix(pojInputString: String): String {
        return convertLomajiInputString(pojInputString, ConvertLomajiInputStringCase.CASE_POJ_INPUT_FIX)
    }

    private fun pojInputFix(pojInput: String): String {
        var str = pojInputFixTrailingN(pojInput)
        str = pojInputFixSianntiauSoojiPosition(str)

        str = str
                .replace("ts", "ch") // ts -> ch
                .replace("Ts", "Ch") // Ts -> Ch
                .replace("TS", "CH") // TS -> CH
                .replace("ou", "oo") // ou -> oo
                .replace("Ou", "Oo") // Ou -> Oo
                .replace("OU", "OO") // OU -> OO

        return str
    }

    private fun pojInputFixSianntiauSoojiPosition(pojInput: String): String {
        if (pojInput.length > 1) {
            if (pojInput.substring(pojInput.length - 1).isNumeric()) {
                return pojInput
            } else {
                val matchSequence = Regex("[0-9]").findAll(pojInput)
                val matchList = matchSequence.toList()
                val size = matchList.size

                if (size == 0) {
                    return pojInput
                } else {
                    val matchResult: MatchResult = matchList[0]

                    return pojInput.substring(0, matchResult.range.first) +
                            pojInput.substring(matchResult.range.last + 1) +
                            pojInput.substring(matchResult.range.first, matchResult.range.last + 1)
                }
            }
        }

        return pojInput
    }

    private fun pojInputToUnicodeFix(pojInput: String): String {
        var fixPojInput = pojInput
                .replace("oo", "o\u0358") // o͘
                .replace("Oo", "O\u0358") // O͘
                .replace("OO", "O\u0358") // O͘

        if (fixPojInput.length > 1) {
            var fixPojInputBoSianntiau = fixPojInput
            if (fixPojInputBoSianntiau.substring(fixPojInputBoSianntiau.length - 1).isNumeric()) {
                fixPojInputBoSianntiau = fixPojInputBoSianntiau.substring(0, fixPojInputBoSianntiau.length - 1)
            }
            if (fixPojInputBoSianntiau.endsWith("nn", true)) {
                fixPojInput = fixPojInput.replace("nn", "ⁿ")
                        .replace("NN", "ⁿ")
            }
        }

        return fixPojInput
    }

    private fun pojInputFixTrailingN(pojInput: String): String {
        if (pojInput.isEmpty() || pojInput.length == 1) {
            return pojInput
        }

        val lastCharString: String = pojInput.substring(pojInput.length - 1)
        var pojInputBoSianntiau: String
        var sianntiauString = ""

        if (!lastCharString.isNumeric()) {
            pojInputBoSianntiau = pojInput
        } else {
            pojInputBoSianntiau = pojInput.substring(0, pojInput.length - 1)
            sianntiauString = pojInput.substring(pojInput.length - 1)
        }

        if (pojInputBoSianntiau.length > 1) {
            val lastCharString2 = pojInputBoSianntiau.substring(pojInputBoSianntiau.length - 1)
            if (lastCharString2 == "N") {
                pojInputBoSianntiau = pojInputBoSianntiau.substring(0, pojInputBoSianntiau.length - 1) + "nn"
            }
        }

        return pojInputBoSianntiau + sianntiauString
    }

    /*
        For single word only, not for string.
     */
    fun pojInputToTailoInput(pojInput: String): String {
        return pojInput
                .replace("ch", "ts") // ch -> ts
                .replace("Ch", "Ts") // Ch -> Ts
                .replace("CH", "TS") // CH -> TS
                .replace("o([aAeE])".toRegex()) { "u" + it.value.substring(1) } // oa -> ua, oe -> ue.
                .replace("O([aAeE])".toRegex()) { "U" + it.value.substring(1) } // Oa -> Ua, Oe -> Ue.
                .replace("ek", "ik") // ek -> ik
                .replace("Ek", "Ik") // Ek -> Ik
                .replace("EK", "IK") // Ek -> IK
                .replace("eng", "ing") // eng -> ing
                .replace("Eng", "Ing") // Eng -> Ing
                .replace("ENG", "ING")
    }

    /*
         For single word only, not for string.
    */
    fun tailoInputToPojInput(tailoInput: String): String {
        return tailoInput
                .replace("ts", "ch") // ts -> ch
                .replace("Ts", "Ch") // Ts -> Ch
                .replace("TS", "CH") // TS -> CH
                .replace("u([aAeE])".toRegex()) { "o" + it.value.substring(1) } // ua -> oa, ue -> oe.
                .replace("U([aAeE])".toRegex()) { "O" + it.value.substring(1) } // Ua -> Oa, Ue -> Oe.
                .replace("ik", "ek") // ik -> ek
                .replace("Ik", "Ek") // Ik -> Ek
                .replace("IK", "EK") // IK -> EK
                .replace("ing", "eng") // ing -> eng
                .replace("Ing", "Eng") // Ing -> Eng
                .replace("ING", "ENG")
    }

    /*
        For single word only, not for string.
    */
    private fun tailoUnicodeToTailoInput(tailoUnicode: String): String {
        for (possibleTailoUnicode in Tailo.sTailoUnicodeToTailoNumberHashMap.keys) {
            if (tailoUnicode.contains(possibleTailoUnicode)) {
                val tailoSoojiSianntiau: String? = Tailo.sTailoUnicodeToTailoNumberHashMap[possibleTailoUnicode]
                if (tailoSoojiSianntiau != null) {
                    val tailoBoSianntiau = tailoSoojiSianntiau.substring(0, tailoSoojiSianntiau.length - 1)
                    val sianntiauSooji = tailoSoojiSianntiau.substring(tailoSoojiSianntiau.length - 1)
                    return tailoUnicode.replace(possibleTailoUnicode, tailoBoSianntiau) + sianntiauSooji
                }
            }
        }

        return tailoUnicode
    }

    /*
    For single word only, not for string.
*/
    private fun pojUnicodeToPojInput(pojUnicode: String): String {
        for (possiblePojUnicode in Poj.sPojUnicodeToPojNumberHashMap.keys) {
            if (pojUnicode.contains(possiblePojUnicode)) {
                val pojSoojiSianntiau: String? = Poj.sPojUnicodeToPojNumberHashMap[possiblePojUnicode]
                if (pojSoojiSianntiau != null) {
                    val pojBoSianntiau = pojSoojiSianntiau.substring(0, pojSoojiSianntiau.length - 1)
                    val sianntiauSooji = pojSoojiSianntiau.substring(pojSoojiSianntiau.length - 1)
                    return pojUnicode
                            .replace(possiblePojUnicode, pojBoSianntiau)
                            .replace("ⁿ", "nn") +
                            sianntiauSooji
                }
            }
        }

        return pojUnicode.replace("ⁿ", "nn")
    }

    fun convertLomajiInputString(inputString: String, convertLomajiInputStringCase: ConvertLomajiInputStringCase): String {
        val matchSequence: Sequence<MatchResult> = LomajiSplitter.splitLomajiSoojiTiauho(inputString)
        val matchList = matchSequence.toList()
        val size = matchList.size

        if (size == 0) {
            return inputString
        }

        val stringBuilder = StringBuilder()

        var lastPos = 0
        for (i in 0 until size) {
            val matchResult: MatchResult = matchList.get(i)

            val nonPojString = inputString.substring(lastPos, matchResult.range.first)
            stringBuilder.append(nonPojString)

            val lomajiSoojiTiauhu = inputString.substring(matchResult.range.first, matchResult.range.last + 1)

            when (convertLomajiInputStringCase) {
                ConvertLomajiInputStringCase.CASE_POJ_INPUT_FIX -> {
                    val pojInputFix = pojInputFix(lomajiSoojiTiauhu)
                    stringBuilder.append(pojInputFix)
                }

                ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE -> {
                    val lomajiUnicode: String = LomajiConverter.pojInputToPojUnicode(lomajiSoojiTiauhu)
                    stringBuilder.append(lomajiUnicode)
                }

                ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_TAILO_UNICODE -> {
                    val lomajiUnicode: String = LomajiConverter.tailoInputToTailoUnicode(lomajiSoojiTiauhu)
                    stringBuilder.append(lomajiUnicode)
                }

                ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE -> {
                    val tailoInput = pojInputToTailoInput(lomajiSoojiTiauhu)
                    val tailoUnicode: String = LomajiConverter.tailoInputToTailoUnicode(tailoInput)
                    stringBuilder.append(tailoUnicode)
                }

                ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_UNICODE -> {
                    val pojInput = tailoInputToPojInput(lomajiSoojiTiauhu)
                    val pojUnicode: String = LomajiConverter.pojInputToPojUnicode(pojInput)
                    stringBuilder.append(pojUnicode)
                }

                ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT -> {
                    val tailoInput = pojInputToTailoInput(lomajiSoojiTiauhu)
                    stringBuilder.append(tailoInput)
                }

                ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT -> {
                    val pojInput = tailoInputToPojInput(lomajiSoojiTiauhu)
                    stringBuilder.append(pojInput)
                }
            }

            lastPos = matchResult.range.last + 1

            if (i == size - 1) {
                val trailingNonPojString = inputString.substring(matchResult.range.last + 1)
                stringBuilder.append(trailingNonPojString)
            }
        }

        return stringBuilder.toString()
    }

    fun convertLomajiUnicodeString(unicodeString: String, convertLomajiUnicodeStringCase: ConvertLomajiUnicodeStringCase): String {
        val matchSequence: Sequence<MatchResult>

        when (convertLomajiUnicodeStringCase) {
            ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT -> {
                matchSequence = LomajiSplitter.splitTailoUnicode(unicodeString)
            }

            ConvertLomajiUnicodeStringCase.CASE_POJ_UNICODE_TO_POJ_INPUT -> {
                matchSequence = LomajiSplitter.splitPojUnicode(unicodeString)
            }
        }

        val matchList = matchSequence.toList()
        val size = matchList.size

        if (size == 0) {
            return unicodeString
        }

        val stringBuilder = StringBuilder()

        var lastPos = 0
        for (i in 0 until size) {
            val matchResult: MatchResult = matchList[i]

            val nonPojString = unicodeString.substring(lastPos, matchResult.range.first)
            stringBuilder.append(nonPojString)

            val lomajiUnicode = unicodeString.substring(matchResult.range.first, matchResult.range.last + 1)

            when (convertLomajiUnicodeStringCase) {
                ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT -> {
                    val tailoInput = tailoUnicodeToTailoInput(lomajiUnicode)
                    stringBuilder.append(tailoInput)
                }

                ConvertLomajiUnicodeStringCase.CASE_POJ_UNICODE_TO_POJ_INPUT -> {
                    val pojInput = pojUnicodeToPojInput(lomajiUnicode)
                    stringBuilder.append(pojInput)
                }
            }

            lastPos = matchResult.range.last + 1

            if (i == size - 1) {
                val trailingNonPojString = unicodeString.substring(matchResult.range.last + 1)
                stringBuilder.append(trailingNonPojString)
            }
        }

        return stringBuilder.toString()
    }

    fun pojInputToPojUnicode(pojInput: String): String {
        val pojInputFix = pojInputToUnicodeFix(pojInput)

        if (pojInputFix.length > 1) {
            val lastCharString: String = pojInputFix.substring(pojInputFix.length - 1)
            if (!lastCharString.isNumeric()) {
                return pojInputFix
            } else {
                val pojBoSianntiau: String = pojInputFix.substring(0, pojInputFix.length - 1)
                val soojiSianntiauString: String = lastCharString

                return convertPojInputBoSianntiauWithSoojiSianntiauToPojUnicode(pojBoSianntiau, soojiSianntiauString)
            }
        }

        return pojInputFix
    }

    private fun convertPojInputBoSianntiauWithSoojiSianntiauToPojUnicode(pojBoSianntiau: String, soojiSianntiauString: String): String {
        val pojSianntiauPosition: Int = getPojSianntiauPosition(pojBoSianntiau)

        if (pojSianntiauPosition == -1) {
            return pojBoSianntiau
        } else {
            return pojBoSianntiau.substring(0, pojSianntiauPosition) +
                    Poj.sPojNumberToPojUnicodeHashMap[pojBoSianntiau.substring(pojSianntiauPosition, pojSianntiauPosition + 1) + soojiSianntiauString] +
                    pojBoSianntiau.substring(pojSianntiauPosition + 1)
        }
    }

    private fun getPojSianntiauPosition(pojBoSianntiau: String): Int {
        val str = pojBoSianntiau.toLowerCase()
        val vowelList = listOf("a", "i", "u", "e", "o")
        val semivowelList = listOf("m", "ng", "n")

        val lastIndexOfAnyVowel = str.lastIndexOfAny(vowelList)

        if (lastIndexOfAnyVowel == -1) {
            // Found no vowel

            val lastIndexOfAnySemiVowel = str.lastIndexOfAny(semivowelList)
            if (lastIndexOfAnySemiVowel == -1) {
                // Found no vowel, nor semivowel. Abort tone marking.
                return -1
            } else {
                // Found no vowel, but found semivowel. Tone marks at semivowel.
                return lastIndexOfAnySemiVowel
            }
        } else {
            // Found a vowel

            val vowelCount: Int = getVowelCount(str, lastIndexOfAnyVowel)

            if (vowelCount == 1) {
                return lastIndexOfAnyVowel
            } else {
                // Found HokBoim

                val last2ndJiboPosition = findJiboPositionFromLastCharExludingPhinnim(str, 2)
                val last2ndJiboString = str.substring(last2ndJiboPosition, last2ndJiboPosition + 1)

                val isPojJipsiann = isPojJipsiann(str)
                if (isPojJipsiann) {
                    // Found HokBoim Jipsiann

                    // Handle special cases:
                    if (str.toLowerCase().contains("iuh")) {
                        // "iuh" found
                        return findJiboPositionFromLastCharExludingPhinnim(str, 2)
                    } else {
                        if (last2ndJiboString.toLowerCase().matches("[iu]".toRegex())) {
                            return findJiboPositionFromLastCharExludingPhinnim(str, 3)
                        } else {
                            return last2ndJiboPosition
                        }
                    }
                } else {
                    // Found HokBoim Not Jipsiann

                    // Handle special cases:
                    if (last2ndJiboString.toLowerCase() == "i") {
                        // Tone marks at the last jibo. (excluding phinnim)
                        return findJiboPositionFromLastCharExludingPhinnim(str, 1)
                    }

                    // Tone marks at the last 2nd jibo. (excluding phinnim)
                    return findJiboPositionFromLastCharExludingPhinnim(str, 2)
                }
            }
        }
    }

    fun isNotChoanTailoString(possibleTailoString: String): Boolean {
        val tailoInput = convertLomajiUnicodeString(possibleTailoString, ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT)
        return tailoInput.matches("[a-zA-Z0-9 -/.!?]+".toRegex()).not()
    }

    private fun isPojJipsiann(pojBoSianntiau: String): Boolean {
        val lastCharExcludingPhinnim = pojBoSianntiau.replace("ⁿ", "").substring(pojBoSianntiau.length - 1)
        return lastCharExcludingPhinnim.toLowerCase().matches("[ptkh]".toRegex())
    }

    private fun findJiboPositionFromLastCharExludingPhinnim(pojBoSianntiau: String, findWhichCharFromRight: Int): Int {
        var pos: Int = pojBoSianntiau.length - 1
        var foundJiboCount = 0

        while (pos >= 0) {
            val currentCharString = pojBoSianntiau.substring(pos, pos + 1)
            var isFoundPojOo = false
            var isFoundPojNg = false

            if (currentCharString == "ⁿ") {
                // skip
            } else if (currentCharString == "\u0358") {
                // found "o͘  " and shift 2 pos
                isFoundPojOo = true
                foundJiboCount++
            } else if (currentCharString.toLowerCase() == "g" && pos - 1 >= 0) {
                val nextCharString = pojBoSianntiau.substring(pos - 1, pos)
                if (nextCharString.toLowerCase() == "n") {
                    // found "ng"
                    isFoundPojNg = true
                } else {
                    // found "g"
                }
                foundJiboCount++
            } else {
                foundJiboCount++
            }

            if (foundJiboCount == findWhichCharFromRight) {
                break
            }

            if (isFoundPojOo || isFoundPojNg) {
                pos -= 2
            } else {
                pos--
            }

            if (pos < 0) {
                break
            }
        }

        return pos
    }

    private fun getVowelCount(pojBoSianntiau: String, lastIndexOfAnyVowel: Int): Int {
        if (lastIndexOfAnyVowel - 1 <= 0) {
            return 1
        }

        val isLeftCharAlsoVowel = pojBoSianntiau.substring(lastIndexOfAnyVowel - 1, lastIndexOfAnyVowel).contains("[aiueo]".toRegex())
        if (!isLeftCharAlsoVowel) {
            return 1
        }

        return 2

//        if (lastIndexOfAnyVowel - 2 <= 0) {
//            return 2
//        }
//
//        val isLeft2CharAlsoVowel = pojBoSianntiau.substring(lastIndexOfAnyVowel - 2, lastIndexOfAnyVowel - 1).contains("a|i|u|e|o".toRegex())
//        if (!isLeft2CharAlsoVowel) {
//            return 2
//        }
//
//        return 3
    }

    fun tailoInputToTailoUnicode(tailoInput: String): String {
        if (tailoInput.length > 1) {
            val lastCharString: String = tailoInput.substring(tailoInput.length - 1)
            if (!lastCharString.isNumeric()) {
                return tailoInput
            } else {
                val tailoBoSianntiau: String = tailoInput.substring(0, tailoInput.length - 1)
                val soojiSianntiauString: String = lastCharString

                return convertTailoInputBoSianntiauWithSoojiSianntiauToTailoUnicode(tailoBoSianntiau, soojiSianntiauString)
            }
        }

        return tailoInput
    }

    private fun convertTailoInputBoSianntiauWithSoojiSianntiauToTailoUnicode(tailoBoSianntiau: String, soojiSianntiauString: String): String {
        // Tone marks with this orders
        if (tailoBoSianntiau.contains("a")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "a")
        } else if (tailoBoSianntiau.contains("A")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "A")
        } else if (tailoBoSianntiau.contains("oo")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "o")
        } else if (tailoBoSianntiau.contains("Oo") || tailoBoSianntiau.contains("OO")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "O")
        } else if (tailoBoSianntiau.contains("e")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "e")
        } else if (tailoBoSianntiau.contains("E")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "E")
        } else if (tailoBoSianntiau.contains("o")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "o")
        } else if (tailoBoSianntiau.contains("O")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "O")
        } else if (tailoBoSianntiau.contains("iu") || tailoBoSianntiau.contains("Iu")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "u")
        } else if (tailoBoSianntiau.contains("IU")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "U")
        } else if (tailoBoSianntiau.contains("ui") || tailoBoSianntiau.contains("Ui")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "i")
        } else if (tailoBoSianntiau.contains("UI")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "I")
        } else if (tailoBoSianntiau.contains("i")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "i")
        } else if (tailoBoSianntiau.contains("I")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "I")
        } else if (tailoBoSianntiau.contains("u")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "u")
        } else if (tailoBoSianntiau.contains("U")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "U")
        } else if (tailoBoSianntiau.contains("ng")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "ng")
        } else if (tailoBoSianntiau.contains("Ng")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "Ng")
        } else if (tailoBoSianntiau.contains("NG")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "NG")
        } else if (tailoBoSianntiau.contains("m")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "m")
        } else if (tailoBoSianntiau.contains("M")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "M")
        } else if (tailoBoSianntiau.contains("n")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "n")
        } else if (tailoBoSianntiau.contains("N")) {
            return replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau, soojiSianntiauString, "N")
        } else {
            return tailoBoSianntiau
        }
    }

    private fun replaceTailoToTailoSianntiauUnicodeWithSoojiSianntiau(tailoBoSianntiau: String, soojiSiautiauStringString: String, replaceCharString: String): String {
        val tailoCharNumber = replaceCharString + soojiSiautiauStringString
        val tailoUnicode = Tailo.sTailoNumberToTailoUnicodeHashMap[tailoCharNumber]
        return if (tailoUnicode != null) {
            tailoBoSianntiau.replaceFirst(replaceCharString, tailoUnicode, false)
        } else {
            tailoBoSianntiau
        }
    }
}