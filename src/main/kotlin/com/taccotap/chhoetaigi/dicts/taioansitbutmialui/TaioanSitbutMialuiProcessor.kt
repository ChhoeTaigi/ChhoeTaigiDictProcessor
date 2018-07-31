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
        val dictArray = loadDict()
        val formattedDictArray = formatDict(dictArray)
        saveDict(formattedDictArray)
        return formattedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanSitbutMialuiSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<TaioanSitbutMialuiSrcEntry>()
        var index = 1
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = TaioanSitbutMialuiSrcEntry()

            //custom id
            dictEntry.id = index.toString()
            index++

            dictEntry.tailo = recordColumnArrayList[0]
            dictEntry.taigiHanji = recordColumnArrayList[1]
            dictEntry.pageNumber = recordColumnArrayList[2]

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun formatDict(dictArray: ArrayList<TaioanSitbutMialuiSrcEntry>): List<TaioanSitbutMialuiOutEntry> {
        val formattedDictArray = ArrayList<TaioanSitbutMialuiOutEntry>()

        for (srcEntry: TaioanSitbutMialuiSrcEntry in dictArray) {
            val outEntry = TaioanSitbutMialuiOutEntry()

            srcEntry.tailo = srcEntry.tailo.toLowerCase().capitalize()
            if (srcEntry.tailo == "€") {
                continue
            }

            outEntry.id = srcEntry.id
            outEntry.pojInput = LomajiConverter.tailoInputToPojInput(srcEntry.tailo)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.pojInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.tailoInput = srcEntry.tailo
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.tailo, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_TAILO_UNICODE)
            outEntry.taigiHanji = srcEntry.taigiHanji.replace("€", "？")
            outEntry.pageNumber = srcEntry.pageNumber

            formattedDictArray.add(outEntry)

        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanSitbutMialuiOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (taihoaDictOutEntry: TaioanSitbutMialuiOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            taihoaDictOutEntry.id.let { entryArray.add(it) }
            taihoaDictOutEntry.pojInput.let { entryArray.add(it) }
            taihoaDictOutEntry.pojUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoInput.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.taigiHanji.let { entryArray.add(it) }
            taihoaDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_unicode",
                "tailo_input",
                "tailo_unicode",
                "taigi_hanji",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}
