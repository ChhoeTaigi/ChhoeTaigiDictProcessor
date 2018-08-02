package com.taccotap.chhoetaigi.dicts.embree.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class EmbreeDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var tailoUnicode: String
    lateinit var tailoInput: String

    lateinit var abbreviations: String
    lateinit var nounClassifiers: String
    lateinit var reduplication: String

    lateinit var hoabun: String
    lateinit var englishDescriptions: String

    lateinit var pageNumber: String
}