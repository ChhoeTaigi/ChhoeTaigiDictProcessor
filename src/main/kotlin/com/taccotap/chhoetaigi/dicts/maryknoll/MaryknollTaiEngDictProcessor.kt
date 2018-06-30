package com.taccotap.chhoetaigi.dicts.maryknoll

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taibuntiongbun.entry.MaryknollTaiEngDictOutEntry
import com.taccotap.chhoetaigi.dicts.taibuntiongbun.entry.MaryknollTaiEngDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object MaryknollTaiEngDictProcessor {
    private const val SRC_FILENAME = "Mkdictionary2013.xls"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_MaryknollTaiEngSuTian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val formattedDictArray = formatDict(dictArray)
        saveDict(formattedDictArray)
        return formattedDictArray.count()
    }

    private fun loadDict(): ArrayList<MaryknollTaiEngDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "TD", true)

        val dictArray = ArrayList<MaryknollTaiEngDictSrcEntry>()
        for (recordColumnArrayList in readXlsDictArrayList) {
            val dictEntry = MaryknollTaiEngDictSrcEntry()

            dictEntry.id = recordColumnArrayList.get(0)

            var fixPoj = recordColumnArrayList[1]
                    .replace("::", "")  // remove "::" symbol for subcatogory
                    .replace("*", "nn")  // fix POJ
                    .replace("+", "o")  // fix POJ
            fixPoj = LomajiConverter.pojInputStringFix(fixPoj)
            dictEntry.poj = fixPoj

            dictEntry.hoagi = recordColumnArrayList[2]
            dictEntry.englishDescriptions = recordColumnArrayList[3]
            dictEntry.pageNumber = "" // TODO: Need to add page number in source file

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun formatDict(dictArray: ArrayList<MaryknollTaiEngDictSrcEntry>): List<MaryknollTaiEngDictOutEntry> {
        val formattedDictArray = ArrayList<MaryknollTaiEngDictOutEntry>()

        var idCount = 1

        for (srcEntry: MaryknollTaiEngDictSrcEntry in dictArray) {
            val outEntry = MaryknollTaiEngDictOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.tailoInput = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT)
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.hoagi = srcEntry.hoagi
            outEntry.englishDescriptions = srcEntry.englishDescriptions
            outEntry.pageNumber = srcEntry.pageNumber

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<MaryknollTaiEngDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: MaryknollTaiEngDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.tailoInput.let { entryArray.add(it) }
            outEntry.tailoUnicode.let { entryArray.add(it) }
            outEntry.hoagi.let { entryArray.add(it) }
            outEntry.englishDescriptions.let { entryArray.add(it) }
            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_unicode",
                "tailo_input",
                "tailo_unicode",
                "hoagi",
                "english_descriptions",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}