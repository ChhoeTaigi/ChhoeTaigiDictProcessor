package com.taccotap.chhoetaigi.dicts.tjpehoe.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class TJPehoeDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeOther: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String

    lateinit var kipUnicode: String
    lateinit var kipUnicodeOther: String
    lateinit var kipInput: String
    lateinit var kipInputOther: String

    lateinit var pageNumber: String
    lateinit var storeLink: String
}