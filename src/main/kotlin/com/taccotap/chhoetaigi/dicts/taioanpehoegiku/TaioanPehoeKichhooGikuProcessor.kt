package com.taccotap.chhoetaigi.dicts.taioanpehoegiku

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuOutEntry
import com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaioanPehoeKichhooGikuProcessor {
    private const val SRC_FILENAME = "TaioanPehoeKichhooGiku20180618.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaioanPehoeKichhooGiku.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        saveLomajiSearchTable(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanPehoeKichhooGikuSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<TaioanPehoeKichhooGikuSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val srcEntry = TaioanPehoeKichhooGikuSrcEntry()

            srcEntry.kiplmj = recordColumnArrayList[0]
            srcEntry.hoabun = recordColumnArrayList[1]
            srcEntry.pageNumber = recordColumnArrayList[2]

            val str3 = recordColumnArrayList[3]
            val str4 = recordColumnArrayList[4]

            if (str3.isEmpty()) {
                srcEntry.kiplmjDialect = ""
            } else {
                if (str4.isEmpty()) {
                    srcEntry.kiplmjDialect = str3
                } else {
                    srcEntry.kiplmjDialect = "$str3/$str4"
                }
            }

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaioanPehoeKichhooGikuSrcEntry>): List<TaioanPehoeKichhooGikuOutEntry> {
        val processedDictArray = ArrayList<TaioanPehoeKichhooGikuOutEntry>()

        var idCount = 1

        for (srcEntry: TaioanPehoeKichhooGikuSrcEntry in dictArray) {
            val outEntry = TaioanPehoeKichhooGikuOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.kiplmjInput = srcEntry.kiplmj
            outEntry.kiplmjUnicode = LomajiConverter.convertLomajiInputString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.kiplmjInputDialect = srcEntry.kiplmjDialect
            outEntry.kiplmjUnicodeDialect = LomajiConverter.convertLomajiInputString(srcEntry.kiplmjDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_KIPLMJ_UNICODE)

            outEntry.pojInput = LomajiConverter.convertLomajiInputString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_UNICODE)
            outEntry.pojInputDialect = LomajiConverter.convertLomajiInputString(srcEntry.kiplmjDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicodeDialect = LomajiConverter.convertLomajiInputString(srcEntry.kiplmjDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_UNICODE)

            outEntry.hoabun = srcEntry.hoabun
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanPehoeKichhooGikuOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TaioanPehoeKichhooGikuOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojUnicodeDialect.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputDialect.let { entryArray.add(it) }

            outEntry.kiplmjUnicode.let { entryArray.add(it) }
            outEntry.kiplmjUnicodeDialect.let { entryArray.add(it) }
            outEntry.kiplmjInput.let { entryArray.add(it) }
            outEntry.kiplmjInputDialect.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_unicode_dialect",
                "poj_input",
                "poj_input_dialect",

                "kiplmj_unicode",
                "kiplmj_unicode_dialect",
                "kiplmj_input",
                "kiplmj_input_dialect",

                "hoabun",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun saveLomajiSearchTable(formattedDictArray: List<TaioanPehoeKichhooGikuOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()

        for (taioanPehoeKichhooGikuOutEntry: TaioanPehoeKichhooGikuOutEntry in formattedDictArray) {
            generateLomajiSearchData(taioanPehoeKichhooGikuOutEntry, dict)
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

    private fun generateLomajiSearchData(taioanPehoeKichhooGikuOutEntry: TaioanPehoeKichhooGikuOutEntry, dict: ArrayList<ArrayList<String>>) {
        // handle default lomaji
        val entryArray: ArrayList<String> = ArrayList()

        taioanPehoeKichhooGikuOutEntry.pojUnicode.let { entryArray.add(it) }
        taioanPehoeKichhooGikuOutEntry.pojInput.let { entryArray.add(it) }
        taioanPehoeKichhooGikuOutEntry.kiplmjUnicode.let { entryArray.add(it) }
        taioanPehoeKichhooGikuOutEntry.kiplmjInput.let { entryArray.add(it) }
        taioanPehoeKichhooGikuOutEntry.id.let { entryArray.add(it) }

        dict.add(entryArray)

        // handle other lomaji
        if (taioanPehoeKichhooGikuOutEntry.pojInputDialect.isNotEmpty()) {
            val pojUnicodeWords: List<String> = taioanPehoeKichhooGikuOutEntry.pojUnicodeDialect.split("/|,".toRegex())
            val pojInputWords: List<String> = taioanPehoeKichhooGikuOutEntry.pojInputDialect.split("/|,".toRegex())
            val tailoUnicodeWords: List<String> = taioanPehoeKichhooGikuOutEntry.kiplmjUnicodeDialect.split("/|,".toRegex())
            val tailoInputWords: List<String> = taioanPehoeKichhooGikuOutEntry.kiplmjInputDialect.split("/|,".toRegex())

            var count = pojInputWords.size
            for (i in 0 until count) {
                val newEntryArray: ArrayList<String> = ArrayList()

                newEntryArray.add(pojUnicodeWords[i].trim())
                newEntryArray.add(pojInputWords[i].trim())
                newEntryArray.add(tailoUnicodeWords[i].trim())
                newEntryArray.add(tailoInputWords[i].trim())
                newEntryArray.add(taioanPehoeKichhooGikuOutEntry.id)

                dict.add(newEntryArray)
            }
        }
    }
}