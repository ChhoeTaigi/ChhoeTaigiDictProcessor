package tw.taibunkesimi.lib.lomajichoanoann.unicode.siannho

import java.util.*

object KipSiannho {
    // <kip_unicode, kip_tonenumber>
    val sUnicodeToSoojiSianntiauHashMap: HashMap<String, String> = HashMap()

    // <kip_tonenumber, kip_unicode>
    val sSoojiSianntiauToUnicodeHashMap: HashMap<String, String> = HashMap()

    init {
        // A
        sUnicodeToSoojiSianntiauHashMap["Á"] = "A2"
        sUnicodeToSoojiSianntiauHashMap["À"] = "A3"
        sUnicodeToSoojiSianntiauHashMap["Â"] = "A5"
        sUnicodeToSoojiSianntiauHashMap["Ā"] = "A7"
        sUnicodeToSoojiSianntiauHashMap["A̍"] = "A8"
        sUnicodeToSoojiSianntiauHashMap["A̋"] = "A9"

        // a
        sUnicodeToSoojiSianntiauHashMap["á"] = "a2"
        sUnicodeToSoojiSianntiauHashMap["à"] = "a3"
        sUnicodeToSoojiSianntiauHashMap["â"] = "a5"
        sUnicodeToSoojiSianntiauHashMap["ā"] = "a7"
        sUnicodeToSoojiSianntiauHashMap["a̍"] = "a8"
        sUnicodeToSoojiSianntiauHashMap["a̋"] = "a9"

        // I
        sUnicodeToSoojiSianntiauHashMap["Í"] = "I2"
        sUnicodeToSoojiSianntiauHashMap["Ì"] = "I3"
        sUnicodeToSoojiSianntiauHashMap["Î"] = "I5"
        sUnicodeToSoojiSianntiauHashMap["Ī"] = "I7"
        sUnicodeToSoojiSianntiauHashMap["I̍"] = "I8"
        sUnicodeToSoojiSianntiauHashMap["I̋"] = "I9"

        // i
        sUnicodeToSoojiSianntiauHashMap["í"] = "i2"
        sUnicodeToSoojiSianntiauHashMap["ì"] = "i3"
        sUnicodeToSoojiSianntiauHashMap["î"] = "i5"
        sUnicodeToSoojiSianntiauHashMap["ī"] = "i7"
        sUnicodeToSoojiSianntiauHashMap["i̍"] = "i8"
        sUnicodeToSoojiSianntiauHashMap["i̋"] = "i9"

        // U
        sUnicodeToSoojiSianntiauHashMap["Ú"] = "U2"
        sUnicodeToSoojiSianntiauHashMap["Ù"] = "U3"
        sUnicodeToSoojiSianntiauHashMap["Û"] = "U5"
        sUnicodeToSoojiSianntiauHashMap["Ū"] = "U7"
        sUnicodeToSoojiSianntiauHashMap["U̍"] = "U8"
        sUnicodeToSoojiSianntiauHashMap["Ű"] = "U9"

        // u
        sUnicodeToSoojiSianntiauHashMap["ú"] = "u2"
        sUnicodeToSoojiSianntiauHashMap["ù"] = "u3"
        sUnicodeToSoojiSianntiauHashMap["û"] = "u5"
        sUnicodeToSoojiSianntiauHashMap["ū"] = "u7"
        sUnicodeToSoojiSianntiauHashMap["u̍"] = "u8"
        sUnicodeToSoojiSianntiauHashMap["ű"] = "u9"

        // E
        sUnicodeToSoojiSianntiauHashMap["É"] = "E2"
        sUnicodeToSoojiSianntiauHashMap["È"] = "E3"
        sUnicodeToSoojiSianntiauHashMap["Ê"] = "E5"
        sUnicodeToSoojiSianntiauHashMap["Ē"] = "E7"
        sUnicodeToSoojiSianntiauHashMap["E̍"] = "E8"
        sUnicodeToSoojiSianntiauHashMap["E̋"] = "E9"

        // e
        sUnicodeToSoojiSianntiauHashMap["é"] = "e2"
        sUnicodeToSoojiSianntiauHashMap["è"] = "e3"
        sUnicodeToSoojiSianntiauHashMap["ê"] = "e5"
        sUnicodeToSoojiSianntiauHashMap["ē"] = "e7"
        sUnicodeToSoojiSianntiauHashMap["e̍"] = "e8"
        sUnicodeToSoojiSianntiauHashMap["e̋"] = "e9"

        // O
        sUnicodeToSoojiSianntiauHashMap["Ó"] = "O2"
        sUnicodeToSoojiSianntiauHashMap["Ò"] = "O3"
        sUnicodeToSoojiSianntiauHashMap["Ô"] = "O5"
        sUnicodeToSoojiSianntiauHashMap["Ō"] = "O7"
        sUnicodeToSoojiSianntiauHashMap["O̍"] = "O8"
        sUnicodeToSoojiSianntiauHashMap["Ő"] = "O9"

        // o
        sUnicodeToSoojiSianntiauHashMap["ó"] = "o2"
        sUnicodeToSoojiSianntiauHashMap["ò"] = "o3"
        sUnicodeToSoojiSianntiauHashMap["ô"] = "o5"
        sUnicodeToSoojiSianntiauHashMap["ō"] = "o7"
        sUnicodeToSoojiSianntiauHashMap["o̍"] = "o8"
        sUnicodeToSoojiSianntiauHashMap["ő"] = "o9"

        // Oo
        sUnicodeToSoojiSianntiauHashMap["Óo"] = "Oo2"
        sUnicodeToSoojiSianntiauHashMap["Òo"] = "Oo3"
        sUnicodeToSoojiSianntiauHashMap["Ôo"] = "Oo5"
        sUnicodeToSoojiSianntiauHashMap["Ōo"] = "Oo7"
        sUnicodeToSoojiSianntiauHashMap["O̍o"] = "Oo8"
        sUnicodeToSoojiSianntiauHashMap["Őo"] = "Oo9"

        // OO
        sUnicodeToSoojiSianntiauHashMap["ÓO"] = "OO2"
        sUnicodeToSoojiSianntiauHashMap["ÒO"] = "OO3"
        sUnicodeToSoojiSianntiauHashMap["ÔO"] = "OO5"
        sUnicodeToSoojiSianntiauHashMap["ŌO"] = "OO7"
        sUnicodeToSoojiSianntiauHashMap["O̍O"] = "OO8"
        sUnicodeToSoojiSianntiauHashMap["ŐO"] = "OO9"

        // o
        sUnicodeToSoojiSianntiauHashMap["óo"] = "oo2"
        sUnicodeToSoojiSianntiauHashMap["òo"] = "oo3"
        sUnicodeToSoojiSianntiauHashMap["ôo"] = "oo5"
        sUnicodeToSoojiSianntiauHashMap["ōo"] = "oo7"
        sUnicodeToSoojiSianntiauHashMap["o̍o"] = "oo8"
        sUnicodeToSoojiSianntiauHashMap["őo"] = "oo9"

        // M
        sUnicodeToSoojiSianntiauHashMap["Ḿ"] = "M2"
        sUnicodeToSoojiSianntiauHashMap["M̀"] = "M3"
        sUnicodeToSoojiSianntiauHashMap["M̂"] = "M5"
        sUnicodeToSoojiSianntiauHashMap["M̄"] = "M7"
        sUnicodeToSoojiSianntiauHashMap["M̍"] = "M8"
        sUnicodeToSoojiSianntiauHashMap["M̋"] = "M9"

        // m
        sUnicodeToSoojiSianntiauHashMap["ḿ"] = "m2"
        sUnicodeToSoojiSianntiauHashMap["m̀"] = "m3"
        sUnicodeToSoojiSianntiauHashMap["m̂"] = "m5"
        sUnicodeToSoojiSianntiauHashMap["m̄"] = "m7"
        sUnicodeToSoojiSianntiauHashMap["m̍"] = "m8"
        sUnicodeToSoojiSianntiauHashMap["m̋"] = "m9"

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
        sUnicodeToSoojiSianntiauHashMap["N̋g"] = "Ng9"

        // NG
        sUnicodeToSoojiSianntiauHashMap["ŃG"] = "NG2"
        sUnicodeToSoojiSianntiauHashMap["ǸG"] = "NG3"
        sUnicodeToSoojiSianntiauHashMap["N̂G"] = "NG5"
        sUnicodeToSoojiSianntiauHashMap["N̄G"] = "NG7"
        sUnicodeToSoojiSianntiauHashMap["N̍G"] = "NG8"
        sUnicodeToSoojiSianntiauHashMap["N̋G"] = "NG9"

        // ng
        sUnicodeToSoojiSianntiauHashMap["ńg"] = "ng2"
        sUnicodeToSoojiSianntiauHashMap["ǹg"] = "ng3"
        sUnicodeToSoojiSianntiauHashMap["n̂g"] = "ng5"
        sUnicodeToSoojiSianntiauHashMap["n̄g"] = "ng7"
        sUnicodeToSoojiSianntiauHashMap["n̍g"] = "ng8"
        sUnicodeToSoojiSianntiauHashMap["n̋g"] = "ng9"

        for ((key, value) in sUnicodeToSoojiSianntiauHashMap) {
            sSoojiSianntiauToUnicodeHashMap[value] = key
        }
    }
}
