package kr.ac.jejunu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RequestMapping
public class ExceptionController {
	@ExceptionHandler(Exception.class)
	public ModelAndView error(Exception e) {
		ModelAndView modelAndView = new ModelAndView("error");

		modelAndView.addObject("e", e);

		return modelAndView;
	}
}
