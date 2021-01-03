package tw.taibunkesimi.phahtaigi.imedict.datamodel


open class ImeDictItem {
    var wordId = 0
    lateinit var poj: String
    lateinit var pojSujip: String
    lateinit var pojSujipBoSooji: String
    lateinit var pojSujipThauJibo: String
    var pojPriority = 0
    lateinit var kip: String
    lateinit var kipSujip: String
    lateinit var kipSujipBoSooji: String
    lateinit var kipSujipThauJibo: String
    var kipPriority = 0
    lateinit var hanji: String

    // temp
    lateinit var pojPriorityString: String
    lateinit var kipPriorityString: String
}
