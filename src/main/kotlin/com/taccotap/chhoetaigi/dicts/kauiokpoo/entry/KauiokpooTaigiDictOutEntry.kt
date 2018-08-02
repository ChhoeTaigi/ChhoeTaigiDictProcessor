package com.taccotap.chhoetaigi.dicts.taibuntiongbun.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KauiokpooTaigiDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeDialect: String
    lateinit var pojInput: String
    lateinit var pojInputDialect: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjUnicodeDialect: String
    lateinit var kiplmjInput: String
    lateinit var kiplmjInputDialect: String

    lateinit var wordProperty: String
    lateinit var wordBunPehProperty: String
    lateinit var wordDialectProperty: String

    lateinit var hanjiTaibun: String
    lateinit var hoabun: String

    lateinit var descriptions: String
}