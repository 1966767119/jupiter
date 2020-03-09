package com.vsniu.jupiter.algorithm;

/**
 * @Auther: wangfeng7
 * @Date: 2020/3/8 19:58
 * @Description: 求数组连续子序列和最大的子数组
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] array = {1,-3,5,6,8,-9,12,-20,16,17,18};
        System.out.println(maxSubList(array));
    }
    /**
     *
     * 功能描述: DP[i] = max{DP[i-1] + A[i],A[i]}
     * @auther: wangfeng7
     * @date: 2020/3/8 20:03
     * @param: array
     * @return: int
     */
    private static int maxSubList(int[] array){
        int maxSum = -1;
        int AddSum=0;
        for(int i=0;i<array.length;++i)
        {
            if(AddSum<0)
                AddSum=array[i];
            else
                AddSum+=array[i];
            if(AddSum>maxSum)
                maxSum=AddSum;
        }
        return maxSum;
    }
}
