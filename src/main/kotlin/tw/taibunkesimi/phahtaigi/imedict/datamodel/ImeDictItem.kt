package tw.taibunkesimi.phahtaigi.imedict.datamodel


open class ImeDictItem {
    var wordId = 0

    lateinit var poj: String
    lateinit var pojSujip: String
    lateinit var pojSujipBoSooji: String
    lateinit var pojSujipThauJibo: String

    lateinit var kip: String
    lateinit var kipSujip: String
    lateinit var kipSujipBoSooji: String
    lateinit var kipSujipThauJibo: String

    var pojPriority = 0
    var kipPriority = 0

    lateinit var hanji: String

    /*
        0: Custom
        1: KIP
        2: TaijitToaSutian
        3: TJ
     */
    var srcDict = 0

    // temp
    lateinit var pojPriorityString: String
    lateinit var kipPriorityString: String
}
