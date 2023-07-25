package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileupload.FileUtil;
import utils.JSFunction;

/**
 * Servlet implementation class EditController
 */
@WebServlet("/mvcboard/edit.do")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		MVCBoardDTO dto = dao.selectView(idx);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
		
		if( mr == null) {
			JSFunction.alertBack(resp, "첨부 파일이 제한 용량을 초과합니다.");
		}
	}
}