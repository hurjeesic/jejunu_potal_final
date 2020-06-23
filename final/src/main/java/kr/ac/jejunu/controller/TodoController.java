package kr.ac.jejunu.controller;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.TodoNumber;
import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public ModelAndView updateMyTodo(HttpServletRequest request, @PathVariable Integer no, @ModelAttribute Todo todo, @Nullable @RequestParam MultipartFile image, @Nullable @RequestParam MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView("redirect:/todo/confirm/" + no);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		Todo original = todoJpaRepository.findById(no).get();
		if (original.getUser().equals(user)) {
			todo.setNo(no);
			todo.setUser(user);
			todo.setTime(original.getTime());
			if (todo.getComplete() == null) {
				todo.setComplete(original.getComplete());
			}

			if (image != null && !image.getOriginalFilename().equals("")) {
				todo.setImageName(image.getOriginalFilename());
				uploadFile(request, no, "image", original.getImageName(), image);
			}
			else if (todo.getImageName() != null) {
				todo.setImageName(original.getImageName());
			}

			if (file != null && !file.getOriginalFilename().equals("")) {
				todo.setFileName(file.getOriginalFilename());
				uploadFile(request, no, "result", original.getFileName(), file);
			}
			else if (todo.getFileName() != null) {
				todo.setFileName(original.getFileName());
			}

			todoJpaRepository.save(todo);
			modelAndView.addObject("msg", "수정되었습니다.");
		}

		modelAndView.addObject("todo", todoJpaRepository.findById(no).get());

		return modelAndView;
	}

	private void uploadFile(HttpServletRequest request, Integer no, String directory, String originalFileName, MultipartFile file) {
		File newFile;
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			// 폴더가 없으면 생성
			newFile = new File(request.getServletContext().getRealPath("/") + "/WEB-INF/static/" + directory + "/" + no);
			newFile.mkdir();

			// 기존 파일 삭제
			originalFileName = no + "/" + originalFileName;
			newFile = new File(request.getServletContext().getRealPath("/") + "/WEB-INF/static/" + directory + "/" + originalFileName);
			newFile.delete();

			// 새로운 파일 삽입
			String fileName = no + "/" + file.getOriginalFilename();
			newFile = new File(request.getServletContext().getRealPath("/") + "/WEB-INF/static/" + directory + "/" + fileName);
			fileOutputStream = new FileOutputStream(newFile);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(file.getBytes());
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
		catch (RuntimeException re) {
			re.printStackTrace();
		}
		finally {
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
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
