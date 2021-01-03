package tw.taibunkesimi.lib.lomajichoanoann.unicode.siannho

import java.util.*

object PojSiannho {
    // <poj_unicode, poj_tonenumber>
    val sUnicodeToSoojiSianntiauHashMap: HashMap<String, String> = HashMap()

    // <poj_tonenumber, poj_unicode>
    val sSoojiSianntiauToUnicodeHashMap: HashMap<String, String> = HashMap()

    init {
        // A
        sUnicodeToSoojiSianntiauHashMap["Á"] = "A2"
        sUnicodeToSoojiSianntiauHashMap["À"] = "A3"
        sUnicodeToSoojiSianntiauHashMap["Â"] = "A5"
        sUnicodeToSoojiSianntiauHashMap["Ā"] = "A7"
        sUnicodeToSoojiSianntiauHashMap["A̍"] = "A8"
        sUnicodeToSoojiSianntiauHashMap["Ă"] = "A9"

        // a
        sUnicodeToSoojiSianntiauHashMap["á"] = "a2"
        sUnicodeToSoojiSianntiauHashMap["à"] = "a3"
        sUnicodeToSoojiSianntiauHashMap["â"] = "a5"
        sUnicodeToSoojiSianntiauHashMap["ā"] = "a7"
        sUnicodeToSoojiSianntiauHashMap["a̍"] = "a8"
        sUnicodeToSoojiSianntiauHashMap["ă"] = "a9"

        // I
        sUnicodeToSoojiSianntiauHashMap["Í"] = "I2"
        sUnicodeToSoojiSianntiauHashMap["Ì"] = "I3"
        sUnicodeToSoojiSianntiauHashMap["Î"] = "I5"
        sUnicodeToSoojiSianntiauHashMap["Ī"] = "I7"
        sUnicodeToSoojiSianntiauHashMap["I̍"] = "I8"
        sUnicodeToSoojiSianntiauHashMap["Ĭ"] = "I9"

        // i
        sUnicodeToSoojiSianntiauHashMap["í"] = "i2"
        sUnicodeToSoojiSianntiauHashMap["ì"] = "i3"
        sUnicodeToSoojiSianntiauHashMap["î"] = "i5"
        sUnicodeToSoojiSianntiauHashMap["ī"] = "i7"
        sUnicodeToSoojiSianntiauHashMap["i̍"] = "i8"
        sUnicodeToSoojiSianntiauHashMap["ĭ"] = "i9"

        // U
        sUnicodeToSoojiSianntiauHashMap["Ú"] = "U2"
        sUnicodeToSoojiSianntiauHashMap["Ù"] = "U3"
        sUnicodeToSoojiSianntiauHashMap["Û"] = "U5"
        sUnicodeToSoojiSianntiauHashMap["Ū"] = "U7"
        sUnicodeToSoojiSianntiauHashMap["U̍"] = "U8"
        sUnicodeToSoojiSianntiauHashMap["Ŭ"] = "U9"

        // u
        sUnicodeToSoojiSianntiauHashMap["ú"] = "u2"
        sUnicodeToSoojiSianntiauHashMap["ù"] = "u3"
        sUnicodeToSoojiSianntiauHashMap["û"] = "u5"
        sUnicodeToSoojiSianntiauHashMap["ū"] = "u7"
        sUnicodeToSoojiSianntiauHashMap["u̍"] = "u8"
        sUnicodeToSoojiSianntiauHashMap["ŭ"] = "u9"

        // Ṳ (Kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
        sUnicodeToSoojiSianntiauHashMap["Ṳ́"] = "Ṳ2"
        sUnicodeToSoojiSianntiauHashMap["Ṳ̀"] = "Ṳ3"
        sUnicodeToSoojiSianntiauHashMap["Ṳ̂"] = "Ṳ5"
        sUnicodeToSoojiSianntiauHashMap["Ṳ̄"] = "Ṳ7"
        sUnicodeToSoojiSianntiauHashMap["Ṳ̍"] = "Ṳ8"
        sUnicodeToSoojiSianntiauHashMap["Ṳ̆"] = "Ṳ9"

        // ṳ (Kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
        sUnicodeToSoojiSianntiauHashMap["ṳ́"] = "ṳ2"
        sUnicodeToSoojiSianntiauHashMap["ṳ̀"] = "ṳ3"
        sUnicodeToSoojiSianntiauHashMap["ṳ̂"] = "ṳ5"
        sUnicodeToSoojiSianntiauHashMap["ṳ̄"] = "ṳ7"
        sUnicodeToSoojiSianntiauHashMap["ṳ̍"] = "ṳ8"
        sUnicodeToSoojiSianntiauHashMap["ṳ̆"] = "ṳ9"

        // E
        sUnicodeToSoojiSianntiauHashMap["É"] = "E2"
        sUnicodeToSoojiSianntiauHashMap["È"] = "E3"
        sUnicodeToSoojiSianntiauHashMap["Ê"] = "E5"
        sUnicodeToSoojiSianntiauHashMap["Ē"] = "E7"
        sUnicodeToSoojiSianntiauHashMap["E̍"] = "E8"
        sUnicodeToSoojiSianntiauHashMap["Ĕ"] = "E9"

        // e
        sUnicodeToSoojiSianntiauHashMap["é"] = "e2"
        sUnicodeToSoojiSianntiauHashMap["è"] = "e3"
        sUnicodeToSoojiSianntiauHashMap["ê"] = "e5"
        sUnicodeToSoojiSianntiauHashMap["ē"] = "e7"
        sUnicodeToSoojiSianntiauHashMap["e̍"] = "e8"
        sUnicodeToSoojiSianntiauHashMap["ĕ"] = "e9"

        // O
        sUnicodeToSoojiSianntiauHashMap["Ó"] = "O2"
        sUnicodeToSoojiSianntiauHashMap["Ò"] = "O3"
        sUnicodeToSoojiSianntiauHashMap["Ô"] = "O5"
        sUnicodeToSoojiSianntiauHashMap["Ō"] = "O7"
        sUnicodeToSoojiSianntiauHashMap["O̍"] = "O8"
        sUnicodeToSoojiSianntiauHashMap["Ŏ"] = "O9"

        // o
        sUnicodeToSoojiSianntiauHashMap["ó"] = "o2"
        sUnicodeToSoojiSianntiauHashMap["ò"] = "o3"
        sUnicodeToSoojiSianntiauHashMap["ô"] = "o5"
        sUnicodeToSoojiSianntiauHashMap["ō"] = "o7"
        sUnicodeToSoojiSianntiauHashMap["o̍"] = "o8"
        sUnicodeToSoojiSianntiauHashMap["ŏ"] = "o9"

        // O̤ (Kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
        sUnicodeToSoojiSianntiauHashMap["Ó̤"] = "O̤2"
        sUnicodeToSoojiSianntiauHashMap["Ò̤"] = "O̤3"
        sUnicodeToSoojiSianntiauHashMap["Ô̤"] = "O̤5"
        sUnicodeToSoojiSianntiauHashMap["Ō̤"] = "O̤7"
        sUnicodeToSoojiSianntiauHashMap["O̤̍̍"] = "O̤8"
        sUnicodeToSoojiSianntiauHashMap["Ŏ̤"] = "O̤9"

        // o̤ (Kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
        sUnicodeToSoojiSianntiauHashMap["ó̤"] = "o̤2"
        sUnicodeToSoojiSianntiauHashMap["ò̤"] = "o̤3"
        sUnicodeToSoojiSianntiauHashMap["ô̤"] = "o̤5"
        sUnicodeToSoojiSianntiauHashMap["ō̤"] = "o̤7"
        sUnicodeToSoojiSianntiauHashMap["o̤̍̍"] = "o̤8"
        sUnicodeToSoojiSianntiauHashMap["ŏ̤"] = "o̤9"

        // O͘
        sUnicodeToSoojiSianntiauHashMap["Ó\u0358"] = "O\u03582"
        sUnicodeToSoojiSianntiauHashMap["Ò\u0358"] = "O\u03583"
        sUnicodeToSoojiSianntiauHashMap["Ô\u0358"] = "O\u03585"
        sUnicodeToSoojiSianntiauHashMap["Ō\u0358"] = "O\u03587"
        sUnicodeToSoojiSianntiauHashMap["O̍\u0358"] = "O\u03588"
        sUnicodeToSoojiSianntiauHashMap["Ŏ\u0358"] = "O\u03589"

        // o͘
        sUnicodeToSoojiSianntiauHashMap["ó\u0358"] = "o\u03582"
        sUnicodeToSoojiSianntiauHashMap["ò\u0358"] = "o\u03583"
        sUnicodeToSoojiSianntiauHashMap["ô\u0358"] = "o\u03585"
        sUnicodeToSoojiSianntiauHashMap["ō\u0358"] = "o\u03587"
        sUnicodeToSoojiSianntiauHashMap["o̍\u0358"] = "o\u03588"
        sUnicodeToSoojiSianntiauHashMap["ŏ\u0358"] = "o\u03589"

        // M
        sUnicodeToSoojiSianntiauHashMap["Ḿ"] = "M2"
        sUnicodeToSoojiSianntiauHashMap["M̀"] = "M3"
        sUnicodeToSoojiSianntiauHashMap["M̂"] = "M5"
        sUnicodeToSoojiSianntiauHashMap["M̄"] = "M7"
        sUnicodeToSoojiSianntiauHashMap["M̍"] = "M8"
        sUnicodeToSoojiSianntiauHashMap["M̆"] = "M9"

        // m
        sUnicodeToSoojiSianntiauHashMap["ḿ"] = "m2"
        sUnicodeToSoojiSianntiauHashMap["m̀"] = "m3"
        sUnicodeToSoojiSianntiauHashMap["m̂"] = "m5"
        sUnicodeToSoojiSianntiauHashMap["m̄"] = "m7"
        sUnicodeToSoojiSianntiauHashMap["m̍"] = "m8"
        sUnicodeToSoojiSianntiauHashMap["m̆"] = "m9"

        // N
        sUnicodeToSoojiSianntiauHashMap["Ń"] = "N2"
        sUnicodeToSoojiSianntiauHashMap["Ǹ"] = "N3"
        sUnicodeToSoojiSianntiauHashMap["N̂"] = "N5"
        sUnicodeToSoojiSianntiauHashMap["N̄"] = "N7"
        sUnicodeToSoojiSianntiauHashMap["N̍"] = "N8"
        sUnicodeToSoojiSianntiauHashMap["N̋"] = "N9"

        // n
        sUnicodeToSoojiSianntiauHashMap["ń"] = "n2"
        sUnicodeToSoojiSianntiauHashMap["ǹ"] = "n3"
        sUnicodeToSoojiSianntiauHashMap["n̂"] = "n5"
        sUnicodeToSoojiSianntiauHashMap["n̄"] = "n7"
        sUnicodeToSoojiSianntiauHashMap["n̍"] = "n8"
        sUnicodeToSoojiSianntiauHashMap["n̋"] = "n9"

        // Ng
        sUnicodeToSoojiSianntiauHashMap["Ńg"] = "Ng2"
        sUnicodeToSoojiSianntiauHashMap["Ǹg"] = "Ng3"
        sUnicodeToSoojiSianntiauHashMap["N̂g"] = "Ng5"
        sUnicodeToSoojiSianntiauHashMap["N̄g"] = "Ng7"
        sUnicodeToSoojiSianntiauHashMap["N̍g"] = "Ng8"
        sUnicodeToSoojiSianntiauHashMap["N̆g"] = "Ng9"

        // NG
        sUnicodeToSoojiSianntiauHashMap["ŃG"] = "NG2"
        sUnicodeToSoojiSianntiauHashMap["ǸG"] = "NG3"
        sUnicodeToSoojiSianntiauHashMap["N̂G"] = "NG5"
        sUnicodeToSoojiSianntiauHashMap["N̄G"] = "NG7"
        sUnicodeToSoojiSianntiauHashMap["N̍G"] = "NG8"
        sUnicodeToSoojiSianntiauHashMap["N̆G"] = "NG9"

        // ng
        sUnicodeToSoojiSianntiauHashMap["ńg"] = "ng2"
        sUnicodeToSoojiSianntiauHashMap["ǹg"] = "ng3"
        sUnicodeToSoojiSianntiauHashMap["n̂g"] = "ng5"
        sUnicodeToSoojiSianntiauHashMap["n̄g"] = "ng7"
        sUnicodeToSoojiSianntiauHashMap["n̍g"] = "ng8"
        sUnicodeToSoojiSianntiauHashMap["n̆g"] = "ng9"

        for ((key, value) in sUnicodeToSoojiSianntiauHashMap) {
            sSoojiSianntiauToUnicodeHashMap[value] = key
        }
    }
}
