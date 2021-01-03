package tw.taibunkesimi.chhoetaigi.database.dicts.taioansitbutmialui.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class TaioanSitbutMialuiOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var hanjiTaibun: String

    lateinit var pageNumber: String
}