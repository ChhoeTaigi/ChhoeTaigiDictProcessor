package com.taccotap.chhoetaigi.dicts.taihoa.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaihoaDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeDialect: String
    lateinit var pojInput: String
    lateinit var pojInputDialect: String

    lateinit var hanloTaibunPoj: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjUnicodeDialect: String
    lateinit var kiplmjInput: String
    lateinit var kiplmjInputDialect: String

    lateinit var hanloTaibunKiplmj: String

    lateinit var hoabun: String
}