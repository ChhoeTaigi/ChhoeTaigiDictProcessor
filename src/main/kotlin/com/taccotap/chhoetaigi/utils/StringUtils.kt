package com.taccotap.chhoetaigi.utils

import java.text.BreakIterator

class StringUtils {

    companion object {
        private val breakIterator = BreakIterator.getCharacterInstance()

        fun getGraphmeLength(str: String): Int {
            breakIterator.setText(str)
            var count = 0
            while (breakIterator.next() != BreakIterator.DONE) {
                count++
            }

            return count
        }

        fun getGraphmeStringAt(str: String, pos: Int): String {
            return getGraphmeStringAt(str, pos, pos + 1)
        }

        fun getGraphmeStringAt(str: String, startPos: Int, endPos: Int): String {
            breakIterator.setText(str)

            val stringBuilder = StringBuilder()

            var count = 0
            var start = breakIterator.first()
            var end = breakIterator.next()
            while (end != BreakIterator.DONE) {
                val chunk = str.substring(start, end)

                if (count in startPos until endPos) {
                    stringBuilder.append(chunk)
                }
                if (count == endPos - 1) {
                    return stringBuilder.toString()
                }

                start = end
                end = breakIterator.next()
                count++
            }

            throw IndexOutOfBoundsException("str = $str: startPos = $startPos, endPos = $endPos, but length is $count.")
        }
    }
}