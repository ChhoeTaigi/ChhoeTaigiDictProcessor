package com.taccotap.chhoetaigi.dicts.maryknoll

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.maryknoll.entry.MaryknollTaiEngDictOutEntry
import com.taccotap.chhoetaigi.dicts.maryknoll.entry.MaryknollTaiEngDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.lomajichoanoann.pojfix.PojInputFix
import tw.taibunkesimi.lomajichoanoann.pojfix.PojInputFixType
import java.util.*
import kotlin.collections.ArrayList

object MaryknollTaiEngDictProcessor {
    private const val SRC_FILENAME = "Mkdictionary2013_fix20201005.xlsx"
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
            fixedPoj = PojInputFix.fixKuikuOnlyPojWithDelimiter(fixedPoj, EnumSet.of(PojInputFixType.ALL))

            srcEntry.poj = fixedPoj

            srcEntry.hoabun = recordColumnArrayList[2]
            srcEntry.englishDescriptions = recordColumnArrayList[3]
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

            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.poj)
            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.poj)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInput)
            outEntry.hoabun = srcEntry.hoabun
            outEntry.englishDescriptions = srcEntry.englishDescriptions
            outEntry.pageNumber = srcEntry.pageNumber

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
            outEntry.englishDescriptions.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_input",

                "kip_unicode",
                "kip_input",

                "hoabun",
                "english_descriptions",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}