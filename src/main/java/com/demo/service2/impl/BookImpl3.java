package com.demo.service2.impl;

import org.springframework.stereotype.Service;

import com.demo.entity.Book;
import com.demo.service2.IBookService;

@Service(value="bookService3")
public class BookImpl3 extends GenericServiceImpl<Book, Integer> implements IBookService {

}
