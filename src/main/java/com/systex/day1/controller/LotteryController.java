package com.systex.day1.controller;

import com.systex.day1.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;


@Controller
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/user/lottery")
    public String showLotteryForm() {
        return "lottery";
    }
    
    @GetMapping("/user/result")
    public String showResult(Model model) {
        return "result";
    }
    

    @PostMapping("/lottery")
    public String generateLotteryNumbers(
            @RequestParam("lottoCount") String lottoCountString,
            @RequestParam("excludeNumbers") String excludeNumberString,
            Model model) {

        LinkedList<String> errorMsgs = new LinkedList<>();
        int lottoCount = 0;
        LinkedList<Integer> excludeNumbersList = new LinkedList<>();

        // 驗證樂透張數
        if (lottoCountString == null || lottoCountString.trim().isEmpty()) {
            errorMsgs.add("樂透張數必須填寫");
        } else {
            try {
                lottoCount = Integer.parseInt(lottoCountString);
                if (lottoCount <= 0) {
                    errorMsgs.add("樂透張數必須大於0");
                }
            } catch (NumberFormatException e) {
                errorMsgs.add("樂透張數必須是數字");
            }
        }

        // 驗證排除的號碼
        if (excludeNumberString != null && !excludeNumberString.trim().isEmpty()) {
            try {
                String[] excludeNumbers = excludeNumberString.split(" ");
                for (String num : excludeNumbers) {
                    int number = Integer.parseInt(num.trim());
                    if (number < 1 || number > 49) {
                        errorMsgs.add("排除號碼必須是1到49之間的數字");
                    } else {
                        excludeNumbersList.add(number);
                    }
                }
            } catch (NumberFormatException e) {
                errorMsgs.add("排除號碼必須是有效的數字");
            }
        }

        // 如果有錯誤信息，返回到表單頁面並顯示錯誤
        if (!errorMsgs.isEmpty()) {
            model.addAttribute("errors", errorMsgs);
            return "lottery";
        }

        // 調用 LotteryService 生成樂透號碼
        try {
            ArrayList<Integer>[] lotteryResults = lotteryService.getNumbers(lottoCount, excludeNumbersList);
            model.addAttribute("lotteryResults", lotteryResults);
            return "result";
        } catch (Exception e) {
            e.printStackTrace();
            errorMsgs.add(e.getMessage());
            model.addAttribute("errors", errorMsgs);
            return "lottery";
        }
    }
}
