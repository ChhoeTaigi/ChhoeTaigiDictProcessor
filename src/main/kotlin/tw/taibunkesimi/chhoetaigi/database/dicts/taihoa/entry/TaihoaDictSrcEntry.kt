package tw.taibunkesimi.chhoetaigi.database.dicts.taihoa.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class TaihoaDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var poj: String
    lateinit var pojOther: String
    lateinit var hanloTaibunPoj: String
    lateinit var hoabun: String
}