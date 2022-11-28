package com.seed.lib.admin.book;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.seed.lib.book.BookVO;

@Mapper
public interface AdminBookMapper {
	
	// 도서 제본 신청
	public int setBoCart(BookPrintVO bookPrintVO)throws Exception;
	
	// 도서 목록 조회
	public List<BookVO> getAdBookList()throws Exception;

	// 도서 등록
	public int setAdBookAdd(BookVO bookVO)throws Exception;
	
}
