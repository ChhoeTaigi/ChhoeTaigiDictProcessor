package com.taccotap.chhoetaigi.dicts.taijit

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictOutEntry
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object TaiJitDictProcessor {
    private const val SRC_FILENAME = "TaijitToaSutian20190417_fix20201006.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaijitToaSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaijitDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "taijit", false)

        val dictArray = ArrayList<TaijitDictSrcEntry>()
        for (recordColumnArrayList in readXlsxDictArrayList) {
            val dictEntry = TaijitDictSrcEntry()

            dictEntry.id = recordColumnArrayList[0]
            dictEntry.poj = recordColumnArrayList[1]
            dictEntry.pojOther = recordColumnArrayList[2]
            dictEntry.hanloTaibunPoj = recordColumnArrayList[3]
            dictEntry.hanloTaibunKaisoehPoj = recordColumnArrayList[4]
            dictEntry.hanloTaibunLekuPoj = recordColumnArrayList[5]
            dictEntry.pageNumber = recordColumnArrayList[11]

            dictArray.add(dictEntry)

//            if (dictEntry.pojDialect.contains("r")) {
//                println("srcEntry.pojDialect = ${dictEntry.pojDialect}")
//            }
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaijitDictSrcEntry>): List<TaijitDictOutEntry> {
        val formattedDictArray = ArrayList<TaijitDictOutEntry>()

        for (srcEntry: TaijitDictSrcEntry in dictArray) {
            val outEntry = TaijitDictOutEntry()

            outEntry.id = srcEntry.id
            outEntry.pojInput = srcEntry.poj
            outEntry.pojInputOther = srcEntry.pojOther
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.poj)
            outEntry.pojUnicodeOther = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.pojOther)

            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.poj)
            outEntry.kipInputOther = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.pojOther)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInput)
            outEntry.kipUnicodeOther = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInputOther)

            outEntry.hanloTaibunPoj = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.hanloTaibunPoj)
            outEntry.hanloTaibunKip = TaigiLomajiKuikuChoanoann.hybridPojInputToKipUnicode(srcEntry.hanloTaibunPoj)
            outEntry.hanloTaibunKaisoehPoj = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.hanloTaibunKaisoehPoj)
            outEntry.hanloTaibunKaisoehKip = TaigiLomajiKuikuChoanoann.hybridPojInputToKipUnicode(srcEntry.hanloTaibunKaisoehPoj)
            outEntry.hanloTaibunLekuPoj = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.hanloTaibunLekuPoj)
            outEntry.hanloTaibunLekuKip = TaigiLomajiKuikuChoanoann.hybridPojInputToKipUnicode(srcEntry.hanloTaibunLekuPoj)

            outEntry.pageNumber = srcEntry.pageNumber

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun fixLekuTrailingNumber(hanloTaibunLekuPoj: String): String {
        if (hanloTaibunLekuPoj.contains("\\+E[0-9]+".toRegex())) {
            println("handle special hanloTaibunLekuPoj: $hanloTaibunLekuPoj")

            return hanloTaibunLekuPoj.replace("\\+E[0-9]+".toRegex(), "")
        } else {
            return hanloTaibunLekuPoj
        }
    }

    private fun saveDict(formattedDictArray: List<TaijitDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (taijitDictOutEntry: TaijitDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            taijitDictOutEntry.id.let { entryArray.add(it) }

            taijitDictOutEntry.pojUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.pojUnicodeOther.let { entryArray.add(it) }
            taijitDictOutEntry.pojInput.let { entryArray.add(it) }
            taijitDictOutEntry.pojInputOther.let { entryArray.add(it) }

            taijitDictOutEntry.hanloTaibunPoj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunKaisoehPoj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunLekuPoj.let { entryArray.add(it) }

            taijitDictOutEntry.kipUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.kipUnicodeOther.let { entryArray.add(it) }
            taijitDictOutEntry.kipInput.let { entryArray.add(it) }
            taijitDictOutEntry.kipInputOther.let { entryArray.add(it) }

            taijitDictOutEntry.hanloTaibunKip.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunKaisoehKip.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunLekuKip.let { entryArray.add(it) }

            taijitDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_unicode_other",
                "poj_input",
                "poj_input_other",

                "hanlo_taibun_poj",
                "hanlo_taibun_kaisoeh_poj",
                "hanlo_taibun_leku_poj",

                "kip_unicode",
                "kip_unicode_other",
                "kip_input",
                "kip_input_other",

                "hanlo_taibun_kip",
                "hanlo_taibun_kaisoeh_kip",
                "hanlo_taibun_leku_kip",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}