package com.vsniu.jupiter.algorithm;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/7 13:38
 * @Description: 二分查找算法（有序数组是前提）
 */
public class BinarySearch {
    private static int binarySearch(int[] array,int target){
        /**
         * 在[l...r]的范围里寻找target
         * 闭区间所以 l = 0, r = n - 1
         */
        int l = 0;
        int r = array.length - 1;
        while (l <= r){ //当l <= r时，区间[l...r]仍然是有效的
            /**
             *这里当l和r都足够大的时候，l+r可以出现整型益处，最好用
             * mid = l + (r-l)/2 才最准确
             */
            int mid = ( l + r )/2;
            if (target == array[mid]){
                return mid;
            }else if (target < array[mid]){
                r = mid - 1;
            }else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1,4,6,7,8,9};
        int location = binarySearch(array,8);
        System.out.println(location);
    }
}
