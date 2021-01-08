package tw.taibunkesimi.chhoetaigi.database.dicts.maryknoll

import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.chhoetaigi.database.ChhoeTaigiDatabaseOutputSettings
import tw.taibunkesimi.chhoetaigi.database.dicts.maryknoll.entry.MaryknollTaiEngDictOutEntry
import tw.taibunkesimi.chhoetaigi.database.dicts.maryknoll.entry.MaryknollTaiEngDictSrcEntry
import tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFix
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFixType
import tw.taibunkesimi.util.io.CsvIO
import tw.taibunkesimi.util.io.XlsxIO
import java.util.*
import kotlin.collections.ArrayList

object MaryknollTaiEngDictProcessor {
    private const val SRC_FILENAME = "Mkdictionary2013_fix20201201.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_MaryknollTaiengSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<MaryknollTaiEngDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "TD", true)

        val dictArray = ArrayList<MaryknollTaiEngDictSrcEntry>()
        for (recordColumnArrayList in readXlsDictArrayList) {
            val srcEntry = MaryknollTaiEngDictSrcEntry()

            srcEntry.id = recordColumnArrayList[0]

            var fixedPoj = recordColumnArrayList[1]
                .replace("::", "")  // remove "::" symbol for subcatogory
                .replace("*", "nn")  // fix POJ
                .replace("+", "o")  // fix POJ
                .replace("11", "1")
                .replace("22", "2")
                .replace("33", "3")
                .replace("44", "4")
                .replace("55", "5")
                .replace("77", "7")
                .replace("88", "8")
            fixedPoj = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                fixedPoj,
                EnumSet.of(
                    PojInputFixType.ONN_SIA_CHO_OONN,
                    PojInputFixType.PHINNIM_NN_SIA_CHO_TOASIA_N,
                    PojInputFixType.SIANNTIAU_SOOJI_BO_TI_IMCHAT_BOE
                )
            )

            srcEntry.poj = fixedPoj

            srcEntry.hoabun = recordColumnArrayList[2]
            srcEntry.english = recordColumnArrayList[3]
            srcEntry.pageNumber = "" // TODO: Need to add page number in source file

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<MaryknollTaiEngDictSrcEntry>): List<MaryknollTaiEngDictOutEntry> {
        val processedDictArray = ArrayList<MaryknollTaiEngDictOutEntry>()

        var idCount = 1

        for (srcEntry: MaryknollTaiEngDictSrcEntry in dictArray) {
            val outEntry = MaryknollTaiEngDictOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.pojInput = srcEntry.poj.trim()
            outEntry.pojUnicode =
                TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)
            outEntry.kipInput =
                TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(outEntry.pojInput)
            outEntry.kipUnicode =
                TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInput)

            outEntry.hoabun = srcEntry.hoabun.trim()
            outEntry.english = srcEntry.english.trim()
            outEntry.pageNumber = srcEntry.pageNumber.trim()

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<MaryknollTaiEngDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: MaryknollTaiEngDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }
            outEntry.english.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path =
            ChhoeTaigiDatabaseOutputSettings.SAVE_FOLDER_DATABASE + ChhoeTaigiDatabaseOutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
            "id",

            "poj_unicode",
            "poj_input",

            "kip_unicode",
            "kip_input",

            "hoabun",
            "english",

            "page_number"
        )

        CsvIO.write(path, dict, csvFormat)
    }
}