package com.taccotap.chhoetaigi.dicts.kam.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KamDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var hanloTaibunPoj: String
    lateinit var pojKaisoeh: String
    lateinit var hanloTaibunKaisoehPoj: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjInput: String

    lateinit var hanloTaibunKiplmj: String
    lateinit var kiplmjKaisoeh: String

    lateinit var pageNumber: String
}