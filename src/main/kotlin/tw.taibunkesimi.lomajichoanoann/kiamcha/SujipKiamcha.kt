package tw.taibunkesimi.lomajichoanoann.kiamcha

class SujipKiamcha {
    val REGEX_SUJIP_KIP_PHENGIM = "(ts|ua|ue|ing|ik)"

    fun isKuiKuPoj(lomajiSujipString: String): Boolean {
        return !lomajiSujipString.contains(REGEX_SUJIP_KIP_PHENGIM.toRegex())
    }

    fun isKuiKuKip(lomajiSujipString: String): Boolean {
        return !isKuiKuPoj(lomajiSujipString)
    }

    fun isImchatPoj(lomajiSujipImchatString: String): Boolean {
        return lomajiSujipImchatString.toLowerCase().matches(LomajiImchatRegex.POJ_INPUT_REGEX.toRegex())
    }

    fun isImchatKip(lomajiSujipImchatString: String): Boolean {
        return lomajiSujipImchatString.toLowerCase().matches(LomajiImchatRegex.KIP_INPUT_REGEX.toRegex())
    }
}
