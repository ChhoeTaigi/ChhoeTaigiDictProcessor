package tw.taibunkesimi.phahtaigi.imedict.comparator

import tw.taibunkesimi.phahtaigi.imedict.datamodel.ImeDictItem

class ImeDictItemKipComparator {
    companion object : Comparator<ImeDictItem> {
        override fun compare(o1: ImeDictItem?, o2: ImeDictItem?): Int {
            when {
                o1!!.kipPriorityString.length < o2!!.kipPriorityString.length -> {
                    return -1
                }
                o1.kipPriorityString.length > o2.kipPriorityString.length -> {
                    return 1
                }
                else -> {
                    if (o1.kip == o2.kip && o1.hanji != o2.hanji) {
                        if (o1.hanji.isEmpty() && o2.hanji.isNotEmpty()) {
                            return 1
                        } else if (o1.hanji.isNotEmpty() && o2.hanji.isEmpty()) {
                            return -1
                        }
                    }

                    return o1.kipPriorityString.compareTo(o2.kipPriorityString)
                }
            }
        }
    }
}