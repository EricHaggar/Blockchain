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
            String msg = "abc";
            String expected = "a9993e36 4706816a ba3e2571 7850c26c 9cd0d89d";
            runTest(msg, expected, 1);

            // Test 2
            msg = "test";
            expected = "a94a8fe5 ccb19ba6 1c4c0873 d391e987 982fbbd3";
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