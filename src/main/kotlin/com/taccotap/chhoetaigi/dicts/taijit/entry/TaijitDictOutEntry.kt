package com.taccotap.chhoetaigi.dicts.taijit.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaijitDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeDialect: String
    lateinit var pojInput: String
    lateinit var pojInputDialect: String

    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunKaisoehPoj: String
    lateinit var hanloTaibunLekuPoj: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjUnicodeDialect: String
    lateinit var kiplmjInput: String
    lateinit var kiplmjInputDialect: String

    lateinit var hanloTaibunKiplmj: String
    lateinit var hanloTaibunKaisoehKiplmj: String
    lateinit var hanloTaibunLekuKiplmj: String

    lateinit var pageNumber: String
}