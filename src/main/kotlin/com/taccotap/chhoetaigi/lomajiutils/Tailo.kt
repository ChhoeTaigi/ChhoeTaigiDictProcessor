package com.taccotap.chhoetaigi.lomajiutils

import java.util.*


object Tailo {

    // <tailo_unicode, tailo_number>
    val sTailoUnicodeToTailoNumberHashMap: HashMap<String, String> = HashMap()

    // <tailo_number, tailo_unicode>
    val sTailoNumberToTailoUnicodeHashMap: HashMap<String, String> = HashMap()

    init {
        // A
        sTailoUnicodeToTailoNumberHashMap["Á"] = "A2"
        sTailoUnicodeToTailoNumberHashMap["À"] = "A3"
        sTailoUnicodeToTailoNumberHashMap["Â"] = "A5"
        sTailoUnicodeToTailoNumberHashMap["Ā"] = "A7"
        sTailoUnicodeToTailoNumberHashMap["A̍"] = "A8"
        sTailoUnicodeToTailoNumberHashMap["A̋"] = "A9"

        // a
        sTailoUnicodeToTailoNumberHashMap["á"] = "a2"
        sTailoUnicodeToTailoNumberHashMap["à"] = "a3"
        sTailoUnicodeToTailoNumberHashMap["â"] = "a5"
        sTailoUnicodeToTailoNumberHashMap["ā"] = "a7"
        sTailoUnicodeToTailoNumberHashMap["a̍"] = "a8"
        sTailoUnicodeToTailoNumberHashMap["a̋"] = "a9"

        // I
        sTailoUnicodeToTailoNumberHashMap["Í"] = "I2"
        sTailoUnicodeToTailoNumberHashMap["Ì"] = "I3"
        sTailoUnicodeToTailoNumberHashMap["Î"] = "I5"
        sTailoUnicodeToTailoNumberHashMap["Ī"] = "I7"
        sTailoUnicodeToTailoNumberHashMap["I̍"] = "I8"
        sTailoUnicodeToTailoNumberHashMap["I̋"] = "I9"

        // i
        sTailoUnicodeToTailoNumberHashMap["í"] = "i2"
        sTailoUnicodeToTailoNumberHashMap["ì"] = "i3"
        sTailoUnicodeToTailoNumberHashMap["î"] = "i5"
        sTailoUnicodeToTailoNumberHashMap["ī"] = "i7"
        sTailoUnicodeToTailoNumberHashMap["i̍"] = "i8"
        sTailoUnicodeToTailoNumberHashMap["i̋"] = "i9"

        // U
        sTailoUnicodeToTailoNumberHashMap["Ú"] = "U2"
        sTailoUnicodeToTailoNumberHashMap["Ù"] = "U3"
        sTailoUnicodeToTailoNumberHashMap["Û"] = "U5"
        sTailoUnicodeToTailoNumberHashMap["Ū"] = "U7"
        sTailoUnicodeToTailoNumberHashMap["U̍"] = "U8"
        sTailoUnicodeToTailoNumberHashMap["Ű"] = "U9"

        // u
        sTailoUnicodeToTailoNumberHashMap["ú"] = "u2"
        sTailoUnicodeToTailoNumberHashMap["ù"] = "u3"
        sTailoUnicodeToTailoNumberHashMap["û"] = "u5"
        sTailoUnicodeToTailoNumberHashMap["ū"] = "u7"
        sTailoUnicodeToTailoNumberHashMap["u̍"] = "u8"
        sTailoUnicodeToTailoNumberHashMap["ű"] = "u9"

        // E
        sTailoUnicodeToTailoNumberHashMap["É"] = "E2"
        sTailoUnicodeToTailoNumberHashMap["È"] = "E3"
        sTailoUnicodeToTailoNumberHashMap["Ê"] = "E5"
        sTailoUnicodeToTailoNumberHashMap["Ē"] = "E7"
        sTailoUnicodeToTailoNumberHashMap["E̍"] = "E8"
        sTailoUnicodeToTailoNumberHashMap["E̋"] = "E9"

        // e
        sTailoUnicodeToTailoNumberHashMap["é"] = "e2"
        sTailoUnicodeToTailoNumberHashMap["è"] = "e3"
        sTailoUnicodeToTailoNumberHashMap["ê"] = "e5"
        sTailoUnicodeToTailoNumberHashMap["ē"] = "e7"
        sTailoUnicodeToTailoNumberHashMap["e̍"] = "e8"
        sTailoUnicodeToTailoNumberHashMap["e̋"] = "e9"

        // O
        sTailoUnicodeToTailoNumberHashMap["Ó"] = "O2"
        sTailoUnicodeToTailoNumberHashMap["Ò"] = "O3"
        sTailoUnicodeToTailoNumberHashMap["Ô"] = "O5"
        sTailoUnicodeToTailoNumberHashMap["Ō"] = "O7"
        sTailoUnicodeToTailoNumberHashMap["O̍"] = "O8"
        sTailoUnicodeToTailoNumberHashMap["Ő"] = "O9"

        // o
        sTailoUnicodeToTailoNumberHashMap["ó"] = "o2"
        sTailoUnicodeToTailoNumberHashMap["ò"] = "o3"
        sTailoUnicodeToTailoNumberHashMap["ô"] = "o5"
        sTailoUnicodeToTailoNumberHashMap["ō"] = "o7"
        sTailoUnicodeToTailoNumberHashMap["o̍"] = "o8"
        sTailoUnicodeToTailoNumberHashMap["ő"] = "o9"

        // Oo
        sTailoUnicodeToTailoNumberHashMap["Óo"] = "Oo2"
        sTailoUnicodeToTailoNumberHashMap["Òo"] = "Oo3"
        sTailoUnicodeToTailoNumberHashMap["Ôo"] = "Oo5"
        sTailoUnicodeToTailoNumberHashMap["Ōo"] = "Oo7"
        sTailoUnicodeToTailoNumberHashMap["O̍o"] = "Oo8"
        sTailoUnicodeToTailoNumberHashMap["Őo"] = "Oo9"

        // OO
        sTailoUnicodeToTailoNumberHashMap["ÓO"] = "OO2"
        sTailoUnicodeToTailoNumberHashMap["ÒO"] = "OO3"
        sTailoUnicodeToTailoNumberHashMap["ÔO"] = "OO5"
        sTailoUnicodeToTailoNumberHashMap["ŌO"] = "OO7"
        sTailoUnicodeToTailoNumberHashMap["O̍O"] = "OO8"
        sTailoUnicodeToTailoNumberHashMap["ŐO"] = "OO9"

        // o
        sTailoUnicodeToTailoNumberHashMap["óo"] = "oo2"
        sTailoUnicodeToTailoNumberHashMap["òo"] = "oo3"
        sTailoUnicodeToTailoNumberHashMap["ôo"] = "oo5"
        sTailoUnicodeToTailoNumberHashMap["ōo"] = "oo7"
        sTailoUnicodeToTailoNumberHashMap["o̍o"] = "oo8"
        sTailoUnicodeToTailoNumberHashMap["őo"] = "oo9"

        // M
        sTailoUnicodeToTailoNumberHashMap["Ḿ"] = "M2"
        sTailoUnicodeToTailoNumberHashMap["M̀"] = "M3"
        sTailoUnicodeToTailoNumberHashMap["M̂"] = "M5"
        sTailoUnicodeToTailoNumberHashMap["M̄"] = "M7"
        sTailoUnicodeToTailoNumberHashMap["M̍"] = "M8"
        sTailoUnicodeToTailoNumberHashMap["M̋"] = "M9"

        // m
        sTailoUnicodeToTailoNumberHashMap["ḿ"] = "m2"
        sTailoUnicodeToTailoNumberHashMap["m̀"] = "m3"
        sTailoUnicodeToTailoNumberHashMap["m̂"] = "m5"
        sTailoUnicodeToTailoNumberHashMap["m̄"] = "m7"
        sTailoUnicodeToTailoNumberHashMap["m̍"] = "m8"
        sTailoUnicodeToTailoNumberHashMap["m̋"] = "m9"

        // N
        sTailoUnicodeToTailoNumberHashMap["Ń"] = "N2"
        sTailoUnicodeToTailoNumberHashMap["Ǹ"] = "N3"
        sTailoUnicodeToTailoNumberHashMap["N̂"] = "N5"
        sTailoUnicodeToTailoNumberHashMap["N̄"] = "N7"
        sTailoUnicodeToTailoNumberHashMap["N̍"] = "N8"
        sTailoUnicodeToTailoNumberHashMap["N̋"] = "N9"

        // n
        sTailoUnicodeToTailoNumberHashMap["ń"] = "n2"
        sTailoUnicodeToTailoNumberHashMap["ǹ"] = "n3"
        sTailoUnicodeToTailoNumberHashMap["n̂"] = "n5"
        sTailoUnicodeToTailoNumberHashMap["n̄"] = "n7"
        sTailoUnicodeToTailoNumberHashMap["n̍"] = "n8"
        sTailoUnicodeToTailoNumberHashMap["n̋"] = "n9"

        // Ng
        sTailoUnicodeToTailoNumberHashMap["Ńg"] = "Ng2"
        sTailoUnicodeToTailoNumberHashMap["Ǹg"] = "Ng3"
        sTailoUnicodeToTailoNumberHashMap["N̂g"] = "Ng5"
        sTailoUnicodeToTailoNumberHashMap["N̄g"] = "Ng7"
        sTailoUnicodeToTailoNumberHashMap["N̍g"] = "Ng8"
        sTailoUnicodeToTailoNumberHashMap["N̋g"] = "Ng9"

        // NG
        sTailoUnicodeToTailoNumberHashMap["ŃG"] = "NG2"
        sTailoUnicodeToTailoNumberHashMap["ǸG"] = "NG3"
        sTailoUnicodeToTailoNumberHashMap["N̂G"] = "NG5"
        sTailoUnicodeToTailoNumberHashMap["N̄G"] = "NG7"
        sTailoUnicodeToTailoNumberHashMap["N̍G"] = "NG8"
        sTailoUnicodeToTailoNumberHashMap["N̋G"] = "NG9"

        // ng
        sTailoUnicodeToTailoNumberHashMap["ńg"] = "ng2"
        sTailoUnicodeToTailoNumberHashMap["ǹg"] = "ng3"
        sTailoUnicodeToTailoNumberHashMap["n̂g"] = "ng5"
        sTailoUnicodeToTailoNumberHashMap["n̄g"] = "ng7"
        sTailoUnicodeToTailoNumberHashMap["n̍g"] = "ng8"
        sTailoUnicodeToTailoNumberHashMap["n̋g"] = "ng9"

        for ((key, value) in sTailoUnicodeToTailoNumberHashMap) {
            sTailoNumberToTailoUnicodeHashMap[value] = key
        }
    }
}
