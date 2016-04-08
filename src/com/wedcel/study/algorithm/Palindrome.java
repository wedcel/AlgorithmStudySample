package com.wedcel.study.algorithm;

import java.util.Random;

/**
 * class: Palindrome
 * desc: 回文算法
 * auther: wedcel
 * datetime: 2016/4/8 11:06
 */
public class Palindrome {

    
    public static void main(String... agrs) {
        String oddStr = "12212321";

        normalFind(oddStr);
        manacherFind(oddStr);

        String evenStr = "2122122321";

        normalFind(evenStr);
        manacherFind(evenStr);

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<10000000;i++){
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        normalFind(sb.toString());
        manacherFind(sb.toString());
    }


    /**
     *desc: 普通循环算法
     *author: wedcel
     *datetime: 2016/4/8 11:51
     */
    public static void normalFind(String source) {
        System.out.println("normal find start---------");
        long now = System.currentTimeMillis();

        char[] s = source.toCharArray();
        int position = 0;//标记一个最长回文串的中心位置
        int maxLength = 0;//标记最长回文串的长度
        int currentMax = 0;//标记当前max
        for (int i = 0; i < s.length; i++) {

            if (i < s.length - 1 && s[i] == s[i + 1]) {//如果回文串是偶数

                for (int j = 0; (i - j >= 0) && (i + j + 1 < s.length); j++) {
                    if (s[i - j] != s[i + j + 1]) {
                        break;
                    }
                    currentMax = j * 2 + 2;
                }

            } else {//回文串是奇数

                for (int j = 0; (i - j >= 0) && (i + j < s.length); j++) {
                    if (s[i - j] != s[i + j]) {
                        break;
                    }
                    currentMax = j * 2 + 1;

                }

            }
            if (currentMax > maxLength) {
                maxLength = currentMax;
                position = i;
            }
        }
        System.out.println("查找耗时：" + (System.currentTimeMillis() - now));

        System.out.println("回文串长度是：" + maxLength);
        System.out.println("中点位置是：" + position);

        if (maxLength % 2 == 0) {//偶数回文串
            int start = position - (maxLength / 2 - 1);
            int end = position + (maxLength / 2 + 1);
            System.out.println("最后得到的偶数回文串是：" + source.substring(start, end));
        } else {
            int start = position - maxLength / 2;
            int end = position + maxLength / 2 + 1;
            System.out.println("最后得到的奇数回文串是：" + source.substring(start, end));
        }

        System.out.println("normal find end---------");
    }
 
    /**
     *desc: manacher算法
     *author: wedcel
     *datetime: 2016/4/8 11:52
     */
    public static void manacherFind(String source) {
        System.out.println("manacher find start---------");

        StringBuffer sb = new StringBuffer();
        //先格式化
        sb.append("$#");
        for (int i = 0; i < source.length(); i++) {
            sb.append(source.charAt(i));
            sb.append("#");
        }
        long now = System.currentTimeMillis();

        char[] s = sb.toString().toCharArray();

        int maxRight = 0, pos = 0;//最右边    当前最右位置
        int[] rl = new int[s.length];
        for (int i = 1; i < s.length; i++) {

            if (maxRight > i) { //若是i在maxRight的左边

                int jRL = rl[2 * pos - i]; //I对应的pos左侧的J的位置 应该是对称的

                if (jRL + i > maxRight) { //若是j比较长  那说明i应该也超出了maxRignt 那就先设置成maxright  之后再去比较
                    rl[i] = maxRight - i;
                } else {// j在pos 的maxLeft范围内  则i等于 对称的左侧 j 的位置
                    rl[i] = rl[2 * pos - i];
                }

            } else {//若是i在maxRight的右边  那就从头开始
                rl[i] = 1;
            }
            //当 rl[i]+i到最右边的时候就不用循环了
            while (i + rl[i] < s.length && s[i - rl[i]] == s[i + rl[i]]) {
                rl[i]++;
            }

            if (i + rl[i] > maxRight) {
                maxRight = i + rl[i];
                pos = i;
            }
        }

        int maxLength = 0, position = 0;
        for (int i = 1; i < s.length; i++) {
            if (rl[i] > maxLength) {
                position = i;
                maxLength = rl[i];
            }
        }
        System.out.println("查找耗时：" + (System.currentTimeMillis() - now));
        maxLength--;

        int start = position - maxLength;
        int end = position + maxLength;


        StringBuffer result = new StringBuffer();
        for (int i = start; i <= end; i++) {
            if (s[i] != '#') {
                result.append(s[i]);
            }
        }

        System.out.println("回文串长度是：" + maxLength);
        System.out.println("中点位置是：" + position);
        System.out.println("最后得到的奇数回文串是：" + result.toString());

        System.out.println("manacher find end---------");
    }

}
