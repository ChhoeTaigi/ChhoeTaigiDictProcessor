package com.taccotap.chhoetaigi.dicts.taioansitbutmialui


import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taioansitbutmialui.entry.TaioanSitbutMialuiOutEntry
import com.taccotap.chhoetaigi.dicts.taioansitbutmialui.entry.TaioanSitbutMialuiSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaioanSitbutMialuiProcessor {
    private const val SRC_FILENAME = "TaioanSitbutMialui20180731.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaioanSitbutMialui.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanSitbutMialuiSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<TaioanSitbutMialuiSrcEntry>()
        var index = 1
        for (recordColumnArrayList in readCsvDictArrayList) {
            val srcEntry = TaioanSitbutMialuiSrcEntry()

            //custom id
            srcEntry.id = index.toString()
            index++

            srcEntry.kiplmj = recordColumnArrayList[0]
            srcEntry.hanjiTaibun = recordColumnArrayList[1]
            srcEntry.pageNumber = recordColumnArrayList[2]

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaioanSitbutMialuiSrcEntry>): List<TaioanSitbutMialuiOutEntry> {
        val processedDictArray = ArrayList<TaioanSitbutMialuiOutEntry>()

        for (srcEntry: TaioanSitbutMialuiSrcEntry in dictArray) {
            val outEntry = TaioanSitbutMialuiOutEntry()

            srcEntry.kiplmj = srcEntry.kiplmj.toLowerCase().capitalize()
            if (srcEntry.kiplmj == "€") {
                continue
            }

            outEntry.id = srcEntry.id
            outEntry.pojInput = LomajiConverter.kiplmjInputToPojInput(srcEntry.kiplmj)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.pojInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.kiplmjInput = srcEntry.kiplmj
            outEntry.kiplmjUnicode = LomajiConverter.convertLomajiInputString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.hanjiTaibun = srcEntry.hanjiTaibun.replace("€", "？")
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)

        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanSitbutMialuiOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TaioanSitbutMialuiOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.kiplmjUnicode.let { entryArray.add(it) }
            outEntry.kiplmjInput.let { entryArray.add(it) }

            outEntry.hanjiTaibun.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_input",

                "kiplmj_unicode",
                "kiplmj_input",

                "hanji_taibun",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}
