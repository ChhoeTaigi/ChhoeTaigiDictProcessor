package com.taccotap.chhoetaigi.dicts.embree

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictOutEntry
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object EmbreeDictProcessor {
    private const val SRC_FILENAME = "EmbreeTaigiDict20180621.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_EmbreeTaigiSuTian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<EmbreeDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "Dict", true)

        val dictArray = ArrayList<EmbreeDictSrcEntry>()
        for (recordColumnArrayList in readXlsDictArrayList) {
            val dictEntry = EmbreeDictSrcEntry()

            dictEntry.poj = recordColumnArrayList[0].replace("*", "") // Remove * symbol for non-Taipei pronunciation
//            dictEntry.pojKithannKhiunnkhau = "" // TODO: Need to substract from poj string

            dictEntry.hoagi = recordColumnArrayList[1]
            dictEntry.abbreviations = "${recordColumnArrayList[2]} ${recordColumnArrayList[3]}"
            dictEntry.nounClassifiers = recordColumnArrayList[4]
            dictEntry.reduplication = recordColumnArrayList[5]
            dictEntry.englishDescriptions = recordColumnArrayList[6]
            dictEntry.pageNumber = "" // TODO: Need to add page number

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<EmbreeDictSrcEntry>): List<EmbreeDictOutEntry> {
        val processedDictArray = ArrayList<EmbreeDictOutEntry>()

        // custom id
        var idCount = 1

        for (srcEntry: EmbreeDictSrcEntry in dictArray) {
            val outEntry = EmbreeDictOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
//            outEntry.pojKithannKhiunnkhauInput = srcEntry.pojKithannKhiunnkhau
//            outEntry.pojKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.tailoInput = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT)
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
//            outEntry.tailoKithannKhiunnkhauInput = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT)
//            outEntry.tailoKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)

            outEntry.hoagi = srcEntry.hoagi
            outEntry.abbreviations = srcEntry.abbreviations
            outEntry.nounClassifiers = LomajiConverter.convertLomajiInputString(srcEntry.nounClassifiers, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.reduplication = LomajiConverter.convertLomajiInputString(srcEntry.reduplication, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.englishDescriptions = srcEntry.englishDescriptions
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<EmbreeDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (embreeDictOutEntry: EmbreeDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            embreeDictOutEntry.id.let { entryArray.add(it) }
            embreeDictOutEntry.pojInput.let { entryArray.add(it) }
//            embreeDictOutEntry.pojKithannKhiunnkhauInput?.let { entryArray.add(it) }
            embreeDictOutEntry.pojUnicode.let { entryArray.add(it) }
//            embreeDictOutEntry.pojKithannKhiunnkhauUnicode?.let { entryArray.add(it) }
            embreeDictOutEntry.tailoInput.let { entryArray.add(it) }
//            embreeDictOutEntry.tailoKithannKhiunnkhauInput?.let { entryArray.add(it) }
            embreeDictOutEntry.tailoUnicode.let { entryArray.add(it) }
//            embreeDictOutEntry.tailoKithannKhiunnkhauUnicode?.let { entryArray.add(it) }
            embreeDictOutEntry.hoagi.let { entryArray.add(it) }
            embreeDictOutEntry.abbreviations.let { entryArray.add(it) }
            embreeDictOutEntry.nounClassifiers.let { entryArray.add(it) }
            embreeDictOutEntry.reduplication.let { entryArray.add(it) }
            embreeDictOutEntry.englishDescriptions.let { entryArray.add(it) }
            embreeDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
//                "poj_other_input",
                "poj_unicode",
//                "poj_other_unicode",
                "tailo_input",
//                "tailo_other_input",
                "tailo_unicode",
//                "tailo_other_unicode",
                "hoagi",
                "abbreviations",
                "noun_classifiers",
                "reduplication",
                "english_descriptions",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}