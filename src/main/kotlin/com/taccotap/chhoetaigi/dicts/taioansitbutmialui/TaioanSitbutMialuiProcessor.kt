package com.taccotap.chhoetaigi.dicts.taioansitbutmialui

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taioansitbutmialui.entry.TaioanSitbutMialuiOutEntry
import com.taccotap.chhoetaigi.dicts.taioansitbutmialui.entry.TaioanSitbutMialuiSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object TaioanSitbutMialuiProcessor {
    private const val SRC_FILENAME = "TaioanSitbutMialui20180731.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaioanSitbutMialui.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanSitbutMialuiSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "sitbut", true)

        val dictArray = ArrayList<TaioanSitbutMialuiSrcEntry>()
        var index = 1
        for (recordColumnArrayList in readXlsxDictArrayList) {
            val srcEntry = TaioanSitbutMialuiSrcEntry()

            //custom id
            srcEntry.id = index.toString()
            index++

            srcEntry.kip = recordColumnArrayList[0]
            srcEntry.hanjiTaibun = recordColumnArrayList[1]
            srcEntry.pageNumber = recordColumnArrayList[2]

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaioanSitbutMialuiSrcEntry>): List<TaioanSitbutMialuiOutEntry> {
        val processedDictArray = ArrayList<TaioanSitbutMialuiOutEntry>()

        for (srcEntry: TaioanSitbutMialuiSrcEntry in dictArray) {
            val outEntry = TaioanSitbutMialuiOutEntry()

            srcEntry.kip = srcEntry.kip.toLowerCase().capitalize()
            if (srcEntry.kip == "€") {
                continue
            }

            outEntry.id = srcEntry.id

            outEntry.pojInput = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kip)
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

            outEntry.kipInput = srcEntry.kip
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kip)

            outEntry.hanjiTaibun = srcEntry.hanjiTaibun.replace("€", "？")
            outEntry.pageNumber = srcEntry.pageNumber

            processedDictArray.add(outEntry)

        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanSitbutMialuiOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TaioanSitbutMialuiOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }

            outEntry.hanjiTaibun.let { entryArray.add(it) }

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

                "hanji_taibun",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}
