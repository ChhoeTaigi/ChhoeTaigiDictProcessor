package tw.taibunkesimi.phahtaigi.imedict.comparator

import tw.taibunkesimi.phahtaigi.imedict.datamodel.ImeDictItem

class ImeDictItemPojComparator {
    companion object : Comparator<ImeDictItem> {
        override fun compare(o1: ImeDictItem?, o2: ImeDictItem?): Int {
            when {
                o1!!.pojPriorityString.length < o2!!.pojPriorityString.length -> {
                    return -1
                }
                o1.pojPriorityString.length > o2.pojPriorityString.length -> {
                    return 1
                }
                else -> {
                    if (o1.poj == o2.poj && o1.hanji != o2.hanji) {
                        if (o1.hanji.isEmpty() && o2.hanji.isNotEmpty()) {
                            return 1
                        } else if (o1.hanji.isNotEmpty() && o2.hanji.isEmpty()) {
                            return -1
                        }
                    }

                    return o1.pojPriorityString.compareTo(o2.pojPriorityString)
                }
            }
        }
    }
}