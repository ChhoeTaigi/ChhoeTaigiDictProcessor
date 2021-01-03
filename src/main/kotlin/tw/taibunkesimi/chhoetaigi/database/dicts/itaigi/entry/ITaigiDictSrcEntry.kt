package tw.taibunkesimi.chhoetaigi.database.dicts.itaigi.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class ITaigiDictSrcEntry : DictEntry() {
    lateinit var kip: String
    lateinit var hanloTaibunKip: String
    lateinit var hoabun: String
    lateinit var from: String
}