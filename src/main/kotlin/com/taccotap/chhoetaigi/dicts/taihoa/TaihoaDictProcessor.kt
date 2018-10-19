package com.taccotap.chhoetaigi.dicts.taihoa

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictOutEntry
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaihoaDictProcessor {
    private const val SRC_FILENAME = "TaiHoa20170516_fixed.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaibunHoabunSoanntengSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        saveLomajiSearchTable(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaihoaDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, false)

        val dictArray = ArrayList<TaihoaDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val srcEntry = TaihoaDictSrcEntry()

            srcEntry.id = recordColumnArrayList[0]
            srcEntry.poj = recordColumnArrayList[1]
            srcEntry.pojDialect = recordColumnArrayList[2]
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

            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.pojDialect = LomajiConverter.pojInputStringFix(srcEntry.pojDialect)
            srcEntry.hanloTaibunPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunPoj)

            outEntry.id = srcEntry.id
            outEntry.pojInput = srcEntry.poj
            outEntry.pojInputDialect = srcEntry.pojDialect
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.pojUnicodeDialect = LomajiConverter.convertLomajiInputString(srcEntry.pojDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.kiplmjInput = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_INPUT)
            outEntry.kiplmjInputDialect = LomajiConverter.convertLomajiInputString(srcEntry.pojDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_INPUT)
            outEntry.kiplmjUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.kiplmjUnicodeDialect = LomajiConverter.convertLomajiInputString(srcEntry.pojDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)

            outEntry.hanloTaibunPoj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.hanloTaibunKiplmj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
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
            outEntry.pojUnicodeDialect.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputDialect.let { entryArray.add(it) }

            outEntry.hanloTaibunPoj.let { entryArray.add(it) }

            outEntry.kiplmjUnicode.let { entryArray.add(it) }
            outEntry.kiplmjUnicodeDialect.let { entryArray.add(it) }
            outEntry.kiplmjInput.let { entryArray.add(it) }
            outEntry.kiplmjInputDialect.let { entryArray.add(it) }

            outEntry.hanloTaibunKiplmj.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_unicode_dialect",
                "poj_input",
                "poj_input_dialect",

                "hanlo_taibun_poj",

                "kiplmj_unicode",
                "kiplmj_unicode_dialect",
                "kiplmj_input",
                "kiplmj_input_dialect",

                "hanlo_taibun_kiplmj",

                "hoabun")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun saveLomajiSearchTable(formattedDictArray: List<TaihoaDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()

        for (taihoaDictOutEntry: TaihoaDictOutEntry in formattedDictArray) {
            generateLomajiSearchData(taihoaDictOutEntry, dict)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + OutputSettings.SAVE_FOLDER_LMJ_SEARCH_TABLE + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "poj_unicode",
                "poj_input",
                "kiplmj_unicode",
                "kiplmj_input",
                "main_id")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun generateLomajiSearchData(outEntry: TaihoaDictOutEntry, dict: ArrayList<ArrayList<String>>) {
        // handle default lomaji
        val entryArray: ArrayList<String> = ArrayList()

        outEntry.pojUnicode.let { entryArray.add(it) }
        outEntry.pojInput.let { entryArray.add(it) }
        outEntry.kiplmjUnicode.let { entryArray.add(it) }
        outEntry.kiplmjInput.let { entryArray.add(it) }
        outEntry.id.let { entryArray.add(it) }

        dict.add(entryArray)

        // handle other lomaji
        if (outEntry.pojInputDialect.isNotEmpty()) {
            val pojUnicodeWords: List<String> = outEntry.pojUnicodeDialect.split("/|,".toRegex())
            val pojInputWords: List<String> = outEntry.pojInputDialect.split("/|,".toRegex())
            val kiplmjUnicodeWords: List<String> = outEntry.kiplmjUnicodeDialect.split("/|,".toRegex())
            val kiplmjInputWords: List<String> = outEntry.kiplmjInputDialect.split("/|,".toRegex())

            var count = pojInputWords.size
            for (i in 0 until count) {
                val newEntryArray: ArrayList<String> = ArrayList()

                newEntryArray.add(pojUnicodeWords[i].trim())
                newEntryArray.add(pojInputWords[i].trim())
                newEntryArray.add(kiplmjUnicodeWords[i].trim())
                newEntryArray.add(kiplmjInputWords[i].trim())
                newEntryArray.add(outEntry.id)

                dict.add(newEntryArray)
            }
        }
    }
}
