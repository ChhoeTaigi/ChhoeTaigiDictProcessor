package tw.taibunkesimi.chhoetaigi.database.dicts.kam.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class KamDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var hanjiTaibun: String

    lateinit var pojBunimUnicode: String
    lateinit var pojBunimInput: String

    lateinit var pojKaisoeh: String
    lateinit var hanloTaibunKaisoehPoj: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var kipBunimUnicode: String
    lateinit var kipBunimInput: String

    lateinit var kipKaisoeh: String

    lateinit var pageNumber: String
}