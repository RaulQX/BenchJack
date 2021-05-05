import java.util.Arrays;

public class SpigotAlgorithm {

        private int digits_requested;
        private int[] digits;
        private StringBuilder predigits = new StringBuilder();

        // Max value such that digits.length <= INT_MAX.
        //   ceil(((2**31-1) - 1) * 3 / 10)
        private static final int MAX_DIGITS_REQUESTED = 644245094;

        // Get the number of digits requested from the command line arguments.
        public boolean setRequestedDigits(int digits_requested) {

            this.digits_requested = digits_requested;

            if (digits_requested > MAX_DIGITS_REQUESTED) {
                System.err.println("Maximum digit count is " + MAX_DIGITS_REQUESTED);
                return false;
            }

            if (digits_requested <= 0) {
                System.err.println("Digit count must be positive.");
                return false;
            }

            return true;
        }


        // Allocate digits[]
        public boolean init() {
            int array_size_needed = digits_requested * 10 / 3 + 1;
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

            for (int iter = 0; iter < digits_requested; iter++) {

                // Work backwards through the array, multiplying each digit by 10,
                // carrying the excess and leaving the remainder.
                int carry = 0;
                for (int i=digits.length-1; i > 0; i--) {
                    int numerator = i;
                    int denomenator = i * 2 + 1;
                    int tmp = digits[i] * 10 + carry;
                    digits[i] = tmp % denomenator;
                    carry = tmp / denomenator * numerator;
                }

                // process the last digit
                int tmp = digits[0] * 10 + carry;
                digits[0] = tmp % 10;
                int digit = tmp / 10;

                // implement buffering and overflow
                if (digit < 9) {
                    flushDigits();
                    // print a decimal after the leading "3"
                    if (iter == 1) System.out.print(".");
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
            System.out.println();
        }


        // write the buffered digits
        void flushDigits() {
            System.out.append(predigits);
            predigits.setLength(0);
        }


        // given an integer 0..9, buffer a digit '0' .. '9'
        void addDigit(int digit) {
            predigits.append((char)('0' + digit));
        }


        // add one to each digit, rolling over from from 9 to 0
        void overflowDigits() {
            for (int i=0; i < predigits.length(); i++) {
                char digit = predigits.charAt(i);
                // This could be implemented with a modulo, but compared to the main
                // loop this code is too quick to measure.
                if (digit == '9') {
                    predigits.setCharAt(i, '0');
                } else {
                    predigits.setCharAt(i, (char)(digit + 1));
                }
            }
        }
}
