package com.example.Bookstore.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Bookstore.model.Book;
import com.example.Bookstore.model.BookRepository;
import com.example.Bookstore.model.CategoryRepository;

@Controller
public class BookstoreController {
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository crepository;
	
	@RequestMapping(value="/books", method = RequestMethod.GET)
	public @ResponseBody List<Book> booklistRest() {
		return (List<Book>) repository.findAll();
	}
	@RequestMapping(value="/book/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId){
		return repository.findById(bookId);
	}
	
    @RequestMapping(value="/index")
    public String index() {
        return "index";
    }
    
    @RequestMapping(value= {"/", "/booklist"})
    public String bookList(Model model) {
    	model.addAttribute("books", repository.findAll());
    	return "booklist";
    }
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
    	repository.deleteById(bookId);
    	return "redirect:../booklist";
    }
    
    @RequestMapping(value= "/add")
    public String addBook(Model model) {
    	model.addAttribute("book", new Book());
    	model.addAttribute("categories", crepository.findAll());
    	return "addbook";
    }
    
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String save(Book book) {
    	repository.save(book);
    	return "redirect:booklist";
    }
    
    @RequestMapping(value="/edit/{id}")
    public String editBook(@PathVariable("id") Long bookId, Model model) {
    	model.addAttribute("book", repository.findById(bookId));
    	return "editbook";
    }
    
}
