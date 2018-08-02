package com.taccotap.chhoetaigi.dicts.taijit.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaijitDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var pojDialect: String
    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunPojKaisoeh: String
    lateinit var hanloTaibunPojLeku: String
    lateinit var pageNumber: String
}