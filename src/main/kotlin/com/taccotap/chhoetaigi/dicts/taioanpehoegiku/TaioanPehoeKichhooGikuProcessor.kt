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
        val dictArray = loadDict()
        val formattedDictArray = formatDict(dictArray)
        saveDict(formattedDictArray)
        return formattedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanPehoeKichhooGikuSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<TaioanPehoeKichhooGikuSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = TaioanPehoeKichhooGikuSrcEntry()

            dictEntry.tailo = recordColumnArrayList[0]
            dictEntry.hoagi = recordColumnArrayList[1]
            dictEntry.pageNumber = recordColumnArrayList[2]

            val str3 = recordColumnArrayList[3]
            val str4 = recordColumnArrayList[4]

            if (str3.isEmpty()) {
                dictEntry.tailoKithannKhiunnkhau = ""
            } else {
                if (str4.isEmpty()) {
                    dictEntry.tailoKithannKhiunnkhau = str3
                } else {
                    dictEntry.tailoKithannKhiunnkhau = "$str3/$str4"
                }
            }

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun formatDict(dictArray: ArrayList<TaioanPehoeKichhooGikuSrcEntry>): List<TaioanPehoeKichhooGikuOutEntry> {
        val formattedDictArray = ArrayList<TaioanPehoeKichhooGikuOutEntry>()

        var idCount = 1

        for (srcEntry: TaioanPehoeKichhooGikuSrcEntry in dictArray) {
            val outEntry = TaioanPehoeKichhooGikuOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.tailoInput = srcEntry.tailo
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.tailo, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_TAILO_UNICODE)
            outEntry.tailoKithannKhiunnkhauInput = srcEntry.tailoKithannKhiunnkhau
            outEntry.tailoKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.tailoKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_TAILO_UNICODE)

            outEntry.pojInput = LomajiConverter.convertLomajiInputString(srcEntry.tailo, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.tailo, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_UNICODE)
            outEntry.pojKithannKhiunnkhauInput = LomajiConverter.convertLomajiInputString(srcEntry.tailoKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT)
            outEntry.pojKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.tailoKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_UNICODE)

            outEntry.hoagi = srcEntry.hoagi
            outEntry.pageNumber = srcEntry.pageNumber

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanPehoeKichhooGikuOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (taioanPehoeKichhooGikuOutEntry: TaioanPehoeKichhooGikuOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            taioanPehoeKichhooGikuOutEntry.id.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.pojInput.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.pojKithannKhiunnkhauInput.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.pojUnicode.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.pojKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.tailoInput.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.tailoKithannKhiunnkhauInput.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.tailoUnicode.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.tailoKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.hoagi.let { entryArray.add(it) }
            taioanPehoeKichhooGikuOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_other_input",
                "poj_unicode",
                "poj_other_unicode",
                "tailo_input",
                "tailo_other_input",
                "tailo_unicode",
                "tailo_other_unicode",
                "hoagi",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}