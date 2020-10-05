package com.taccotap.chhoetaigi.dicts.maryknoll.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class MaryknollTaiEngDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var hoabun: String
    lateinit var englishDescriptions: String

    lateinit var pageNumber: String
}