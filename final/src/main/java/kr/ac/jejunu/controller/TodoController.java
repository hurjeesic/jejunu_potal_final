package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.TodoNumber;
import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {
	private final TodoJpaRepository todoJpaRepository;

	@GetMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ModelAndView modelAndView = new ModelAndView("index");
		List<Todo> todoList = todoJpaRepository.findAllByUser(user);
		if (todoList != null) {
			HashMap<String, TodoNumber> todoNumberList = new HashMap<>();
			for (Todo todo : todoList) {
				Calendar todoDate = todo.getTime();
				String monthStr = (todoDate.get(Calendar.MONTH) < 9 ? "0" : "") + (todoDate.get(Calendar.MONTH) + 1);
				String dateStr = todoDate.get(Calendar.YEAR) + "-" + monthStr + "-" + todoDate.get(Calendar.DATE);
				if (todoNumberList.get(dateStr) == null) {
					todoNumberList.put(dateStr, TodoNumber.builder().total(0).process(0).complete(0).build());
				}
				else {
					TodoNumber todoNumber = todoNumberList.get(dateStr);
					todoNumber.setTotal(todoNumber.getTotal() + 1);
					if (todo.getComplete()) {
						todoNumber.setComplete(todoNumber.getComplete() + 1);
					}
					else {
						todoNumber.setProcess(todoNumber.getProcess() + 1);
					}

					todoNumberList.put(dateStr, todoNumber);
				}
			}

			modelAndView.addObject("todoNumberList", todoNumberList);
		}

		modelAndView.addObject("user", user);

		return modelAndView;
	}

	@GetMapping("/todo/list/{date}")
	public ModelAndView getMyTodoListByDate(HttpServletRequest request, @PathVariable String date) throws ParseException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ModelAndView modelAndView = new ModelAndView("todoList");
		List<Todo> myTodoList = todoJpaRepository.findAllByUser(user);
		List<Todo> myDayTodoList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		final long day = 24 * 60 * 60 * 1000;
		for (Todo todo : myTodoList) {
			if ((todo.getTime().getTimeInMillis() - calendar.getTimeInMillis()) / day == 0) {
				myDayTodoList.add(todo);
			}
		}

		modelAndView.addObject("today", date);
		modelAndView.addObject("todoList", myDayTodoList);
		modelAndView.addObject("user", user);

		return modelAndView;
	}

	@PostMapping("/todo/insert")
	public @ResponseBody Todo insertMyTodo(HttpServletRequest request, @ModelAttribute Todo todo) {
		HttpSession session = request.getSession();

		todo.setUser((User)session.getAttribute("user"));

		return todoJpaRepository.save(todo);
	}

	@DeleteMapping("/todo/delete/{no}")
	public @ResponseBody Integer deleteMyTodo(HttpServletRequest request, @PathVariable Integer no) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		if (todoJpaRepository.findById(no).get().getUser().equals(user)) {
			todoJpaRepository.deleteById(no);

			return no;
		}

		return null;
	}
}
