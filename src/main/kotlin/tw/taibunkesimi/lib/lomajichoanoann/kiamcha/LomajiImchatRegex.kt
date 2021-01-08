package tw.taibunkesimi.lib.lomajichoanoann.kiamcha

object LomajiImchatRegex {
    const val POJ_INPUT_REGEX =
        "(ph|p|m|b|th|t|n|l|kh|k|ng|g|h|chh|ch|s|j)?((?:a|i|u|oo|e|o|r)*)(nn)?(m|ng|n)?([23579]|p8|t8|k8|h8|p|t|k|h)?"

    const val KIP_INPUT_REGEX =
        "(ph|p|m|b|tsh|ts|th|t|n|l|kh|k|ng|g|h|s|j)?((?:a|i|u|oo|e|o|r)*)(nn)?(m|ng|n)?([23579]|p8|t8|k8|h8|p|t|k|h)?"

    const val POJ_UNICODE_REGEX =
        "(ph|p|m|b|th|t|n|l|kh|k|ng|g|h|chh|ch|s|j)?((?:á|à|â|ā|a̍|̍ă|a|í|ì|î|ī|i̍|ĭ|i|ṳ́|ṳ̀|ṳ̂|ṳ̄|ṳ̍|ṳ̆|ṳ|ú|ù|û|ū|u̍|ŭ|u|ó͘|ò͘|ô͘|ō͘|o̍͘|ǒ͘|o͘|ó̤|ò̤|ô̤|ō̤|o̤̍|ŏ̤|o̤|é|è|ê|ē|e̍|ĕ|e|ó|ò|ô|ō|o̍|ŏ|o|r)*)(ⁿ)?(ḿ|m̀|m̂|m̄|m̍|m̆|m|ńg|ǹg|n̂g|n̄g|n̍g|n̆g|ng|ń|ǹ|n̂|n̄|n̍|n̆|n)?(p|t|k|h)?"

    const val KIP_UNICODE_REGEX =
        "(ph|p|m|b|tsh|ts|th|t|n|l|kh|k|ng|g|h|s|j)?((?:á|à|â|ā|a̍|a̋|a|í|ì|î|ī|i̍|̍i̋|i|ú|ù|û|ū|u̍|ű|u|é|è|ê|ē|e̍|e̋|e|ó|ò|ô|ō|o̍|ő|o|r)*)(nn)?(ḿ|m̀|m̂|m̄|m̍|m̆|m|ńg|ǹg|n̂g|n̄g|n̍g|n̆g|ng|ń|ǹ|n̂|n̄|n̍|n̆|n)?(p|t|k|h)?"
}
