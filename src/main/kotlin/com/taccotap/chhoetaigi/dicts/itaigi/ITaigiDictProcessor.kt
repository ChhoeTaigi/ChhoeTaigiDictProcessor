package com.taccotap.chhoetaigi.dicts.itaigi

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictOutEntry
import com.taccotap.chhoetaigi.dicts.itaigi.entry.ITaigiDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object ITaigiDictProcessor {
    private const val SRC_FILENAME = "itaigi20201004.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_iTaigiHoataiSutian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<ITaigiDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, true)

        val dictArray = ArrayList<ITaigiDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val srcEntry = ITaigiDictSrcEntry()

            srcEntry.hoabun = recordColumnArrayList[0]

            srcEntry.from = recordColumnArrayList[1]
            if (srcEntry.from.matches("(臺灣閩南語常用詞辭典|台文華文線頂辭典)".toRegex())) {
                // skip data import from exist dict
                continue
            }

            srcEntry.hanloTaibunKip = recordColumnArrayList[2]
            srcEntry.kip = recordColumnArrayList[3]

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<ITaigiDictSrcEntry>): List<ITaigiDictOutEntry> {
        val processedDictArray = ArrayList<ITaigiDictOutEntry>()

        // custom id
        var idCount = 1

        for (srcEntry: ITaigiDictSrcEntry in dictArray) {
            try {
                val outEntry = ITaigiDictOutEntry()

                outEntry.id = idCount.toString()
                idCount++

                outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyKipUnicodeToKipInput(srcEntry.kip)
                outEntry.kipUnicode = srcEntry.kip

                outEntry.pojInput = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(outEntry.kipInput)
                outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

                outEntry.hanloTaibunKip = srcEntry.hanloTaibunKip
                outEntry.hanloTaibunPoj = TaigiLomajiKuikuChoanoann.hybridKipUnicodeToPojUnicode(srcEntry.hanloTaibunKip)
                outEntry.hoabun = srcEntry.hoabun
                outEntry.from = srcEntry.from

                processedDictArray.add(outEntry)
            } catch (e: Exception) {
                continue
            }
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

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }

            outEntry.hanloTaibunPoj.let { entryArray.add(it) }
            outEntry.hanloTaibunKip.let { entryArray.add(it) }
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

                "hanlo_taibun_poj",
                "hanlo_taibun_kip",
                "hoabun",

                "from")

        CsvIO.write(path, dict, csvFormat)
    }
}
