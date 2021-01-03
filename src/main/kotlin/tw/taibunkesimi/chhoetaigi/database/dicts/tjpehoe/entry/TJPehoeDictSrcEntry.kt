package tw.taibunkesimi.chhoetaigi.database.dicts.tjpehoe.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class TJPehoeDictSrcEntry : DictEntry() {
    lateinit var id: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String
    lateinit var pageNumber: String
}