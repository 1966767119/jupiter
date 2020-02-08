package com.vsniu.jupiter.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/8 12:20
 * @Description: 二分搜索树
 */
public class BST <E extends Comparable<E>>{
    /**
     *
     * 功能描述: 树的节点
     * @auther: wangfeng7
     * @date: 2020/2/8 12:22
     */
    private class Node {
        private E e;
        private Node left,right;

        public Node(E e) {
            this.e = e;
        }
    }
    //树的大小
    private int size ;
    //树的根节点
    private Node root;

    public BST() {
        this.size = 0;
        this.root = null;
    }

    /**
     *
     * 功能描述: 向二分搜索树中添加元素
     * @auther: wangfeng7
     * @date: 2020/2/8 12:36
     * @param: e
     */
    public void add(E e){
        root = add(root,e);
    }
    /**
     *
     * 功能描述: 以node为根，向二分搜索树中添加元素(迭代)
     * @auther: wangfeng7
     * @date: 2020/2/8 12:37
     * @param: node,e
     */
    private Node add(Node node ,E e){
        if (node == null){//递归终止条件
            size++;
            return new Node(e);
        }
        if (e.compareTo(node.e) < 0 ){
            node.left = add(node.left,e);
        }else if (e.compareTo(node.e) > 0){
            node.right = add(node.right,e);
        }else {
            //重复元素不处理
        }
        return node;
    }
    /**
     *
     * 功能描述: 查看二分搜索树中是否包含某元素
     * @auther: wangfeng7
     * @date: 2020/2/8 12:43
     * @param: e
     * @return: boolean
     */
    public boolean contains(E e){
        return contains(root,e);
    }
    /**
     *
     * 功能描述: 以node为根的二分搜索树中是否包含某元素，递归算法
     * @auther: wangfeng7
     * @date: 2020/2/8 12:43
     * @param: node,e
     * @return: boolean
     */
    private boolean contains(Node node,E e){
        if (node == null){//递归终止条件
            return false;
        }
        if (e.compareTo(node.e) ==0 ){
            return true;
        }else if (e.compareTo(node.e) < 0){
            return contains(node.left,e);
        }else{
            return contains(node.right,e);
        }
    }
    /**
     *
     * 功能描述: 前序遍历二分搜索树
     * @auther: wangfeng7
     * @date: 2020/2/8 12:53
     */
    public void preOrder(){
        preOrder(root);
    }
    /**
     *
     * 功能描述: 以node为根，前序遍历二分搜索树（递归）
     * @auther: wangfeng7
     * @date: 2020/2/8 12:54
     * @param: node
     */
    private void preOrder(Node node){
        if (node == null){
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }
    /**
     *
     * 功能描述: 前序遍历二分搜索树(非递归)
     * @auther: wangfeng7
     * @date: 2020/2/8 12:53
     */
    public void preOrderNR(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node currentNode = stack.pop();
            System.out.println(currentNode.e);
            if (currentNode.right != null){
                stack.push(currentNode.right);
            }
            if (currentNode.left != null){
                stack.push(currentNode.left);
            }
        }
    }
    /**
     *
     * 功能描述: 中序遍历二分搜索树(非递归)
     * @auther: wangfeng7
     * @date: 2020/2/8 12:53
     */
    public void inOrderNR(){
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            Node tmp = stack.pop();
            System.out.print(tmp.e);
            node = tmp.right;
        }
    }
    /**
     *
     * 功能描述: 二分搜索树的层序遍历
     * @auther: wangfeng7
     * @date: 2020/2/8 22:22
     */
    public void levelOrder(){
        //先进先出队列
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node currentNode = queue.remove();
            System.out.println(currentNode.e);
            if (currentNode.left != null){
                queue.add(currentNode.left);
            }
            if (currentNode.right != null){
                queue.add(currentNode.right);
            }
        }
    }
    /**
     *
     * 功能描述: 删除二分搜索树中最小的node节点
     * @auther: wangfeng7
     * @date: 2020/2/8 20:48
     */
    public E removeMin(){
        E ret = minNode();
        root = removeMin(root);
        return ret;
    }
    /**
     *
     * 功能描述: 删除以node为根的二分搜索树中最小的node节点(递归实现)
     * @auther: wangfeng7
     * @date: 2020/2/8 20:48
     * @param: node
     * @return: node (返回删除节点后新的二分搜索树的根)
     */
    private Node removeMin(Node node){
        if (node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }
    /**
     *
     * 功能描述: 删除以node为根的二分搜索树中最小的node节点(非递归实现)
     * @auther: wangfeng7
     * @date: 2020/2/8 20:48
     * @param: node
     * @return: node (返回删除节点)
     */
    public Node removeMinNR(Node node){
        if (node == null) {
            // 或者通过size判断是否为空也可以
            throw new IllegalArgumentException("BST is Empty!");
        }
        //用minParentNode代表最小node的父节点
        Node minParentNode = null;
        //找到树的最小node
        while (node.left != null){
            minParentNode = node;
            node = node.left;
        }
        minParentNode.left = node.right;
        size --;
        return node;
    }
    /**
     *
     * 功能描述: 删除二分搜索树中最大的node节点
     * @auther: wangfeng7
     * @date: 2020/2/8 20:48
     */
    public E removeMax(){
        E ret = maxNode();
        root = removeMax(root);
        return ret;
    }
    /**
     *
     * 功能描述: 删除以node为根的二分搜索树中最大的node节点(递归实现)
     * @auther: wangfeng7
     * @date: 2020/2/8 20:48
     * @param: node
     * @return: node (返回删除节点后新的二分搜索树的根)
     */
    private Node removeMax(Node node){
        if (node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }
    /**
     *
     * 功能描述:删除元素e
     * @auther: wangfeng7
     * @date: 2020/2/8 21:38
     * @param: e
     */
    public void remove(E e){
        root = remove(root,e);
    }
    /**
     *
     * 功能描述:删除以node为根的二分搜素树中值为e的node节点,递归处理
     * @auther: wangfeng7
     * @date: 2020/2/8 21:38
     * @param: node,e
     * @return node (返回删除节点后新的二分搜索树的根)
     */
    private Node remove(Node node,E e){
        if (node == null || e == null){
            return null;
        }
        if (e.compareTo(node.e) < 0){
            node.left = remove(node.left,e);
            return node;
        }else if (e.compareTo(node.e) > 0){
            node.right = remove(node.right,e);
            return node;
        }else {//e.compareTo(node.e) == 0
            //待删除节点的左子树为空的情况
            if (node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }
            //待删除节点的右子树为空的情况
            if (node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }
            /**
             * 待删除节点的左右子树均不为空的情况，找到比待删除节点大的最小节点（即待删除节点右子树最小的节点），
             * 或者找到比待删除节点小的最大节点（即待删除节点左子树最大的节点），用这个节点顶替待删除节点的位置,
             * successor为后继节点
             */
            Node successor = minNode(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;
            return successor;

//            Node successor = maxNode(node.left);
//            successor.left = removeMax(node.left);
//            successor.right = node.right;
//            node.left = node.right = null;
//            return successor;

        }
    }
    /**
     *
     * 功能描述: 查找二分搜索树中最小元素值
     * @auther: wangfeng7
     * @date: 2020/2/8 20:54
     * @return: E
     */
    public E minNode(){
        if (size == 0){
            throw  new IllegalArgumentException("BST is empty");
        }
        return minNode(root).e;
    }
    /**
     *
     * 功能描述: 查找二分搜索树中最小元素节点(递归查找)
     * @auther: wangfeng7
     * @date: 2020/2/8 20:54
     * @param: node
     * @return: node
     */
    private Node minNode(Node node){
        if (node.left == null){
            return node;
        }else {
            return minNode(node.left);
        }
    }

    /**
     *
     * 功能描述: 查找二分搜索树中最大元素值
     * @auther: wangfeng7
     * @date: 2020/2/8 20:54
     * @return: E
     */
    public E maxNode(){
        if (size == 0){
            throw  new IllegalArgumentException("BST is empty");
        }
        return maxNode(root).e;
    }
    /**
     *
     * 功能描述: 查找二分搜索树中最大元素节点(递归查找)
     * @auther: wangfeng7
     * @date: 2020/2/8 20:54
     * @param: node
     * @return: node
     */
    private Node maxNode(Node node){
        if (node.right == null){
            return node;
        }else {
            return maxNode(node.right);
        }
    }
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] array = {5,3,6,8,4,2};
        for (int m:array){
            bst.add(m);
        }
        System.out.println(bst.size);
        bst.preOrder();

    }
}
