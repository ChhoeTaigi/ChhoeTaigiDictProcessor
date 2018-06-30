package com.taccotap.chhoetaigi.dicts.kam.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KamDictOutEntry : DictEntry() {
    lateinit var id: String
    lateinit var pojInput: String
    lateinit var pojUnicode: String
    lateinit var tailoInput: String
    lateinit var tailoUnicode: String
    lateinit var taigiHanjiWithTailo: String
    lateinit var taigiHanjiWithPoj: String
    lateinit var taigiKaisoehPoj: String
    lateinit var taigiKaisoehTailo: String
    lateinit var taigiKaisoehHanloPoj: String
    lateinit var pageNumber: String
}