package tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku

import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.chhoetaigi.database.ChhoeTaigiDatabaseOutputSettings
import tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuOutEntry
import tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuSrcEntry
import tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFix
import tw.taibunkesimi.lib.lomajichoanoann.pojfix.PojInputFixType
import tw.taibunkesimi.util.io.CsvIO
import tw.taibunkesimi.util.io.XlsxIO
import java.util.*
import kotlin.collections.ArrayList

object TaioanPehoeKichhooGikuProcessor {
    private const val SRC_FILENAME = "TaioanPehoeKichhooGiku20201008.xlsx"
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

            srcEntry.english = recordColumnArrayList[3]
            srcEntry.englishSoatbeng = recordColumnArrayList[4]

            srcEntry.nounClassifiers = recordColumnArrayList[5]
            srcEntry.opposite = recordColumnArrayList[6]
            srcEntry.exampleSu = recordColumnArrayList[7]
            srcEntry.fromSu = recordColumnArrayList[8]

            srcEntry.exampleKuTaibun = recordColumnArrayList[9]
            srcEntry.exampleKuHoabun = recordColumnArrayList[10]
            srcEntry.exampleKuEnglish = recordColumnArrayList[11]

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

            val pojInput = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kip)
            val fixedPojInput = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                pojInput.trim(),
                EnumSet.of(PojInputFixType.ONN_SIA_CHO_OONN)
            )

            val pojInputOther = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kipOther)
            val fixedPojInputOther = PojInputFix.fixKuikuOnlyPojWithDelimiter(
                pojInputOther.trim(),
                EnumSet.of(PojInputFixType.ONN_SIA_CHO_OONN)
            )

            outEntry.pojInput = fixedPojInput
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

            outEntry.pojInputOther = fixedPojInputOther
            outEntry.pojUnicodeOther = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInputOther)

            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(outEntry.pojInput)
            outEntry.kipUnicode = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kip)

            outEntry.kipInputOther = TaigiLomajiKuikuChoanoann.onlyPojInputToKipInput(outEntry.pojInputOther)
            outEntry.kipUnicodeOther = TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kipOther)

            outEntry.hoabun = srcEntry.hoabun.trim()

            outEntry.english = srcEntry.english.trim()
            outEntry.englishSoatbeng = srcEntry.englishSoatbeng.trim()

            outEntry.nounClassifiers =
                TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.nounClassifiers.trim())
            outEntry.opposite = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.opposite.trim())
            outEntry.exampleSu = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.exampleSu.trim())
            outEntry.fromSu = TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.fromSu.trim())

            outEntry.pageNumber = srcEntry.pageNumber.trim()
//            println("srcEntry.pageNumber:${srcEntry.pageNumber}")

            if (srcEntry.pageNumber.toInt() in 441..660) {
                outEntry.exampleKuTaibunPoj =
                    TaigiLomajiKuikuChoanoann.onlyKipUnicodeToPojUnicode(srcEntry.exampleKuTaibun.trim())
            } else {
                outEntry.exampleKuTaibunPoj = srcEntry.exampleKuTaibun.trim()
            }
            outEntry.exampleKuHoabun = srcEntry.exampleKuHoabun.trim()
            outEntry.exampleKuEnglish = srcEntry.exampleKuEnglish.trim()

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

            outEntry.english.let { entryArray.add(it) }
            outEntry.englishSoatbeng.let { entryArray.add(it) }

            outEntry.nounClassifiers.let { entryArray.add(it) }
            outEntry.exampleSu.let { entryArray.add(it) }
            outEntry.opposite.let { entryArray.add(it) }

            outEntry.exampleKuTaibunPoj.let { entryArray.add(it) }
            outEntry.exampleKuEnglish.let { entryArray.add(it) }
            outEntry.exampleKuHoabun.let { entryArray.add(it) }

            outEntry.fromSu.let { entryArray.add(it) }

            outEntry.pageNumber.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path =
            ChhoeTaigiDatabaseOutputSettings.SAVE_FOLDER_DATABASE + ChhoeTaigiDatabaseOutputSettings.timestamp + SAVE_FILENAME_PATH
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

            "english",
            "english_soatbeng",

            "noun_classifiers",
            "example_su",
            "opposite",

            "example_ku_taibun_poj",
            "example_ku_english",
            "example_ku_hoabun",

            "from_su",

            "page_number"
        )

        CsvIO.write(path, dict, csvFormat)
    }
}