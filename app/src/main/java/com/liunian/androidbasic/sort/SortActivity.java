package com.liunian.androidbasic.sort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.R;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        Button sortButton = (Button) findViewById(R.id.sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int[] array = new int[]{93, 222, 444, 9934, 3, 6, 1, 2, 7, 2, 7, 31};
//                radixSort(array);
//                logArray(array);
                try {
                    Log.i("liunianprint:", "" + strToInt("2143434"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 两个数比较大小，较大的往下沉，较小的冒上来
    public void bubbleSort(int[] array) {
        if (array != null && array.length > 1) { // 如果数组为空或者小于两个数据，则不需要排序
            int temp; // 临时变量定义在循环外面，较少重复定义消耗
            boolean flag; // 记录每次排序的过程是否进行了数据交换
            for (int i = 0; i < array.length - 1; i++) { // 遍历处理数组的第1个到倒数第二个位置的要填充的数据
                flag = false;
                for (int j = array.length - 1; j > i; j--) { // 从数组尾部开始，将较小的数字不断往前推，较大的数字往后排，直到将最小的数字推到第i个位置
                    if (array[j] < array[j - 1]) { // 如果后面的数字小于前面的数字，则将后面的数字和前面的数字交换，相当于把较小的数字往前推
                        temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                        flag = true; // 如果进行了数据交换则将其设置为true
                    }
                }
                if (!flag) { // 如果本次排序过程中没有进行数据交换，则说明后面的数据都已经排序好了，不需要继续排序，退出循环结束排序
                    break;
                }
            }
        }
    }

    // 每次找到最小元素位置，和第一个数据交换
    public void selectSort(int[] array) { // 如果数组为空或者小于两个数据，则不需要排序
        if (array != null && array.length > 1) {
            int temp;
            int minPos;
            for (int i = 0; i < array.length - 1; i++) {
                // 找到第i个位置到最后一个位置之间最小数据的位置
                minPos = i;
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[minPos]) {
                        minPos = j; // 记录最小位置
                    }
                }
                if (minPos != i) { // 如果最小数据的位置不是第i个，则将其数据和第i个位置的数据交换
                    temp = array[i];
                    array[i] = array[minPos];
                    array[minPos] = temp;
                }
            }
        }
    }

    // 假设前k个元素是排序好了的，将第k+1个元素按照排序规则插入到前k个元素中，则前k+1个元素也就排序好了
    public void insertSort(int[] array) {
        if (array != null && array.length > 1) { // 如果数组为空或者小于两个数据，则不需要排序
            int temp;
            for (int i = 0; i < array.length - 1; i++) { // 从0到第i个元素是已经排序好的数据
                for (int j = i + 1; j > 0; j--) { // 将第i+1个元素按照排序规则插入到前i个元素中
                    if (array[j] < array[j - 1]) { // 不断的将较小的元素往前推直到前面的元素都比它小
                        temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                    } else { // 因为前面的元素是排序好了的，所以只要当前元素小于它，就说明前面的元素都要小于它
                        break;
                    }
                }
            }
        }
    }

    // 希尔排序，将数据分为若干组，分别对每组数据进行插入排序，排序完后缩小分组组数继续重复上面过程直到只有一组数据，然后对这一组数据进行插入排序即可完成整个排序过程
    public void shellSort(int[] array) {
        if (array != null && array.length > 1) { // 如果数组为空或者小于两个数据，则不需要排序
            int temp;
            // 不断的将数据分隔成grap组，对每一组数据分别进行插入排序
            int grap = array.length / 2;
            while (grap > 0) { // 当grap=0表示排序完成
                // 对grap组数据分别进行插入排序
                for (int k = 0; k < grap; k++) {
                    for (int i = k + grap; i < array.length; i += grap) {
                        for (int j = i; j > k; j -= grap) {
                            if (array[j] < array[j - grap]) {
                                temp = array[j];
                                array[j] = array[j - grap];
                                array[j - grap] = temp;
                            }
                        }
                    }
                }
                grap = grap / 2; // 每次处理完缩小grap的值
            }
        }
    }

    // 快速排序，采用分治的思想，在所有元素中任意取一个值key，将比其大的元素排在key的左边，比其小的元素排在数组的右边，然后分别对左右两边的元素进行快速排序
    public void quickSort(int[] array, int start, int end) {
        if (array != null && array.length > 0) { // 如果数组为空或者小于两个数据，则不需要排序
            if (end > array.length - 1) {
                end = array.length - 1;
            }
            if (start < end) { // 如果数组中只有一个元素，则不需要对其进行排序
                int i = start;
                int j = end;
                int key = array[start]; // 这里取数组中的第一个元素作为key值，也可以随机从数组中选取，选取完后和第一个数据交换即可
                while (i < j) { // 位置i左边的都是比key值小的元素，位置j右边的都是比key值大的元素，当i等于j时，证明整个数组都处理完毕，位置i左边的元素都是比key值小的，位置i右边的元素都是比key值大的
                    while (j > i && array[j] >= key) { // 从右向左查找，直到找到第一个比key值小的元素
                        j--;
                    }
                    if (j != i) { // 将右边找到的比key值小的元素放在数组的左边
                        array[i] = array[j];
                        i++;
                    }
                    while (i < j && array[i] <= key) { // 从左向右查找，直到找到第一个比key值大的元素
                        i++;
                    }
                    if (i != j) { // 将左边找到的比key值大的元素放在数组的右边
                        array[j] = array[i];
                        j--;
                    }
                }
                array[i] = key; // 将key设置给位置i
                quickSort(array, start, i - 1); // 利用递归对数组左边的元素进行快速排序
                quickSort(array, i + 1, end); // 利用递归对数组右边的元素进行快速排序
            }
        }
    }

    // 归并排序
    public void memerySort(int[] array, int start, int end, int[] temp) { // 需要创建一个临时数组来临时保存合并的数据
        if (array != null && array.length > 0 && temp != null && temp.length >= array.length) { // 如果数组为空或者小于两个数据，则不需要排序
            if (start < end) { // 当分组中只有一个元素时，不需要对该分组进行排序
                int middle = (start + end) / 2; // 找到需要排序的数组的中间位置
                memerySort(array, start, middle, temp); // 利用递归对左边的元素进行归并排序
                memerySort(array, middle + 1, end, temp); // 利用递归对右边的元素进行归并排序
                memery(array, start, middle, end, temp); // 将左右两边排序好的元素进行合并
            }
        }
    }

    // 合并排序好的两个分组（start到middle是一个分组，middle+1到end时一个分组）
    private void memery(int[] array, int start, int middle, int end, int[] temp) {
        int i = start;
        int j = middle + 1;
        int k = start;
        while (i <= middle && j <= end) { // 首先循环比较两个分组直到其中一个结束，将两个分组中较小的元素放在临时数组中
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= middle) { // 如果前面的分组有剩余元素，则将其添加到临时数组尾部，注意，两个分组中只有一个会有剩余元素
            temp[k++] = array[i++];
        }
        while (j <= end) { // 如果后面的分组有剩余元素，则将其添加到临时数组尾部
            temp[k++] = array[j++];
        }

        for (int ii = start; ii <= end; ii++) { // 将合并好的临时数组数据拷贝到数组中
            array[ii] = temp[ii];
        }
    }

    // 基数排序，依次根据每一位的值排序数组，最终完成排序
    public void radixSort(int[] array) {
        // 找到数组中的最大值，以找到最大要排序的位数
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }

        for (int exp = 1; max / exp > 0; exp *= 10) { // 依次根据每一位的值排序数组
            countSort(array, exp);
        }
    }

    // 根据对应位数的值排列数组，如果exp为1表示根据个位数的值来排列，如果exp=10表示根据十位数来排列，以此类推
    private void countSort(int[] array, int exp) {
        int[] temp = new int[array.length];
        int buckets[] = new int[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = 0;
        }
        for (int i = 0; i < array.length; i++) { // buckets记录了相应位数每位数字（0-9）出现的次数
            buckets[(array[i] / exp) % 10]++;
        }
        /**
         * buckets现在记录了相应位数每位数字加上之前所有数字出现的次数，
         * 比如buckets[7]就记录了当前位数上0-7出现的总次数，通过buckets
         * 我们就可以知道对应数字应该插入到哪个范围内，比如对应位数值为7
         * 的数据就应该插入在[buckets[6]，buckets[7])这个区间内，并且之前位数
         * 较大的值应该插入在后面，较小的值应该插入在前面
         */
        for (int i = 0; i < buckets.length - 1; i++) {
            buckets[i + 1] += buckets[i];
        }
        // 注意一定要倒序处理，因为针对对应位数相同数字的数据，也是倒序插入到临时数组中的，所以应该把之前位数较大的先插入
        for (int i = array.length - 1; i >= 0; i--) {
            temp[--buckets[(array[i] / exp) % 10]] = array[i];
        }
        // 复制临时数组到array中
        for (int i = 0; i < array.length; i++) {
            array[i] = temp[i];
        }
    }

    public int strToInt(String str) throws NumberFormatException {
        if (str == null || str.contentEquals("")) { // 如果传入的字符串为空对象或者传入的字符串为空字符串，则抛出异常
            throw new NumberFormatException("null or empty string"); // 这里直接利用java封装好的异常类，当然我们也可以自己封装异常类，面试官要考察的不是对异常类的封装，而是你要知道要处理异常情况
        }
        boolean negative = false; // negative为true表示是负数，反之为正数
        int pos = 0;
        if (str.charAt(0) == '-') { // 如果为负数
            negative = true;
            pos++; // 调过第一位符号位
        } else if (str.charAt(0) == '+') {
            pos++; // 调过第一位符号位
        }
        int limit = negative ? Integer.MIN_VALUE : (-Integer.MAX_VALUE);
        int mult = limit / 10;
        int number = 0;
        while (pos < str.length()) {
            if (str.charAt(pos) >= '0' && str.charAt(pos) <= '9') { // 只有字符在'0'到'9'的范围内，才算正确的字符
                if (number < mult) {
                    throw new NumberFormatException("input string beyond int size");
                }
                number *= 10;
                int digit = str.charAt(pos) - '0';
                if (number < limit + digit) {
                    throw new NumberFormatException("input string beyond int size");
                } else {
                    number -= digit;
                }
                pos++;
            } else {
                throw new NumberFormatException("invalid string"); // 当字符是其他字符时，抛出异常告知调用者传入的字符串错误
            }
        }

        return negative ? number : -number;
    }

    //    public int strToInt(String str) throws Exception {
//        if (str == null || str.contentEquals("")) {
//            throw new NumberFormatException();
//        } else {
//            int number = 0;
//            boolean fuhao = true;
//            int i=0;
//            if (str.charAt(i) == '-') {
//                fuhao = false;
//                i++;
//            } else if (str.charAt(i) == '+') {
//                i++;
//            }
//            if (i == str.length()) {
//                throw new NumberFormatException();
//            }
//            int limit; // 数字的限制范围，这里负数和正数的现在范围不一样
//            if (fuhao) {
//                limit = -Integer.MAX_VALUE; // 这里应该已负数来设置数字的限制范围，因为int型数据的范围是(-2^31)-(2^31-1)，如果采用最大正数来记录数字范围，-Integer.MIN_Value会超出整数的范围
//            } else {
//                limit = Integer.MIN_VALUE;
//            }
//            int multMin = limit / 10;
//            while (i < str.length()) {
//                char curChar = str.charAt(i);
//                if (number < multMin * 10) {
//                    throw new NumberFormatException();
//                }
//                if (curChar >= '0' && curChar <= '9') {
//                    number *= 10;
//                    int curDigit = curChar - '0';
//                    if (number < limit + curDigit) {
//                        throw new NumberFormatException();
//                    }
//                    number -= curDigit;
//                } else {
//                    throw new NumberFormatException();
//                }
//                i++;
//            }
//            if (fuhao) {
//                number = -number;
//            }
//            return number;
//        }
//    }
    public void logArray(int[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                Log.i("liunianprint:", array[i] + "");
            }
        }
    }
}
