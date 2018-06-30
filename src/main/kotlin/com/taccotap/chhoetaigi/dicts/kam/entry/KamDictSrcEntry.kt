package com.taccotap.chhoetaigi.dicts.kam.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KamDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var taigiHanji: String
    lateinit var taigiKaisoehPoj: String
    lateinit var taigiKaisoehHanlo: String
    lateinit var pageNumber: String
}