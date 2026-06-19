package com.example.demo._02_movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieTest {

    private final DefaultDiscountPolicy periodConditionAmountPolicy = new AmountDiscountPolicy(
            Money.wons(800),
            new PeriodCondition(
                    DayOfWeek.MONDAY,
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0)
            )
    );

    private final DefaultDiscountPolicy sequenceConditionPercentPolicy = new PercentDiscountPolicy(
            10.0,
            new SequenceCondition(1),
            new SequenceCondition(10));

    @Test
    @DisplayName("영화를 생성하고 영화의 정보를 확인한다")
    public void whenCreateMovie_thenInitializeMovieInfo(){
        //given
        Movie wildsing = new Movie(
                "와일드싱",
                Money.wons(10000),
                Duration.ofMinutes(120),
                periodConditionAmountPolicy
        );
        // then
        Assertions.assertThat(wildsing.getFee()).isEqualTo(Money.wons(10000));
    }

    @Test
    @DisplayName("월요일 오전 10시에 상영하는 영화는 할인 조건을 만족한다.")
    public void whenCreateMovie_thenSatisfiedDiscountCondition(){
        //given
        Movie wildsing = new Movie(
                "와일드싱",
                Money.wons(10000),
                Duration.ofMinutes(120),
                periodConditionAmountPolicy
        );
        Screening screening = new Screening(
                wildsing,
                1,
                LocalDateTime.of(2026, 6, 22, 11, 0)
        );
        //when
        Money discountFee = wildsing.calculateMovieFee(screening);
        Money originFee = wildsing.getFee();
        //then
        Assertions.assertThat(originFee).isNotEqualTo(discountFee);
    }


    @Test
    @DisplayName("영화를 생성하고 영화가 할인 조건을 만족하지 않으면 할인되지 않는다")
    public void whenCreateMovie_thenNotSatisfiedDiscountCondition(){
        //given
        Movie toyStory = new Movie(
                "토이스토리",
                Money.wons(13000),
                Duration.ofMinutes(120),
                sequenceConditionPercentPolicy
        );
        Screening screening = new Screening(
                toyStory,
                3,
                LocalDateTime.of(2026, 6, 22, 11, 0)
        );
        //when
        Money discountFee = toyStory.calculateMovieFee(screening);
        Money originFee = toyStory.getFee();
        //then
        Assertions.assertThat(originFee).isEqualTo(discountFee);
    }
}