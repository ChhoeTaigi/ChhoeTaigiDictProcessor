package tw.taibunkesimi.chhoetaigi.database.dicts.taijit.entry

import tw.taibunkesimi.chhoetaigi.database.entry.DictEntry

open class TaijitDictOutEntry : DictEntry() {
    lateinit var id: String

    lateinit var pojUnicode: String
    lateinit var pojUnicodeOther: String
    lateinit var pojInput: String
    lateinit var pojInputOther: String

    lateinit var hanloTaibunPoj: String
    lateinit var hanloTaibunKaisoehPoj: String
    lateinit var hanloTaibunLekuPoj: String

    lateinit var kipUnicode: String
    lateinit var kipUnicodeOther: String
    lateinit var kipInput: String
    lateinit var kipInputOther: String

    lateinit var hanloTaibunKip: String
    lateinit var hanloTaibunKaisoehKip: String
    lateinit var hanloTaibunLekuKip: String

    lateinit var pageNumber: String
}