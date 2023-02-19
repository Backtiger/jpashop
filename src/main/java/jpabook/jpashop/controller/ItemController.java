package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }
    @PostMapping("items/new")
    public String create(BookForm form){
        //아래처럼 set으로 일일히 생성하기보단 메서드 만들어서 자동으로 설계되는게 더 좋은 방식
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
    return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
     return "items/itemList";
    }
    //{}하는것을 패스베리어블? 이라하는듯함
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId,Model model){
        Book item = (Book) itemService.findOne(itemId); //item타입으로 반환

        BookForm form = new BookForm();
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setId(item.getId());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);

        return "items/updateItemForm";
    }
    //실무에서는 객체의 데이터가 조작되어서 넣어질 수 있다 따라서 서비스계층이나 프론트쪽에서 권한체크로직을 넣어야함
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute("form") BookForm form)//updateItemForm에서 넘겨주는 값을 파라미터로받음
    {
        //아래 book객체는 준영속entity 식별자가 존재하는 객체는 준영속 entity이다
        //준영속 entity는 jpa가 관리를 하지 않기때문에 트랜잭션에서 관리도 마찬가지로 안됨
       // Book book = new Book();
       // book.setId(form.getId());
       // book.setName(form.getName());
       // book.setPrice(form.getPrice());
       // book.setStockQuantity(form.getStockQuantity());
       // book.setAuthor(form.getAuthor());
       // book.setIsbn(form.getIsbn());
        // itemService.saveItem(book);
        //위방식은 엔티티로 받아서 처리하는방식이나 그냥 아래처럼 필요한 파라미터만 받아 코드를 줄임
        itemService.updatItem(itemId,form.getPrice(),form.getName(),form.getStockQuantity());
        return "redirect:/items";
    }
}
