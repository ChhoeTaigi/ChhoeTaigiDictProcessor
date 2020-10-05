package com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaioanPehoeKichhooGikuOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeOther: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String

    lateinit var kipUnicode: String
    lateinit var kipUnicodeOther: String
    lateinit var kipInput: String
    lateinit var kipInputOther: String

    lateinit var hoabun: String

    lateinit var engbun: String
    lateinit var engbunChukai: String

    lateinit var nounClassifiers: String
    lateinit var opposite: String
    lateinit var exampleSu: String
    lateinit var fromSu: String

    lateinit var exampleKuTaibunPoj: String
    lateinit var exampleKuHoabun: String
    lateinit var exampleKuEngbun: String

    lateinit var pageNumber: String
}