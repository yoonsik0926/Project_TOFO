package com.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.main.MainServlet;
import com.member.SessionInfo;
import com.util.MyUtil;

import net.sf.json.JSONObject;

@WebServlet("/bbs/*")
public class BoardServlet extends MainServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session=req	.getSession();
		String cp=req.getContextPath();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String uri=req.getRequestURI();
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("countBoardLike.do")!=-1) {
			// �Խù� ���� ����
			countBoardLike(req, resp);
		} else if(uri.indexOf("insertBoardLike.do")!=-1) {
			// �Խù� ���� ����
			insertBoardLike(req, resp);
		} else if(uri.indexOf("insertReply.do")!=-1) {
			// ��� �߰�
			insertReply(req, resp);
		} else if(uri.indexOf("listReply.do")!=-1) {
			// ��� ����Ʈ
			listReply(req, resp);
		} else if(uri.indexOf("deleteReply.do")!=-1) {
			// ��� ����
			deleteReply(req, resp);
		} else if(uri.indexOf("insertReplyLike.do")!=-1) {
			// ��� ���ƿ�/�Ⱦ�� �߰�
			insertReplyLike(req, resp);
		} else if(uri.indexOf("countReplyLike.do")!=-1) {
			// ��� ���ƿ�/�Ⱦ�� ����
			countReplyLike(req, resp);
		} else if(uri.indexOf("insertReplyAnswer.do")!=-1) {
			// ����� ��� �߰�
			insertReplyAnswer(req, resp);
		} else if(uri.indexOf("listReplyAnswer.do")!=-1) {
			// ����� ��� ����Ʈ
			listReplyAnswer(req, resp);
		} else if(uri.indexOf("deleteReplyAnswer.do")!=-1) {
			// ����� ��� ����
			deleteReplyAnswer(req, resp);
		} else if(uri.indexOf("countReplyAnswer.do")!=-1) {
			// ����� ��� ����
			countReplyAnswer(req, resp);
		}

	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		int rows = 10;
		String srows = req.getParameter("rows");
		if(srows!=null) {
			rows = Integer.parseInt(srows);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "UTF-8");
		}
		
		int dataCount;
		if(keyword.length()==0)
			dataCount = dao.dataCount();
		else
			dataCount = dao.dataCount(condition, keyword);
		
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page)
			current_page = total_page;
		
		int offset = (current_page-1)*rows;
		if(offset<0) offset = 0;
		
		List<BoardDTO> list;
		if(keyword.length()==0)
			list = dao.listBoard(offset, rows);
		else
			list = dao.listBoard(offset, rows, condition, keyword);
		
		int listNum, n=0;
		for(BoardDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query="rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
		         +URLEncoder.encode(keyword, "UTF-8");
		}
		
		String listUrl = cp+"/bbs/list.do?"+query;
		String articleUrl = cp+"/bbs/article.do?page="+current_page+"&"+query;
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		// list.jsp�� ������
		forward(req, resp, "/WEB-INF/views/bbs/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req	.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();

		dto.setUserId(info.getUserId());
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		dao.insertBoard(dto);
		
		String cp=req.getContextPath();
		resp.sendRedirect(cp+"/bbs/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "UTF-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
		           +URLEncoder.encode(keyword, "UTF-8");
		}
		
		BoardDAO dao=new BoardDAO();
		dao.updateHitCount(num);
		
		BoardDTO dto=dao.readBoard(num);
		if(dto==null) {
			String cp=req.getContextPath();
			resp.sendRedirect(cp+"/bbs/list.do?"+query);
			return;
		}
		
		BoardDTO preDto = dao.preReadBoard(num, condition, keyword);
		BoardDTO nextDto = dao.nextReadBoard(num, condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preDto", preDto);
		req.setAttribute("nextDto", nextDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		
		forward(req, resp, "/WEB-INF/views/bbs/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req	.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		BoardDAO dao=new BoardDAO();
		BoardDTO dto=dao.readBoard(num);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/bbs/list.do?page="+page+"&rows="+rows);
			return;
		}
		
		if(! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/bbs/list.do?page="+page+"&rows="+rows);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		BoardDAO dao=new BoardDAO();
		BoardDTO dto=new BoardDTO();
		
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setUserId(info.getUserId());
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		dao.updateBoard(dto);
		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		String url=cp+"/bbs/list.do?page="+page+"&rows="+rows;
		
		resp.sendRedirect(url);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "UTF-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
		           +URLEncoder.encode(keyword, "UTF-8");
		}
		
		BoardDAO dao=new BoardDAO();
		dao.deleteBoard(num, info.getUserId());
		
		resp.sendRedirect(cp+"/bbs/list.do?"+query);
	}
	
	private void countBoardLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ���� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		int countBoardLike=dao.countBoardLike(num);
		JSONObject job=new JSONObject();
		job.put("countBoardLike", countBoardLike);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}
	
	private void insertBoardLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ���� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String state="false";
		int num = Integer.parseInt(req.getParameter("num"));

		int result=dao.insertBoardLike(num, info.getUserId());
		if(result==1)
			state="true";
		
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}
	
	private void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� ����Ʈ - AJAX:TEXT
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNo = req.getParameter("pageNo");
		int current_page = 1;
		if (pageNo != null)
			current_page = Integer.parseInt(pageNo);

		int rows = 5;
		int total_page = 0;
		int replyCount = 0;

		replyCount = dao.dataCountReply(num);
		total_page = util.pageCount(rows, replyCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;

		// ����Ʈ�� ����� ������
		List<ReplyDTO> listReply = dao.listReply(num, offset, rows);

		// ���͸� <br>
		for(ReplyDTO dto:listReply) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}

		// ����¡ ó��(�μ�2�� ¥���� �ڹٽ�ũ��Ʈ listPage(page) �Լ� ȣ��)
		String paging = util.paging(current_page, total_page);

		req.setAttribute("listReply", listReply);
		req.setAttribute("pageNo", current_page);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);

		// ������
		forward(req, resp, "/WEB-INF/views/bbs/listReply.jsp");
	}

	private void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� �Ǵ� ���  ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		ReplyDTO dto = new ReplyDTO();
		
		int num = Integer.parseInt(req.getParameter("num"));
		dto.setNum(num);
		dto.setUserId(info.getUserId());
		dto.setContent(req.getParameter("content"));
		String answer=req.getParameter("answer");
		if(answer!=null)
			dto.setAnswer(Integer.parseInt(answer));

		String state="false";
		int result=dao.insertReply(dto);
		if(result==1)
			state="true";
		
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}

	private void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� �Ǵ� ��� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int replyNum = Integer.parseInt(req.getParameter("replyNum"));
		
		String state="false";
		int result=dao.deleteReply(replyNum, info.getUserId());
		if(result==1)
			state="true";
		
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}

	private void insertReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��� ���ƿ� / �Ⱦ�� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		int replyNum = Integer.parseInt(req.getParameter("replyNum"));
		int replyLike = Integer.parseInt(req.getParameter("replyLike"));

		ReplyDTO dto = new ReplyDTO();
		
		dto.setReplyNum(replyNum);
		dto.setUserId(info.getUserId());
		dto.setReplyLike(replyLike);
		
		String state="false";
		int result=dao.insertReplyLike(dto);
		if(result==1)
			state="true";
		
		JSONObject job=new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}

	private void countReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��� ���ƿ� / �Ⱦ�� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		int replyNum = Integer.parseInt(req.getParameter("replyNum"));
		
		Map<String, Integer> map=dao.countReplyLike(replyNum);
		int likeCount=0;
		int disLikeCount=0;
		
		if(map.containsKey("likeCount"))
			likeCount=map.get("likeCount");
		
		if(map.containsKey("disLikeCount"))
			disLikeCount=map.get("disLikeCount");
		
		JSONObject job=new JSONObject();
		job.put("likeCount", likeCount);
		job.put("disLikeCount", disLikeCount);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString() );
	}

	private void insertReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��� ���� - AJAX:JSON
		insertReply(req, resp);
	}

	private void listReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ������ ��� ����Ʈ - AJAX:TEXT
		BoardDAO dao = new BoardDAO();
		
		int answer = Integer.parseInt(req.getParameter("answer"));
		
		List<ReplyDTO> listReplyAnswer = dao.listReplyAnswer(answer);

		// ���͸� <br>(��Ÿ�� => style="white-space:pre;")
		for(ReplyDTO dto:listReplyAnswer) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}
		
		req.setAttribute("listReplyAnswer", listReplyAnswer);

		forward(req, resp, "/WEB-INF/views/bbs/listReplyAnswer.jsp");
	}

	private void deleteReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� ��� ���� - AJAX:JSON
		deleteReply(req, resp);
	}
	
	private void countReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ������ ��� ���� - AJAX:JSON
		BoardDAO dao = new BoardDAO();
		
		int answer = Integer.parseInt(req.getParameter("answer"));
		
		int count=dao.dataCountReplyAnswer(answer);
		
		JSONObject job=new JSONObject();
		job.put("count", count);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print(job.toString());
	}
	
}
