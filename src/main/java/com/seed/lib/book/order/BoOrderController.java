package com.seed.lib.book.order;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seed.lib.member.MemberService;
import com.seed.lib.member.MemberVO;
import com.seed.lib.mypage.MypageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/print/*")
@Slf4j
public class BoOrderController {
	
	@Autowired
	private BoOrderService boOrderService;
	
	@Autowired
	private MypageService mypageService;
	
	@PostMapping("boOrder")
	@ResponseBody
	public String setBoOrder()throws Exception{
		return "print/boOrder";
	}
	
	// 바구니 제본신청 목록
	@GetMapping("boCart")
	public ModelAndView getBoCartList(HttpSession session)throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		BookPrintVO bookPrintVO = new BookPrintVO();
		
		MemberVO memberVO = new MemberVO();
		SecurityContextImpl context = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authentication = context.getAuthentication();
		memberVO  = (MemberVO)authentication.getPrincipal();
		memberVO = mypageService.getMyPage(memberVO);
		log.info("mvo:{}",memberVO);
		
		
//		bookPrintVO.setUserName(memberVO.getUserName());
		
		memberVO = boOrderService.getBoCartList(memberVO);
		
		mv.addObject("list", memberVO);
		mv.setViewName("print/boCart");
		
		return mv;
	}
	
	// 도서 바구니 추가	
	@PostMapping("boAdCart")
	@ResponseBody
	public int setBoAdCart(String imp_uid, 
							  String merchant_uid, 
							  String printNum,
							  String amount,
							  String userName)throws Exception{
		
		BookPrintVO bookPrintVO = new BookPrintVO();	
		bookPrintVO.setImp_uid(imp_uid);
		bookPrintVO.setMerchant_uid(merchant_uid);
		bookPrintVO.setCaNum(Long.parseLong(printNum));
		bookPrintVO.setCaAmount(Long.parseLong(amount));
		bookPrintVO.setUserName(userName);
		
		int result = boOrderService.setBoCart(bookPrintVO);
		
		if(result > 0) {
			return result;
		}
		
		return result;
	}
	
}
