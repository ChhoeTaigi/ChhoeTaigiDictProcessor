package tw.taibunkesimi.chhoetaigi.database.dicts.maryknoll.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class MaryknollTaiEngDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var hoabun: String
    lateinit var english: String

    lateinit var pageNumber: String
}