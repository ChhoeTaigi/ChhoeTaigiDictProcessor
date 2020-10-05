package tw.taibunkesimi.lomajichoanoann.unicode

import tw.taibunkesimi.lomajichoanoann.unicode.siannho.KipSiannho
import tw.taibunkesimi.lomajichoanoann.unicode.siannho.PojSiannho

object ImchatUnicodeToInputChoanoann {

    fun pojUnicodeToInput(pojUnicodeString: String, isKuiKuUppercase: Boolean): String {
        for (possiblePojUnicode in PojSiannho.sUnicodeToSoojiSianntiauHashMap.keys) {
            if (pojUnicodeString.contains(possiblePojUnicode)) {
                val pojSoojiSianntiau: String? = PojSiannho.sUnicodeToSoojiSianntiauHashMap[possiblePojUnicode]
                if (pojSoojiSianntiau != null) {
                    val pojBoSianntiau = pojSoojiSianntiau.substring(0, pojSoojiSianntiau.length - 1)
                    val sianntiauSooji = pojSoojiSianntiau.substring(pojSoojiSianntiau.length - 1)
                    val pojSianntiauSoojiString = pojUnicodeString.replace(possiblePojUnicode, pojBoSianntiau) + sianntiauSooji
                    return pojUnicodeToInputFixJibo(pojSianntiauSoojiString, isKuiKuUppercase)
                }
            }
        }

        return pojUnicodeToInputFixJibo(pojUnicodeString, isKuiKuUppercase)
    }

    private fun pojUnicodeToInputFixJibo(pojUnicodeString: String, isKuiKuUppercase: Boolean): String {
        var fixedString = pojUnicodeString.replace("ⁿ", "nn")

        if (isKuiKuUppercase) {
            fixedString = fixedString.replace("O͘", "OO")
                    .replace("Ṳ", "UR")
                    .replace("O̤̤", "OR")
        } else {
            fixedString = fixedString.replace("O͘", "Oo")
                    .replace("Ṳ", "Ur")
                    .replace("O̤", "Or")
        }

        return fixedString.replace("o͘", "oo")
                .replace("ṳ", "ur")
                .replace("o̤", "or")
    }

    fun kipUnicodeToInput(kipUnicodeString: String): String {
        for (possibleKipUnicode in KipSiannho.sUnicodeToSoojiSianntiauHashMap.keys) {
            if (kipUnicodeString.contains(possibleKipUnicode)) {
                val kipSoojiSianntiau: String? = KipSiannho.sUnicodeToSoojiSianntiauHashMap[possibleKipUnicode]
                if (kipSoojiSianntiau != null) {
                    val kipBoSianntiau = kipSoojiSianntiau.substring(0, kipSoojiSianntiau.length - 1)
                    val sianntiauSooji = kipSoojiSianntiau.substring(kipSoojiSianntiau.length - 1)
                    return kipUnicodeString
                            .replace(possibleKipUnicode, kipBoSianntiau) +
                            sianntiauSooji
                }
            }
        }

        return kipUnicodeString
    }
}
