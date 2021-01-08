package tw.taibunkesimi.phahtaigi.imedict

import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.phahtaigi.imedict.PhahTaigiDatabaseSettings.customDict
import tw.taibunkesimi.phahtaigi.imedict.comparator.ImeDictItemKipComparator
import tw.taibunkesimi.phahtaigi.imedict.comparator.ImeDictItemPojComparator
import tw.taibunkesimi.phahtaigi.imedict.datamodel.ImeDictItem
import tw.taibunkesimi.util.SplitStringByCodePoint
import tw.taibunkesimi.util.extension.isNumeric
import tw.taibunkesimi.util.io.CsvIO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object PhahTaigiDatabaseSettings {
    const val SRC_FOLDER_DATABASE = "./output_database/20210103_173026/"

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
    val timestamp = dateFormatter.format(LocalDateTime.now())
    const val SAVE_FOLDER_DATABASE = "./output_imedict/"

    const val SAVE_FILENAME_PATH = "/PhahTaigi_ImeDict.csv"

    val customDict: ArrayList<ArrayList<String>> = arrayListOf(
        arrayListOf("Tâi-oân", "台灣"),
        arrayListOf("Tâi-oân-lâng", "台灣人"),
        arrayListOf("Tâi-oân-ōe", "台灣話"),
        arrayListOf("Tâi-gí", "台語"),
        arrayListOf("Tâi-gí-bûn", "台語文"),
        arrayListOf("Tâi-bûn", "台文"),
        arrayListOf("Pe̍h-ōe-jī", "白話字"),
        arrayListOf("POJ", "白話字"),
        arrayListOf("Lô-má-jī", "羅馬字"),
        arrayListOf("LMJ", "羅馬字"),
        arrayListOf("su-ji̍p-hoat", "輸入法"),
        arrayListOf("Hàn-lô", "漢羅"),
        arrayListOf("Lô-hàn", "羅漢")
    )
}

object LomajiImchatStartWithJiboRegex {
    val POJ = "(ph|p|m|b|th|t|n|l|kh|k|ng|g|h|chh|ch|s|j|a|i|u|oo|o|e)".toRegex(RegexOption.IGNORE_CASE)
    val KIP = "(ph|p|m|b|tsh|ts|th|t|n|l|kh|k|ng|g|h|s|j|a|i|u|oo|o|e)".toRegex(RegexOption.IGNORE_CASE)
}

var imeDict = ArrayList<ImeDictItem>()

fun main(args: Array<String>) {
    generateDict()

    println("ImeDict total count: ${imeDict.count()}")

    writeResult()
}

private fun generateDict() {
    println("generateCustom()")
    generateCustom()
    println("generateKip()")
    generateKip()
    println("generateTaijitToaSutian()")
    generateTaijitToaSutian()

    println("generatePriorities()")
    generatePriorities()
}

private fun generateCustom() {
    for (customDictArray in customDict) {
        val poj = customDictArray[0]
        val hanji = customDictArray[1]

        val imeDictItem = ImeDictItem()
        imeDictItem.poj = poj
        imeDictItem.hanji = hanji
        imeDictItem.srcDict = 0

        addToImeDict(imeDictItem)
    }
}

private fun generateKip() {
    val dict: ArrayList<ArrayList<String>> =
        CsvIO.read(PhahTaigiDatabaseSettings.SRC_FOLDER_DATABASE + "ChhoeTaigi_KauiokpooTaigiSutian" + ".csv", true)
    for (sutiauRowArray in dict) {
        val wordId = sutiauRowArray[0].trim()

        // skip Sio̍k-gān-gí
        if (wordId.toInt() >= 60000) {
            continue
        }

        var poj = sutiauRowArray[1].trim()
        var hanji = sutiauRowArray[9].trim()
        val hanjiCount = hanji.codePoints().count().toInt()

        // start with "--" fix
        if (poj.startsWith("--")) {
            poj = poj.substring(2)
        }

        // 台 fix
        if (hanjiCount == 1 && hanji == "台") {
            hanji = hanji.replace("台", "臺")
        } else if (hanjiCount > 1) {
            hanji = hanji.replace("臺", "台")
        }

        val pojSplits = poj.split("/").toMutableList().filter { s -> s != "" }

        for (pojBokangKonghoat in pojSplits) {
            if (isHanjiMatchPojImchatCount(pojBokangKonghoat, hanji)) {
                val imeDictItem = ImeDictItem()
                imeDictItem.poj = pojBokangKonghoat.trim()
                imeDictItem.hanji = hanji
                imeDictItem.srcDict = 1

                // fix kooHanji Toasia
                if ((imeDictItem.hanji.codePoints().count() == 1L)) {
                    imeDictItem.poj = imeDictItem.poj.toLowerCase()
                }

                addToImeDict(imeDictItem)

                processToimchatKooHanji(imeDictItem)
            }
        }
    }
}

private fun generateTaijitToaSutian() {
    val dict: ArrayList<ArrayList<String>> =
        CsvIO.read(PhahTaigiDatabaseSettings.SRC_FOLDER_DATABASE + "ChhoeTaigi_TaijitToaSutian" + ".csv", true)
    for (sutiauRowArray in dict) {
        val poj = sutiauRowArray[1].trim()
        val pojOther = sutiauRowArray[2].trim()
        val hanji = sutiauRowArray[5].trim()

        var pojSplits = poj.split("[/,]".toRegex()).toMutableList()
        var pojOtherSplits = pojOther.split("[/,]".toRegex()).toMutableList()

        // remove "(XXX)"
        pojSplits = removeBracketInfo(pojSplits)
        pojOtherSplits = removeBracketInfo(pojOtherSplits)

        val pojAll = ArrayList<String>()
        pojAll.addAll(pojSplits)
        pojAll.addAll(pojOtherSplits)

        for (pojBokangKonghoat in pojAll) {
            val imeDictItem = ImeDictItem()
            imeDictItem.poj = pojBokangKonghoat
            imeDictItem.hanji = ""
            imeDictItem.srcDict = 2

            addToImeDict(imeDictItem)
        }
    }
}

private fun removeBracketInfo(stringList: MutableList<String>): MutableList<String> {
    var fixStringList = stringList
    for (i in fixStringList.indices) {
        var fixString = fixStringList[i]
        while (true) {
            val indexOfLeftBracket = fixString.indexOf("(")
            if (indexOfLeftBracket < 0) {
                break
            }

            val indexOfRightBracket = fixString.indexOf(")")
            fixString = fixString.substring(0, indexOfLeftBracket) + fixString.substring(indexOfRightBracket + 1)
        }
        fixStringList[i] = fixString.trim()
    }
    fixStringList = fixStringList.filter { s -> s != "" }.toMutableList()
    return fixStringList
}

private fun addToImeDict(imeDictItem: ImeDictItem) {
    // check duplicate
    for (imeDictItemExist in imeDict) {
        if (imeDictItem.srcDict == 1 && imeDictItemExist.hanji == imeDictItem.hanji && imeDictItemExist.poj.equals(
                imeDictItem.poj,
                ignoreCase = true
            )
        ) {
            return
        }

        if (imeDictItemExist.poj == imeDictItem.poj && imeDictItem.srcDict > 1) {
            return
        }

        if (imeDictItemExist.hanji == imeDictItem.hanji && imeDictItemExist.poj == imeDictItem.poj) {
            return
        }
    }

    preprocessImeDictItem(imeDictItem)

    // skip chāi-lâi Tâi-oân Pe̍h-ōe-jī bô--ê
    if (imeDictItem.pojSujip.contains("(ur|or)".toRegex())) {
        return
    }

//    println("${imeDictItem.poj}:${imeDictItem.hanji}")

    imeDict.add(imeDictItem)
}

private fun preprocessImeDictItem(imeDictItem: ImeDictItem) {
    val pojRowInput = TaigiLomajiKuikuChoanoann.onlyPojUnicodeToPojInput(imeDictItem.poj)
    val kipRowUnput = TaigiLomajiKuikuChoanoann.onlyPojUnicodeToKipInput(imeDictItem.poj)

    val pojRowInputImchatSplits = pojRowInput.split("[ -]".toRegex()).filter { s -> s != "" }
    val kipRowUnputImchatSplits = kipRowUnput.split("[ -]".toRegex()).filter { s -> s != "" }

    imeDictItem.pojSujip = pojRowInputImchatSplits.joinToString("").toLowerCase()
    imeDictItem.pojSujipBoSooji = imeDictItem.pojSujip.replace("[0-9]".toRegex(), "")
    imeDictItem.pojSujipThauJibo = joinPojRowInputImchatFirstJibo(pojRowInputImchatSplits).toLowerCase()
    imeDictItem.pojPriorityString = generatePriorityString(pojRowInputImchatSplits)

    imeDictItem.kip = TaigiLomajiKuikuChoanoann.onlyPojUnicodeToKipUnicode(imeDictItem.poj)
    imeDictItem.kipSujip = kipRowUnputImchatSplits.joinToString("").toLowerCase()
    imeDictItem.kipSujipBoSooji = imeDictItem.kipSujip.replace("[0-9]".toRegex(), "")
    imeDictItem.kipSujipThauJibo = joinKipRowInputImchatFirstJibo(kipRowUnputImchatSplits).toLowerCase()
    imeDictItem.kipPriorityString = generatePriorityString(kipRowUnputImchatSplits)
}

private fun joinPojRowInputImchatFirstJibo(pojRowInputImchatSplits: List<String>): String {
    // skip koo imchat
    if (pojRowInputImchatSplits.size <= 1) {
        return ""
    }

    val stringBuilder = StringBuilder()

    for (imchat in pojRowInputImchatSplits) {
        val firstJibo = LomajiImchatStartWithJiboRegex.POJ.find(imchat)!!.value
        stringBuilder.append(firstJibo)
    }

    return stringBuilder.toString()
}

private fun joinKipRowInputImchatFirstJibo(kipRowInputImchatSplits: List<String>): String {
    // skip koo imchat
    if (kipRowInputImchatSplits.size <= 1) {
        return ""
    }

    val stringBuilder = StringBuilder()

    for (imchat in kipRowInputImchatSplits) {
        val firstJibo = LomajiImchatStartWithJiboRegex.KIP.find(imchat)!!.value
        stringBuilder.append(firstJibo)
    }

    return stringBuilder.toString()
}

private fun generatePriorityString(rowInputImchatSplits: List<String>): String {
    val stringBuilder = StringBuilder()

    for (imchat in rowInputImchatSplits) {
        val lastChar = imchat.substring(imchat.length - 1)
        if (lastChar.isNumeric()) {
            stringBuilder.append(imchat)
        } else {
            if (lastChar.matches("[ptkh]".toRegex())) {
                stringBuilder.append(imchat + "4")
            } else {
                stringBuilder.append(imchat + "1")
            }
        }
    }

    return stringBuilder.toString().toLowerCase()
}

private fun processToimchatKooHanji(imeDictItem: ImeDictItem) {
    val hanjiSplits = SplitStringByCodePoint.split(imeDictItem.hanji)
    val hanjCount = hanjiSplits.count()
    if (hanjCount == 1) {
        return
    }

    val pojImchatSplits = imeDictItem.poj.split("[ -]".toRegex()).filter { s -> s != "" }
    if (pojImchatSplits.size != hanjiSplits.size) {
        println("(processToimchatKooHanji) Hanji not match Poj Imchat: \"${imeDictItem.hanji}\" count:${hanjiSplits.size}, \"${imeDictItem.poj}\" count:${pojImchatSplits.size}")
    }

    for (i in 0 until hanjCount) {
        val kooHanji = hanjiSplits[i]
        val pojImchat = pojImchatSplits[i].toLowerCase()

        val imeDictItem = ImeDictItem()
        imeDictItem.poj = pojImchat
        imeDictItem.hanji = kooHanji

        addToImeDict(imeDictItem)
    }
}

private fun isHanjiMatchPojImchatCount(poj: String, hanji: String): Boolean {
    val pojImchatSplits = poj.split("[ -]".toRegex()).filter { s -> s != "" }
    val hanjiCount = hanji.codePoints().count().toInt()
    if (pojImchatSplits.size != hanjiCount) {
        println("Hanji not match Poj Imchat: \"$hanji\" count:$hanjiCount, \"$poj\" count:${pojImchatSplits.size}")
    }
    return hanjiCount == pojImchatSplits.size
}

private fun generatePriorities() {
    // poj
    imeDict.sortWith(ImeDictItemPojComparator)
    for (i in 0 until imeDict.size) {
        imeDict[i].pojPriority = i + 1
        imeDict[i].wordId = i + 1
    }
    imeDict.forEach { println("${it.pojPriority}: ${it.pojPriorityString}: \"${it.hanji}\"=\"${it.poj}\"") }

    // kip
    imeDict.sortWith(ImeDictItemKipComparator)
    for (i in 0 until imeDict.size) {
        imeDict[i].kipPriority = i + 1
    }
//    imeDict.forEach { println("${it.kipPriority}: ${it.kipPriorityString}: ${it.hanji}=${it.kip}") }
}

private fun writeResult() {
    val dict: ArrayList<ArrayList<String>> = ArrayList()
    for (imeDictItem: ImeDictItem in imeDict) {
        val entryArray: ArrayList<String> = ArrayList()

        imeDictItem.wordId.let { entryArray.add(it.toString()) }

        imeDictItem.poj.let { entryArray.add(it) }
        imeDictItem.pojSujip.let { entryArray.add(it) }
        imeDictItem.pojSujipBoSooji.let { entryArray.add(it) }
        imeDictItem.pojSujipThauJibo.let { entryArray.add(it) }

        imeDictItem.kip.let { entryArray.add(it) }
        imeDictItem.kipSujip.let { entryArray.add(it) }
        imeDictItem.kipSujipBoSooji.let { entryArray.add(it) }
        imeDictItem.kipSujipThauJibo.let { entryArray.add(it) }

        imeDictItem.hanji.let { entryArray.add(it) }

        imeDictItem.pojPriority.let { entryArray.add(it.toString()) }
        imeDictItem.kipPriority.let { entryArray.add(it.toString()) }

        imeDictItem.srcDict.let { entryArray.add(it.toString()) }

        dict.add(entryArray)
    }

    val path =
        PhahTaigiDatabaseSettings.SAVE_FOLDER_DATABASE + PhahTaigiDatabaseSettings.timestamp + PhahTaigiDatabaseSettings.SAVE_FILENAME_PATH
    val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
        "wordId",

        "poj",
        "pojSujip",
        "pojSujipBoSooji",
        "pojSujipThauJibo",

        "kip",
        "kipSujip",
        "kipSujipBoSooji",
        "kipSujipThauJibo",

        "hanji",

        "pojPriority",
        "kipPriority",

        "srcDict"
    )

    CsvIO.write(path, dict, csvFormat)
}