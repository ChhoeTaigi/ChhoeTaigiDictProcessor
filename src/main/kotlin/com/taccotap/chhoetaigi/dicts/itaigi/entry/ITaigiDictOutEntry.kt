package com.taccotap.chhoetaigi.dicts.itaigi.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class ITaigiDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kiplmjUnicode: String
    lateinit var kiplmjInput: String

    lateinit var hanloTaibunKiplmj: String
    lateinit var hoabun: String

    lateinit var from: String
}