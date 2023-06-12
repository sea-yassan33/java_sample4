package com.example.sample4app;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(@ModelAttribute Person Person, @PathVariable int id, ModelAndView mav){
    mav.setViewName("edit");
    mav.addObject("title", "edit Person.");
    Optional<Person> data = repository.findById((long)id);
    mav.addObject("formModel", data.get());
    return mav;
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  @Transactional
  public ModelAndView update(@ModelAttribute Person Person, ModelAndView mav){
    repository.saveAndFlush(Person);
    return new ModelAndView("redirect:/");
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public ModelAndView delete(@PathVariable int id, ModelAndView mav){
    mav.setViewName("delete");
    mav.addObject("title", "Delte page");
    mav.addObject("msg","Can I delete this record?");
    Optional<Person> data = repository.findById((long)id);
    mav.addObject("formModel", data.get());
    return mav;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @Transactional
  public ModelAndView remove(@RequestParam long id, ModelAndView mav){
    repository.deleteById(id);
    return new ModelAndView("redirect:/");
  }
}
