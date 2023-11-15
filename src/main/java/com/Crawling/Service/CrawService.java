package com.Crawling.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class CrawService {

	public ArrayList<HashMap<String, String>> getOliveRankItem() throws IOException {
		System.out.println("Service - 올리브영 랭킹 아이템 수집 기능 호출");
		// Jsoup
		//1. https://www.oliveyoung.co.kr/store/main/getBestList.do 접속
		String oliveRankUrl = "https://www.oliveyoung.co.kr/store/main/getBestList.do";
		//2. 랭킹 페이지 문서 리턴 <HTML> ~ <HTML>
		Document oliveRankDoc = Jsoup.connect(oliveRankUrl).get();
//		System.out.println(oliveRankDoc);
		
		//3. 필요한 정보가 있는 부분(태그,요소) 선택<CSS선택자>
		Elements itemsDiv = oliveRankDoc.select("div.TabsConts"); //div선택
		
		Elements items = itemsDiv.get(0).select("ul.cate_prd_list li");//0번 인덱스 지정후 ul태그 중 cate_prd_list 안에 li태그 선택
//		System.out.println(items.get(1));
//		System.out.println(items.size());
		
		
		/*리뷰 수 >> 상세페이지 이동 후 리뷰 조회*/
/*		System.out.println(items.get(1).select("div.prd_info>a").attr("href")); */
		
		
		//4. 데이터를 수집
		/*브랜드명, 상품이름, 상품가격, 상품이미지, 리뷰수 */
		ArrayList<HashMap<String,String>> prdList = new ArrayList<HashMap<String,String>>();
		
		for(int i = 0; i < items.size(); i++) {
			HashMap<String, String> prd_map = new HashMap<String, String>();
			String imgUrl = items.get(i).select("div.prd_info>a>img").attr("src");
			prd_map.put("prdImg", imgUrl);
			System.out.println("상품 이미지 : " + imgUrl);
			String brandName = items.get(i).select("span.tx_brand").text(); 
			prd_map.put("prdBrd", brandName);   
			System.out.println("브랜드명 : " + brandName); 
			String prdName = items.get(i).select("p.tx_name").text();
			prd_map.put("prdName", prdName);
			System.out.println("상품이름 : " + prdName);
			String prdPrice = items.get(i).select("span.tx_cur>span.tx_num").text();
			prd_map.put("prdPrice", prdPrice);
			System.out.println("상품가격 : " + prdPrice); 		  
			
			  
			// 상세페이지 URL
			String detailUrl = items.get(i).select("div.prd_info>a").attr("href");
			// 상세페이지 Document
			Document detatilDoc = Jsoup.connect(detailUrl).get();
			// #repReview > em			
			String reviewCount = detatilDoc.select("#repReview > em").text();
			reviewCount = reviewCount.replace("(", "").replace(")", "").replace(",", "");
			prd_map.put("prdRev", reviewCount);
			System.out.println("리뷰 수 : " + reviewCount);
			System.out.println(prd_map);
			prdList.add(prd_map);
		  }

		return prdList;
	}
/*
	public ArrayList<HashMap<String, String>> getprdList_11st(String searchText) throws IOException {
		System.out.println("SERVICE - getPrdList_11st()");
		//접속할 페이지 url
		//https://search.11st.co.kr/Search.tmall?kwd=KEYBOARD
		
		String connectUrl = "https://search.11st.co.kr/Search.tmall";
		HashMap<String,String> paramList = new HashMap<String,String>();
		paramList.put("kwd", searchText);
		
		Document targetPage = Jsoup.connect(connectUrl).data(paramList).get();
		// data("key","value");
		
		//SELENIUM
		System.out.println(targetPage.select("section.search_section"));	
		
		return null;
	}*/


	public ArrayList<HashMap<String, String>> getPrdList_coopang(String searchText) throws IOException {
		System.out.println("SERVICE - getPrdList_coopang()");
		//접속할 페이지 url
		//https://www.coupang.com/np/search?component=&q=keybaord&channel=user
		String connectUrl = "https://www.coupang.com/np/search";
		HashMap<String,String> paramList = new HashMap<String,String>();
		paramList.put("component","");
		paramList.put("q",searchText);
		paramList.put("channel", "user");
		Document targetPage = Jsoup.connect(connectUrl).data(paramList).cookie("auth","token").get();
		
		Elements items = targetPage.select("li.search-product");
		
//		System.out.println(targetPage.select("li.search-product"));
//		System.out.println(items.size());
		
		//상품이름, 상품가격 수집, 상세페이지URL 수집
		ArrayList<HashMap<String,String>> prdList_coopang = new ArrayList<HashMap<String,String>>();
		for(int i=0; i<items.size(); i++) { //Element item : items
			HashMap<String,String> prdMap = new HashMap<String,String>();
			prdMap.put("prdSite", "coopang");
			//상품이름
			String prdName = items.get(i).select("div.name").text();
							//item.select~~~
			prdMap.put("prdName", prdName);
			
			//상품가격
			String prdPrice = items.get(i).select("em.sale strong.price-value").text();
			prdPrice=prdPrice.replace(",", "");
			prdMap.put("prdPrice", prdPrice);
			
			//상세페이지 이동 URL
			String prdUrl = "https://www.coupang.com"+items.get(i).select("li.search-product>a").attr("href");
			prdMap.put("prdUrl", prdUrl);
//			System.out.println("상품이름 : "+prdName);
//			System.out.println("상품가격 : "+prdPrice);
//			System.out.println("url : "+prdUrl);
			
			String sortOption = "PRICE_DESC"; //PRICE_ASC; 경우에 따른 정렬
			//상품을 가격순 정렬 (높은 가격~낮은 가격순)
			int idx=-1; //prdList_coopang에 상품을 추가할 인덱스 번호
			for(int j=0; j<prdList_coopang.size(); j++) { //가격에 콤마(,)가 없어야 실행이 가능
				int prdPrice_int = Integer.parseInt(prdPrice); //목록에 추가할...아이템
				int listPrice = Integer.parseInt(prdList_coopang.get(j).get("prdPrice"));//ArrayList안에 채워져있던 상품의 가격
				
				boolean sortType = prdPrice_int > listPrice;
				switch(sortOption) {
				case "PRICE_DESC":
					sortType=prdPrice_int > listPrice;
					break;
				case "PRICE_ASC":
					sortType=prdPrice_int < listPrice;
				}
				if(sortType) {
					idx=j;
					break;
				}
			}
			
			if(idx >-1) { //idx가 -1보다 크면 인덱스를 지정해서 넣어주고
				prdList_coopang.add(idx,prdMap);				
			}else { //그게 아니라면 가장 마지막자리에 추가
				prdList_coopang.add(prdMap); 				
			}
			
			
			
			/*
			 ArrayList :: [0]키보드 [1]모니터 [2]마우스 [3]스피커....
			 ArrayList.add(2,"그래픽카드")
			 ArrayList :: [0]키보드 [1]모니터 [2]그래픽카드 [3]마우스 [4]스피커....
			 */
		}
		return prdList_coopang;
	}


	public ArrayList<HashMap<String, String>> getPrdList_gmarket(String searchText) throws IOException {
		System.out.println("SERVICE - getPrdList_gmarket()");
		
		String connectUrl = "https://browse.gmarket.co.kr/search";
		
		HashMap<String,String> paramList = new HashMap<String,String>();
		paramList.put("keyword", searchText);
		
		System.out.println(connectUrl);
		Document gmarket_Doc = Jsoup.connect(connectUrl).data(paramList).get();
		
		Elements items = gmarket_Doc.select("div.box__component-itemcard");
		
		//상품이름, 상품가격 수집, 상세페이지URL 수집
		ArrayList<HashMap<String,String>> gmarket_List = new ArrayList<HashMap<String,String>>();
		for(Element item : items) {
			HashMap<String,String> prdMap = new HashMap<String,String>();
			prdMap.put("prdSite", "gmarket");
			//상품이름
			String prdName = item.select("div.box__item-container div.box__item-title span.text__item").text();
			prdMap.put("GprdName", prdName);
//			System.out.println("G상품 이름 : "+prdName);
			
			//상품가격
			String prdPrice = item.select("div.box__item-container div.box__item-price div.box__price-seller strong.text__value").text();
			prdPrice = prdPrice.replace(",", "");
			prdMap.put("GprdPrice", prdPrice);
//			System.out.println("G상품가격 : "+prdPrice);
			
			//이동 url
			String prdUrl = item.select("a.link__item").attr("href");
			prdMap.put("GprdUrl", prdUrl);
//			System.out.println("G상품이동 URL : "+prdUrl);
			
			gmarket_List.add(prdMap);
		}

		
		return gmarket_List;
	}


	public ArrayList<HashMap<String, String>> getPrdList_11st(String searchText) {
		System.out.println("SERVICE - getPrdList_11st()");
		
		ChromeOptions chromeOptions = new ChromeOptions();
	    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//	    chromeOptions.addArguments("headless");//quit명령어 없어도 크롬창이 화면에 안보임
	    WebDriver driver = new ChromeDriver(chromeOptions);
	    String connectUrl = "https://search.11st.co.kr/Search.tmall?kwd="+searchText;
        driver.get(connectUrl);
        List<WebElement> items = driver.findElements(By.cssSelector("section.search_section>ul.c_listing>li>div.c_card"));
        System.out.println("items.size() : "+items.size());
        
        ArrayList<HashMap<String,String>> prdList_11st = new ArrayList<HashMap<String,String>>();
        
        for(WebElement item : items) {
        	HashMap<String,String> prdInfo = new HashMap<String,String>();
        	prdInfo.put("prdSite","11st");
        
        	String prdName = item.findElement(By.cssSelector("div.c_prd_name strong")).getText();
        	//  	System.out.println("prdName : "+prdName);
        	prdInfo.put("prdName",prdName);
        	
        	String prdUrl= item.findElement(By.cssSelector("div.c_prd_name a")).getAttribute("href");
        	//    	System.out.println("prdUrl : "+prdUrl);
        	prdInfo.put("prdUrl", prdUrl);
        	
        	String prdPrice = item.findElement(By.cssSelector("dd span.value")).getText();
        	// 	System.out.println("prdPrice : "+prdPrice);
        	prdPrice = prdPrice.replace(",", "");
        	prdInfo.put("prdPrice", prdPrice);

        	prdList_11st.add(prdInfo);
        }	
        
        driver.quit();
		return prdList_11st;
	}

}
