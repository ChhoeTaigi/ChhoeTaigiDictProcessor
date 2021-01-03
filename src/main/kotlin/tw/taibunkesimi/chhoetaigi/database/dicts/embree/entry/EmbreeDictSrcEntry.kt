package tw.taibunkesimi.chhoetaigi.database.dicts.embree.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class EmbreeDictSrcEntry : DictEntry() {
    lateinit var poj: String
    lateinit var hoabun: String
    lateinit var abbreviations: String
    lateinit var nounClassifiers: String
    lateinit var reduplication: String
    lateinit var synonym: String
    lateinit var cf: String
    lateinit var english: String
    lateinit var pageNumber: String
}