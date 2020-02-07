package com.vsniu.jupiter.algorithm;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/7 15:28
 * @Description: Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * Example:
 *
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 */
public class MoveZeros {
    public void moveZeroes(int[] nums) {
        //数组中0的个数
        int zeroNums = 0;
        for(int i=0;i<nums.length-zeroNums;i++){
            if(nums[i] == 0){
                moveOneStep(i,nums);
                zeroNums++;
                i--;
            }

        }
    }
    /**
     *
     * 功能描述: 从location位置向前移动后面所有的元素"一步"
     * @auther: wangfeng7
     * @date: 2020/2/7 15:30
     * @param: location,nums
     */
    public void moveOneStep(int location,int[] nums){
        for(int m=location;m< nums.length-1;m++){
            nums[m] = nums[m+1];
        }
        nums[nums.length-1]=0;
    }
    public static void main(String[] args){
        MoveZeros solution = new MoveZeros();
        //int[] array = {0,1,0,3,12};
        int[] array = {0,0,1};
        solution.moveZeroes(array);
    }
}
