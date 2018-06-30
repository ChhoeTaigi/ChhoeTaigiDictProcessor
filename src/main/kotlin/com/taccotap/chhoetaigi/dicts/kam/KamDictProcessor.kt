package com.taccotap.chhoetaigi.dicts.kam

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictOutEntry
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object KamDictProcessor {
    private const val SRC_FILENAME = "KamJiTian20170516.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_KamJitian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<KamDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, false)

        val dictArray = ArrayList<KamDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = KamDictSrcEntry()

            dictEntry.id = recordColumnArrayList[0]
            dictEntry.poj = recordColumnArrayList[1]
            dictEntry.taigiHanji = recordColumnArrayList[3]
            dictEntry.taigiKaisoehPoj = recordColumnArrayList[4]
            dictEntry.taigiKaisoehHanlo = recordColumnArrayList[5]
            dictEntry.pageNumber = recordColumnArrayList[11]

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<KamDictSrcEntry>): List<KamDictOutEntry> {
        val processedDictArray = ArrayList<KamDictOutEntry>()

        for (srcEntry: KamDictSrcEntry in dictArray) {
            val outEntry = KamDictOutEntry()

            // fix lomaji
            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.taigiHanji = LomajiConverter.pojInputStringFix(srcEntry.taigiHanji)
            srcEntry.taigiKaisoehPoj = LomajiConverter.pojInputStringFix(srcEntry.taigiKaisoehPoj)
            srcEntry.taigiKaisoehHanlo = LomajiConverter.pojInputStringFix(srcEntry.taigiKaisoehHanlo)

            // parse
            outEntry.id = srcEntry.id
            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = LomajiConverter.pojInputToPojUnicode(srcEntry.poj)
            outEntry.tailoInput = LomajiConverter.pojInputToTailoInput(srcEntry.poj)
            outEntry.tailoUnicode = LomajiConverter.tailoInputToTailoUnicode(outEntry.tailoInput)
            outEntry.taigiHanjiWithTailo = LomajiConverter.convertLomajiInputString(srcEntry.taigiHanji, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.taigiHanjiWithPoj = LomajiConverter.convertLomajiInputString(srcEntry.taigiHanji, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.taigiKaisoehPoj = LomajiConverter.convertLomajiInputString(srcEntry.taigiKaisoehPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.taigiKaisoehTailo = LomajiConverter.convertLomajiInputString(srcEntry.taigiKaisoehPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.taigiKaisoehHanloPoj = srcEntry.taigiKaisoehHanlo
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<KamDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (KamDictOutEntry: KamDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            KamDictOutEntry.id.let { entryArray.add(it) }
            KamDictOutEntry.pojInput.let { entryArray.add(it) }
            KamDictOutEntry.pojUnicode.let { entryArray.add(it) }
            KamDictOutEntry.tailoInput.let { entryArray.add(it) }
            KamDictOutEntry.tailoUnicode.let { entryArray.add(it) }
            KamDictOutEntry.taigiHanjiWithPoj.let { entryArray.add(it) }
            KamDictOutEntry.taigiHanjiWithTailo.let { entryArray.add(it) }
            KamDictOutEntry.taigiKaisoehPoj.let { entryArray.add(it) }
            KamDictOutEntry.taigiKaisoehTailo.let { entryArray.add(it) }
            KamDictOutEntry.taigiKaisoehHanloPoj.let { entryArray.add(it) }
            KamDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_unicode",
                "tailo_input",
                "tailo_unicode",
                "taigi_hanlo_poj",
                "taigi_hanlo_tailo",
                "taigi_kaisoeh_poj",
                "taigi_kaisoeh_tailo",
                "taigi_kaisoeh_hanlo_poj",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}