package com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaioanPehoeKichhooGikuSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var kip: String
    lateinit var kipOther: String

    lateinit var hoabun: String

    lateinit var engbun: String
    lateinit var engbunChukai: String

    lateinit var nounClassifiers: String
    lateinit var opposite: String
    lateinit var exampleSu: String
    lateinit var fromSu: String

    lateinit var exampleKuTaibun: String
    lateinit var exampleKuHoabun: String
    lateinit var exampleKuEngbun: String

    lateinit var pageNumber: String
}