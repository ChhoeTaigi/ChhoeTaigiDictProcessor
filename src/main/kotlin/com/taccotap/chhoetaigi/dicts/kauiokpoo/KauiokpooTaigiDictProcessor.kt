package com.taccotap.chhoetaigi.dicts.kauiokpoo

import com.taccotap.chhoetaigi.OutputSettings
import com.taccotap.chhoetaigi.dicts.taibuntiongbun.entry.KauiokpooTaigiDictOutEntry
import com.taccotap.chhoetaigi.dicts.taibuntiongbun.entry.KauiokpooTaigiDictSrcEntry
import com.taccotap.chhoetaigi.io.CsvIO
import com.taccotap.chhoetaigi.io.XlsxIO
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import org.apache.commons.csv.CSVFormat

object KauiokpooTaigiDictProcessor {
    private const val SRC_FILE_PATH = "KauiokpooTaigiDict20170515/"
    private const val SRC_FILENAME_WORD = "words.xls"
    private const val SRC_FILENAME_ANOTHER_WORD = "wordsAnother.xls"
    private const val SRC_FILENAME_HOAGI = "hoagi.xls"
    private const val SRC_FILENAME_DESC = "desc.xls"
    private const val SRC_FILENAME_EXAMPLE = "examples.xls"

    private const val SAVE_FILENAME_PATH = "/ChhoeTaigi_KauiokpooTaigiSutian.csv"

    fun run(): Int {
        val dict = loadDict()
        val processedDictArray = processDict(dict)
        saveDict(processedDictArray)
        saveLomajiSearchTable(processedDictArray)
        return processedDictArray.count()
    }

    // return Hashmap<wordId, SrcEntry>
    private fun loadDict(): HashMap<String, KauiokpooTaigiDictSrcEntry> {
        val dict = HashMap<String, KauiokpooTaigiDictSrcEntry>()

        loadWordDict(dict)
        loadAnotherWordDict(dict)
        loadHoagiDict(dict)
        loadDescription(dict)

        return dict
    }

    private fun loadWordDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
        val wordPropertyMap = loadWordProperty()
        val wordBunPehPropertyMap = loadWordBunPehProperty()

        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_WORD")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "詞目總檔", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val dictSrcEntry = KauiokpooTaigiDictSrcEntry()

            dictSrcEntry.id = recordColumnArrayList[0]

            val wordPropertyId = recordColumnArrayList[1]
            val wordPropertyString = wordPropertyMap[wordPropertyId] ?: ""
            dictSrcEntry.wordProperty = wordPropertyString

            dictSrcEntry.hanjiTaibun = recordColumnArrayList[2]
            dictSrcEntry.kiplmj = recordColumnArrayList[3]

            val wordBunPehPropertyId = recordColumnArrayList[4]
            val wordBunPehPropertyString = wordBunPehPropertyMap[wordBunPehPropertyId] ?: ""
            dictSrcEntry.wordBunPehProperty = wordBunPehPropertyString

            // init first
            dictSrcEntry.kiplmjDialect = ""
            dictSrcEntry.wordKithannKhiunnkhauProperty = ""
            dictSrcEntry.hoabun = ""
            dictSrcEntry.descriptions = ""

            dict[dictSrcEntry.id] = dictSrcEntry
        }
    }

    private fun loadWordProperty(): HashMap<String, String> {
        val wordPropertyMap = HashMap<String, String>()

        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILE_PATH + SRC_FILENAME_WORD)
        println("path: " + resource.path)

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

    private fun loadWordBunPehProperty(): HashMap<String, String> {
        val wordBunPehPropertyMap = HashMap<String, String>()

        val resource = Thread.currentThread().contextClassLoader.getResource(SRC_FILE_PATH + SRC_FILENAME_WORD)
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "文白屬性", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val numberString = recordColumnArrayList[0]
            var wordProperty = recordColumnArrayList[1]

            // ignore "0: 複音詞或本典只收一音，不標"
            if (numberString == "0") {
                wordProperty = ""
            }

            wordBunPehPropertyMap[numberString] = wordProperty
        }

        return wordBunPehPropertyMap
    }

    private fun loadAnotherWordDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_ANOTHER_WORD")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "iuim", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val mainId = recordColumnArrayList[1]
            val dictSrcEntry = dict[mainId]

            if (dictSrcEntry == null) {
                println("loadAnotherWordDict(): Can't find mainId = $mainId")
                continue
            }

            dictSrcEntry.kiplmjDialect = recordColumnArrayList[2]

            val wordAnotherPropertyIdString = recordColumnArrayList[3]

            when (wordAnotherPropertyIdString) {
                "1" -> {
                    dictSrcEntry.wordKithannKhiunnkhauProperty = "又唸作"
                }

                "2" -> {
                    dictSrcEntry.wordKithannKhiunnkhauProperty = "俗唸作"
                }

                "3" -> {
                    dictSrcEntry.wordKithannKhiunnkhauProperty = "合音唸作"
                }
            }
        }
    }

    private fun loadHoagiDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_HOAGI")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "華語對應", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val mainId = recordColumnArrayList[0]
            val dictSrcEntry = dict[mainId]

            if (dictSrcEntry == null) {
                println("loadHoagiDict(): Can't find mainId = $mainId")
                continue
            }

            val currentHoagi = recordColumnArrayList[1]

            if (dictSrcEntry.hoabun.isEmpty()) {
                dictSrcEntry.hoabun = currentHoagi
            } else {
                dictSrcEntry.hoabun = "${dictSrcEntry.hoabun}、$currentHoagi"
            }
        }
    }

    private fun loadDescription(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>) {
        val descPropertyMap = loadDescriptionProperty()
        val examplesMap = loadExamples()

        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_DESC")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "sikgi", true)

        for (recordColumnArrayList in readXlsDictArrayList) {
            val mainId = recordColumnArrayList[1]
            val dictSrcEntry = dict[mainId]

            if (dictSrcEntry == null) {
                println("loadDescription(): Can't find mainId = $mainId")
                continue
            }

            // generate desc string
            val currentDescriptionOrder = recordColumnArrayList[2]
            var currentDescriptionOrderString: String
            if (currentDescriptionOrder == "0") {
                currentDescriptionOrderString = ""
            } else {
                currentDescriptionOrderString = "$currentDescriptionOrder. "
            }

            val currentDescriptionPropertyId = recordColumnArrayList[3]
            val currentDescriptionPropertyString = descPropertyMap[currentDescriptionPropertyId] ?: ""
            var currentDescriptionProperty: String
            if (currentDescriptionPropertyString.isEmpty()) {
                currentDescriptionProperty = ""
            } else {
                currentDescriptionProperty = "【$currentDescriptionPropertyString】"
            }

            val currentDescription = recordColumnArrayList[4]

            // generate example string
            val descId = recordColumnArrayList[0]
            val descExampleMap = examplesMap[mainId]
            var currentFullExampleString = ""

            if (descExampleMap != null) {
                currentFullExampleString = descExampleMap[descId] ?: ""
            }

            // merge string
            val currentFullDescriptionString = "$currentDescriptionOrderString$currentDescriptionProperty$currentDescription$currentFullExampleString"

            // store
            if (dictSrcEntry.descriptions.isEmpty()) {
                dictSrcEntry.descriptions = currentFullDescriptionString
            } else {
                dictSrcEntry.descriptions = "${dictSrcEntry.descriptions} $currentFullDescriptionString"
            }
        }
    }

    // return Hashmap<propertyId, propertyName>
    private fun loadDescriptionProperty(): HashMap<String, String> {
        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_DESC")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "詞性對照", true)

        val propertyMap = HashMap<String, String>()
        for (recordColumnArrayList in readXlsDictArrayList) {
            val propertyId = recordColumnArrayList[0]
            val propertyName = recordColumnArrayList[1]

            propertyMap[propertyId] = propertyName
        }

        return propertyMap
    }

    // return HashMap<mainId, HashMap<descId, fullExampleString>>
    private fun loadExamples(): HashMap<String, HashMap<String, String>> {
        val resource = Thread.currentThread().contextClassLoader.getResource("$SRC_FILE_PATH$SRC_FILENAME_EXAMPLE")
        println("path: " + resource.path)

        val readXlsDictArrayList = XlsxIO.read(resource.path, "leku", true)

        val exampleMap = HashMap<String, HashMap<String, String>>()
        for (recordColumnArrayList in readXlsDictArrayList) {
//            val exampleId = recordColumnArrayList[0]
            val mainId = recordColumnArrayList[1]
            val descId = recordColumnArrayList[2]
//            val exampleOrder = recordColumnArrayList[3]
            val exampleHanji = recordColumnArrayList[4]
            val exampleTailo = recordColumnArrayList[5]
            val exampleHoagi = recordColumnArrayList[6]

            val currentDescExampleHoagi: String
            if (exampleHoagi.isEmpty()) {
                currentDescExampleHoagi = ""
            } else {
                currentDescExampleHoagi = " （$exampleHoagi）"
            }

            val currentDescExample = "$exampleTailo $exampleHanji$currentDescExampleHoagi"

            val descExampleMap = exampleMap[mainId]
            if (descExampleMap == null) {
                val currentDescExampleMap = HashMap<String, String>()

                currentDescExampleMap[descId] = "例：$currentDescExample"
                exampleMap[mainId] = currentDescExampleMap
            } else {
                val existDescExample = descExampleMap[descId] ?: ""
                if (existDescExample.isEmpty()) {
                    descExampleMap[descId] = "例：$currentDescExample"
                } else {
                    descExampleMap[descId] = "$existDescExample、$currentDescExample"
                }
            }
        }

        return exampleMap
    }

    private fun processDict(dict: HashMap<String, KauiokpooTaigiDictSrcEntry>): List<KauiokpooTaigiDictOutEntry> {
        val processedDictArray = ArrayList<KauiokpooTaigiDictOutEntry>()

        for (srcEntry: KauiokpooTaigiDictSrcEntry in dict.values) {
            val outEntry = KauiokpooTaigiDictOutEntry()

            outEntry.id = srcEntry.id

            outEntry.kiplmjInput = LomajiConverter.convertLomajiUnicodeString(srcEntry.kiplmj, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_KIPLMJ_UNICODE_TO_KIPLMJ_INPUT)
            outEntry.kiplmjUnicode = srcEntry.kiplmj
            outEntry.pojInput = LomajiConverter.convertLomajiInputString(outEntry.kiplmjInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.pojInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.kiplmjInputDialect = LomajiConverter.convertLomajiUnicodeString(srcEntry.kiplmjDialect, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_KIPLMJ_UNICODE_TO_KIPLMJ_INPUT)
            outEntry.kiplmjUnicodeDialect = srcEntry.kiplmjDialect
            outEntry.pojInputDialect = LomajiConverter.convertLomajiInputString(outEntry.kiplmjInputDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_KIPLMJ_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicodeDialect = LomajiConverter.convertLomajiInputString(outEntry.pojInputDialect, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.hanjiTaibun = srcEntry.hanjiTaibun
            outEntry.hoabun = srcEntry.hoabun

            outEntry.wordProperty = srcEntry.wordProperty
            outEntry.wordBunPehProperty = srcEntry.wordBunPehProperty
            outEntry.wordDialectProperty = srcEntry.wordKithannKhiunnkhauProperty

            outEntry.descriptions = srcEntry.descriptions

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
            outEntry.pojUnicodeDialect.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojInputDialect.let { entryArray.add(it) }

            outEntry.kiplmjUnicode.let { entryArray.add(it) }
            outEntry.kiplmjUnicodeDialect.let { entryArray.add(it) }
            outEntry.kiplmjInput.let { entryArray.add(it) }
            outEntry.kiplmjInputDialect.let { entryArray.add(it) }

            outEntry.wordProperty.let { entryArray.add(it) }
            outEntry.wordBunPehProperty.let { entryArray.add(it) }
            outEntry.wordDialectProperty.let { entryArray.add(it) }

            outEntry.hanjiTaibun.let { entryArray.add(it) }
            outEntry.hoabun.let { entryArray.add(it) }

            outEntry.descriptions.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER_DATABASE + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",

                "poj_unicode",
                "poj_unicode_dialect",
                "poj_input",
                "poj_input_dialect",

                "kiplmj_unicode",
                "kiplmj_unicode_dialect",
                "kiplmj_input",
                "kiplmj_input_dialect",

                "word_property",
                "word_bunpeh_property",
                "word_dialect_property",

                "hanji_taibun",
                "hoabun",

                "descriptions")

        CsvIO.write(path, dict, csvFormat)
    }

    private fun saveLomajiSearchTable(formattedDictArray: List<KauiokpooTaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()

        for (outEntry: KauiokpooTaigiDictOutEntry in formattedDictArray) {
            generateLomajiSearchData(outEntry, dict)
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

    private fun generateLomajiSearchData(outEntry: KauiokpooTaigiDictOutEntry, dict: ArrayList<ArrayList<String>>) {
        // handle default lomaji
        if (outEntry.pojInput.isNotEmpty()) {
            val pojUnicodeWords: List<String> = outEntry.pojUnicode.split("/|,".toRegex())
            val pojInputWords: List<String> = outEntry.pojInput.split("/|,".toRegex())
            val kiplmjUnicodeWords: List<String> = outEntry.kiplmjUnicode.split("/|,".toRegex())
            val kiplmjInputWords: List<String> = outEntry.kiplmjInput.split("/|,".toRegex())

            var pojInputWordCount = pojInputWords.size
            for (i in 0 until pojInputWordCount) {
                val newEntryArray: ArrayList<String> = ArrayList()

                newEntryArray.add(pojUnicodeWords[i].trim())
                newEntryArray.add(pojInputWords[i].trim())
                newEntryArray.add(kiplmjUnicodeWords[i].trim())
                newEntryArray.add(kiplmjInputWords[i].trim())
                newEntryArray.add(outEntry.id)

                dict.add(newEntryArray)
            }
        }

        // handle other lomaji
        if (outEntry.pojInputDialect.isNotEmpty()) {
            val pojUnicodeOtherWords: List<String> = outEntry.pojUnicodeDialect.split("/|,".toRegex())
            val pojInputOtherWords: List<String> = outEntry.pojInputDialect.split("/|,".toRegex())
            val kiplmjUnicodeOtherWords: List<String> = outEntry.kiplmjUnicodeDialect.split("/|,".toRegex())
            val kiplmjInputOtherWords: List<String> = outEntry.kiplmjInputDialect.split("/|,".toRegex())

            var pojInputOtherWordCount = pojInputOtherWords.size
            for (i in 0 until pojInputOtherWordCount) {
                val newEntryArray: ArrayList<String> = ArrayList()

                newEntryArray.add(pojUnicodeOtherWords[i].trim())
                newEntryArray.add(pojInputOtherWords[i].trim())
                newEntryArray.add(kiplmjUnicodeOtherWords[i].trim())
                newEntryArray.add(kiplmjInputOtherWords[i].trim())
                newEntryArray.add(outEntry.id)

                dict.add(newEntryArray)
            }
        }
    }
}