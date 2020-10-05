package com.taccotap.chhoetaigi.io

import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileInputStream
import java.util.*


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
                var cellCount = getColumnsCount(sheet)
                println("cellCount:$cellCount")

                val rowIterator = sheet.iterator()
                while (rowIterator.hasNext()) {
                    val currentRow = rowIterator.next()

                    if (skipFirstRowHeader && currentRow.rowNum == 0) {
                        continue
                    }

                    val dictArray = ArrayList<String>()
                    for (i in 0 until cellCount) {
                        val currentCell = currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
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

        private fun getColumnsCount(sheet: Sheet): Int {
            var maxColumnCount = 0

                val rowIterator = sheet.iterator()
                while (rowIterator.hasNext()) {
                    val currentRow = rowIterator.next()
                    val currentRowColumnCount = currentRow.lastCellNum + 1
                    if (currentRowColumnCount > maxColumnCount) {
                        maxColumnCount = currentRowColumnCount
                    }
                }

            return maxColumnCount
        }
    }
}