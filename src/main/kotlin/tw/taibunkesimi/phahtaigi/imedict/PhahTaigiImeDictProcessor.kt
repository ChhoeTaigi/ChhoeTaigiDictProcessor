package tw.taibunkesimi.phahtaigi.imedict

import tw.taibunkesimi.phahtaigi.imedict.datamodel.ImeDictItem
import tw.taibunkesimi.util.SplitStringByCodePoint
import tw.taibunkesimi.util.io.CsvIO
import java.util.ArrayList


object PhahTaigiDatabaseSettings {
    const val SAVE_FOLDER_DATABASE = "./output_database/20210103_173026/"
}

var imeDict = ArrayList<ImeDictItem>()

fun main(args: Array<String>) {
    generateDict()
}

fun generateDict() {
    generateKipDict()
    sortAll()
}

fun sortAll() {
    imeDict.sortBy { it.hanji }
    imeDict.forEach { println(it.hanji + "=" + it.poj) }
}

fun generateKipDict() {
    val dict: ArrayList<ArrayList<String>> = CsvIO.read(PhahTaigiDatabaseSettings.SAVE_FOLDER_DATABASE + "ChhoeTaigi_KauiokpooTaigiSutian" + ".csv", true)
    for (sutiauRowArray in dict) {
        val wordId = sutiauRowArray[0]

        // skip Sio̍k-gān-gí
        if (wordId.toInt() >= 60000) {
            continue
        }

        val poj = sutiauRowArray[1]
        val hanji = sutiauRowArray[9]

        val pojSplits = poj.split("/")
        for (pojBokangKonghoat in pojSplits) {
            if (isHanjiMatchPojImchatCount(pojBokangKonghoat, hanji)) {
                val imeDictItem = ImeDictItem()
                // TODO: Add other columns
                imeDictItem.poj = pojBokangKonghoat
                imeDictItem.hanji = hanji

                // fix kooHanji Toasia
                if ((imeDictItem.hanji.codePoints().count() == 1L)) {
                    imeDictItem.poj = imeDictItem.poj.toLowerCase()
                }

                addToImeDict(imeDictItem = imeDictItem, checkExist = true)
                
                processToimchatKooHanji(imeDictItem = imeDictItem)
            }
        }
    }
}


fun addToImeDict(imeDictItem: ImeDictItem, checkExist: Boolean) {
    if ((checkExist)) {
        for (imeDictItemExist in imeDict) {
            if ((imeDictItemExist.poj == imeDictItem.poj && imeDictItemExist.hanji == imeDictItem.hanji)) {
                return
            }
        }
    }
    imeDict.add(imeDictItem)
}

fun processToimchatKooHanji(imeDictItem: ImeDictItem) {
    val hanjiSplits = SplitStringByCodePoint.split(imeDictItem.hanji)
    val hanjCount = hanjiSplits.count()
    if (hanjCount > 1) {
        return
    }

    val pojImchatSplits = imeDictItem.poj.split("[ -]".toRegex()).filter { s -> s != "" }
    if (pojImchatSplits.size != hanjiSplits.size) {
        println("(processToimchatKooHanji) Hanji not match Poj Imchat: \"" + imeDictItem.hanji + "\" count:" + hanjiSplits.size + ", \"" + imeDictItem.poj + "\" count:" + pojImchatSplits.size)
    }

    for (i in 0 until hanjCount) {
        val kooHanji = hanjiSplits[i]
        val pojImchat = pojImchatSplits[i].toLowerCase()

        val imeDictItem = ImeDictItem()
        // TODO: Add other columns
        imeDictItem.poj = pojImchat
        imeDictItem.hanji = kooHanji
        addToImeDict(imeDictItem, true)
    }
}

fun isHanjiMatchPojImchatCount(poj: String, hanji: String): Boolean {
    val pojImchatSplits = poj.split("[ -]".toRegex()).filter { s -> s != "" }
    val hanjiCount = hanji.codePoints().count().toInt()
    if (pojImchatSplits.size != hanjiCount) {
        println("Hanji not match Poj Imchat: \"" + hanji + "\" count:" + hanjiCount + ", \"" + poj + "\" count:" + pojImchatSplits.size)
    }
    return hanjiCount == pojImchatSplits.size
}
