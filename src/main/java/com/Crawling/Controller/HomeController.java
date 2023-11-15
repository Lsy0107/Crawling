package com.Crawling.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Crawling.Service.CrawService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private CrawService csvc;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("이동");
		
		return "home";
	}
	
	@RequestMapping(value="olive")
	public ModelAndView olive() throws IOException {
		ModelAndView mav = new ModelAndView();
		System.out.println("Controller - 올리브영");
		ArrayList<HashMap<String,String>>prdList = csvc.getOliveRankItem();
		
		mav.addObject("prdList",prdList);
		mav.setViewName("OliveRank"); //OliveBest.jsp
		
		return mav;
		
	}
	@RequestMapping(value="/prdSearch")
	public ModelAndView prdSearch(String searchText) throws IOException {
		System.out.println("CONTROLLER - /prdSearch");
		ModelAndView mav = new ModelAndView();
		System.out.println("searchTesxt : "+searchText);
		ArrayList<HashMap<String,String>> prdList_coopang = csvc.getPrdList_coopang(searchText);
		mav.addObject("prdList_coopang",prdList_coopang);
		
		ArrayList<HashMap<String,String>> prdList_gmarket = csvc.getPrdList_gmarket(searchText);
		mav.addObject("prdList_gmarket",prdList_gmarket);
		
		ArrayList<HashMap<String,String>> prdList_11st = csvc.getPrdList_11st(searchText);
		mav.addObject("prdList_11st",prdList_11st);
		
		mav.setViewName("PrdSearchResult"); //PrdSearchResult.jsp
		
		return mav;
	}
	
}
