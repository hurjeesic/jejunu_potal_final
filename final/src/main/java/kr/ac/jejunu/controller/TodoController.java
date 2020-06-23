package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.TodoNumber;
import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
					if (todo.getComplete()) {
						todoNumberList.put(dateStr, TodoNumber.builder().total(1).process(0).complete(1).build());
					}
					else {
						todoNumberList.put(dateStr, TodoNumber.builder().total(1).process(1).complete(0).build());
					}
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
	@ResponseBody
	public Todo insertMyTodo(HttpServletRequest request, @ModelAttribute Todo todo) {
		HttpSession session = request.getSession();

		todo.setUser((User)session.getAttribute("user"));

		return todoJpaRepository.save(todo);
	}

	@RequestMapping(value = { "/todo/confirm/{no}", "/todo/update/{no}" })
	public ModelAndView confirmUpdateFormMyTodo(HttpServletRequest request, @PathVariable Integer no) {
		ModelAndView modelAndView = new ModelAndView("update");

		String url = request.getRequestURL().toString();
		String[] urlAry = url.split("/");
		String title = null;
		switch (urlAry[urlAry.length - 2]) {
			case "confirm":
				title = "Todo 확인";
				break;
			case "update":
				title = "Todo 수정";
				break;
		}

		modelAndView.addObject("no", no);
		modelAndView.addObject("title", title);
		modelAndView.addObject("todo", todoJpaRepository.findById(no).get());

		return modelAndView;
	}

	@PostMapping("/todo/update/{no}")
	public ModelAndView updateMyTodo(HttpServletRequest request, @PathVariable Integer no, @ModelAttribute Todo todo, @Nullable MultipartFile image, @Nullable MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("redirect:/todo/confirm/" + no);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		Todo original = todoJpaRepository.findById(no).get();
		if (original.getUser().equals(user)) {
			System.out.println(todo.toString());
			todo.setNo(no);
			todo.setUser(user);
			todo.setTime(original.getTime());
			if (todo.getComplete() == null) {
				todo.setComplete(original.getComplete());
			}

			todoJpaRepository.save(todo);
			modelAndView.addObject("msg", "수정되었습니다.");
		}

		modelAndView.addObject("todo", todoJpaRepository.findById(no).get());

		return modelAndView;
	}

	@DeleteMapping("/todo/delete/{no}")
	@ResponseBody
	public Integer deleteMyTodo(HttpServletRequest request, @PathVariable Integer no) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		if (todoJpaRepository.findById(no).get().getUser().equals(user)) {
			todoJpaRepository.deleteById(no);

			return no;
		}

		return null;
	}
}
