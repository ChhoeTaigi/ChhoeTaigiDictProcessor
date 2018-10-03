package com.taccotap.chhoetaigi.dicts.kam

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictOutEntry
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object KamDictProcessor {
    private const val SRC_FILENAME = "KamJitian_SBA_20180607.csv"
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

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        var cantTypeHanjiCount = 0
        var noHanjiCount = 0
        val dictArray = ArrayList<KamDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = KamDictSrcEntry()

            dictEntry.id = recordColumnArrayList[0]
            dictEntry.poj = recordColumnArrayList[1]
//            dictEntry.pojDialect = recordColumnArrayList[2]

            dictEntry.hanloTaibunPoj = recordColumnArrayList[3]
            if (dictEntry.hanloTaibunPoj == "●") {
                dictEntry.hanloTaibunPoj = "？"
                cantTypeHanjiCount++
            } else if (dictEntry.hanloTaibunPoj == "—") {
                dictEntry.hanloTaibunPoj = "-"
                noHanjiCount++
            }

            dictEntry.pojKaisoeh = recordColumnArrayList[4]
            dictEntry.hanloTaibunKaisoehPoj = recordColumnArrayList[5]
            dictEntry.pageNumber = recordColumnArrayList[10]

            if (dictEntry.pageNumber.isNotEmpty()) { // There are few words added by Lim Chuniok, not from the book.
                dictArray.add(dictEntry)
            }
        }

        println("KamDictProcessor: cantTypeHanjiCount = $cantTypeHanjiCount")
        println("KamDictProcessor: noHanjiCount = $noHanjiCount")

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<KamDictSrcEntry>): List<KamDictOutEntry> {
        val processedDictArray = ArrayList<KamDictOutEntry>()

        for (srcEntry: KamDictSrcEntry in dictArray) {
            val outEntry = KamDictOutEntry()

            // fix lomaji
            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.hanloTaibunPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunPoj)
            srcEntry.pojKaisoeh = LomajiConverter.pojInputStringFix(srcEntry.pojKaisoeh)
            srcEntry.hanloTaibunKaisoehPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunKaisoehPoj)

            // parse
            outEntry.id = srcEntry.id

            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = LomajiConverter.pojInputToPojUnicode(srcEntry.poj)
            outEntry.kiplmjInput = LomajiConverter.pojInputToKiplmjInput(srcEntry.poj)
            outEntry.kiplmjUnicode = LomajiConverter.kiplmjInputToTailoUnicode(outEntry.kiplmjInput)

            outEntry.hanloTaibunKiplmj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.hanloTaibunPoj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.pojKaisoeh = LomajiConverter.convertLomajiInputString(srcEntry.pojKaisoeh, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.kiplmjKaisoeh = LomajiConverter.convertLomajiInputString(srcEntry.pojKaisoeh, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.hanloTaibunKaisoehPoj = srcEntry.hanloTaibunKaisoehPoj
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

            KamDictOutEntry.pojUnicode.let { entryArray.add(it) }
            KamDictOutEntry.pojInput.let { entryArray.add(it) }

            KamDictOutEntry.hanloTaibunPoj.let { entryArray.add(it) }
            KamDictOutEntry.pojKaisoeh.let { entryArray.add(it) }
            KamDictOutEntry.hanloTaibunKaisoehPoj.let { entryArray.add(it) }

            KamDictOutEntry.kiplmjUnicode.let { entryArray.add(it) }
            KamDictOutEntry.kiplmjInput.let { entryArray.add(it) }

            KamDictOutEntry.hanloTaibunKiplmj.let { entryArray.add(it) }
            KamDictOutEntry.kiplmjKaisoeh.let { entryArray.add(it) }

            KamDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_input",

                "hanlo_taibun_poj",
                "poj_kaisoeh",
                "hanlo_taibun_kaisoeh_poj",

                "kiplmj_unicode",
                "kiplmj_input",

                "hanlo_taibun_kiplmj",
                "kiplmj_kaisoeh",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}