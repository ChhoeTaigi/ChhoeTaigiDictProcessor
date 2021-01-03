package tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class TaioanPehoeKichhooGikuSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var kip: String
    lateinit var kipOther: String

    lateinit var hoabun: String

    lateinit var english: String
    lateinit var englishSoatbeng: String

    lateinit var nounClassifiers: String
    lateinit var opposite: String
    lateinit var exampleSu: String
    lateinit var fromSu: String

    lateinit var exampleKuTaibun: String
    lateinit var exampleKuHoabun: String
    lateinit var exampleKuEnglish: String

    lateinit var pageNumber: String
}