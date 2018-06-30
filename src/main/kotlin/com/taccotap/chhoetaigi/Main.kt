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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OutputSettings {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
    val timestamp = dateFormatter.format(LocalDateTime.now())

    const val SAVE_FOLDER = "./dict_output/"
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
}
