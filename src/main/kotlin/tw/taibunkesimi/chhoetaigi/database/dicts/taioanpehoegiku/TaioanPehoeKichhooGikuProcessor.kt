package tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku

import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.chhoetaigi.database.ChhoeTaigiDatabaseOutputSettings
import tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuOutEntry
import tw.taibunkesimi.chhoetaigi.database.dicts.taioanpehoegiku.entry.TaioanPehoeKichhooGikuSrcEntry
import tw.taibunkesimi.util.io.CsvIO
import tw.taibunkesimi.util.io.XlsxIO

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

            outEntry.kipInput = srcEntry.kip
            outEntry.kipUnicode = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kip)

            outEntry.kipInputOther = srcEntry.kipOther
            outEntry.kipUnicodeOther = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToKipUnicode(srcEntry.kipOther)

            outEntry.pojInput = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kip)
            outEntry.pojUnicode = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

            outEntry.pojInputOther = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(srcEntry.kipOther)
            outEntry.pojUnicodeOther = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInputOther)

            outEntry.hoabun = srcEntry.hoabun

            outEntry.english = srcEntry.english
            outEntry.englishSoatbeng = srcEntry.englishSoatbeng

            outEntry.nounClassifiers = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.nounClassifiers)
            outEntry.opposite = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.opposite)
            outEntry.exampleSu = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.exampleSu)
            outEntry.fromSu = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipInputToPojUnicode(srcEntry.fromSu)

            outEntry.pageNumber = srcEntry.pageNumber
//            println("srcEntry.pageNumber:${srcEntry.pageNumber}")

            if (srcEntry.pageNumber.toInt() in 441..660) {
                outEntry.exampleKuTaibunPoj = tw.taibunkesimi.lib.lomajichoanoann.TaigiLomajiKuikuChoanoann.onlyKipUnicodeToPojUnicode(srcEntry.exampleKuTaibun)
            } else {
                outEntry.exampleKuTaibunPoj = srcEntry.exampleKuTaibun
            }
            outEntry.exampleKuHoabun = srcEntry.exampleKuHoabun
            outEntry.exampleKuEnglish = srcEntry.exampleKuEnglish

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

        val path = ChhoeTaigiDatabaseOutputSettings.SAVE_FOLDER_DATABASE + ChhoeTaigiDatabaseOutputSettings.timestamp + SAVE_FILENAME_PATH
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

                "page_number")

        CsvIO.write(path, dict, csvFormat)
    }
}