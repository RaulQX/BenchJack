import java.util.Arrays;

public class SpigotAlgorithm {

        private int digitsRequested;
        private int[] digits;
        private StringBuilder preDigits = new StringBuilder();
        private String allDigits = "";
        private String last10Digits = "";

    public String getAllDigits() {
        return allDigits;
    }

    public String getLast10Digits() {
        return last10Digits;
    }

    // Max value such that digits.length <= INT_MAX.
        //   ceil(((2**31-1) - 1) * 3 / 10)
        private static final int MAX_DIGITS_REQUESTED = 644245094;

        // Get the number of digits requested from the command line arguments.
        public boolean setRequestedDigits(int digits_requested) {

            //TODO CHANGE THIS

            this.digitsRequested = digits_requested;

            if (digits_requested > MAX_DIGITS_REQUESTED) {
                System.err.println("Maximum digit count is " + MAX_DIGITS_REQUESTED);
                return false;
            }

            if (digits_requested <= 10) {
                System.err.println("Digit count must be positive and > 10");
                return false;
            }

            return true;
        }

        // Allocate digits[]
        public boolean init() {
            int array_size_needed = digitsRequested * 10 / 3 + 1;
            digits = new int[array_size_needed];
            if (digits == null) {
                System.err.print("Failed to allocate " + (array_size_needed*4)
                        + " bytes.");
                return false;
            }

            // fill each digit with a 2
            Arrays.fill(digits, 2);

            return true;
        }

        // Produce digits
        void run() {
            if (!init()) return;

            for (int iter = 0; iter < digitsRequested; iter++) {

                // Work backwards through the array, multiplying each digit by 10,
                // carrying the excess and leaving the remainder.
                int carry = 0;
                for (int i = digits.length - 1; i > 0; i--) {
                    int numerator = i;
                    int denominator = i * 2 + 1;
                    int tmp = digits[i] * 10 + carry;
                    digits[i] = tmp % denominator;
                    carry = tmp / denominator * numerator;
                }

                // process the last digit
                int tmp = digits[0] * 10 + carry;
                digits[0] = tmp % 10;
                int digit = tmp / 10;

                // implement buffering and overflow
                if (digit < 9) {
                    flushDigits();
                    // print a decimal after the leading "3"
                    if (iter == 1) //System.out.print(".");
                        allDigits += ".";
                    addDigit(digit);
                } else if (digit == 9) {
                    addDigit(digit);
                } else {
                    overflowDigits();
                    flushDigits();
                    addDigit(0);
                }
                // System.out.flush();
            }
            flushDigits();
            //System.out.println();

            //System.out.println("ALL: " + allDigits);
            last10Digits = allDigits.substring(digitsRequested - 10);
        }

        // write the buffered digits
        void flushDigits() {
            allDigits += preDigits;
            //System.out.append(predigits);
            preDigits.setLength(0);
        }

        // given an integer 0..9, buffer a digit '0' .. '9'
        void addDigit(int digit) {
            preDigits.append((char)('0' + digit));
        }

        // add one to each digit, rolling over from from 9 to 0
        void overflowDigits() {
            for (int i = 0; i < preDigits.length(); i++) {
                char digit = preDigits.charAt(i);
                // This could be implemented with a modulo, but compared to the main
                // loop this code is too quick to measure.
                if (digit == '9') {
                    preDigits.setCharAt(i, '0');
                } else {
                    preDigits.setCharAt(i, (char)(digit + 1));
                }
            }
        }
}
