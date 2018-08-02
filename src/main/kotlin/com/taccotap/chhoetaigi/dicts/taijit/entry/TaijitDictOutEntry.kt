package com.taccotap.chhoetaigi.dicts.taijit.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaijitDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeDialect: String
    lateinit var pojInput: String
    lateinit var pojInputDialect: String

    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunPojKaisoeh: String
    lateinit var hanloTaibunPojLeku: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjUnicodeDialect: String
    lateinit var kiplmjInput: String
    lateinit var kiplmjInputDialect: String

    lateinit var hanloTaibunKiplmj: String
    lateinit var hanloTaibunKiplmjKaisoeh: String
    lateinit var hanloTaibunKiplmjLeku: String

    lateinit var pageNumber: String
}