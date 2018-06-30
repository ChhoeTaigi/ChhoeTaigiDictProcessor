package com.taccotap.chhoetaigi.io

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream


class XlsxIO {
    companion object {
        fun read(path: String, sheetName: String, skipFirstRowHeader: Boolean): ArrayList<ArrayList<String>> {
            val formatter = DataFormatter()
            var excelFile: FileInputStream? = null
            var workbook: Workbook? = null

            val recordsArrayList = ArrayList<ArrayList<String>>()

            try {
                excelFile = FileInputStream(File(path))
                workbook = WorkbookFactory.create(excelFile)

                val sheet = workbook.getSheet(sheetName)

                val rowIterator = sheet.iterator()
                while (rowIterator.hasNext()) {
                    val currentRow = rowIterator.next()
                    val cellIterator = currentRow.iterator()

                    if (skipFirstRowHeader && currentRow.rowNum == 0) {
                        continue
                    }

                    val dictArray = ArrayList<String>()
                    while (cellIterator.hasNext()) {
                        val currentCell = cellIterator.next()
                        val cellValue: String = formatter.formatCellValue(currentCell)
                        dictArray.add(cellValue)
                    }

                    recordsArrayList.add(dictArray)
                }
            } finally {
                workbook?.close()
                excelFile?.close()
            }

            return recordsArrayList
        }
    }
}