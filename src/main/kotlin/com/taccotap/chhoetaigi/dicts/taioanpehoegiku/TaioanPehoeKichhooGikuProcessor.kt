package com.taccotap.chhoetaigi.dicts.taioanpehoegiku

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuOutEntry
import com.taccotap.chhoetaigi.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object TaioanPehoeKichhooGikuProcessor {
    private const val SRC_FILENAME = "TaioanPehoeKichhooGiku20201005.xlsx"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaioanPehoeKichhooGiku.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaioanPehoeKichhooGikuSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "Titeng", true)

        val dictArray = ArrayList<TaioanPehoeKichhooGikuSrcEntry>()
        for (recordColumnArrayList in readXlsxDictArrayList) {
            val srcEntry = TaioanPehoeKichhooGikuSrcEntry()

            srcEntry.kip = recordColumnArrayList[0]
            srcEntry.kipOther = recordColumnArrayList[1]

            srcEntry.hoabun = recordColumnArrayList[2]

            srcEntry.engbun = recordColumnArrayList[3]
            srcEntry.engbunChukai = recordColumnArrayList[4]

            srcEntry.nounClassifiers = recordColumnArrayList[5]
            srcEntry.opposite = recordColumnArrayList[6]
            srcEntry.exampleSu = recordColumnArrayList[7]
            srcEntry.fromSu = recordColumnArrayList[8]

            srcEntry.exampleKuTaibun = recordColumnArrayList[9]
            srcEntry.exampleKuHoabun = recordColumnArrayList[10]
            srcEntry.exampleKuEngbun = recordColumnArrayList[11]

            srcEntry.pageNumber = recordColumnArrayList[12]

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaioanPehoeKichhooGikuSrcEntry>): List<TaioanPehoeKichhooGikuOutEntry> {
        val processedDictArray = ArrayList<TaioanPehoeKichhooGikuOutEntry>()

        var idCount = 1

        for (srcEntry: TaioanPehoeKichhooGikuSrcEntry in dictArray) {
            val outEntry = TaioanPehoeKichhooGikuOutEntry()

            outEntry.id = idCount.toString()
            idCount++

            outEntry.kipInput = srcEntry.kip
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kip)

            outEntry.kipInputOther = srcEntry.kipOther
            outEntry.kipUnicodeOther = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kipOther)

            outEntry.pojInput = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kip)
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

            outEntry.pojInputOther = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kipOther)
            outEntry.pojUnicodeOther = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInputOther)

            outEntry.hoabun = srcEntry.hoabun

            outEntry.engbun = srcEntry.engbun
            outEntry.engbunChukai = srcEntry.engbunChukai

            outEntry.nounClassifiers = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.nounClassifiers)
            outEntry.opposite = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.opposite)
            outEntry.exampleSu = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.exampleSu)
            outEntry.fromSu = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.fromSu)

            outEntry.pageNumber = srcEntry.pageNumber
//            println("srcEntry.pageNumber:${srcEntry.pageNumber}")

            if (srcEntry.pageNumber.toInt() in 441..660) {
                outEntry.exampleKuTaibunPoj = TaigiLomajiKuikuChoanoann.onlyKipUnicodeToPojUnicode(srcEntry.exampleKuTaibun)
            } else {
                outEntry.exampleKuTaibunPoj = srcEntry.exampleKuTaibun
            }
            outEntry.exampleKuHoabun = srcEntry.exampleKuHoabun
            outEntry.exampleKuEngbun = srcEntry.exampleKuEngbun

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaioanPehoeKichhooGikuOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TaioanPehoeKichhooGikuOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojUnicodeOther.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputOther.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipUnicodeOther.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }
            outEntry.kipInputOther.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }

            outEntry.engbun.let { entryArray.add(it) }
            outEntry.engbunChukai.let { entryArray.add(it) }

            outEntry.nounClassifiers.let { entryArray.add(it) }
            outEntry.opposite.let { entryArray.add(it) }
            outEntry.exampleSu.let { entryArray.add(it) }
            outEntry.fromSu.let { entryArray.add(it) }

            outEntry.exampleKuTaibunPoj.let { entryArray.add(it) }
            outEntry.exampleKuHoabun.let { entryArray.add(it) }
            outEntry.exampleKuEngbun.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_unicode_other",
                "poj_input",
                "poj_input_other",

                "kip_unicode",
                "kip_unicode_other",
                "kip_input",
                "kip_input_other",

                "hoabun",

                "engbun",
                "engbun_chukai",

                "noun_classifiers",
                "opposite",
                "example_su",
                "from_su",

                "example_ku_taibun_poj",
                "example_ku_hoabun",
                "example_ku_engbun",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}