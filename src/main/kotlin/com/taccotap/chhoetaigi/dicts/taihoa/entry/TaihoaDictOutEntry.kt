package com.taccotap.chhoetaigi.dicts.taihoa.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaihoaDictOutEntry : DictEntry() {
    lateinit var id: String
    lateinit var pojInput: String
    lateinit var pojUnicode: String
    lateinit var pojKithannKhiunnkhauInput: String
    lateinit var pojKithannKhiunnkhauUnicode: String
    lateinit var tailoInput: String
    lateinit var tailoUnicode: String
    lateinit var tailoKithannKhiunnkhauInput: String
    lateinit var tailoKithannKhiunnkhauUnicode: String
    lateinit var hanloPoj: String
    lateinit var hanloTailo: String
    lateinit var hoagi: String
}