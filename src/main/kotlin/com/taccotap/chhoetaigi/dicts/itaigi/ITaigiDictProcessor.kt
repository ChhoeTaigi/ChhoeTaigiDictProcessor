package com.taccotap.chhoetaigi.dicts.itaigi

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictOutEntry
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object ITaigiDictProcessor {
    private const val SRC_FILENAME = "iTaigi20180617.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_iTaigiHoaTaiSutian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<ITaigiDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<ITaigiDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = ITaigiDictSrcEntry()

            dictEntry.from = recordColumnArrayList[0]

            if (dictEntry.from.matches("(臺灣閩南語常用詞辭典|台文華文線頂辭典)".toRegex())) {
                // skip data import from exist dict
                continue
            }

            dictEntry.hanlo = recordColumnArrayList[1]
            dictEntry.hoagi = recordColumnArrayList[2]
            dictEntry.tailo = recordColumnArrayList[3]

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<ITaigiDictSrcEntry>): List<ITaigiDictOutEntry> {
        val processedDictArray = ArrayList<ITaigiDictOutEntry>()

        // custom id
        var idCount = 1

        for (srcEntry: ITaigiDictSrcEntry in dictArray) {
            val outEntry = ITaigiDictOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.tailoInput = LomajiConverter.convertLomajiUnicodeString(srcEntry.tailo, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT)

            if (LomajiConverter.isNotChoanTailoString(outEntry.tailoInput)) {
                println("Skip incorrect data: tailo = ${srcEntry.tailo}, hanlo = ${srcEntry.hanlo}, hoagi = ${srcEntry.hoagi}, from = ${srcEntry.from}")
                continue
            }

            outEntry.tailoUnicode = srcEntry.tailo

            outEntry.pojInput = LomajiConverter.convertLomajiInputString(outEntry.tailoInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.tailoInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_UNICODE)

            outEntry.hanlo = srcEntry.hanlo
            outEntry.hoagi = srcEntry.hoagi
            outEntry.from = srcEntry.from

            processedDictArray.add(outEntry)
        }

        return processedDictArray
    }

    private fun saveDict(formattedDictArray: List<ITaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (iTaigiDictOutEntry: ITaigiDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            iTaigiDictOutEntry.id.let { entryArray.add(it) }
            iTaigiDictOutEntry.pojInput.let { entryArray.add(it) }
            iTaigiDictOutEntry.pojUnicode.let { entryArray.add(it) }
            iTaigiDictOutEntry.tailoInput.let { entryArray.add(it) }
            iTaigiDictOutEntry.tailoUnicode.let { entryArray.add(it) }
            iTaigiDictOutEntry.hanlo.let { entryArray.add(it) }
            iTaigiDictOutEntry.hoagi.let { entryArray.add(it) }
            iTaigiDictOutEntry.from.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_unicode",
                "tailo_input",
                "tailo_unicode",
                "hanlo",
                "hoagi",
                "from")

        CsvIO.write(path, dict, csvFormat)
    }

}
