package tw.taibunkesimi.lib.lomajichoanoann

import tw.taibunkesimi.lib.lomajichoanoann.unicode.KuiKuChoan
import tw.taibunkesimi.lib.lomajichoanoann.unicode.choanoannsoantek.ChoanoannSoantek

object TaigiLomajiKuikuChoanoann {
    val DEBUG = true

    // only
    fun onlyPojUnicodeToPojInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_UNICODE_TO_POJ_INPUT)
    }

    fun onlyPojUnicodeToKipInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_UNICODE_TO_KIP_INPUT)
    }

    fun onlyKipUnicodeToKipInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_UNICODE_TO_KIP_INPUT)
    }

    fun onlyKipUnicodeToPojInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_UNICODE_TO_POJ_INPUT)
    }

    fun onlyPojInputToPojUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_INPUT_TO_POJ_UNICODE)
    }

    fun onlyPojInputToKipUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_INPUT_TO_KIP_UNICODE)
    }

    fun onlyKipInputToKipUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_INPUT_TO_KIP_UNICODE)
    }

    fun onlyKipInputToPojUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_INPUT_TO_POJ_UNICODE)
    }

    fun onlyPojInputToKipInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_INPUT_TO_KIP_INPUT)
    }

    fun onlyKipInputToPojInput(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_INPUT_TO_POJ_INPUT)
    }

    fun onlyPojUnicodeToKipUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.POJ_UNICODE_TO_KIP_UNICODE)
    }

    fun onlyKipUnicodeToPojUnicode(str: String): String {
        return KuiKuChoan.choanOnlyLomajiInputOrUnicodeWithDelimiter(str, ChoanoannSoantek.KIP_UNICODE_TO_POJ_UNICODE)
    }

    // hybrid without other Latin
    fun hybridPojUnicodeToPojInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_UNICODE_TO_POJ_INPUT)
    }

    fun hybridPojUnicodeToKipInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_UNICODE_TO_KIP_INPUT)
    }

    fun hybridKipUnicodeToKipInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_UNICODE_TO_KIP_INPUT)
    }

    fun hybridKipUnicodeToPojInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_UNICODE_TO_POJ_INPUT)
    }

    fun hybridPojInputToPojUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_INPUT_TO_POJ_UNICODE)
    }

    fun hybridPojInputToKipUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_INPUT_TO_KIP_UNICODE)
    }

    fun hybridKipInputToKipUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_INPUT_TO_KIP_UNICODE)
    }

    fun hybridKipInputToPojUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_INPUT_TO_POJ_UNICODE)
    }

    fun hybridPojInputToKipInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_INPUT_TO_KIP_INPUT)
    }

    fun hybridKipInputToPojInput(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_INPUT_TO_POJ_INPUT)
    }

    fun hybridPojUnicodeToKipUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.POJ_UNICODE_TO_KIP_UNICODE)
    }

    fun hybridKipUnicodeToPojUnicode(str: String): String {
        return KuiKuChoan.choanHybridInputWithRegex(str, ChoanoannSoantek.KIP_UNICODE_TO_POJ_UNICODE)
    }
}