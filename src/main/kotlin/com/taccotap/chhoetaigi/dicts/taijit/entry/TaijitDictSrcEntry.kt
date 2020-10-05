package com.taccotap.chhoetaigi.dicts.taijit.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaijitDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var pojOther: String
    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunKaisoehPoj: String
    lateinit var hanloTaibunLekuPoj: String
    lateinit var pageNumber: String
}