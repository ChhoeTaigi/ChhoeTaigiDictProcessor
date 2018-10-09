package com.taccotap.chhoetaigi.dicts.itaigi

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictOutEntry
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object ITaigiDictProcessor {
    private const val SRC_FILENAME = "iTaigi20181005.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_iTaigiHoataiSutian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        saveLomajiSearchTable(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<ITaigiDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<ITaigiDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val srcEntry = ITaigiDictSrcEntry()

            srcEntry.from = recordColumnArrayList[0]

            if (srcEntry.from.matches("(臺灣閩南語常用詞辭典|台文華文線頂辭典)".toRegex())) {
                // skip data import from exist dict
                continue
            }

            srcEntry.hanloTaibunKiplmj = recordColumnArrayList[1]
            srcEntry.hoabun = recordColumnArrayList[2]
            srcEntry.kiplmj = recordColumnArrayList[3]

            dictArray.add(srcEntry)
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

            outEntry.kiplmjInput = LomajiConverter.convertLomajiUnicodeString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_KIPLMJ_UNICODE_TO_KIPLMJ_INPUT)

            if (LomajiConverter.isNotChoanKiplmjString(outEntry.kiplmjInput)) {
                println("Skip incorrect data: kiplmj = ${srcEntry.kiplmj}, hanloTaibunPoj = ${srcEntry.hanloTaibunKiplmj}, hoabun = ${srcEntry.hoabun}, from = ${srcEntry.from}")
                continue
            }

            outEntry.kiplmjUnicode = srcEntry.kiplmj

            outEntry.pojInput = LomajiConverter.convertLomajiInputString(outEntry.kiplmjInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.kiplmjInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_UNICODE)

            outEntry.hanloTaibunKiplmj = srcEntry.hanloTaibunKiplmj
            outEntry.hoabun = srcEntry.hoabun
            outEntry.from = srcEntry.from

            processedDictArray.add(outEntry)
        }

        return processedDictArray
    }

    private fun saveDict(formattedDictArray: List<ITaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: ITaigiDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.kiplmjUnicode.let { entryArray.add(it) }
            outEntry.kiplmjInput.let { entryArray.add(it) }

            outEntry.hanloTaibunKiplmj.let { entryArray.add(it) }
            outEntry.hoabun.let { entryArray.add(it) }

            outEntry.from.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_input",

                "kiplmj_unicode",
                "kiplmj_input",

                "hanlo_taibun_kiplmj",
                "hoabun",

                "from")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun saveLomajiSearchTable(formattedDictArray: List<ITaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()

        for (outEntry: ITaigiDictOutEntry in formattedDictArray) {
            generateLomajiSearchData(outEntry, dict)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + OutputSettings.SAVE_FOLDER_LMJ_SEARCH_TABLE + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "poj_unicode",
                "poj_input",
                "kiplmj_unicode",
                "kiplmj_input",
                "main_id")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun generateLomajiSearchData(outEntry: ITaigiDictOutEntry, dict: ArrayList<ArrayList<String>>) {
        // handle default lomaji
        if (outEntry.pojInput.isNotEmpty()) {
            val pojUnicodeWords: List<String> = outEntry.pojUnicode.split("/|,".toRegex())
            val pojInputWords: List<String> = outEntry.pojInput.split("/|,".toRegex())
            val kiplmjUnicodeWords: List<String> = outEntry.kiplmjUnicode.split("/|,".toRegex())
            val kiplmjInputWords: List<String> = outEntry.kiplmjInput.split("/|,".toRegex())

            var pojInputWordCount = pojInputWords.size
            for (i in 0 until pojInputWordCount) {
                val newEntryArray: ArrayList<String> = ArrayList()

                newEntryArray.add(pojUnicodeWords[i].trim())
                newEntryArray.add(pojInputWords[i].trim())
                newEntryArray.add(kiplmjUnicodeWords[i].trim())
                newEntryArray.add(kiplmjInputWords[i].trim())
                newEntryArray.add(outEntry.id)

                dict.add(newEntryArray)
            }
        }
    }
}
