package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional //transcational에 의해서 변경된 부분을 찾아서 commit 함 아래코드는 파라미터로 변경된값을 다 넣어주기때문에 변경감지의 기능을 수행 반면에 merge는 모든
    public void updatItem (Long itemId , int price , String name , int stockQuantity){
        Item findItem = itemRepository.findOne(itemId); //id를 조회해서 db에서 영속상태의 entity를 찾아옴
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
