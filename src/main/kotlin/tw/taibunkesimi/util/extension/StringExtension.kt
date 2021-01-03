package tw.taibunkesimi.util.extension

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())
