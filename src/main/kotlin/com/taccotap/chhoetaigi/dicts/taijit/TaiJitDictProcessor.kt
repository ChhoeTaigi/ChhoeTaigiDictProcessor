package com.taccotap.chhoetaigi.dicts.taijit

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictOutEntry
import com.taccotap.chhoetaigi.dicts.taijit.entry.TaijitDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaiJitDictProcessor {
    private const val SRC_FILENAME = "TaiJitToaSuTian20170516.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaiJitToaSuTian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val formattedDictArray = formatDict(dictArray)
        saveDict(formattedDictArray)
        return formattedDictArray.count()
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
            dictEntry.pojKithannKhiunnkhau = recordColumnArrayList[2]
            dictEntry.hanlo = recordColumnArrayList[3]
            dictEntry.taigiKaisoehHanlo = recordColumnArrayList[4]
            dictEntry.taigiLekuHanlo = recordColumnArrayList[5]
            dictEntry.pageNumber = recordColumnArrayList[8]

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun formatDict(dictArray: ArrayList<TaijitDictSrcEntry>): List<TaijitDictOutEntry> {
        val formattedDictArray = ArrayList<TaijitDictOutEntry>()

        for (srcEntry: TaijitDictSrcEntry in dictArray) {
            val outEntry = TaijitDictOutEntry()

            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.pojKithannKhiunnkhau = LomajiConverter.pojInputStringFix(srcEntry.pojKithannKhiunnkhau)
            srcEntry.hanlo = LomajiConverter.pojInputStringFix(srcEntry.hanlo)
            srcEntry.taigiKaisoehHanlo = LomajiConverter.pojInputStringFix(srcEntry.taigiKaisoehHanlo)
            srcEntry.taigiLekuHanlo = LomajiConverter.pojInputStringFix(srcEntry.taigiLekuHanlo)

            outEntry.id = srcEntry.id
            outEntry.pojInput = srcEntry.poj
            outEntry.pojKithannKhiunnkhauInput = srcEntry.pojKithannKhiunnkhau
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.pojKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.tailoInput = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT)
            outEntry.tailoKithannKhiunnkhauInput = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_INPUT)
            outEntry.tailoUnicode = LomajiConverter.convertLomajiInputString(srcEntry.poj, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.tailoKithannKhiunnkhauUnicode = LomajiConverter.convertLomajiInputString(srcEntry.pojKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)

            outEntry.hanloPoj = LomajiConverter.convertLomajiInputString(srcEntry.hanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.hanloTailo = LomajiConverter.convertLomajiInputString(srcEntry.hanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.taigiKaisoehHanloPoj = LomajiConverter.convertLomajiInputString(srcEntry.taigiKaisoehHanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.taigiKaisoehHanloTailo = LomajiConverter.convertLomajiInputString(srcEntry.taigiKaisoehHanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.taigiLekuHanloPoj = LomajiConverter.convertLomajiInputString(srcEntry.taigiLekuHanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)
            outEntry.taigiLekuHanloTailo = LomajiConverter.convertLomajiInputString(srcEntry.taigiLekuHanlo, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_TAILO_UNICODE)
            outEntry.pageNumber = fixPageNumberFormat(srcEntry.pageNumber)

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun fixPageNumberFormat(pageNumber: String): String {
        val matchSequence: Sequence<MatchResult> = Regex("[a-zA-Z]+[0-9]+").findAll(pageNumber)
        val matchList = matchSequence.toList()
        val size = matchList.size

        if (matchList.isEmpty()) {
            return pageNumber
        }

        val stringBuilder = StringBuilder()
        for (i in 0 until size) {
            val matchResult: MatchResult = matchList[i]

            var singlePageNumber = pageNumber.substring(matchResult.range.first, matchResult.range.last + 1)
            singlePageNumber = singlePageNumber
                    .toLowerCase()
                    .replace("v", "b")

            stringBuilder.append(singlePageNumber)

            if (i < size - 1) {
                stringBuilder.append(",")
            }
        }

        return stringBuilder.toString()
    }

    private fun saveDict(formattedDictArray: List<TaijitDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (taijitDictOutEntry: TaijitDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            taijitDictOutEntry.id.let { entryArray.add(it) }
            taijitDictOutEntry.pojInput.let { entryArray.add(it) }
            taijitDictOutEntry.pojKithannKhiunnkhauInput.let { entryArray.add(it) }
            taijitDictOutEntry.pojUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.pojKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.tailoInput.let { entryArray.add(it) }
            taijitDictOutEntry.tailoKithannKhiunnkhauInput.let { entryArray.add(it) }
            taijitDictOutEntry.tailoUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.tailoKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taijitDictOutEntry.hanloPoj.let { entryArray.add(it) }
            taijitDictOutEntry.hanloTailo.let { entryArray.add(it) }
            taijitDictOutEntry.taigiKaisoehHanloPoj.let { entryArray.add(it) }
            taijitDictOutEntry.taigiKaisoehHanloTailo.let { entryArray.add(it) }
            taijitDictOutEntry.taigiLekuHanloPoj.let { entryArray.add(it) }
            taijitDictOutEntry.taigiLekuHanloTailo.let { entryArray.add(it) }
            taijitDictOutEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_other_input",
                "poj_unicode",
                "poj_other_unicode",
                "tailo_input",
                "tailo_other_input",
                "tailo_unicode",
                "tailo_other_unicode",
                "hanlo_poj",
                "hanlo_tailo",
                "taigi_kaisoeh_hanlo_poj",
                "taigi_kaisoeh_hanlo_tailo",
                "taigi_leku_hanlo_poj",
                "taigi_leku_hanlo_tailo",
                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }

}