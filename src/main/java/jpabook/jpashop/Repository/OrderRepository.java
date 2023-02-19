package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em ;

    public void save(jpabook.jpashop.domain.Order order){
        em.persist(order);
    }

    public jpabook.jpashop.domain.Order findOne(Long id){
        return em.find(jpabook.jpashop.domain.Order.class,id);
    }
  // 동적쿼리를 문자열 조합해서 사용하는방법
    //문자조합은 에러가 많이나서 안씀
   public List<jpabook.jpashop.domain.Order> findAllByString(OrderSearch orderSearch) {
       String jpql = "select o from Order o join o.member m ";
       boolean isFirstCondition = true;
       //주문상태 검색
       if (orderSearch.getOrderStatus() != null) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " o.status =:status";
       }
       //회원이름검색
       if (StringUtils.hasText(orderSearch.getMemberName())) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " m.name like :name";
       }


       TypedQuery<jpabook.jpashop.domain.Order> query = em.createQuery(jpql, jpabook.jpashop.domain.Order.class).setMaxResults(1000); //행 개수제한 1000개

       if (orderSearch.getOrderStatus() != null) {
           query = query.setParameter("status", orderSearch.getOrderStatus());
       }
       if (StringUtils.hasText(orderSearch.getMemberName())) {
           query = query.setParameter("name", orderSearch.getMemberName());
       }
       List<jpabook.jpashop.domain.Order> resultList = query.getResultList();
       return resultList;
   }
    // jpa criteria jpa가 동적쿼리를 작성하게 해주는 표준 방법
    // 가독성이 떨어져서 실무에선 안씀
   public List<jpabook.jpashop.domain.Order> findAllByCriteria(OrderSearch orderSearch){
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<jpabook.jpashop.domain.Order> cq = cb.createQuery(jpabook.jpashop.domain.Order.class);
       Root<jpabook.jpashop.domain.Order> o = cq.from(jpabook.jpashop.domain.Order.class);

       Join<Object, Object> m = o.join("member", JoinType.INNER);

       List<Predicate> criteria = new ArrayList<>();
        //주문상태검색
       if(orderSearch.getOrderStatus()!=null){
           Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
           criteria.add(status);
       }
       //회원 이름 검색
       if(StringUtils.hasText(orderSearch.getMemberName())){
           Predicate name = cb.like(m.<String>get("name"),"%"+orderSearch.getMemberName()+"%");
           criteria.add(name);
       }
       cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
       TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

       return query.getResultList();
   }
   //Querydsl은 동적쿼리를 손쉽게 해주기 위한 라이브러리 Querydsl를 사용함
}
