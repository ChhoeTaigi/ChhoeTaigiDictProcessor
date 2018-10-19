package com.taccotap.chhoetaigi.io

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.QuoteMode
import java.io.*
import java.util.*

class CsvIO {

    companion object {
        fun read(path: String, withFirstRecordAsHeader: Boolean): ArrayList<ArrayList<String>> {
            var fileReader: BufferedReader? = null
            var csvParser: CSVParser? = null

            val recordsArrayList = ArrayList<ArrayList<String>>()

            try {
                fileReader = BufferedReader(FileReader(path))
                if (withFirstRecordAsHeader) {
                    csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
                } else {
                    csvParser = CSVParser(fileReader, CSVFormat.DEFAULT.withTrim())
                }

                val csvRecords = csvParser.records

                for (csvRecord in csvRecords) {
                    val recordColumnArrayList = ArrayList<String>()

                    val iterator = csvRecord.iterator()
                    while (iterator.hasNext()) {
                        val columnString = iterator.next()
                        recordColumnArrayList.add(columnString)
                    }

                    recordsArrayList.add(recordColumnArrayList)
                }
            } catch (e: Exception) {
                println("Reading CSV Error!")
                e.printStackTrace()
            } finally {
                try {
                    fileReader?.close()
                    csvParser?.close()
                } catch (e: IOException) {
                    println("Closing fileReader/csvParser Error!")
                    e.printStackTrace()
                }
            }

            return recordsArrayList
        }

        fun write(path: String, recordsArrayList: ArrayList<ArrayList<String>>, csvFormat: CSVFormat) {
            var fileWriter: FileWriter? = null
            var csvPrinter: CSVPrinter? = null

            try {
                val file = File(path.substringBeforeLast("/"))
                file.mkdirs()

                fileWriter = FileWriter(path)
                csvPrinter = CSVPrinter(fileWriter, csvFormat.withQuoteMode(QuoteMode.ALL))

                for (records: ArrayList<String> in recordsArrayList) {
                    csvPrinter.printRecord(records)
                }

                println("Write CSV successfully!")
            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                    csvPrinter!!.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }
        }
    }
}