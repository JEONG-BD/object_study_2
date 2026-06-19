package com.example.demo._02_movie;

import lombok.Getter;

import java.time.Duration;


public class Movie {

    private String title;
    private Money fee;
    private Duration runningTime;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Money fee, Duration runningTime, DefaultDiscountPolicy discountPolicy) {
        this.title = title;
        this.fee = fee;
        this.runningTime = runningTime;
        this.discountPolicy = discountPolicy;
    }

    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening){
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy){
        this.discountPolicy = discountPolicy;
    }
}
