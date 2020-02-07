package com.vsniu.jupiter.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/7 17:01
 * @Description: Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 *
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 * Example 2:
 *
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 */
public class KthLargest {
    public int findKthLargest(int[] nums, int k) {
        sortArray(nums);
        return nums[k-1];
        //如果要求返回的去重的则需要map辅助
//        Map<Integer,Integer> thAndValue = new HashMap<>(nums.length);
//        int lastKth = 1;
//        thAndValue.put(lastKth,nums[0]);
//        for (int m = 0;m<nums.length;m++){
//            thAndValue.put(m+1,nums[m]);
//            if(thAndValue.get(lastKth) == nums[m]){
//                continue;
//            }
//            lastKth = lastKth + 1;
//            thAndValue.put(lastKth,nums[m]);
//        }
//        return thAndValue.get(k);
    }
    private void sortArray(int[] nums){
        for (int i=0;i<nums.length;i++){
            for (int j = i+1;j<nums.length;j++){
                if (nums[i] < nums[j]){
                    //交换
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
    }
    /**
     *
     * 功能描述: 无序数组中查找第K大的元素
     * @auther: wangfeng7
     * @date: 2020/2/7 21:57
     * @param: nums,k
     * @return: 第k大的元素
     */
    public int findKthLargestStandard(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int kSmallest = nums.length - k;
        Random random = new Random();
        while(true) {
            if (left >= right)
                return nums[left];

            int partIdx = left + random.nextInt(right - left);
            partIdx = partition(nums,left, right, partIdx);
            if (kSmallest == partIdx) {
                break;
            } else if (kSmallest < partIdx) {
                right = partIdx - 1;
            } else {
                left = partIdx + 1;
            }
        }
        return nums[kSmallest];
    }
    /**
     *
     * 功能描述:以随机支点位置的值（pivotVal）为分割点，将比pivotVal小的都移动到pivotVal的左边
     * @auther: wangfeng7
     * @date: 2020/2/7 21:21
     * @param: nums,l,r,pivotIdx(随机支点)
     * @return: 返回pivotVal最新的下标
     */
    private int partition(int[] nums,int l, int r, int pivotIdx) {
        int pivotVal = nums[pivotIdx];
        //将随机支点的值pivotVal和最右侧的交换位置
        swap(nums,pivotIdx, r);
        int saveIdx = l;
        for (int i = l; i < r; i++) {
            //将小于pivotVal值的都依次排在左侧
            if (nums[i] < pivotVal) {
                swap(nums,saveIdx++, i);
            }
        }
        swap(nums,saveIdx, r);
        return saveIdx;
    }
    /**
     *
     * 功能描述: 交换两个元素位置
     * @auther: wangfeng7
     * @date: 2020/2/7 21:19
     * @param: nums,i,j
     */
    void swap(int[] nums,int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
