package com.github.liuyedeqi1.octopus.core.utils;

import java.util.List;
import java.util.Random;


/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: 权重计算函数
 * @date 2020/4/2016:06
 */
public class WeightCalculate {

    private static Random random = new Random();

    public static Integer calculate(List<Weight> weights){

        Integer weightSum = 0;
        for (Weight wc : weights) {
            weightSum += wc.getWeight();
        }

        if (weightSum <= 0) {
            return null;
        }

        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;
        for (Weight wc : weights) {
            if (m <= n && n < m + wc.getWeight()) {
                return wc.getIndex();
            }
            m += wc.getWeight();
        }
        return null;
    }
}
