package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileupload.FileUtil;

/**
 * Servlet implementation class DownloadController
 */
@WebServlet("/mvcboard/download.do")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ofile = req.getParameter("ofile");
		String sfile = req.getParameter("sfile");
		String idx = req.getParameter("idx");
		
		FileUtil.download(req, resp, "/Uploads", sfile, ofile);
		
		MVCBoardDAO dao = new MVCBoardDAO();
		dao.downCountPlus(idx);
		dao.close();
	}
}