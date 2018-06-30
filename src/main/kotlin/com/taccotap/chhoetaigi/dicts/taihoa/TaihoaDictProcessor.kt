package com.taccotap.chhoetaigi.dicts.taihoa

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictOutEntry
import com.taccotap.chhoetaigi.dicts.taihoa.entry.TaihoaDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object TaihoaDictProcessor {
    private const val SRC_FILENAME = "TaiHoa20170516.csv"
    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_TaibunHoabunSoanntengSutian.csv"

    fun run(): Int {
        val dictArray = loadDict()
        val formattedDictArray = formatDict(dictArray)
        saveDict(formattedDictArray)
        return formattedDictArray.count()
    }

    private fun loadDict(): ArrayList<TaihoaDictSrcEntry> {
        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILENAME)
        println("path: " + resource.path)

        val readCsvDictArrayList = CsvIO.read(resource.path, false)

        val dictArray = ArrayList<TaihoaDictSrcEntry>()
        for (recordColumnArrayList in readCsvDictArrayList) {
            val dictEntry = TaihoaDictSrcEntry()

            dictEntry.id = recordColumnArrayList[0]
            dictEntry.poj = recordColumnArrayList[1]
            dictEntry.pojKithannKhiunnkhau = recordColumnArrayList[2]
            dictEntry.hanlo = recordColumnArrayList[3]
            dictEntry.hoagi = recordColumnArrayList[4]

            dictArray.add(dictEntry)
        }

        return dictArray
    }

    private fun formatDict(dictArray: ArrayList<TaihoaDictSrcEntry>): List<TaihoaDictOutEntry> {
        val formattedDictArray = ArrayList<TaihoaDictOutEntry>()

        for (srcEntry: TaihoaDictSrcEntry in dictArray) {
            val outEntry = TaihoaDictOutEntry()

            srcEntry.poj = LomajiConverter.pojInputStringFix(srcEntry.poj)
            srcEntry.pojKithannKhiunnkhau = LomajiConverter.pojInputStringFix(srcEntry.pojKithannKhiunnkhau)
            srcEntry.hanlo = LomajiConverter.pojInputStringFix(srcEntry.hanlo)

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
            outEntry.hoagi = srcEntry.hoagi

            formattedDictArray.add(outEntry)

        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<TaihoaDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (taihoaDictOutEntry: TaihoaDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            taihoaDictOutEntry.id.let { entryArray.add(it) }
            taihoaDictOutEntry.pojInput.let { entryArray.add(it) }
            taihoaDictOutEntry.pojKithannKhiunnkhauInput.let { entryArray.add(it) }
            taihoaDictOutEntry.pojUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.pojKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoInput.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoKithannKhiunnkhauInput.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.tailoKithannKhiunnkhauUnicode.let { entryArray.add(it) }
            taihoaDictOutEntry.hanloPoj.let { entryArray.add(it) }
            taihoaDictOutEntry.hanloTailo.let { entryArray.add(it) }
            taihoaDictOutEntry.hoagi.let { entryArray.add(it) }

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
                "hoagi")

        CsvIO.write(path, dict, csvFormat)
    }

}
