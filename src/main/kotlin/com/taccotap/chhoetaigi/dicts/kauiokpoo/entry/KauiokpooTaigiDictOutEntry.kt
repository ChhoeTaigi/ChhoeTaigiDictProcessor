package com.taccotap.chhoetaigi.dicts.kauiokpoo.entry

import com.taccotap.chhoetaigi.entry.DictEntry

open class KauiokpooTaigiDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeOther: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String

    lateinit var kipUnicode: String
    lateinit var kipUnicodeOther: String
    lateinit var kipInput: String
    lateinit var kipInputOther: String

    lateinit var wordProperty: String
    lateinit var wordBunPehProperty: String
    lateinit var otherWordBunPehProperty: String
//    lateinit var wordKithannKonghoatProperty: String

    lateinit var hanji: String
    lateinit var hanjiOther: String
    lateinit var hoabun: String

    lateinit var descriptionsPoj: String
    lateinit var descriptionsKip: String

    lateinit var dialectsPoj: String
    lateinit var dialectsKip: String

    lateinit var synonym: String
    lateinit var opposite: String
}