package com.demo.service2;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.BaseTest;
import com.demo.entity.Book;

public class BookService2Test extends BaseTest {

	@Resource
	private IBookService bookService;

	@Test
	public void testGetList() throws Exception {
		List<Book> books = bookService.getList("getList2",null);
		for (Book book : books) {
			System.out.println(book);
		}
	}

}
