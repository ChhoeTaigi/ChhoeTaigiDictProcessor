package tw.taibunkesimi.chhoetaigi.database.dicts.kam.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class KamDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String

    //    lateinit var pojOther: String
    lateinit var hanjiTaibun: String
    lateinit var pojBunim: String
    lateinit var pojKaisoeh: String
    lateinit var hanloTaibunKaisoehPoj: String
    lateinit var pageNumber: String
}