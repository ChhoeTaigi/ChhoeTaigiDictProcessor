package tw.taibunkesimi.lib.lomajichoanoann.extensions

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())

fun String.isUpper(): Boolean = this.toUpperCase() == this

fun String.replaceLast(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
    val index = this.lastIndexOf(oldValue, ignoreCase = ignoreCase)
    return if (index < 0) this else this.replaceRange(index, index + oldValue.length, newValue)
}
