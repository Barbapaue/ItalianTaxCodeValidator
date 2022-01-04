import java.util.*
import kotlin.jvm.Throws

/**
 * Created by Paolo Pasianot on 04/01/22.
 */

/**
 * Library heavily inspired by:
 * https://www.icosaedro.it/cf-pi/vedi-codice.cgi?f=cf-java.txt
 */

/**
 * Verifies the basic syntax, length and control code of the given CF.
 * @throws CFInvalidCharacters if there are any invalid characters
 * @throws CFInvalidChecksum if the composition of the words is not correct
 * @throws CFIsEmpty if the string is empty
 * @throws CFInvalidLength if the string is outside the length parameters
 * @return validate regular cf
 */
@Throws(
    CFInvalidCharacters::class,
    CFInvalidChecksum::class,
    CFIsEmpty::class,
    CFInvalidLength::class
)
fun String.validateCF(): String =
    with(normalizeCF()) {
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
 */
private fun String.normalizeCF(): String =
    replace("[ \t\r\n]".toRegex(), "")
        .uppercase(Locale.getDefault())

/**
 * Validates a regular CF.
 * @throws CFInvalidCharacters if there are any invalid characters
 * @throws CFInvalidChecksum if the composition of the words is not correct
 * @return validate regular cf
 */
@Throws(
    CFInvalidCharacters::class,
    CFInvalidChecksum::class
)
private fun String.validateRegularCF(): String {
    val pattern = "^[A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]$".toRegex()
    //val oldPattern = "^[0-9A-Z]{16}$"
    if (!this.matches(pattern)) throw CFInvalidCharacters()
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
 * @throws CFInvalidCharacters if there are any invalid characters
 * @throws CFInvalidChecksum if the composition of the words is not correct
 * @return validate temporary cf
 */
@Throws(
    CFInvalidCharacters::class,
    CFInvalidChecksum::class
)
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


