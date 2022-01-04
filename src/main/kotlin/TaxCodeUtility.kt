import java.util.*
import kotlin.jvm.Throws

/**
 * Validazione di un codice fiscale, utilizando come codice base
 * https://www.icosaedro.it/cf-pi/vedi-codice.cgi?f=cf-java.txt
 */

/**
 * Verifies the basic syntax, length and control code of the given CF.
 */
@Throws(CFInvalidCharacters::class, CFInvalidChecksum::class, CFIsEmpty::class)
fun String.validateCF(): String = with(normalizeCF()) {
    when {
        isNullOrBlank() -> throw CFIsEmpty()
        length == 16 -> validateRegularCF()
        length == 11 -> validateTemporaryCF()
        else -> throw CFInvalidLength()
    }
}


/**
 * Normalizes a CF by removing white spaces and converting to upper-case.
 * Useful to clean-up user's input and to save the result in the DB.
 * @return Normalized CF.
 */
private fun String.normalizeCF(): String =
    replace("[ \t\r\n]".toRegex(), "")
        .uppercase(Locale.getDefault())

/**
 * Validates a regular CF.
 * @return validate regular cf
 * @throws CFInvalidChecksum if string contain wrong chars
 */
@Throws(CFInvalidCharacters::class, CFInvalidChecksum::class)
fun String.validateRegularCF(): String {
    if (!this.matches("^[0-9A-Z]{16}$".toRegex())) throw CFInvalidCharacters()
    var s = 0
    val evenMap = "BAFHJNPRTVCESULDGIMOQKWZYX"
    for (index in 0..14) {
        val char = this[index].code
        var n = if ('0'.code <= char && char <= '9'.code) char - '0'.code else char - 'A'.code
        if (index and 1 == 0) n = evenMap[n] - 'A'
        s += n
    }
    return if (s % 26 + 'A'.code != this[15].code) throw CFInvalidChecksum() else this
}

/**
 * Validates a temporary CF.
 * @return Null if valid, or string describing why this CF must be rejected.
 */
@Throws(CFInvalidCharacters::class, CFInvalidChecksum::class)
private fun String.validateTemporaryCF(): String {
    if (!matches("^[0-9]{11}$".toRegex())) throw CFInvalidCharacters()
    var s = 0
    for (index in 0..10) {
        var n = this[index] - '0'
        if (index and 1 == 1) {
            n *= 2
            if (n > 9) n -= 9
        }
        s += n
    }
    return if (s % 10 != 0) throw CFInvalidChecksum() else this
}

/**
 * Group the cf exceptions
 */
sealed class CFExceptions : Exception()

/**
 * Called when the characters are invalid
 */
class CFInvalidCharacters : CFExceptions()

/**
 * Called when the characters are invalid
 */
class CFInvalidChecksum : CFExceptions()

/**
 * Called when the characters are invalid
 */
class CFIsEmpty : CFExceptions()

/**
 * Called when string contains different cont of chat
 */
class CFInvalidLength : CFExceptions()


