package com.example.demo._01_ticket;

import jakarta.persistence.OrderBy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AudienceTest {
    static Ticket ticket;

    @BeforeAll
    static void beforeAll(){
        ticket = new Ticket(1L);
    }

    @Test
    @DisplayName("관람객을 생성한다")
    public void givenBag_whenCreateAudience_thenNotNull(){
        //given
        Bag bag = new Bag(null, 100L);
        //when
        Audience audience = new Audience(bag);
        //then
        Assertions.assertThat(audience).isNotNull();
    }


    @Test
    @DisplayName("초대장을 소유한 관람객은 티켓을 무료 수령한다.")
    public void givenInvitation_whenBuyTicket_thenReturnZeroFee(){
        //given
        Bag bag = new Bag(new Invitation(), 100L);
        Audience audience = new Audience(bag);
        //when
        Long resultFee = audience.buy(ticket);
        //then
        Assertions.assertThat(resultFee).isEqualTo(0L);
    }


    @Test
    @DisplayName("초대장을 소유하지 않은 관람객은 티켓값을 구매한다")
    public void whenAudienceHasNoInvitation_thenPayTicketFee(){
        //given
        Bag bag = new Bag(null, 100L);
        Audience audience = new Audience(bag);
        //when
        Long resultFee = audience.buy(ticket);
        //then
        Assertions.assertThat(resultFee).isEqualTo(1L);
    }

    @Test
    @DisplayName("초대장도 소유하지 않고, 잔액이 부족하면 오류를 발생시킨다")
    public void whenAudienceHasNoInvitationAndInsufficientBalance_thenThrowException(){
        //given
        Bag bag = new Bag(null, 0L);
        Audience audience = new Audience(bag);
        //when

        //then
        assertThatThrownBy(() -> audience.buy(ticket))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔액 부족");
    }
}