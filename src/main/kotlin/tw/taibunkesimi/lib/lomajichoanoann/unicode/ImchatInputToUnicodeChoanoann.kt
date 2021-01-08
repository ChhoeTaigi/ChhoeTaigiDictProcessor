package tw.taibunkesimi.lib.lomajichoanoann.unicode

import tw.taibunkesimi.lib.lomajichoanoann.extensions.isNumeric
import tw.taibunkesimi.lib.lomajichoanoann.extensions.isUpper
import tw.taibunkesimi.lib.lomajichoanoann.extensions.replaceLast
import tw.taibunkesimi.lib.lomajichoanoann.hethong.ImchatInputHethongChoanoann
import tw.taibunkesimi.lib.lomajichoanoann.unicode.siannho.KipSiannho
import tw.taibunkesimi.lib.lomajichoanoann.unicode.siannho.PojSiannho
import tw.taibunkesimi.lib.lomajichoanoann.unicode.utils.PojSianntiauPosition
import tw.taibunkesimi.lib.lomajichoanoann.unicode.utils.PojUnicodeNotFoundException

object ImchatInputToUnicodeChoanoann {

    fun pojInputToUnicode(pojInputString: String): String {
        var pojInputStringFixJibo = pojInputToUnicodeFixJibo(pojInputString)

        if (pojInputStringFixJibo.length <= 1) {
            return pojInputStringFixJibo
        }

        val sianntiauSoojiString = pojInputStringFixJibo.substring(pojInputStringFixJibo.length - 1)
        if (!sianntiauSoojiString.isNumeric()) {
            return pojInputStringFixJibo
        }

        val pojInputBoSianntiauSooji: String = pojInputStringFixJibo.substring(0, pojInputStringFixJibo.length - 1)
        val pojInputSianntiauPosition = getPojInputSianntiauPosition(pojInputBoSianntiauSooji)

        if (pojInputSianntiauPosition == null) {
            return pojInputBoSianntiauSooji
        } else {
            val str1 = pojInputBoSianntiauSooji.substring(0, pojInputSianntiauPosition.pos)

            val str2PojBosianntiau = pojInputBoSianntiauSooji.substring(
                pojInputSianntiauPosition.pos,
                pojInputSianntiauPosition.pos + pojInputSianntiauPosition.length
            )
            val str2PojNumber = str2PojBosianntiau + sianntiauSoojiString
            val str2 = PojSiannho.sSoojiSianntiauToUnicodeHashMap[str2PojNumber]
            if (str2.isNullOrEmpty()) {
                println("Poj.sPojNumberToPojUnicodeHashMap not found: $pojInputString, $str2PojNumber")
                throw PojUnicodeNotFoundException("PojSiannho.sSoojiSianntiauToUnicodeHashMap[$str2PojNumber] not found!")
            }

            val str3 = pojInputBoSianntiauSooji.substring(
                pojInputSianntiauPosition.pos + pojInputSianntiauPosition.length,
                pojInputBoSianntiauSooji.length
            )

//            println("$pojBoSianntiau$soojiSianntiauString -> $str1$str2$str3")

            return str1 + str2 + str3
        }
    }

    private fun pojInputToUnicodeFixJibo(pojInputString: String): String {
        var fixedString = pojInputString

//        println("1: $fixedString")

        if (!fixedString.contains("nng")) {
            fixedString = fixedString.replace("nn", "ⁿ")
        }

//        println("2: $fixedString")

        if (fixedString.isUpper()) {
            fixedString = fixedString.replace("OO", "O͘")
                .replace("UR", "Ṳ")
                .replace("OR", "O̤̤")
        } else {
            fixedString = fixedString.replace("Oo", "O͘")
                .replace("oo", "o͘")
                .replace("Ur", "Ṳ")
                .replace("ur", "ṳ")
                .replace("Or", "O̤")
                .replace("or", "o̤")
        }

        return fixedString
    }

    fun getPojInputSianntiauPosition(pojInputBoSianntiauSooji: String): PojSianntiauPosition? {
        val str = pojInputBoSianntiauSooji.toLowerCase()
        val vowelList = listOf("a", "i", "u", "o͘", "e", "o")
        val semivowelList = listOf("m", "ng", "n")
        val haikhaukhiunnVowelList = listOf("ṳ", "o̤")

        val lastIndexOfAnyVowel = str.lastIndexOfAny(vowelList)

        if (lastIndexOfAnyVowel == -1) {
            // Found no vowel

            val lastIndexOfAnySemiVowel = str.lastIndexOfAny(semivowelList)
            if (lastIndexOfAnySemiVowel == -1) {
                // Found no vowel, nor semivowel. Abort tone marking.
                return null
            } else {
                // Found no vowel, but found semivowel. Tone marks at semivowel.
                if (str.contains("ng")) {
                    return PojSianntiauPosition(lastIndexOfAnySemiVowel, 2)
                } else {
                    return PojSianntiauPosition(lastIndexOfAnySemiVowel, 1)
                }
            }
        } else {
            // Found a vowel

            // handle ir/er
            if (str.contains("(ṳ|o̤)".toRegex())) {
                val lastIndexOfAnyChoankhiunnVowel = str.lastIndexOfAny(haikhaukhiunnVowelList)
                return PojSianntiauPosition(lastIndexOfAnyChoankhiunnVowel, 1)
            }

            val vowelCount: Int = getVowelCount(str, lastIndexOfAnyVowel)

            if (vowelCount == 1) {
                return PojSianntiauPosition(lastIndexOfAnyVowel, 1)
            } else {    // Found HokBoim
                val last2ndJiboPosition = findJiboPositionFromLastCharExludingPhinnim(str, 2)
                val last2ndJiboString = str.substring(last2ndJiboPosition, last2ndJiboPosition + 1)

                val isPojJipsiann = isPojJipsiann(str)
                if (isPojJipsiann) {    // Found HokBoim Jipsiann
                    // Handle special cases:
                    if (str.toLowerCase().contains("iuh")) {
                        // "iuh" found
                        val findJiboPositionFromLastCharExludingPhinnim =
                            findJiboPositionFromLastCharExludingPhinnim(str, 2)
                        return PojSianntiauPosition(findJiboPositionFromLastCharExludingPhinnim, 1)
                    } else {
                        if (last2ndJiboString.toLowerCase().matches("[iu]".toRegex())) {
                            val findJiboPositionFromLastCharExludingPhinnim =
                                findJiboPositionFromLastCharExludingPhinnim(str, 3)
                            return PojSianntiauPosition(findJiboPositionFromLastCharExludingPhinnim, 1)
                        } else {
                            return PojSianntiauPosition(last2ndJiboPosition, 1)
                        }
                    }
                } else {    // Found HokBoim Not Jipsiann
                    // Handle special cases:
                    if (last2ndJiboString.toLowerCase() == "i") {
                        // Tone marks at the last jibo. (excluding phinnim)
                        val findJiboPositionFromLastCharExludingPhinnim =
                            findJiboPositionFromLastCharExludingPhinnim(str, 1)
                        return PojSianntiauPosition(findJiboPositionFromLastCharExludingPhinnim, 1)
                    }

                    // Tone marks at the last 2nd jibo. (excluding phinnim)
                    val findJiboPositionFromLastCharExludingPhinnim =
                        findJiboPositionFromLastCharExludingPhinnim(str, 2)
                    return PojSianntiauPosition(findJiboPositionFromLastCharExludingPhinnim, 1)
                }
            }
        }
    }

    private fun isPojJipsiann(pojBoSianntiau: String): Boolean {
        val lastCharExcludingPhinnim = pojBoSianntiau.replace("ⁿ", "")
        return lastCharExcludingPhinnim.substring(lastCharExcludingPhinnim.length - 1).toLowerCase()
            .matches("[ptkh]".toRegex())
    }

    private fun findJiboPositionFromLastCharExludingPhinnim(pojBoSianntiau: String, findWhichCharFromRight: Int): Int {
        var pos: Int = pojBoSianntiau.length - 1
        var foundJiboCount = 0

        while (pos >= 0) {
            val currentCharString = pojBoSianntiau.substring(pos, pos + 1)
            var isFoundPojOoPoint = false
            var isFoundPojNg = false

            if (currentCharString == "ⁿ") {
                // skip
            } else if (currentCharString == "\u0358") {
                // found "o͘  "'s "͘  "
                isFoundPojOoPoint = true
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

            if (isFoundPojNg) {
                pos -= 2
            } else if (isFoundPojOoPoint) {
                pos--
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
        if (lastIndexOfAnyVowel == 0) {
            return 1
        }

        val isLeftCharAlsoVowel =
            pojBoSianntiau.substring(lastIndexOfAnyVowel - 1, lastIndexOfAnyVowel).contains("[aiueo]".toRegex())
        if (!isLeftCharAlsoVowel) {
            return 1
        }

        return 2
    }

    fun kipInputToUnicode(kipInputString: String): String {
        if (kipInputString.length <= 1) {
            return kipInputString
        }

        val sianntiauSoojiString = kipInputString.substring(kipInputString.length - 1)
        if (!sianntiauSoojiString.isNumeric()) {
            return kipInputString
        }

        val kipInputBoSianntiauSooji: String = kipInputString.substring(0, kipInputString.length - 1)

        return kipInputToUnicodeMapping(kipInputBoSianntiauSooji, sianntiauSoojiString)
    }

    private fun kipInputToUnicodeMapping(kipInputBoSianntiauSooji: String, sianntiauSoojiString: String): String {
        val lowerInput = kipInputBoSianntiauSooji.toLowerCase()

        val lastIndexOfA = lowerInput.lastIndexOf("a")
        if (lastIndexOfA >= 0) {
            return kipInputToUnicodeMapper(
                kipInputBoSianntiauSooji,
                sianntiauSoojiString,
                kipInputBoSianntiauSooji.substring(lastIndexOfA, lastIndexOfA + 1)
            )
        }

        val lastIndexOfOtherBoim = lowerInput.lastIndexOfAny(arrayListOf("i", "u", "o", "e"))
        if (lastIndexOfOtherBoim >= 0) {
            // check oo
            if (lastIndexOfOtherBoim > 0 && lowerInput.substring(
                    lastIndexOfOtherBoim - 1,
                    lastIndexOfOtherBoim + 1
                ) == "oo"
            ) {
                return kipInputToUnicodeMapper(
                    kipInputBoSianntiauSooji,
                    sianntiauSoojiString,
                    kipInputBoSianntiauSooji.substring(lastIndexOfOtherBoim - 1, lastIndexOfOtherBoim + 1)
                )
            } else {
                return kipInputToUnicodeMapper(
                    kipInputBoSianntiauSooji,
                    sianntiauSoojiString,
                    kipInputBoSianntiauSooji.substring(lastIndexOfOtherBoim, lastIndexOfOtherBoim + 1)
                )
            }
        }

        val lastIndexOfPhinnchuim = lowerInput.lastIndexOfAny(arrayListOf("ng", "m", "n"))
        if (lastIndexOfPhinnchuim >= 0) {
            // check ng
            if (lastIndexOfPhinnchuim + 1 < lowerInput.length && lowerInput.substring(
                    lastIndexOfPhinnchuim,
                    lastIndexOfPhinnchuim + 2
                ) == "ng"
            ) {
                return kipInputToUnicodeMapper(
                    kipInputBoSianntiauSooji,
                    sianntiauSoojiString,
                    kipInputBoSianntiauSooji.substring(lastIndexOfPhinnchuim, lastIndexOfPhinnchuim + 2)
                )
            } else {
                return kipInputToUnicodeMapper(
                    kipInputBoSianntiauSooji,
                    sianntiauSoojiString,
                    kipInputBoSianntiauSooji.substring(lastIndexOfPhinnchuim, lastIndexOfPhinnchuim + 1)
                )
            }
        }

        return kipInputBoSianntiauSooji
    }

    private fun kipInputToUnicodeMapper(
        kipInputBoSianntiauSooji: String,
        sianntiauSoojiString: String,
        replaceCharString: String
    ): String {
        val kipJiboAndSianntiauSooji = replaceCharString + sianntiauSoojiString
        val kipJiboUnicode = KipSiannho.sSoojiSianntiauToUnicodeHashMap[kipJiboAndSianntiauSooji]
        return if (kipJiboUnicode != null) {
            kipInputBoSianntiauSooji.replaceLast(replaceCharString, kipJiboUnicode, false)
        } else {
            kipInputBoSianntiauSooji
        }
    }

    fun pojInputToKipUnicode(pojInputString: String): String {
        val kipInputString = ImchatInputHethongChoanoann.pojToKip(pojInputString)
        return kipInputToUnicode(kipInputString)
    }

    fun kipInputToPojUnicode(kipInputString: String): String {
        val pojInputString = ImchatInputHethongChoanoann.kipToPoj(kipInputString)
        return pojInputToUnicode(pojInputString)
    }
}
