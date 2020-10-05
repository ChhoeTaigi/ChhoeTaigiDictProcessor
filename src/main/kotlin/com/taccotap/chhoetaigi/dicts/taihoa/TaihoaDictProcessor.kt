package com.taccotap.chhoetaigi.dicts.taihoa

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictOutEntry
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.lomajichoanoann.pojfix.PojInputFix
import tw.taibunkesimi.lomajichoanoann.pojfix.PojInputFixType
import java.util.*
import kotlin.collections.ArrayList

object TaihoaDictProcessor {
    private const val SRC_FILENAME = "TaiHoa20170516_fix20201005.xls"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaibunHoabunSoanntengSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaihoaDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource!!.path)

        val readXlsxDictArrayList = XlsxIO.read(resource.path, "taihoa",false)

        val dictArray = ArrayList<TaihoaDictSrcEntry>()
        for (recordColumnArrayList in readXlsxDictArrayList) {
            val srcEntry = TaihoaDictSrcEntry()

            srcEntry.id = recordColumnArrayList[0].trim()

            srcEntry.poj = PojInputFix.fixKuikuOnlyPojWithDelimiter(recordColumnArrayList[1], EnumSet.of(PojInputFixType.ALL))
            if (srcEntry.poj.isEmpty) {
                continue
            }
            if (srcEntry.poj.contains("(\\[|\\(|（)".toRegex())) {
                println("srcEntry.poj:${srcEntry.poj}")
                throw IllegalArgumentException()
            }

            srcEntry.pojOther = PojInputFix.fixKuikuOnlyPojWithDelimiter(recordColumnArrayList[2], EnumSet.of(PojInputFixType.ALL))
            srcEntry.hanloTaibunPoj = recordColumnArrayList[3]
            srcEntry.hoabun = recordColumnArrayList[4]

            dictArray.add(srcEntry)
        }

        return dictArray
    }

    private fun processDict(dictArray: ArrayList<TaihoaDictSrcEntry>): List<TaihoaDictOutEntry> {
        val processedDictArray = ArrayList<TaihoaDictOutEntry>()

        for (srcEntry: TaihoaDictSrcEntry in dictArray) {
            val outEntry = TaihoaDictOutEntry()

//            println("srcEntry.poj=${srcEntry.poj}")

            outEntry.id = srcEntry.id.trim()
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
            outEntry.hoabun = srcEntry.hoabun

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaihoaDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: TaihoaDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }

            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojUnicodeOther.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputOther.let { entryArray.add(it) }

            outEntry.hanloTaibunPoj.let { entryArray.add(it) }

            outEntry.kipUnicode.let { entryArray.add(it) }
            outEntry.kipUnicodeOther.let { entryArray.add(it) }
            outEntry.kipInput.let { entryArray.add(it) }
            outEntry.kipInputOther.let { entryArray.add(it) }

            outEntry.hanloTaibunKip.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }

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

                "kip_unicode",
                "kip_unicode_other",
                "kip_input",
                "kip_input_other",

                "hanlo_taibun_kip",

                "hoabun")

        CsvIO.write(path, dict, csvFormat)
    }
}
