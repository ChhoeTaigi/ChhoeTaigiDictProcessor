package com.taccotap.chhoetaigi.dicts.taihoa.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaihoaDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var pojDialect: String
    lateinit var hanloTaibunPoj: String
    lateinit var hoabun: String
}