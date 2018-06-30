package com.taccotap.chhoetaigi.dicts.taijit.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaijitDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var pojKithannKhiunnkhau: String
    lateinit var hanlo: String
    lateinit var taigiKaisoehHanlo: String
    lateinit var taigiLekuHanlo: String
    lateinit var pageNumber: String
}