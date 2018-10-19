package com.taccotap.chhoetaigi.dicts.kam.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KamDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    //    lateinit var pojDialect: String
    lateinit var hanjiTaibun: String
    lateinit var pojBunim: String
    lateinit var pojKaisoeh: String
    lateinit var hanloTaibunKaisoehPoj: String
    lateinit var pageNumber: String
}