package progra.avanzada.ejercicios.unlam;

public class SumaDeDos {

    static int[] sumaCuadratica(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] nums = { 2, 11, 7, 15 };
        int target = 9;
        int[] result = sumaCuadratica(nums, target);
        System.out.println("[" + result[0] + ", " + result[1] + "]");
    }
}
