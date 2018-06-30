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

            dictSrcEntry.taigiHanji = recordColumnArrayList[2]
            dictSrcEntry.tailo = recordColumnArrayList[3]

            val wordBunPehPropertyId = recordColumnArrayList[4]
            val wordBunPehPropertyString = wordBunPehPropertyMap[wordBunPehPropertyId] ?: ""
            dictSrcEntry.wordBunPehProperty = wordBunPehPropertyString

            // init first
            dictSrcEntry.tailoKithannKhiunnkhau = ""
            dictSrcEntry.wordKithannKhiunnkhauProperty = ""
            dictSrcEntry.hoagi = ""
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

            dictSrcEntry.tailoKithannKhiunnkhau = recordColumnArrayList[2]

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

            if (dictSrcEntry.hoagi.isEmpty()) {
                dictSrcEntry.hoagi = currentHoagi
            } else {
                dictSrcEntry.hoagi = "${dictSrcEntry.hoagi}、$currentHoagi"
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
        val formattedDictArray = ArrayList<KauiokpooTaigiDictOutEntry>()

        for (srcEntry: KauiokpooTaigiDictSrcEntry in dict.values) {
            val outEntry = KauiokpooTaigiDictOutEntry()

            outEntry.id = srcEntry.id

            outEntry.tailoInput = LomajiConverter.convertLomajiUnicodeString(srcEntry.tailo, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT)
            outEntry.tailoUnicode = srcEntry.tailo
            outEntry.pojInput = LomajiConverter.convertLomajiInputString(outEntry.tailoInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicode = LomajiConverter.convertLomajiInputString(outEntry.pojInput, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.tailoInputKithannKhiunnkhau = LomajiConverter.convertLomajiUnicodeString(srcEntry.tailoKithannKhiunnkhau, LomajiConverter.ConvertLomajiUnicodeStringCase.CASE_TAILO_UNICODE_TO_TAILO_INPUT)
            outEntry.tailoUnicodeKithannKhiunnkhau = srcEntry.tailoKithannKhiunnkhau
            outEntry.pojInputKithannKhiunnkhau = LomajiConverter.convertLomajiInputString(outEntry.tailoInputKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_TAILO_INPUT_TO_POJ_INPUT)
            outEntry.pojUnicodeKithannKhiunnkhau = LomajiConverter.convertLomajiInputString(outEntry.pojInputKithannKhiunnkhau, LomajiConverter.ConvertLomajiInputStringCase.CASE_POJ_INPUT_TO_POJ_UNICODE)

            outEntry.taigiHanji = srcEntry.taigiHanji
            outEntry.hoagi = srcEntry.hoagi

            outEntry.wordProperty = srcEntry.wordProperty
            outEntry.wordBunPehProperty = srcEntry.wordBunPehProperty
            outEntry.wordKithannKhiunnkhauProperty = srcEntry.wordKithannKhiunnkhauProperty

            outEntry.descriptions = srcEntry.descriptions

            formattedDictArray.add(outEntry)
        }

        // sort
        return formattedDictArray.sortedWith(compareBy { it.id.toInt() })
    }

    private fun saveDict(formattedDictArray: List<KauiokpooTaigiDictOutEntry>) {
        val dict: ArrayList<ArrayList<String>> = ArrayList()
        for (outEntry: KauiokpooTaigiDictOutEntry in formattedDictArray) {
            val entryArray: ArrayList<String> = ArrayList()

            outEntry.id.let { entryArray.add(it) }
            outEntry.pojInput.let { entryArray.add(it) }
            outEntry.pojUnicode.let { entryArray.add(it) }
            outEntry.pojInputKithannKhiunnkhau.let { entryArray.add(it) }
            outEntry.pojUnicodeKithannKhiunnkhau.let { entryArray.add(it) }
            outEntry.tailoInput.let { entryArray.add(it) }
            outEntry.tailoUnicode.let { entryArray.add(it) }
            outEntry.tailoInputKithannKhiunnkhau.let { entryArray.add(it) }
            outEntry.tailoUnicodeKithannKhiunnkhau.let { entryArray.add(it) }
            outEntry.wordProperty.let { entryArray.add(it) }
            outEntry.wordBunPehProperty.let { entryArray.add(it) }
            outEntry.wordKithannKhiunnkhauProperty.let { entryArray.add(it) }
            outEntry.taigiHanji.let { entryArray.add(it) }
            outEntry.hoagi.let { entryArray.add(it) }
            outEntry.descriptions.let { entryArray.add(it) }

            dict.add(entryArray)
        }

        val path = OutputSettings.SAVE_FOLDER + OutputSettings.timestamp + SAVE_FILENAME_PATH
        val csvFormat: CSVFormat = CSVFormat.DEFAULT.withHeader(
                "id",
                "poj_input",
                "poj_unicode",
                "poj_other_input",
                "poj_other_unicode",
                "tailo_input",
                "tailo_unicode",
                "tailo_other_input",
                "tailo_other_unicode",
                "word_property",
                "word_bunpeh_property",
                "word_other_property",
                "taigi_hanji",
                "hoagi",
                "descriptions")

        CsvIO.write(path, dict, csvFormat)
    }
}