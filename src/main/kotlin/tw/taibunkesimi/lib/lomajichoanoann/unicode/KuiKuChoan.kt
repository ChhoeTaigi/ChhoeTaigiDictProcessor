package tw.taibunkesimi.lib.lomajichoanoann.unicode

import tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.ChhiatKoeh
import tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.HybridType
import tw.taibunkesimi.lib.lomajichoanoann.chhiatkoeh.koeh.LomajiKoeh
import tw.taibunkesimi.lib.lomajichoanoann.hethong.ImchatInputHethongChoanoann
import tw.taibunkesimi.lib.lomajichoanoann.unicode.choanoannsoantek.ChoanoannSoantek

object KuiKuChoan {

    fun choanHybridInputWithRegex(str: String, choanoannSoantek: ChoanoannSoantek): String {
        var isKuiKuUppercase = false
        if (str.toUpperCase() == str) {
            isKuiKuUppercase = true
        }

        val koehArrayList: ArrayList<LomajiKoeh>

        when (choanoannSoantek) {
            ChoanoannSoantek.POJ_INPUT_TO_POJ_UNICODE,
            ChoanoannSoantek.POJ_INPUT_TO_KIP_UNICODE,
            ChoanoannSoantek.POJ_INPUT_TO_KIP_INPUT -> {
                koehArrayList = ChhiatKoeh.chhiatHybridInputWithRegex(
                    str,
                    HybridType.HYBRID_TYPE_POJ_INPUT
                )
            }

            ChoanoannSoantek.POJ_UNICODE_TO_POJ_INPUT,
            ChoanoannSoantek.POJ_UNICODE_TO_KIP_INPUT,
            ChoanoannSoantek.POJ_UNICODE_TO_KIP_UNICODE -> {
                koehArrayList = ChhiatKoeh.chhiatHybridInputWithRegex(
                    str,
                    HybridType.HYBRID_TYPE_POJ_UNICODE
                )
            }

            ChoanoannSoantek.KIP_INPUT_TO_KIP_UNICODE,
            ChoanoannSoantek.KIP_INPUT_TO_POJ_UNICODE,
            ChoanoannSoantek.KIP_INPUT_TO_POJ_INPUT -> {
                koehArrayList = ChhiatKoeh.chhiatHybridInputWithRegex(
                    str,
                    HybridType.HYBRID_TYPE_KIP_INPUT
                )
            }

            ChoanoannSoantek.KIP_UNICODE_TO_KIP_INPUT,
            ChoanoannSoantek.KIP_UNICODE_TO_POJ_INPUT,
            ChoanoannSoantek.KIP_UNICODE_TO_POJ_UNICODE -> {
                koehArrayList = ChhiatKoeh.chhiatHybridInputWithRegex(
                    str,
                    HybridType.HYBRID_TYPE_KIP_UNICODE
                )
            }
        }

        val stringBuilder = StringBuilder()
        for (koeh in koehArrayList) {
            var imchatString = koeh.string

            when (choanoannSoantek) {
                ChoanoannSoantek.POJ_UNICODE_TO_POJ_INPUT -> {
                    imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                }

                ChoanoannSoantek.POJ_UNICODE_TO_KIP_INPUT -> {
                    imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                    imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                }

                ChoanoannSoantek.KIP_UNICODE_TO_KIP_INPUT -> {
                    imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                }

                ChoanoannSoantek.KIP_UNICODE_TO_POJ_INPUT -> {
                    imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                    imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                }

                ChoanoannSoantek.POJ_INPUT_TO_POJ_UNICODE -> {
                    if (koeh.isPojInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputToUnicodeChoanoann.pojInputToUnicode(imchatString)
                    }
                }

                ChoanoannSoantek.POJ_INPUT_TO_KIP_UNICODE -> {
                    if (koeh.isPojInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputToUnicodeChoanoann.pojInputToKipUnicode(imchatString)
                    }
                }

                ChoanoannSoantek.KIP_INPUT_TO_KIP_UNICODE -> {
                    if (koeh.isKipInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputToUnicodeChoanoann.kipInputToUnicode(imchatString)
                    }
                }

                ChoanoannSoantek.KIP_INPUT_TO_POJ_UNICODE -> {
                    if (koeh.isKipInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputToUnicodeChoanoann.kipInputToPojUnicode(imchatString)
                    }
                }

                ChoanoannSoantek.POJ_INPUT_TO_KIP_INPUT -> {
                    if (koeh.isPojInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                    }
                }

                ChoanoannSoantek.KIP_INPUT_TO_POJ_INPUT -> {
                    if (koeh.isKipInputNeedToConvertToUnicode()) {
                        imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                    }
                }

                ChoanoannSoantek.POJ_UNICODE_TO_KIP_UNICODE -> {
                    imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                    imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                    imchatString = ImchatInputToUnicodeChoanoann.kipInputToUnicode(imchatString)
                }

                ChoanoannSoantek.KIP_UNICODE_TO_POJ_UNICODE -> {
                    imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                    imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                    imchatString = ImchatInputToUnicodeChoanoann.pojInputToUnicode(imchatString)
                }
            }

            stringBuilder.append(imchatString)
        }
        return stringBuilder.toString()
    }

    fun choanOnlyLomajiInputOrUnicodeWithDelimiter(str: String, choanoannSoantek: ChoanoannSoantek): String {
        var isKuiKuUppercase = false
        if (str.toUpperCase() == str) {
            isKuiKuUppercase = true
        }

        val koehArrayList = ChhiatKoeh.chhiatOnlyLomajiWithDelimiter(str)
        val stringBuilder = StringBuilder()
        for (koeh in koehArrayList) {
            if (!koeh.isDelimiter) {
                var imchatString = koeh.string

                when (choanoannSoantek) {
                    ChoanoannSoantek.POJ_UNICODE_TO_POJ_INPUT -> {
                        imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                    }

                    ChoanoannSoantek.POJ_UNICODE_TO_KIP_INPUT -> {
                        imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                        imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                    }

                    ChoanoannSoantek.KIP_UNICODE_TO_KIP_INPUT -> {
                        imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                    }

                    ChoanoannSoantek.KIP_UNICODE_TO_POJ_INPUT -> {
                        imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                        imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                    }

                    ChoanoannSoantek.POJ_INPUT_TO_POJ_UNICODE -> {
                        imchatString = ImchatInputToUnicodeChoanoann.pojInputToUnicode(imchatString)
                    }

                    ChoanoannSoantek.POJ_INPUT_TO_KIP_UNICODE -> {
                        imchatString = ImchatInputToUnicodeChoanoann.pojInputToKipUnicode(imchatString)
                    }

                    ChoanoannSoantek.KIP_INPUT_TO_KIP_UNICODE -> {
                        imchatString = ImchatInputToUnicodeChoanoann.kipInputToUnicode(imchatString)
                    }

                    ChoanoannSoantek.KIP_INPUT_TO_POJ_UNICODE -> {
                        imchatString = ImchatInputToUnicodeChoanoann.kipInputToPojUnicode(imchatString)
                    }

                    ChoanoannSoantek.POJ_INPUT_TO_KIP_INPUT -> {
                        imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                    }

                    ChoanoannSoantek.KIP_INPUT_TO_POJ_INPUT -> {
                        imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                    }

                    ChoanoannSoantek.POJ_UNICODE_TO_KIP_UNICODE -> {
                        imchatString = ImchatUnicodeToInputChoanoann.pojUnicodeToInput(imchatString, isKuiKuUppercase)
                        imchatString = ImchatInputHethongChoanoann.pojToKip(imchatString)
                        imchatString = ImchatInputToUnicodeChoanoann.kipInputToUnicode(imchatString)
                    }

                    ChoanoannSoantek.KIP_UNICODE_TO_POJ_UNICODE -> {
                        imchatString = ImchatUnicodeToInputChoanoann.kipUnicodeToInput(imchatString)
                        imchatString = ImchatInputHethongChoanoann.kipToPoj(imchatString)
                        imchatString = ImchatInputToUnicodeChoanoann.pojInputToUnicode(imchatString)
                    }
                }
                stringBuilder.append(imchatString)
            } else {
                stringBuilder.append(koeh.string)
            }
        }
        return stringBuilder.toString()
    }
}
