package tw.taibunkesimi.chhoetaigi.database.dicts.tjpehoe

import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.chhoetaigi.database.ChhoeTaigiDatabaseOutputSettings
import tw.taibunkesimi.chhoetaigi.database.dicts.tjpehoe.entry.TJPehoeDictOutEntry
import tw.taibunkesimi.chhoetaigi.database.dicts.tjpehoe.entry.TJPehoeDictSrcEntry
import tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFix
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFixType
import tw.taibunkesimi.util.io.CsvIO
import tw.taibunkesimi.util.io.XlsxIO
import java.util.*
import kotlin.collections.ArrayList

object TJPehoeDictProcessor {
    private const val SRC_FILENAME = "TJPehoeSiosutian20201212.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigiBookIndex_TJTaigiPehoeSioSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TJPehoeDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "tj", true)

        val dictArray = ArrayList<TJPehoeDictSrcEntry>()

        for (recordColumnArrayList in readXlsxDictArrayList) {
            val dictEntry = TJPehoeDictSrcEntry()

            dictEntry.pojInput = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                recordColumnArrayList[1],
                EnumSet.of(PojInputFixType.IR_KAI_CHO_UR, PojInputFixType.ER_KAI_CHO_OR)
            )

            dictEntry.pojInputOther = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                recordColumnArrayList[2],
                EnumSet.of(PojInputFixType.IR_KAI_CHO_UR, PojInputFixType.ER_KAI_CHO_OR)
            )

            dictEntry.pageNumber = recordColumnArrayList[3]

            dictArray.add(dictEntry)

//            if (dictEntry.pojDialect.contains("r")) {
//                println("srcEntry.pojDialect = ${dictEntry.pojDialect}")
//            }
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TJPehoeDictSrcEntry>): List<TJPehoeDictOutEntry> {
        val formattedDictArray = ArrayList<TJPehoeDictOutEntry>()

        var index = 1

        for (srcEntry: TJPehoeDictSrcEntry in dictArray) {
            val outEntry = TJPehoeDictOutEntry()

            outEntry.id = index.toString()
            index++

            val fixedPojInput = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                srcEntry.pojInput.trim(),
                EnumSet.of(PojInputFixType.ONN_SIA_CHO_OONN)
            )
            val fixedPojInputOther = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                srcEntry.pojInputOther.trim(),
                EnumSet.of(PojInputFixType.ONN_SIA_CHO_OONN)
            )

            outEntry.pojInput = fixedPojInput
            outEntry.pojInputOther = fixedPojInputOther
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.pojInput)
            outEntry.pojUnicodeOther = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.pojInputOther)

            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(outEntry.pojInput)
            outEntry.kipInputOther = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(outEntry.pojInputOther)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInput)
            outEntry.kipUnicodeOther = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInputOther)

            outEntry.pageNumber = srcEntry.pageNumber.trim()
            outEntry.storeLink = ""

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TJPehoeDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TJPehoeDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojUnicodeOther.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputOther.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipUnicodeOther.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }
            outEntry.kipInputOther.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }
            outEntry.storeLink.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path =
            ChhoeTaigiDatabaseOutputSettings.SAVE_FOLDER_DATABASE + ChhoeTaigiDatabaseOutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
            "id",

            "poj_unicode",
            "poj_unicode_other",
            "poj_input",
            "poj_input_other",

            "kip_unicode",
            "kip_unicode_other",
            "kip_input",
            "kip_input_other",

            "page_number",
            "store_link"
        )

        CsvIO.write(path, dict, csvFormat)
    }
}