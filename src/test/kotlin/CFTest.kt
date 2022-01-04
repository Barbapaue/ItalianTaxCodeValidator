import org.junit.jupiter.api.*
import kotlin.test.*
import kotlin.test.Test

internal class CFTest {

    @Test
    fun `test empty tax code`(){
        assertThrows<CFIsEmpty>{
            "".validateCF()
        }
    }

    @Test
    fun `test invalid tax code with invalid length`(){
        assertThrows<CFInvalidLength>{
            "@@@".validateCF()
        }
    }

    @Test
    fun `test invalid 16 chars tax code with invalid characters`(){
        assertThrows<CFInvalidCharacters>{
            "@@@@@@@@@@@@@@@@".validateCF()
        }
    }

    @Test
    fun `test invalid 11 chars tax code with invalid characters`(){
        assertThrows<CFInvalidCharacters>{
            "@@@@@@@@@@@".validateCF()
        }
    }

    @Test
    fun `test invalid 16 chars tax code with invalid checksum`(){
        assertThrows<CFInvalidChecksum>{
            "MRORSS00A00A000V".validateCF()
        }
    }

    @Test
    fun `test invalid 16 chars tax code with invalid char`(){
        assertThrows<CFInvalidCharacters>{
            "MRORSS00A+0A000V".validateCF()
        }
    }

    @Test
    fun `test invalid 11 chars tax code with invalid characters pt2`(){
        assertThrows<CFInvalidCharacters>{
            "00000+00000".validateCF()
        }
    }

    @Test
    fun `test invalid 11 chars tax code with invalid checksum`(){
        assertThrows<CFInvalidChecksum>{
            "12345678901".validateCF()
        }
    }

    @Test
    fun `test invalid 11 chars tax code with invalid checksum pt2`(){
        assertThrows<CFInvalidChecksum>{
            "00000000001".validateCF()
        }
    }

    @Test
    fun `test valid 16 chars tax code with white spaces and lower case chars`(){
        assertEquals(
            "MRORSS00A00A000U",
            "MRO rSs 00a00 A000U".validateCF()
        )
    }

    @Test
    fun `test valid 16 chars tax code pt1`(){
        assertEquals(
            "KJWMFE88C50E205S",
            "KJWMFE88C50E205S".validateCF()
        )
    }

    @Test
    fun `test valid 16 chars tax code pt2`(){
        assertEquals(
            "GNNTIS14L02X498V",
            "GNNTIS14L02X498V".validateCF()
        )
    }

    @Test
    fun `test valid 16 chars tax code pt3`(){
        assertEquals(
            "JKNXZK26E16Y097M",
            "JKNXZK26E16Y097M".validateCF()
        )
    }

    @Test
    fun `test valid 11 chars tax code pt1`(){
        assertEquals(
            "00000000000",
            "00000000000".validateCF()
        )
    }

    @Test
    fun `test valid 11 chars tax code pt2`(){
        assertEquals(
            "44444444440",
            "44444444440".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code pt3`(){
        assertEquals(
            "12345678903",
            "12345678903".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code pt4`(){
        assertEquals(
            "74700694370",
            "74700694370".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code pt5`(){
        assertEquals(
            "57636564049",
            "57636564049".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code pt6`(){
        assertEquals(
            "19258897628",
            "19258897628".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code pt7`(){
        assertEquals(
            "08882740981",
            "08882740981".validateCF()
        )
    }
    @Test
    fun `test valid 11 chars tax code with white spaces`(){
        assertEquals(
            "47309842806",
            "4730 9842  806".validateCF()
        )
    }
}