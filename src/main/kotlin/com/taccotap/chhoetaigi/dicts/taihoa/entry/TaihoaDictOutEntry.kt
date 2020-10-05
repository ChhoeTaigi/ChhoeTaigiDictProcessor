package com.taccotap.chhoetaigi.dicts.taihoa.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaihoaDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeOther: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String

    lateinit var hanloTaibunPoj: String

    lateinit var kipUnicode: String
    lateinit var kipUnicodeOther: String
    lateinit var kipInput: String
    lateinit var kipInputOther: String

    lateinit var hanloTaibunKip: String

    lateinit var hoabun: String
}