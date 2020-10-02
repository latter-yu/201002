import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Main {
    static class InsertionSort {
        public static void insertSort(int[] array) {
            //直接插入排序
            //时间复杂度：O(N^2) 空间复杂度：O(1)
            //稳定性：稳定排序
            //有序区间：[0, bound) 无序区间：[bound, size);
            for (int bound = 1; bound < array.length; bound++) {
                //处理 bound 对应的元素往前插入
                int tmp = array[bound];
                //需要从后往前，找到合适的位置进行插入
                int cur = bound - 1;
                for(; cur >= 0; cur--) {
                    // 如果 cur >= tmp, 则为不稳定排序
                    if(array[cur] > tmp) {
                        array[cur + 1] = array[cur];
                    }else {
                        break;
                    }
                }
                array[cur + 1] = tmp;
            }
        }

        public static void main(String[] args) {
            int[] array = {5, 7, 2, 9, 4, 3, 6, 8};
            insertSort(array);
            System.out.println(Arrays.toString(array));
        }
    }

    class MergeSort {
        //归并排序
        //时间复杂度：O(NlogN) 空间复杂度：O(N) + O(logN)（临时空间）=> O(N)
        //稳定性：稳定排序

        //特点：
        // 1.能高效的针对链表进行排序
        // 2. “外部排序” 的主要实现方式（数据在外存中）
        public void mergeSort(int[] array) {
            // [0, length)
            mergeSortHelper(array, 0, array.length);
        }

        private void mergeSortHelper(int[] array, int left, int right) {
            //[left, right)
            if (right - left <= 1) {
                //当前区间中有 0 或 1 个元素，不需要进行排序
                return;
            }
            //对于 [left, right）区间，分成对等的两个区间
            // [left, mid) [mid, right)
            int mid = (left + right) / 2;
            mergeSortHelper(array, left, mid);
            mergeSortHelper(array, mid, right);
            //已排好序，将两个有序数字进行合并
            merge(array, left, mid, right);
        }

        private void merge(int[] array, int left, int mid, int right) {
            int cur1 = left;
            int cur2 = mid;
            //临时空间需要容纳下两个数组 数量之和
            int[] output = new int[right - left];
            int outputIndex = 0;
            // 当前 output 中被插入了几个元素

            while (cur1 < mid && cur2 < right) {
                if (array[cur1] <= array[cur2]) {
                    // 如果是 < ，无法保证稳定性
                    // 把 cur1 位置的元素插入到 output 中
                    output[outputIndex] = array[cur1];
                    cur1++;
                    outputIndex++;
                } else {
                    output[outputIndex] = array[cur2];
                    cur2++;
                    outputIndex++;
                }
            }
            while (cur1 < mid) {
                output[outputIndex] = array[cur1];
                cur1++;
                outputIndex++;
            }
            while (cur2 < right) {
                output[outputIndex] = array[cur2];
                cur2++;
                outputIndex++;
            }
            //把数据从临时空间中拷贝回原来的数组中
            for (int i = 0; i < right - left; i++) {
                array[left + i] = output[i];
            }
        }
    }

    class Quicker {
        //JAVA 自带排序
        public void main1(String[] args) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(6);
            arrayList.add(3);
            arrayList.add(7);
            arrayList.add(1);
            arrayList.add(5);
            arrayList.add(9);
            arrayList.add(8);
            Collections.sort(arrayList);
            System.out.println(arrayList);

            int[] array = {6, 3, 7, 1, 5, 9, 8};
            Arrays.sort(array);
            System.out.println(arrayList);
            //ctrl + 鼠标左键 跳转到 类/方法 定义
        }

        //非递归实现快速排序(借助一个栈)
        public void byLoop(int[] array) {
            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            stack.push(array.length - 1);
            while (!stack.isEmpty()) {
                int right = stack.pop();
                int left = stack.pop();
                if (left >= right) {
                    //区间为空 或 区间内只有一个元素
                    continue;
                }
                int index = partition(array, left, right);
                //把右子树入栈 [ index + 1, right)
                stack.push(index + 1);
                stack.push(right);
                //把左子树入栈 [left, index - 1)
                stack.push(left);
                stack.push(index - 1);
            }
        }

        private int partition(int[] array, int left, int right) {
            int baseValue = array[right];//设基准值为最后一个元素
            int i = left;
            int j = right;
            while (i < j) {
                //从左往右找到一个 大于基准值 的元素
                while (i < j && array[i] <= baseValue) {
                    i++;
                }
                //再从右往左找到一个 小于基准值 的元素
                while (i < j && array[j] >= baseValue) {
                    j--;
                }

                //此时 j 指向的元素要么和 i 重合，要么小于基准值
                //交换 i 和 j 的值
                if (i < j) {
                    swap(array, i, j);
                }
            }
            //循环结束， i、j 重合,再与基准值交换
            //重合元素一定大于基准值元素
            swap(array, i, right);
            return i;
        }

        private void swap(int[] array, int i, int j) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }
}
