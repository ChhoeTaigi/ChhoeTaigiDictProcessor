package com.taccotap.chhoetaigi.dicts.embree

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictOutEntry
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object EmbreeDictProcessor {
    private const val SRC_FILENAME = "EmbreeTaigiDict20190616.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_EmbreeTaigiSutian.csv"

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
            val srcEntry = EmbreeDictSrcEntry()

            srcEntry.poj = recordColumnArrayList[0].replace("*", "") // Remove * symbol for non-Taipei pronunciation
            srcEntry.hoabun = recordColumnArrayList[1]
            srcEntry.abbreviations = "${recordColumnArrayList[2]} ${recordColumnArrayList[3]}"
            srcEntry.nounClassifiers = recordColumnArrayList[4]
            srcEntry.reduplication = recordColumnArrayList[5]
            srcEntry.englishDescriptions = recordColumnArrayList[6]
            srcEntry.pageNumber = "" // TODO: Need to add page number

            dictArray.add(srcEntry)
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
            outEntry.tailoInput = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_INPUT)
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)

            outEntry.hoabun = srcEntry.hoabun
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
        for (outEntry: EmbreeDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.tailoUnicode.let { entryArray.add(it) }
            outEntry.tailoInput.let { entryArray.add(it) }

            outEntry.abbreviations.let { entryArray.add(it) }
            outEntry.nounClassifiers.let { entryArray.add(it) }
            outEntry.reduplication.let { entryArray.add(it) }

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

                "kiplmj_unicode",
                "kiplmj_input",

                "abbreviations",
                "noun_classifiers",
                "reduplication",

                "hoabun",
                "english_descriptions",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}