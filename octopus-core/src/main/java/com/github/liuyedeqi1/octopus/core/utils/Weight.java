package com.github.liuyedeqi1.octopus.core.utils;


/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: 权重基础对象
 * @date 2020/4/2016:06
 */
public class Weight {
    private int index;
    private int weight;

    public Weight(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
