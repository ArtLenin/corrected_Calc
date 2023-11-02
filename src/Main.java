import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }

    public static String calc(String input) throws IOException {
        Converter converter = new Converter();
        String ans = new String();
        char[] charInput = input.toCharArray();
        int num1;
        int num2;
        Character cursi = ' ';
        int isCorrectSign = 0;
        String[] component = input.split(" ");

        if(component.length > 3 || component.length <= 1)
            throw new IOException("Повторите операцию добавив пробелы.");



        for(int i = 0; i < charInput.length; i++){
            if(charInput[i] == '+' || charInput[i] == '-' || charInput[i] == '*' || charInput[i] == '/'){
                cursi = charInput[i];
                isCorrectSign++;
            }
        }

        if(isCorrectSign != 1){
            throw new IOException("Неверная операция!");
        }

        if(cursi == '+'){
            component = input.split(" \\+ ");
        } else if (cursi == '-') {
            component = input.split(" - ");
        } else if (cursi == '*') {
            component = input.split(" \\* ");
        } else if (cursi == '/') {
            component = input.split(" / ");
        }

        if(!converter.isRoman(component[0]) && !converter.isRoman(component[1])){
            num1 = Integer.parseInt(component[0]);
            num2 = Integer.parseInt(component[1]);

            ans = Integer.toString(converter.calcInArabic(num1, num2, cursi));

        } else if (!converter.isRoman(component[0]) == !converter.isRoman(component[1])) {
            num1 = converter.romanToArabic(component[0]);
            num2 = converter.romanToArabic(component[1]);

            int result = converter.calcInArabic(num1, num2, cursi);

            if(result > 0)
                return converter.ArabicToRoman(result);
            else
                throw new ArithmeticException("Римская система счисления не поддерживает отрицательные числа.");


        } else {
            throw new IOException("Разные системы счисления");
        }

        return ans;
    }

}

class Converter{
    private HashMap<Character, Integer> romanMap = new HashMap<>();

    public Converter(){
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
    }

    public boolean inArrange(int curnum){
        if(curnum >= 1 && curnum <= 10)
            return true;
        else
            return false;
    }
    public int calcInArabic(int num1, int num2, char cursi) throws IOException {
        int ans = -1;
        if(inArrange(num1) && inArrange(num2)){
            switch (cursi) {
                case ('+') -> {
                    ans = num1 + num2;
                }
                case ('-') -> {
                    ans = num1 - num2;
                }
                case ('*') -> {
                    ans = num1 * num2;
                }
                case ('/') -> {
                    try {
                        ans = num1 / num2;
                    } catch (ArithmeticException | InputMismatchException e) {
                        System.err.println("Exception : " + e);
                        System.err.println("Результат может быть только целым, остаток отбрасывается.");
                    }
                }
                default -> throw new IllegalArgumentException("Неверный знак операции.");
            }
        } else {
            throw new IOException("Числа должны быть в диапазоне от 1 до 10.");

        }
        return ans;
    }
5 *
    public boolean isRoman(String number){
        if(romanMap.containsKey(number.charAt(0))){
            return true;
        } else {
            return false;
        }
    }

    public int romanToArabic(String roman) {
        int res = 0;
        int preval = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curval = romanMap.get(roman.charAt(i));

            if (curval < preval) {
                res -= curval;
            } else {
                res += curval;
            }

            preval = curval;
        }

        return res;
    }

    public String ArabicToRoman(int arabic){
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabnum = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < arabnum.length; i++) {
            while (arabic >= arabnum[i]) {
                roman.append(romanSymbols[i]);
                arabic -= arabnum[i];
            }
        }

        return roman.toString();
    }
}