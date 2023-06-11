package com.example.sample4app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sample4app.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Controller
public class MemoController {

  @Autowired
  PersonRepository repository;

  @RequestMapping("/")
  public ModelAndView index(@ModelAttribute("formModel") Person Person,ModelAndView mav){
    mav.setViewName("index");
    mav.addObject("title", "Hello page");
    mav.addObject("msg","This is Sample4-SITE");
    Iterable<Person> list =repository.findAll();
    mav.addObject("data", list);
    return mav;
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  @Transactional
  public ModelAndView form(@ModelAttribute("formModel") Person Person,ModelAndView mav){
    repository.saveAndFlush(Person);
    return new ModelAndView("redirect:/");
  }
}
