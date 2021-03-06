package com.study.demo.leetcode;


import com.alibaba.fastjson.JSON;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2021-02-27 10:33
 **/
public class t2 {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1), pre = dummyHead;
        int t = 0;
        while (l1 != null || l2 != null || t != 0) {
            if (l1 != null) {
                t += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                t += l2.val;
                l2 = l2.next;
            }
            pre.next = new ListNode(t % 10);
            pre = pre.next;
            t /= 10;
        }

        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(4, new ListNode(3));
        ListNode listNode = new ListNode(2, listNode1);

        ListNode listNode2 = new ListNode(6, new ListNode(4));
        ListNode listNode3 = new ListNode(5, listNode2);

        ListNode result = addTwoNumbers(listNode, listNode3);
        System.out.println(JSON.toJSONString(result));
    }
}
