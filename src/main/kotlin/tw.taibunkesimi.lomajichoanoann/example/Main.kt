package tw.taibunkesimi.lomajichoanoann.example

import tw.taibunkesimi.lomajichoanoann.TaigiLomajiKuikuChoanoann

fun main() {
//    var result = TaigiLomajiSujipKiamcha().isKuPoj("Gua2 si7 chit8 chiah kau2.")
//    println("$result")

//    var result = TaigiLomajiSujipKiamcha().isImchatPoj("Goa2 ")
//    println("$result")

//    val str = "Tē 1 Chiong Tōa-lâng kiò góa mài ōe gím-chôa La̍k-hòe ê sî, góa bat khòaⁿ tio̍h chi̍t tiuⁿ súi tô͘, he sī tī kóng goân-sú chhiū-nâ ê chheh lāi-té, chheh miâ hō-chò: “Chhin-sin ê Kò͘-sū”. Tô͘ seⁿ-chò án-ne: 1a Chheh lāi-té kóng: “Gím-chôa kā lia̍h tio̍h ê mi̍h-kiāⁿ chi̍t chhùi thun lo̍h, lóng bô pō͘. Án-ne i tō bē tín-tāng ah, ài khùn pòaⁿ-nî khì siau-hòa.\" Hit chūn khai-sí, góa chhiâng-chāi ē siūⁿ chhiū-nâ ê thàm-hiám, góa iōng sek-pit ka-tī ōe chi̍t tiṳⁿ tô͘. Góa ê it-hō tô͘ seⁿ-chò án-ne: 1b Góa kā góa ê chok-phín hō͘ tōa-lâng khòaⁿ, mn̄g in kóng, khòaⁿ tio̍h ē kiaⁿ bô. In ìn góa kóng, \"Khòaⁿ bō-á ná ē kiaⁿ?\" Góa ê tô͘ m̄ sī bō-á. He sī gím-chôa teh siau-hòa chhiūⁿ. Án-ne, góa koh ōe gím-chôa ê lāi-té, hō͘ tōa-lâng khòaⁿ. In lóng ài lâng kā soat-bêng. Góa ê tē-jī-hō tô͘ sī án-ne: 1c Tōa-lâng kiò góa mài koh ōe gím-chôa, m̄-koán gōa-kháu a̍h lāi-té, chòe-hó sī khì tha̍k tē-lí, le̍k-sú, kah bûn-hoat. Chū án-ne, la̍k hòe ê sî, góa pàng-lo̍h úi-tāi ê ōe-ka sū-gia̍p. In-ūi it-hō tô͘ kah jī-hō tô͘ ê sit-pāi, hō͘ góa chin sit-chì. Tòa-lâng chóng-sī ka-tī bē liáu-kái, it-ti̍t su-iàu kā in soat-bêng, hō͘ gín-á kám-kak chiok thiám ê. Chū án-ne, góa ài soán pa̍t hāng sū-gia̍p, góa khì o̍h sái hui-hêng-ki. Sè-kài kok-tē góa lóng ū poe-kòe. Góa chin tông-ì, tē-lí tùi góa chin ū-iōng. Góa ē-tàng chi̍t-ē tō hun-pia̍t Tiong-kok kah Arizona. Pòaⁿ-mê bê-lō͘ ê sî, che chin iàu-kín. Tī góa ê seng-oa̍h tiong, góa bat tio̍h chin chē chin jīn-chin ê lâng. Hām tōa-lâng chò-hóe seng-oa̍h, góa kīn-kīn koan-chhat in. Tān-sī góa tùi in ê khòaⁿ-hoat pēng bô kái-piàn. Ū sî, góa tú tio̍h chi̍t-ê ká-ná khah bêng-lí, góa tō the̍h it-hō tô͘ hō͘ khòaⁿ, siūⁿ boeh chai, i sī-m̄-sī ē lí-kái. Khó-sioh i ìn kóng: \"Che sī bō-á.\" Nā án-ne, tùi chit lâng góa bē koh kóng gím-chôa, a̍h-sī tōa chhiū-nâ, a̍h sī thiⁿ-chheⁿ. Góa sūn-ho̍k i ê lí-kái-le̍k. Tōa-lâng khak-si̍t khah kah-ì pêng-pêng siông-siông ê lâng."
//    val result1 = TaigiLomajiChoanoann.kuiKuPojUnicodeToPojInput(str)
////    val result = TaigiLomajiChoanoann.kuiKuKipInputToKipUnicode("hir5 hiri5 her5 here5 Te7 1 Tsiong Tua7-lang5 kio3 gua2 mai3 ue7 gim2-tsua5 Lak8-hue3 e5 si5, gua2 bat khuann3 tioh8 tsit8 tiunn sui2 too5, he si7 ti7 kong2 guan5-su2 tshiu7-na5 e5 tsheh lai7-te2, tsheh mia5 ho7-tso3: “Tshin-sin e5 Koo3-su7”. Too5 senn-tso3 an2-ne: 1a Tsheh lai7-te2 kong2: “Gim2-tsua5 ka7 liah8 tioh8 e5 mih8-kiann7 tsit8 tshui3 thun loh8, long2 bo5 poo7. An2-ne i to7 be7 tin2-tang7 ah, ai3 khun3 puann3-ni5 khi3 siau-hua3.\" Hit tsun7 khai-si2, gua2 tshiang5-tsai7 e7 siunn7 tshiu7-na5 e5 tham3-hiam2, gua2 iong7 sik-pit ka-ti7 ue7 tsit8 tiirnn too5. Gua2 e5 it-ho7 too5 senn-tso3 an2-ne: 1b Gua2 ka7 gua2 e5 tsok-phin2 hoo7 tua7-lang5 khuann3, mng7 in kong2, khuann3 tioh8 e7 kiann bo5. In in3 gua2 kong2, \"Khuann3 bo7-a2 na2 e7 kiann?\" Gua2 e5 too5 m7 si7 bo7-a2. He si7 gim2-tsua5 teh siau-hua3 tshiunn7. An2-ne, gua2 koh ue7 gim2-tsua5 e5 lai7-te2, hoo7 tua7-lang5 khuann3. In long2 ai3 lang5 ka7 suat-bing5. Gua2 e5 te7-ji7-ho7 too5 si7 an2-ne: 1c Tua7-lang5 kio3 gua2 mai3 koh ue7 gim2-tsua5, m7-kuan2 gua7-khau2 ah8 lai7-te2, tsue3-ho2 si7 khi3 thak8 te7-li2, lik8-su2, kah bun5-huat. Tsu7 an2-ne, lak8 hue3 e5 si5, gua2 pang3-loh8 ui2-tai7 e5 ue7-ka su7-giap8. In-ui7 it-ho7 too5 kah ji7-ho7 too5 e5 sit-pai7, hoo7 gua2 tsin sit-tsi3. Tua3-lang5 tsong2-si7 ka-ti7 be7 liau2-kai2, it-tit8 su-iau3 ka7 in suat-bing5, hoo7 gin2-a2 kam2-kak tsiok thiam2 e5. Tsu7 an2-ne, gua2 ai3 suan2 pat8 hang7 su7-giap8, gua2 khi3 oh8 sai2 hui-hing5-ki. Se3-kai3 kok-te7 gua2 long2 u7 pue-kue3. Gua2 tsin tong5-i3, te7-li2 tui3 gua2 tsin u7-iong7. Gua2 e7-tang3 tsit8-e7 to7 hun-piat8 Tiong-kok kah Arizona. Puann3-me5 be5-loo7 e5 si5, tse tsin iau3-kin2. Ti7 gua2 e5 sing-uah8 tiong, gua2 bat tioh8 tsin tse7 tsin jin7-tsin e5 lang5. Ham7 tua7-lang5 tso3-hue2 sing-uah8, gua2 kin7-kin7 kuan-tshat in. Tan7-si7 gua2 tui3 in e5 khuann3-huat ping7 bo5 kai2-pian3. U7 si5, gua2 tu2 tioh8 tsit8-e5 ka2-na2 khah bing5-li2, gua2 to7 theh8 it-ho7 too5 hoo7 khuann3, siunn7 bueh tsai, i si7-m7-si7 e7 li2-kai2. Kho2-sioh i in3 kong2: \"Tse si7 bo7-a2.\" Na7 an2-ne, tui3 tsit lang5 gua2 be7 koh kong2 gim2-tsua5, ah8-si7 tua7 tshiu7-na5, ah8 si7 thinn-tshenn. Gua2 sun7-hok8 i e5 li2-kai2-lik8. Tua7-lang5 khak-sit8 khah kah-i3 ping5-ping5 siong5-siong5 e5 lang5.")
//    val result2 = TaigiLomajiChoanoann.kuiKuPojInputToPojUnicode(result1)
//    println("$result1")
//    println("$str")
//    println("$result2")
//
//    val same = str == result2
//    println("$same")

    val result = TaigiLomajiKuikuChoanoann.hybridPojInputToPojUnicode("Goa2 chit-ma2 beh ka7 是不是li2 kong2 Taiwan e5 siann2-mih8? a")
    val result2 = TaigiLomajiKuikuChoanoann.hybridPojUnicodeToKipUnicode(result)
    println("$result")
    println("$result2")
}
