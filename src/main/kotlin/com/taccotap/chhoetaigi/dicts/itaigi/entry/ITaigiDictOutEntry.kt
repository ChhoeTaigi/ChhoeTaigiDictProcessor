package com.taccotap.chhoetaigi.dicts.itaigi.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class ITaigiDictOutEntry : DictEntry() {
    lateinit var id: String
    lateinit var pojInput: String
    lateinit var pojUnicode: String
    lateinit var tailoInput: String
    lateinit var tailoUnicode: String
    lateinit var hanlo: String
    lateinit var hoagi: String
    lateinit var from: String
}