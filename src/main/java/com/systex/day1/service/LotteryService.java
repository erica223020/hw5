package com.systex.day1.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class LotteryService {
    // 生成隨機樂透號碼的方法
    public ArrayList<Integer>[] getNumbers(int groups, LinkedList<Integer> excludes) {
        ArrayList<Integer>[] lotteryResults = new ArrayList[groups];
        Random random = new Random();

        // 生成樂透號碼，排除指定的號碼
        for (int i = 0; i < groups; i++) {
            ArrayList<Integer> lottoNumbers = new ArrayList<>();
            while (lottoNumbers.size() < 6) {
                int randomNum = random.nextInt(49) + 1;
                if (!excludes.contains(randomNum) && !lottoNumbers.contains(randomNum)) {
                    lottoNumbers.add(randomNum);
                }
            }
            lotteryResults[i] = lottoNumbers; // 將每組號碼存入陣列
        }

        return lotteryResults;
    }
}
