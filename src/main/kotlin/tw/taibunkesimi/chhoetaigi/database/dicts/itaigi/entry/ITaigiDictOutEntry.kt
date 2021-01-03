package tw.taibunkesimi.chhoetaigi.database.dicts.itaigi.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class ITaigiDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunKip: String
    lateinit var hoabun: String

    lateinit var from: String
}
