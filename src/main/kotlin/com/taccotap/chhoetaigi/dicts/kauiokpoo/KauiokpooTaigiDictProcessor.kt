package com.taccotap.chhoetaigi.dicts.kauiokpoo

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.kauiokpoo.entry.KauiokpooTaigiDictOutEntry
import com.taccotap.chhoetaigi.dicts.kauiokpoo.entry.KauiokpooTaigiDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import org.apache.commons.csv.CSVFormat
import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

object KauiokpooTaigiDictProcessor {
    private const val SRC_FILE_PATH = "KauiokpooTaigiDict20200807/"
    private const val SRC_FILENAME_ALL = "AIO_20200807.xlsx"

    // old files
    private const val SRC_FILENAME_ANOTHER_WORD = "wordsAnother.xlsx"

    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_KauiokpooTaigiSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        return processedDictArray.count()
    }

    // return Hashmap<wordId, SrcEntry>
    private fun loadDict(): HashMap<String, KauiokpooTaigiDictSrcEntry> {
        val dict = HashMap<String, KauiokpooTaigiDictSrcEntry>()

        loadWordDict(dict)
//        loadAnotherWordDict(dict)

        return dict
    }

    private fun loadWordDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
        val wordPropertyMap = loadWordProperty()

        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_ALL")
        println("path: " + resource!!.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "fullTable", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
//            println("recordColumnArrayList.size:${recordColumnArrayList.size}")

            val srcEntry = KauiokpooTaigiDictSrcEntry()

            srcEntry.id = recordColumnArrayList[0]

            val wordPropertyId = recordColumnArrayList[1]
            val wordPropertyString = wordPropertyMap[wordPropertyId] ?: ""
            srcEntry.wordProperty = wordPropertyString

            srcEntry.hanji = recordColumnArrayList[2]

            val wordBunPehPropertyAndPhengim = recordColumnArrayList[3]
            if (wordBunPehPropertyAndPhengim.isEmpty) {
                continue
            } else {
                wordBunPehPropertyAndPhengim.replace("、", "/")
            }

            if (wordBunPehPropertyAndPhengim.contains("【")) {
                srcEntry.wordBunPehProperty = wordBunPehPropertyAndPhengim
//                println("dictSrcEntry.wordBunPehProperty:${srcEntry.wordBunPehProperty}")

                val endIndex = wordBunPehPropertyAndPhengim.indexOf("】")
                srcEntry.kip = wordBunPehPropertyAndPhengim.substring(endIndex + 1)
            } else {
                srcEntry.wordBunPehProperty = ""
                srcEntry.kip = wordBunPehPropertyAndPhengim
            }

            srcEntry.hanjiOther = recordColumnArrayList[4]
            srcEntry.hoabun = recordColumnArrayList[5]

            var anotherWordBunPehPropertyAndPhengim = recordColumnArrayList[6]
            srcEntry.kipOther = ""
            srcEntry.otherWordBunPehProperty = ""
            if (!anotherWordBunPehPropertyAndPhengim.isEmpty) {
                anotherWordBunPehPropertyAndPhengim = anotherWordBunPehPropertyAndPhengim.replace("、", "/")

                if (anotherWordBunPehPropertyAndPhengim.contains("【")) {
                    srcEntry.otherWordBunPehProperty = anotherWordBunPehPropertyAndPhengim
//                    println("dictSrcEntry.anotherWordBunPehProperty:${srcEntry.otherWordBunPehProperty}")

                    srcEntry.kipOther = anotherWordBunPehPropertyAndPhengim
                            .replace("【", "")
                            .replace("】", "")
                            .replace("文", "")
                            .replace("白", "")
                            .replace("俗", "")
                            .replace("替", "")
                } else {
                    srcEntry.otherWordBunPehProperty = ""
                    srcEntry.kipOther = anotherWordBunPehPropertyAndPhengim
                }
            }

            srcEntry.synonym = recordColumnArrayList[7]
            srcEntry.opposite = recordColumnArrayList[8]

            srcEntry.descriptions = recordColumnArrayList[9] + recordColumnArrayList[10] + recordColumnArrayList[11] + recordColumnArrayList[12] + recordColumnArrayList[13] +
                    recordColumnArrayList[14] + recordColumnArrayList[15] + recordColumnArrayList[16] + recordColumnArrayList[17] + recordColumnArrayList[18] +
                    recordColumnArrayList[19] + recordColumnArrayList[20] + recordColumnArrayList[21] + recordColumnArrayList[22] + recordColumnArrayList[23]

            if (recordColumnArrayList[28].isEmpty) {
                srcEntry.dialects = ""
            } else {
                srcEntry.dialects = "【台北】" + recordColumnArrayList[28] +
                        "。【三峽】" + recordColumnArrayList[27] +
                        "。【新竹】" + recordColumnArrayList[34] +
                        "。【台中】" + recordColumnArrayList[35] +
                        "。【鹿港】" + recordColumnArrayList[26] +
                        "。【台南】" + recordColumnArrayList[30] +
                        "。【高雄】" + recordColumnArrayList[31] +
                        "。【宜蘭】" + recordColumnArrayList[29] +
                        "。【馬公】" + recordColumnArrayList[33] + "。"
            }

//            dictSrcEntry.wordKithannKonghoatProperty = ""

            dict[srcEntry.id] = srcEntry
        }
    }

    private fun loadWordProperty(): HashMap<String, String> {
        val wordPropertyMap = HashMap<String, String>()

        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILE_PATH + SRC_FILENAME_ALL)
        println("path: " + resource!!.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "詞目屬性對照", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val numberString = recordColumnArrayList[0]
            var wordProperty = recordColumnArrayList[1]

            // ignore "1: 主詞目"
            if (numberString == "1") {
                wordProperty = ""
            }

            wordPropertyMap[numberString] = wordProperty
        }

        return wordPropertyMap
    }

//    private fun loadAnotherWordDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
//        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_ANOTHER_WORD")
//        println("path: " + resource!!.path)
//
//        val readXlsDictArrayList = XlsxIO.read(resource.path, "iuim", true)
//
//        for (recordColumnArrayList in readXlsDictArrayList) {
//            val mainId = recordColumnArrayList[1]
//            val dictSrcEntry = dict[mainId]
//
//            if (dictSrcEntry == null) {
//                println("loadAnotherWordDict(): Can't find mainId = $mainId")
//                continue
//            }
//
//            dictSrcEntry.kipOther = recordColumnArrayList[2]
//
//            val wordAnotherPropertyIdString = recordColumnArrayList[3]
//
//            when (wordAnotherPropertyIdString) {
//                "1" -> {
//                    dictSrcEntry.wordKithannKonghoatProperty = "又唸作"
//                }
//
//                "2" -> {
//                    dictSrcEntry.wordKithannKonghoatProperty = "俗唸作"
//                }
//
//                "3" -> {
//                    dictSrcEntry.wordKithannKonghoatProperty = "合音唸作"
//                }
//            }
//        }
//    }

    private fun processDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>): List<KauiokpooTaigiDictOutEntry> {
        val processedDictArray = ArrayList<KauiokpooTaigiDictOutEntry>()

        for (srcEntry: KauiokpooTaigiDictSrcEntry in dict.values) {
            val outEntry = KauiokpooTaigiDictOutEntry()

            outEntry.id = srcEntry.id

            outEntry.kipInput = TaigiLomajiKuikuChoanoann.onlyKipUnicodeToKipInput(srcEntry.kip)
            outEntry.kipUnicode = srcEntry.kip
            outEntry.pojInput = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(outEntry.kipInput)
            outEntry.pojUnicode = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInput)

            outEntry.kipInputOther = TaigiLomajiKuikuChoanoann.onlyKipUnicodeToKipInput(srcEntry.kipOther)
            outEntry.kipUnicodeOther = srcEntry.kipOther
            outEntry.pojInputOther = TaigiLomajiKuikuChoanoann.onlyKipInputToPojInput(outEntry.kipInputOther)
            outEntry.pojUnicodeOther = TaigiLomajiKuikuChoanoann.onlyPojInputToPojUnicode(outEntry.pojInputOther)

            outEntry.hanji = srcEntry.hanji
            outEntry.hanjiOther = srcEntry.hanjiOther
            outEntry.hoabun = srcEntry.hoabun

            outEntry.wordProperty = srcEntry.wordProperty
            outEntry.wordBunPehProperty = srcEntry.wordBunPehProperty
            outEntry.otherWordBunPehProperty = srcEntry.otherWordBunPehProperty
//            outEntry.wordKithannKonghoatProperty = srcEntry.wordKithannKonghoatProperty

            outEntry.descriptionsPoj = TaigiLomajiKuikuChoanoann.hybridKipUnicodeToPojUnicode(srcEntry.descriptions)
            outEntry.descriptionsKip = srcEntry.descriptions

            outEntry.dialectsPoj = TaigiLomajiKuikuChoanoann.hybridKipUnicodeToPojUnicode(srcEntry.dialects)
            outEntry.dialectsKip = srcEntry.dialects

            outEntry.synonym = srcEntry.synonym
            outEntry.opposite = srcEntry.opposite

            processedDictArray.add(outEntry)
        }

        // sort
        return processedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<KauiokpooTaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: KauiokpooTaigiDictOutEntry in formattedDictArray) {
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

            outEntry.hanji.let { entryArray.add(it) }
            outEntry.hanjiOther.let { entryArray.add(it) }

            outEntry.wordProperty.let { entryArray.add(it) }
            outEntry.wordBunPehProperty.let { entryArray.add(it) }
            outEntry.otherWordBunPehProperty.let { entryArray.add(it) }
//            outEntry.wordKithannKonghoatProperty.let { entryArray.add(it) }

            outEntry.hoabun.let { entryArray.add(it) }

            outEntry.descriptionsPoj.let { entryArray.add(it) }
            outEntry.descriptionsKip.let { entryArray.add(it) }

            outEntry.dialectsPoj.let { entryArray.add(it) }
            outEntry.dialectsKip.let { entryArray.add(it) }

            outEntry.synonym.let { entryArray.add(it) }
            outEntry.opposite.let { entryArray.add(it) }

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

                "hanji",
                "hanji_other",

                "word_property",
                "word_bunpeh_property",
                "other_word_bunpeh_property",
//                "word_kithannkonghoat_property",

                "hoabun",

                "descriptions_poj",
                "descriptions_kip",

                "dialects_poj",
                "dialects_kip",

                "synonym",
                "opposite")

        CsvIO.write(path, dict, csvFormat)
    }
}