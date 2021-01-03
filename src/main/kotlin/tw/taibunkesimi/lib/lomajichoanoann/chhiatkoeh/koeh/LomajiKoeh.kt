package tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.koeh

import tw.taibunkesimi.lib.lomajichoanoann.extensions.isNumeric

data class LomajiKoeh(val string: String, var isLomaji: Boolean) {

    private fun isEndingWithNumber(): Boolean {
        return this.string.substring(this.string.length - 1).isNumeric()
    }

    fun isKipInputNeedToConvertToUnicode(): Boolean {
        return isLomaji && isEndingWithNumber()
    }

    fun isPojInputNeedToConvertToUnicode(): Boolean {
        return isLomaji && (isEndingWithNumber() || this.string.contains("(oo|nn)".toRegex()))
    }
}