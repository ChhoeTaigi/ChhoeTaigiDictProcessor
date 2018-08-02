package com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TaioanPehoeKichhooGikuOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeDialect: String
    lateinit var pojInput: String
    lateinit var pojInputDialect: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjUnicodeDialect: String
    lateinit var kiplmjInput: String
    lateinit var kiplmjInputDialect: String

    lateinit var hoabun: String

    lateinit var pageNumber: String
}