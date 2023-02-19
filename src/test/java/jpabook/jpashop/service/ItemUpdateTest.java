package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;
    
        @Test
            public void updatesTest() throws Exception
            {
                //given
                Book book = em.find(Book.class, 1L);
                //merge사용시 변경된 값을 알아서 update해줌
                //이때 변경된것을 감지해서 update후 커밋하는 것을 더티체킹이라고함
                book.setName("ASdf");

        
                //when
            }
}
