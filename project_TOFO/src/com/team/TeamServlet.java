package com.team;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.schedule.ScheduleDAO;
import com.util.FileManager;

@WebServlet("/team/*")
public class TeamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + File.separator + "uploads" + File.separator + "photo" + File.separator + "team";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}

		if (uri.indexOf("list.do") != -1) {
			teamList(req, resp);
		} else if (uri.indexOf("insert.do") != -1) {
			insertSubmit(req, resp);
		} else if (uri.indexOf("updateRank.do") != -1) {
//			updateRank(req, resp);
		} else if (uri.indexOf("deleteTeamList.do") != -1) {
//			deleteTeamList(req, resp);
		}
	}

	/**
	 * 모임 목록 불러오기
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void teamList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		TeamDAO dao = new TeamDAO();
		ScheduleDAO scheDao = new ScheduleDAO();
		//이번주 내 일정 가져오기
		
		
		
		
		
		
		
		
		
		
		
		
		
		//모임목록 가져오기
		List<TeamDTO> list = dao.listTeam(info.getUserId());
		
		req.setAttribute("list", list);

		forward(req, resp, "/WEB-INF/views/main/myMain.jsp");
	}

	/**
	 * 모임 추가하기
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void insertSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String cp = req.getContextPath();
		String encType = "UTF-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());
		
		TeamDAO dao = new TeamDAO();
		TeamDTO dto = new TeamDTO();
		// 이미지 파일을 업로드 한경우

		dto.setTitle(mreq.getParameter("title"));
		dto.setContent(mreq.getParameter("content"));
		dto.setUserId(info.getUserId());
		dto.setUserCount(Integer.parseInt(mreq.getParameter("userCount")));
		if (mreq.getFile("imageFilename") == null) {
			dto.setImageFilename("");
		} else {
			// 서버에 저장된 파일명
			String saveFilename = mreq.getFilesystemName("imageFilename");

			// 파일이름변경
			saveFilename = FileManager.doFilerename(pathname, saveFilename);

			dto.setImageFilename(saveFilename);
		}

		// 저장
		dao.insertTeam(dto);
		resp.sendRedirect(cp + "/main/myMain.do");

	}


	protected void weekList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		if (info == null) {
			resp.sendRedirect(cp);
			return;
		}
		
	
		//날짜 계산
	}
	
}