package tw.taibunkesimi.chhoetaigi.database.dicts.embree.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class EmbreeDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojInput: String

    lateinit var kipUnicode: String
    lateinit var kipInput: String

    lateinit var abbreviations: String
    lateinit var nounClassifiers: String
    lateinit var reduplication: String

    lateinit var synonym: String
    lateinit var cf: String

    lateinit var hoabun: String
    lateinit var english: String

    lateinit var pageNumber: String
}
