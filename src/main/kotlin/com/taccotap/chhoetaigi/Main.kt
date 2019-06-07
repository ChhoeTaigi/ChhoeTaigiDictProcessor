package com.taccotap.chhoetaigi

import com.taccotap.chhoetaigi.dicts.embree.EmbreeDictProcessor
import com.taccotap.chhoetaigi.dicts.itaigi.ITaigiDictProcessor
import com.taccotap.chhoetaigi.dicts.kam.KamDictProcessor
import com.taccotap.chhoetaigi.dicts.kauiokpoo.KauiokpooTaigiDictProcessor
import com.taccotap.chhoetaigi.dicts.maryknoll.MaryknollTaiEngDictProcessor
import com.taccotap.chhoetaigi.dicts.taihoa.TaihoaDictProcessor
import com.taccotap.chhoetaigi.dicts.taijit.TaiJitDictProcessor
import com.taccotap.chhoetaigi.dicts.taioanpehoegiku.TaioanPehoeKichhooGikuProcessor
import com.taccotap.chhoetaigi.dicts.taioansitbutmialui.TaioanSitbutMialuiProcessor
import com.taccotap.chhoetaigi.lomajiutils.LomajiConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OutputSettings {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
    val timestamp = dateFormatter.format(LocalDateTime.now())

    const val SAVE_FOLDER_DATABASE = "./output_database/"
    const val SAVE_FOLDER_LMJ_SEARCH_TABLE = "/lomaji_search_table"
}

fun main(args: Array<String>) {
    val count1 = EmbreeDictProcessor.run()
    val count2 = ITaigiDictProcessor.run()
    val count3 = KamDictProcessor.run()
    val count4 = KauiokpooTaigiDictProcessor.run()
    val count5 = MaryknollTaiEngDictProcessor.run()
    val count6 = TaihoaDictProcessor.run()
    val count7 = TaiJitDictProcessor.run()
    val count8 = TaioanPehoeKichhooGikuProcessor.run()
    val count9 = TaioanSitbutMialuiProcessor.run()

    println()

    println("${EmbreeDictProcessor.javaClass.simpleName}: $count1")
    println("${ITaigiDictProcessor.javaClass.simpleName}: $count2")
    println("${KamDictProcessor.javaClass.simpleName}: $count3")
    println("${KauiokpooTaigiDictProcessor.javaClass.simpleName}: $count4")
    println("${MaryknollTaiEngDictProcessor.javaClass.simpleName}: $count5")
    println("${TaihoaDictProcessor.javaClass.simpleName}: $count6")
    println("${TaiJitDictProcessor.javaClass.simpleName}: $count7")
    println("${TaioanPehoeKichhooGikuProcessor.javaClass.simpleName}: $count8")
    println("${TaioanSitbutMialuiProcessor.javaClass.simpleName}: $count9")

    println()

    val total = count1 + count2 + count3 + count4 + count5 + count6 + count7 + count8 + count9
    println("Total: $total")

//    testPojSianntiauPos()
}

//fun testPojSianntiauPos() {
//    val a1 = listOf("a", "i", "u", "o͘", "e", "o")
//    val a2 = listOf("am", "an", "ang", "im", "in", "eng", "om", "ong")
//    val a3 = listOf("ai", "au", "ia", "io", "iu", "ui", "oa", "oe", "iau", "oai")
//    val a4 = listOf("iam", "ian", "iang", "iong", "oan", "oang")
//    val a5 = listOf("ap", "at", "ak", "ah", "ip", "it", "ih", "ut", "uh", "op", "ok", "o͘ h", "ek", "eh", "oh")
//    val a6 = listOf("aih", "auh", "iah", "ioh", "iuh", "uih", "oah", "oeh", "iauh", "oaih")
//    val a7 = listOf("iap", "iat", "iak", "iok", "oat")
//    val a8 = listOf("m", "n", "ng", "mh", "ngh")
//    val a9 = listOf("giern", "pherh", "her", "hiriⁿ", "girn", "chere", "girn", "cherh", "chiriⁿ", "ir")
//
//    for (s in a1) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a2) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a3) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a4) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a5) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a6) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a7) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a8) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//    for (s in a9) {
//        val position = LomajiConverter.getPojSianntiauPosition(s)
//        println("$s = ${position?.pos}")
//    }
//}