package tw.taibunkesimi.lib.lomajichoanoann.hethong

object ImchatInputHethongChoanoann {

    fun pojToKip(pojImchatString: String): String {
        return pojImchatString
                .replace("ch", "ts") // ch -> ts
                .replace("Ch", "Ts") // Ch -> Ts
                .replace("CH", "TS") // CH -> TS
                .replace("o([aAeE])".toRegex()) { "u" + it.value.substring(1) } // oa -> ua, oe -> ue
                .replace("O([aAeE])".toRegex()) { "U" + it.value.substring(1) } // Oa -> Ua, Oe -> Ue
                .replace("ek", "ik") // ek -> ik
                .replace("Ek", "Ik") // Ek -> Ik
                .replace("EK", "IK") // Ek -> IK
                .replace("eng", "ing") // eng -> ing
                .replace("Eng", "Ing") // Eng -> Ing
                .replace("ENG", "ING") // Eng -> ING
                .replace("UR", "IR") // UR -> IR (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("Ur", "Ir") // Ur -> Ir (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("ur", "ir") // ur -> ir (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("OR", "ER") // OR -> ER (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("Or", "Er") // Or -> Er (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("or", "er") // or -> er (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
    }

    fun kipToPoj(kipImchatString: String): String {
        return kipImchatString
                .replace("ts", "ch") // ts -> ch
                .replace("Ts", "Ch") // Ts -> Ch
                .replace("TS", "CH") // TS -> CH
                .replace("u([aAeE])".toRegex()) { "o" + it.value.substring(1) } // ua -> oa, ue -> oe
                .replace("U([aAeE])".toRegex()) { "O" + it.value.substring(1) } // Ua -> Oa, Ue -> Oe
                .replace("ik", "ek") // ik -> ek
                .replace("Ik", "Ek") // Ik -> Ek
                .replace("IK", "EK") // IK -> EK
                .replace("ing", "eng") // ing -> eng
                .replace("Ing", "Eng") // Ing -> Eng
                .replace("ING", "ENG") // Ing -> ENG
                .replace("IR", "UR") // IR -> ER (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("Ir", "Ur") // Ir -> Er (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("ir", "ur") // ir -> er (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("ER", "OR") // ER -> OR (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("Er", "Or") // Er -> Or (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
                .replace("er", "or") // er -> or (POJ kan-na the̍h lâi hō͘ sû-tián kì Hái-kháu-khiuⁿ iōng.)
    }
}
