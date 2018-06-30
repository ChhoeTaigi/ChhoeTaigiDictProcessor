package com.taccotap.chhoetaigi.dicts.embree.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class EmbreeDictOutEntry : DictEntry() {
    lateinit var id: String
    lateinit var pojInput: String
    lateinit var pojUnicode: String
    //    lateinit var pojKithannKhiunnkhauInput: String
//    lateinit var pojKithannKhiunnkhauUnicode: String
    lateinit var tailoInput: String
    lateinit var tailoUnicode: String
    //    lateinit var tailoKithannKhiunnkhauInput: String
//    lateinit var tailoKithannKhiunnkhauUnicode: String
    lateinit var hoagi: String
    lateinit var abbreviations: String
    lateinit var nounClassifiers: String
    lateinit var reduplication: String
    lateinit var englishDescriptions: String
    lateinit var pageNumber: String
}