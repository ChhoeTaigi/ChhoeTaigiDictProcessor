package com.taccotap.chhoetaigi.dicts.taijit

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictOutEntry
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaiJitDictProcessor {
    private const val SRC_FILENAME = "TaijitToaSutian_20190417_fix.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaijitToaSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        saveLomajiSearchTable(processedDictArray)
        return processedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaijitDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, false)

        val dictArray = ArrayList<TaijitDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = TaijitDictSrcEntry()

            dictEntry.id = recordColumnArrayList[0]
            dictEntry.poj = recordColumnArrayList[1]
            dictEntry.pojDialect = recordColumnArrayList[2]
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

            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.pojDialect = LomajiConverter.pojInputStringFix(srcEntry.pojDialect)
            srcEntry.hanloTaibunPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunPoj)
            srcEntry.hanloTaibunKaisoehPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunKaisoehPoj)
            srcEntry.hanloTaibunLekuPoj = LomajiConverter.pojInputStringFix(srcEntry.hanloTaibunLekuPoj)

            srcEntry.hanloTaibunLekuPoj = fixLekuTrailingNumber(srcEntry.hanloTaibunLekuPoj)

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
            outEntry.hanloTaibunKaisoehPoj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunKaisoehPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.hanloTaibunKaisoehKiplmj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunKaisoehPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.hanloTaibunLekuPoj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunLekuPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.hanloTaibunLekuKiplmj = LomajiConverter.convertLomajiInputString(srcEntry.hanloTaibunLekuPoj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_KIPLMJ_UNICODE)
            outEntry.pageNumber = srcEntry.pageNumber

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun fixLekuTrailingNumber(hanloTaibunLekuPoj: String): String {
        if (hanloTaibunLekuPoj.contains("\\+E[0-9]+".toRegex())) {
            println("hanloTaibunLekuPoj: $hanloTaibunLekuPoj")

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
            taijitDictOutEntry.pojUnicodeDialect.let { entryArray.add(it) }
            taijitDictOutEntry.pojInput.let { entryArray.add(it) }
            taijitDictOutEntry.pojInputDialect.let { entryArray.add(it) }

            taijitDictOutEntry.hanloTaibunPoj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunKaisoehPoj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunLekuPoj.let { entryArray.add(it) }

            taijitDictOutEntry.kiplmjUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.kiplmjUnicodeDialect.let { entryArray.add(it) }
            taijitDictOutEntry.kiplmjInput.let { entryArray.add(it) }
            taijitDictOutEntry.kiplmjInputDialect.let { entryArray.add(it) }

            taijitDictOutEntry.hanloTaibunKiplmj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunKaisoehKiplmj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTaibunLekuKiplmj.let { entryArray.add(it) }

            taijitDictOutEntry.pageNumber.let { entryArray.add(it) }

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
                "hanlo_taibun_kaisoeh_poj",
                "hanlo_taibun_leku_poj",

                "kiplmj_unicode",
                "kiplmj_unicode_dialect",
                "kiplmj_input",
                "kiplmj_input_dialect",

                "hanlo_taibun_kiplmj",
                "hanlo_taibun_kaisoeh_kiplmj",
                "hanlo_taibun_leku_kiplmj",

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun saveLomajiSearchTable(formattedDictArray: List<TaijitDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()

        for (taijitDictOutEntry: TaijitDictOutEntry in formattedDictArray) {
            generateLomajiSearchData(taijitDictOutEntry, dict)
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

    private fun generateLomajiSearchData(outEntry: TaijitDictOutEntry, dict: ArrayList<ArrayList<String>>) {
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
            val tailoUnicodeWords: List<String> = outEntry.kiplmjUnicodeDialect.split("/|,".toRegex())
            val tailoInputWords: List<String> = outEntry.kiplmjInputDialect.split("/|,".toRegex())

            var count = pojInputWords.size
            for (i in 0 until count) {
                val newEntryArray: ArrayList<String> = ArrayList()

                var pojUnicodeWord = pojUnicodeWords[i].trim()
                var pojInputWord = pojInputWords[i].trim()
                var tailoUnicodeWord = tailoUnicodeWords[i].trim()
                var tailoInputWord = tailoInputWords[i].trim()

                val regex = "\\(.\\)".toRegex()
                if (pojInputWord.contains(regex)) {
                    pojInputWord = pojInputWord.replace(regex, "")
                    pojUnicodeWord = pojUnicodeWord.replace(regex, "")
                    tailoUnicodeWord = tailoUnicodeWord.replace(regex, "")
                    tailoInputWord = tailoInputWord.replace(regex, "")
                }

                if (pojInputWord.contains("(")) {
                    println("pojInputDialect not handled: pojInputWord = $pojInputWord")
                }

                newEntryArray.add(pojUnicodeWord)
                newEntryArray.add(pojInputWord)
                newEntryArray.add(tailoUnicodeWord)
                newEntryArray.add(tailoInputWord)
                newEntryArray.add(outEntry.id)

                dict.add(newEntryArray)
            }
        }
    }
}