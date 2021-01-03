package tw.taibunkesimi.chhoetaigi.database.dicts.kauiokpoo.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class KauiokpooTaigiDictSrcEntry : DictEntry() {
    lateinit var id: String

    lateinit var hanji: String
    lateinit var hanjiOther: String
    lateinit var wordProperty: String

    lateinit var kip: String
    lateinit var kipOther: String
    lateinit var wordBunPehProperty: String
    lateinit var otherWordBunPehProperty: String

    lateinit var synonym: String
    lateinit var opposite: String

//    lateinit var wordKithannKonghoatProperty: String
    lateinit var hoabun: String
    lateinit var descriptions: String

    lateinit var dialects: String
}