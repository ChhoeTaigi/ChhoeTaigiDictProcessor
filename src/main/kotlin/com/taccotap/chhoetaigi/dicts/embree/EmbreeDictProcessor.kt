package com.taccotap.chhoetaigi.dicts.embree

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictOutEntry
import com.taccotap.chhoetaigi.dicts.embree.entry.EmbreeDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object EmbreeDictProcessor {
    private const val SRC_FILENAME = "EmbreeTaigiDict20201008.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_EmbreeTaiengSutian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<EmbreeDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "Dict", true)

        val dictArray = ArrayList<EmbreeDictSrcEntry>()
        for (recordColumnArrayList in readXlsDictArrayList) {
            val srcEntry = EmbreeDictSrcEntry()

            srcEntry.poj = recordColumnArrayList[0].replace("*", "") // Remove * symbol for non-Taipei pronunciation
            srcEntry.hoabun = recordColumnArrayList[1]
            srcEntry.abbreviations = recordColumnArrayList[2]
            srcEntry.nounClassifiers = recordColumnArrayList[3]
            srcEntry.reduplication = recordColumnArrayList[4]
            srcEntry.synonym = recordColumnArrayList[5]
            srcEntry.cf = recordColumnArrayList[6]
            srcEntry.english = recordColumnArrayList[7]
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
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.poj)
            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.poj)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToKipUnicode(srcEntry.poj)

            outEntry.hoabun = srcEntry.hoabun
            outEntry.abbreviations = srcEntry.abbreviations
            outEntry.nounClassifiers = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.nounClassifiers)
            outEntry.reduplication = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.reduplication)

            outEntry.synonym = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.synonym)
            outEntry.cf = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.cf)

            outEntry.english = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.english)
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

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }

            outEntry.abbreviations.let { entryArray.add(it) }
            outEntry.nounClassifiers.let { entryArray.add(it) }
            outEntry.reduplication.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }
            outEntry.english.let { entryArray.add(it) }

            outEntry.synonym.let { entryArray.add(it) }
            outEntry.cf.let { entryArray.add(it) }

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

                "abbreviations",
                "noun_classifiers",
                "reduplication",

                "hoabun",
                "english",

                "synonym",
                "cf",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}