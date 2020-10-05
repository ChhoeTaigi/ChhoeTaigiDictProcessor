package tw.taibunkesimi.lomajichoanoann.chhiatkoeh.koeh

import tw.taibunkesimi.lomajichoanoann.extensions.isNumeric

data class LomajiKoeh(val string: String, var isLomaji: Boolean) {

    private fun isEndingWithNumber(): Boolean {
        return this.string.substring(this.string.length - 1).isNumeric()
    }

    fun isLomajiNeedToConvertToUnicode(): Boolean {
        return isLomaji && isEndingWithNumber()
    }
}