package kr.ac.jejunu.service;

import kr.ac.jejunu.entity.Todo;
import kr.ac.jejunu.entity.TodoNumber;
import kr.ac.jejunu.entity.User;
import kr.ac.jejunu.repository.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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

@Service
@RequiredArgsConstructor
public class TodoService {
	private final UserService userService;
	private final TodoJpaRepository todoJpaRepository;

	public Todo getTodo(Integer no) {
		return todoJpaRepository.findById(no).get();
	}

	public HashMap<String, TodoNumber> getDateTodoNumber(HttpSession session) {
		HashMap<String, TodoNumber> todoNumberMap = null;
		User user = userService.getUser(session);
		List<Todo> todoList = todoJpaRepository.findAllByUser(user);
		if (todoList != null) {
			todoNumberMap = new HashMap<>();
			for (Todo todo : todoList) {
				Calendar todoDate = todo.getTime();
				String monthStr = (todoDate.get(Calendar.MONTH) < 9 ? "0" : "") + (todoDate.get(Calendar.MONTH) + 1);
				String dateStr = todoDate.get(Calendar.YEAR) + "-" + monthStr + "-" + todoDate.get(Calendar.DATE);
				if (todoNumberMap.get(dateStr) == null) {
					if (todo.getComplete()) {
						todoNumberMap.put(dateStr, TodoNumber.builder().total(1).process(0).complete(1).build());
					}
					else {
						todoNumberMap.put(dateStr, TodoNumber.builder().total(1).process(1).complete(0).build());
					}
				}
				else {
					TodoNumber todoNumber = todoNumberMap.get(dateStr);
					todoNumber.setTotal(todoNumber.getTotal() + 1);
					if (todo.getComplete()) {
						todoNumber.setComplete(todoNumber.getComplete() + 1);
					}
					else {
						todoNumber.setProcess(todoNumber.getProcess() + 1);
					}

					todoNumberMap.put(dateStr, todoNumber);
				}
			}
		}

		return todoNumberMap;
	}

	public List<Todo> getDayTodoList(HttpSession session, String date) throws ParseException {
		User user = userService.getUser(session);
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

		return myDayTodoList;
	}

	public Todo insertTodo(HttpSession session, Todo todo) {
		User user = userService.getUser(session);

		todo.setUser(user);

		return todoJpaRepository.save(todo);
	}

	public Todo updateTodo(HttpServletRequest request, Integer no, Todo todo, MultipartFile image, MultipartFile file) {
		HttpSession session = request.getSession();
		User user = userService.getUser(session);

		Todo original = todoJpaRepository.findById(no).get();
		Todo result = null;
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
			else if (todo.getImageName() == null) {
				todo.setImageName(original.getImageName());
			}

			if (file != null && !file.getOriginalFilename().equals("")) {
				todo.setFileName(file.getOriginalFilename());
				uploadFile(request, no, "result", original.getFileName(), file);
			}
			else if (todo.getFileName() == null) {
				todo.setFileName(original.getFileName());
			}

			result = todoJpaRepository.save(todo);
		}

		return result;
	}

	public Todo updateTodo(HttpSession session, Integer no) {
		Todo original = todoJpaRepository.findById(no).get();

		if (userService.getUser(session).equals(original.getUser())) {
			original.setComplete(!original.getComplete());

			return todoJpaRepository.save(original);
		}

		return null;
	}

	public Integer deleteTodo(HttpSession session, Integer no) {
		User user = userService.getUser(session);
		if (todoJpaRepository.findById(no).get().getUser().equals(user)) {
			todoJpaRepository.deleteById(no);

			return no;
		}

		return null;
	}

	public void uploadFile(HttpServletRequest request, Integer no, String directory, String originalFileName, MultipartFile file) {
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

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Integer no) throws IOException {
		String fileName = todoJpaRepository.findById(no).get().getFileName();
		Path path = Paths.get(request.getServletContext().getRealPath("/") + "/WEB-INF/static/result/" + no + "/" + fileName);
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\";");

		IOUtils.copy(Files.newInputStream(path), response.getOutputStream());
	}

	public String getTitleForUrl(HttpServletRequest request) {
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

		return title;
	}
}