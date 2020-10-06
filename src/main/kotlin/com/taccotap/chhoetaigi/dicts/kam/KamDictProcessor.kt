package com.taccotap.chhoetaigi.dicts.kam

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictOutEntry
import com.taccotap.chhoetaigi.dicts.kam.entry.KamDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import com.taccotap.chhoetaigi.utils.StringUtils
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object KamDictProcessor {
    private const val SRC_FILENAME = "KamJitian_SBA_20201004.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_KamJitian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val processedDictArray = processDict(dictArray)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<KamDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "kam", true)

        var cantTypeHanjiCount = 0
        var noHanjiCount = 0
        val dictArray = ArrayList<KamDictSrcEntry>()
        for (recordColumnArrayList in readXlsxDictArrayList) {
            val srcEntry = KamDictSrcEntry()

            srcEntry.id = recordColumnArrayList[0]
            srcEntry.poj = recordColumnArrayList[1]
//            srcEntry.pojDialect = recordColumnArrayList[2]

            srcEntry.hanjiTaibun = recordColumnArrayList[3]
                    .replace(" ", "")
            if (srcEntry.hanjiTaibun.contains("●")) {
                srcEntry.hanjiTaibun = srcEntry.hanjiTaibun.replace("●", "?")
                cantTypeHanjiCount++
            } else if (srcEntry.hanjiTaibun.contains("—")) {
                srcEntry.hanjiTaibun = srcEntry.hanjiTaibun.replace("—", "-")
                noHanjiCount++
            }
            srcEntry.pojBunim = " "

            if (srcEntry.hanjiTaibun.contains("^\\(.[a-zA-Z0-9]+\\)\$".toRegex())) {
                val length = StringUtils.getGraphmeLength(srcEntry.hanjiTaibun)
                val hanji = StringUtils.getGraphmeStringAt(srcEntry.hanjiTaibun, 1)
                val pojBunim = StringUtils.getGraphmeStringAt(srcEntry.hanjiTaibun, 2, length - 1)

                srcEntry.hanjiTaibun = hanji
                srcEntry.pojBunim = pojBunim.capitalize()
            }

            srcEntry.pojKaisoeh = recordColumnArrayList[4]
            srcEntry.hanloTaibunKaisoehPoj = recordColumnArrayList[5]
            srcEntry.pageNumber = recordColumnArrayList[10]

            if (srcEntry.pageNumber.isNotEmpty()) { // There are few words added by Lim Chuniok, not from the book.
                dictArray.add(srcEntry)
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

//            println("entry: ${srcEntry.id}")

            // parse
            outEntry.id = srcEntry.id

            outEntry.pojInput = srcEntry.poj
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.poj)

            outEntry.hanjiTaibun = srcEntry.hanjiTaibun

            outEntry.pojBunimInput = srcEntry.pojBunim
            outEntry.pojBunimUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(srcEntry.pojBunim)

            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.poj)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipInput)

            outEntry.kipBunimInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(srcEntry.pojBunim)
            outEntry.kipBunimUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(outEntry.kipBunimInput)

            outEntry.pojKaisoeh = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.pojKaisoeh)
            outEntry.kipKaisoeh = TaigiLomajiKuikuChoanoann.hybridKipInputToKipUnicode(srcEntry.pojKaisoeh)

            outEntry.hanloTaibunKaisoehPoj = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode(srcEntry.hanloTaibunKaisoehPoj)
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<KamDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: KamDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.hanjiTaibun.let { entryArray.add(it) }

            outEntry.pojBunimUnicode.let { entryArray.add(it) }
            outEntry.pojBunimInput.let { entryArray.add(it) }

            outEntry.pojKaisoeh.let { entryArray.add(it) }
            outEntry.hanloTaibunKaisoehPoj.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }

            outEntry.kipBunimUnicode.let { entryArray.add(it) }
            outEntry.kipBunimInput.let { entryArray.add(it) }

            outEntry.kipKaisoeh.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_input",

                "hanji_taibun",

                "poj_bunim_unicode",
                "poj_bunim_input",

                "poj_kaisoeh",
                "hanlo_taibun_kaisoeh_poj",

                "kip_unicode",
                "kip_input",

                "kip_bunim_unicode",
                "kip_bunim_input",

                "kip_kaisoeh",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}