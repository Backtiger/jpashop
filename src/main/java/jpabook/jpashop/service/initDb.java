package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDb {

    private final  InitService initServic;

    @PostConstruct
    public void init(){
        initServic.dbInit1();
        initServic.dbInit2();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService
    {
        private final EntityManager em;
        public void dbInit1()
        {
            Member member = CreateMember("userA","서울","1","1111");
            em.persist(member);

            Book book1 = createBook("JPA BOOK1",10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA BOOK2",20000, 100);
            em.persist(book2);

            Orderitem orderItem1 = Orderitem.createOrderItem(book1, 10000, 1);
            Orderitem orderItem2 = Orderitem.createOrderItem(book2, 20000, 1);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }

        public void dbInit2()
        {
            Member member = CreateMember("userA","부산","2","1111");
            em.persist(member);

            Book book1 = createBook("Spring1 BOOK",20000, 200);
            em.persist(book1);

            Book book2 = createBook("Spring2 BOOK",40000, 300);
            em.persist(book2);

            Orderitem orderItem1 = Orderitem.createOrderItem(book1, 20000, 3);
            Orderitem orderItem2 = Orderitem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }
        @NotNull
        private  Member CreateMember(String name,String city, String street, String zipcode) {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }
        @NotNull
        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        @NotNull
        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity); // ctrl + alt + p 자동파라미터생성
            return book1;
        }

    }
}

