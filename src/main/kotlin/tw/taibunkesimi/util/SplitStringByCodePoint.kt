package tw.taibunkesimi.util

class SplitStringByCodePoint {
    companion object {
        fun split(str: String): ArrayList<String> {
            return splitImplement(str).toCollection(ArrayList())
        }

        private fun splitImplement(str: String): Array<String> {
            val codepoints = str.codePoints().toArray()
            return Array(codepoints.size) { index ->
                String(codepoints, index, 1)
            }
        }
    }
}