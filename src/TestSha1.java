import java.io.UnsupportedEncodingException;

public class TestSha1 {

    public static void runTest(String msg, String expected, int test_nb) throws UnsupportedEncodingException {
        String result = Sha1.hash(msg, Sha1.OUT_HEXW);

        System.out.println("Test #" + String.valueOf(test_nb));
        System.out.println("Message:  " + msg);
        System.out.println("Result:   " + result);
        System.out.println("Expected: " + expected);

        if (result.equals(expected)) {
            System.out.println("----- SUCCESS -----");
        } else {
            System.out.println("----- FAIL -----");
        }

    }

    public static void main(String[] args) {
        try {
            // Test 1
            long timestamp1 = Long.valueOf("1536150600000");
            java.sql.Timestamp timetest1 = new java.sql.Timestamp(timestamp1);
            String msg = timetest1.toString()+":satoshi:lucia=25.ZI4b]Cg+g2Tr`fn4EB00000613d1aec0be473e97e50e2a9e9f9f3fd73c";
            String expected = "0000062b fc086a10 241b1574 18346447 10e82e0b";
            runTest(msg, expected, 1);

            // Test 2
            long timestamp2 = Long.valueOf("1231477200000");
            java.sql.Timestamp timetest2 = new java.sql.Timestamp(timestamp2);
            msg = timetest2.toString()+":bitcoin:satoshi=50.XNm.c@@*X\4Ff*=9GB200000";
            expected = "00000613 d1aec0be 473e97e5 0e2a9e9f 9f3fd73c";
            runTest(msg, expected, 2);

            // Test 3
            msg = "This is a really long text.";
            expected = "01510c33 89626cee 7a7a8548 8088adb4 500435ba";
            runTest(msg, expected, 3);

        } catch (UnsupportedEncodingException e) {
            System.out.println("EncodingError");
        }
    }
}
