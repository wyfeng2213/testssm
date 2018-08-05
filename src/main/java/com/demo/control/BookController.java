package com.demo.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.demo.dto.AppointExecution;
import com.demo.dto.Result;
import com.demo.entity.Book;
import com.demo.enums.AppointStateEnum;
import com.demo.exception.NoNumberException;
import com.demo.exception.RepeatAppointException;
import com.demo.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.github.yedaxia.apidocs.ApiDoc;

@Controller
@RequestMapping("/book") // url:/模块/资源/{id}/细分 /seckill/list
public class BookController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private BookService bookService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private String list(Model model,@RequestBody JSONObject obj) {

		PageHelper.startPage(1, 10,true);

		List<Book> list = bookService.getList();
		model.addAttribute("list", list);

		PageInfo<Book> pageInfo = new PageInfo<>(list);
		// list.jsp + model = ModelAndView
		return "list";// WEB-INF/jsp/"list".jsp
	}

	/**
	 * 
	 * @api {get} /company/list 获取公司信息
	 * @apiName 获取公司列表
	 * @apiGroup All
	 * @apiVersion 0.1.0
	 * @apiDescription 接口详细描述
	 * 
	 * @apiParam {int} pageNum分页大小
	 * 
	 * @apiSuccess {String} code 结果码
	 * @apiSuccess {String} msg 消息说明
	 * @apiSuccess {Object} data 分页数据封装
	 * @apiSuccess {int} data.count 总记录数
	 * @apiSuccess {Object[]} data.list 分页数据对象数组
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK { code:0, msg:'success',
	 *                    data:{} }
	 * 
	 * @apiError All 对应<code>id</code>的用户没找到 asdfasdf
	 * @apiErrorExample {json} Error-Response: HTTP/1.1 404 Not Found { code:1,
	 *                  msg:'user not found', }
	 */
	@RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
	@ApiDoc(Book.class)
	private String detail(@PathVariable("bookId") Long bookId, Model model) {
		if (bookId == null) {
			return "redirect:/book/list";
		}
		Book book = bookService.getById(bookId);
		if (book == null) {
			return "forward:/book/list";
		}
		model.addAttribute("book", book);
		return "detail";
	}

	// ajax json
	@RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId,
			@RequestParam("studentId") Long studentId) {
		if (studentId == null || studentId.equals("")) {
			return new Result<>(false, "学号不能为空");
		}
		AppointExecution execution = null;
		try {
			execution = bookService.appoint(bookId, studentId);
		} catch (NoNumberException e1) {
			execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
		} catch (RepeatAppointException e2) {
			execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
		} catch (Exception e) {
			execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
		}
		return new Result<AppointExecution>(true, execution);
	}

}
