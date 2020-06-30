package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.service.TodoService;
import kr.ac.jejunu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {
	private final UserService userService;
	private final TodoService todoService;

	@GetMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("index");
		User user = userService.getUser(session);

		modelAndView.addObject("todoNumberList", todoService.getDateTodoNumber(session));
		modelAndView.addObject("user", user);

		return modelAndView;
	}

	@GetMapping("/todo/list/{date}")
	public ModelAndView getMyTodoListByDate(HttpSession session, @PathVariable String date) throws ParseException {
		ModelAndView modelAndView = new ModelAndView("todoList");
		List<Todo> myDayTodoList = todoService.getDayTodoList(session, date);
		User user = userService.getUser(session);

		modelAndView.addObject("today", date);
		modelAndView.addObject("todoList", myDayTodoList);
		modelAndView.addObject("user", user);

		return modelAndView;
	}

	@PostMapping("/todo/insert")
	@ResponseBody
	public Todo insertTodo(HttpSession session, @ModelAttribute Todo todo) {
		return todoService.insertTodo(session, todo);
	}

	@RequestMapping(value = { "/todo/confirm/{no}", "/todo/update/{no}" })
	public ModelAndView confirmUpdateFormMyTodo(HttpServletRequest request, @PathVariable Integer no) {
		ModelAndView modelAndView = new ModelAndView("updatedTodo");
		String title = todoService.getTitleForUrl(request);

		modelAndView.addObject("no", no);
		modelAndView.addObject("title", title);
		modelAndView.addObject("todo", todoService.getTodo(no));

		return modelAndView;
	}

	@PostMapping("/todo/update/{no}")
	public ModelAndView updateTodo(HttpServletRequest request, @PathVariable Integer no, @ModelAttribute Todo todo,
	                               @Nullable @RequestParam MultipartFile image, @Nullable @RequestParam MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("redirect:/todo/confirm/" + no);

		if (todoService.updateTodo(request, no, todo, image, file) != null) {
			modelAndView.addObject("msg", "수정되었습니다.");
		}

		modelAndView.addObject("todo", todoService.getTodo(no));

		return modelAndView;
	}

	@PutMapping("/todo/complete/{no}")
	@ResponseBody
	public Todo toggleTodoComplete(HttpSession session, @PathVariable Integer no) {
		return todoService.updateTodo(session, no);
	}

	@GetMapping("/todo/download/{no}")
	public void downloadTodoFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer no) throws IOException {
		todoService.downloadFile(request, response, no);
	}

	@DeleteMapping("/todo/delete/{no}")
	@ResponseBody
	public Integer deleteTodo(HttpSession session, @PathVariable Integer no) {
		return todoService.deleteTodo(session, no);
	}
}